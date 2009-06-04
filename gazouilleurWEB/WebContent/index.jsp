<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:rich="http://richfaces.org/rich"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:a4j="http://richfaces.org/a4j"
      xmlns:f="http://java.sun.com/jsf/core">
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta content="fr-fr" http-equiv="Content-Language" />

<!-- 
	Sommaire 
entete de la page
	menu de connexion
		lien de connexion affichant la popup de connexion
		lien de deconnexion
		lien d'inscription affichant la popup d'inscription
		popup de connexion
		popup de d'inscription
corps de la page
	onglet principal
	onglet suiveur
		ajout d'un suivi
		liste des suivis
		liste des suiveurs
		poll d'actualisation de la liste des suiveurs
	-- onglets manquants --

popup d'expiration de session
poll de gestion d'expiration de session

-->

<title>Gazouilleur</title>
<link href="<%=request.getContextPath()%>/resources/stylesheets/screen.css" media="screen, projection" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resources/stylesheets/master.css" media="screen, projection" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resources/stylesheets/connexionMenu.css" media="screen, projection" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resources/stylesheets/principale.css" media="screen, projection" rel="stylesheet" type="text/css" />
<style type="text/css">
body {
	background: #9ae4e8 url(resources/images/bg.gif) fixed no-repeat top left;
}
.content-bubble-arrow {
	background-image: url(resources/images/arr2.gif);
}
.status-btn input.round-btn {
	background-image: url('resources/images/round-btn.gif');
}
.status-btn input.round-btn:hover {
	background-image: url('resources/images/round-btn-hover.gif');
}
.status-btn input.disabled, .status-btn input.disabled:hover {
	background-image: url('resources/images/round-btn.gif');
}
.hentry .actions .fav {
	background-image: url('resources/images/icon_star_full.gif');
}

</style>
<style type="text/css">
		  #header {
			text-align: left;
			margin: 8px 0 0 0;
		  }
		  
	</style>
</head>
<body>
	<f:view>
	
	<!-- Debut entete de la page -->
	
	
	<!-- Debut menu de connexion -->
	<h:panelGroup id="connexionMenu">
	
		<!-- Debut lien de connexion affichant la popup de connexion -->
		<h:panelGroup id="panelGroupConnexion">
			<a4j:form>	
				<a4j:commandLink value="Se connecter" id="lienConnexion" rendered="#{membreControlleur.estConnecte == false}">
			        <rich:componentControl for="panel_connexion" attachTo="lienConnexion" operation="show" event="onclick"/>
			    </a4j:commandLink>
			</a4j:form>
		<!-- Fin lien de connexion -->
	    
		<!-- Debut lien de deconnexion -->
		    <a4j:form>
				<a4j:commandLink value="Se déconnecter" id="lienDeconnexion" 
					rendered="#{membreControlleur.estConnecte}" action="#{membreControlleur.deconnexion}"
					reRender="panelGroupConnexion,panelGroupInscription,tabPanel,panel_inscription,panel_connexion"/>
			</a4j:form>
		<!-- Fin lien de deconnexion -->
		</h:panelGroup>
		
		<!-- Debut lien d'inscription affichant la popup d'inscription-->
		<h:panelGroup id="panelGroupInscription">
			<a4j:form>
				<a4j:commandLink value="Rejoindre Gazouilleur!" id="lienInscription" rendered="#{membreControlleur.estConnecte ==  false}">
				     <rich:componentControl for="panel_inscription" attachTo="lienInscription" operation="show" event="onclick"/>
				</a4j:commandLink>
			</a4j:form>
		<!-- Fin lien d'inscription -->
		
		<!-- Debut lien de modification du compte affichant la popup associee-->
			<a4j:form>
				<a4j:commandLink value="Modifier ses infos" id="lienModifCompte" rendered="#{membreControlleur.estConnecte ==  true}">
				     <rich:componentControl for="panelModifCompte" attachTo="lienModifCompte" operation="show" event="onclick"/>
				</a4j:commandLink>
			</a4j:form>
		</h:panelGroup>
		<!-- Fin lien de modification du compte -->
	
		<!-- Debut popup de connexion -->
		<rich:modalPanel id="panel_connexion" autosized="true">
			<f:facet name="header">
	            <h:panelGroup>
	                <h:outputText value="Authentification"></h:outputText>
	            </h:panelGroup>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	                <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelinkConnexionPanel"/>
	                <rich:componentControl for="panel_connexion" attachTo="hidelinkConnexionPanel" operation="hide" event="onclick"/>
	            </h:panelGroup>
	        </f:facet>
	        <a4j:form id="formConnexion">
	        	<rich:messages layout="list" showSummary="true" style="color:Red;">
					<f:facet name="errorMarker">
						<h:graphicImage value="/resources/images/error.gif" style="padding-right:7px;" />   
					</f:facet>
		 		</rich:messages>        		
		        <h:panelGrid columns="2">
			     	<h:outputLabel id="loginLabel" for="login" value="Login" />
			     	<h:inputText id="login" value="#{membreControlleur.membre.pseudo}">
			     		<rich:ajaxValidator event="onblur" />
			     	</h:inputText>
			     	<h:outputLabel id="passwordLabel" for="password" value="Mot de passe"/>
			     	<h:inputSecret id="password" value="#{membreControlleur.membre.password}">
			     		<rich:ajaxValidator event="onblur" />
			     	</h:inputSecret>
			  		<f:facet name="footer">
		    			<h:panelGroup style="display:block; text-align:center">
							<a4j:commandButton value="Ok" id="formConnexionBouton"
								action="#{membreControlleur.connexion}" 
								oncomplete="if ('#{membreControlleur.closePanelConnexion}' == 'true') { Richfaces.hideModalPanel('panel_connexion'); }"
								reRender="panelGroupConnexion,panelGroupInscription,tabPanel,panelModifCompte">
							</a4j:commandButton>
						</h:panelGroup>
					</f:facet>
			  	</h:panelGrid>
		  	</a4j:form>
		</rich:modalPanel>
		<!-- Fin popup de connexion -->
		
		<!-- Debut popup d'inscription -->
		<rich:modalPanel id="panel_inscription" autosized="true">
			<f:facet name="header">
				<h:panelGroup>
		        	<h:outputText value="Inscription"></h:outputText>
		        </h:panelGroup>
		    </f:facet>
		    <f:facet name="controls">
		        <h:panelGroup>
		        	<h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelink"/>
		            <rich:componentControl for="panel_inscription" attachTo="hidelink" operation="hide" event="onclick"/>
				</h:panelGroup>
		    </f:facet>
		    <a4j:form id="formInscription" ajaxSubmit="true">
		 		<rich:messages layout="list" showSummary="true" style="color:Red;">
					<f:facet name="errorMarker">
						<h:graphicImage value="/resources/images/error.gif" style="padding-right:7px;"/>   
					</f:facet>
		 		</rich:messages>
		      	<h:panelGrid columns="2">
		    		<h:outputLabel id="pseudoLabel" for="pseudo" value="Pseudo" />
		    		<h:inputText id="pseudo" value="#{membreControlleur.membre.pseudo}" >
		    			<rich:ajaxValidator event="onsubmit" />
		    		</h:inputText>
		
		    		<h:outputLabel id="passwordLabel" for="password" value="Choisissez votre mot de passe" />
		    		<h:inputSecret id="password" value="#{membreControlleur.membre.password}" />
		
		    		<h:outputLabel id="password2Label" for="password2" value="Réinsérez votre mot de passe" />
		    		<h:inputSecret id="password2" value="#{membreControlleur.password2}" />
		
					<h:outputLabel id="nomLabel" for="nom" value="Nom" />
					<h:inputText id="nom" value="#{membreControlleur.membre.nom}">
		    			<rich:ajaxValidator event="onsubmit" />
		    		</h:inputText>
		
		   			<h:outputLabel id="prenomLabel" for="prenom" value="Prénom" />
		    		<h:inputText id="prenom" value="#{membreControlleur.membre.prenom}">
		    			<rich:ajaxValidator event="onsubmit" />
		    		</h:inputText>
		    		
		    		<h:outputLabel id="emailLabel" for="email" value="Email" />
		    		<h:inputText id="email" value="#{membreControlleur.membre.email}">
		    			<rich:ajaxValidator event="onsubmit" />
		    		</h:inputText>
		
		 			<f:facet name="footer">
		  				<h:panelGroup style="display:block; text-align:center">
		  				<!-- Modifier la valeur onclick pour ajouter le script de connexion -->
							<a4j:commandButton value="Ok" action="#{membreControlleur.creerMembre}" id="formInscriptionButton" 
								oncomplete="if('#{membreControlleur.closePanelInscription}' == 'true'){javascript:Richfaces.hideModalPanel('panel_inscription');}"
								reRender="panelGroupConnexion,panelGroupInscription,tabPanel,panelModifCompte"/>
						</h:panelGroup>
					</f:facet>
		 		</h:panelGrid>
			</a4j:form>
		</rich:modalPanel>
		<!-- Fin popup d'inscription -->
		
		<!-- Debut popup de modification du compte -->
		<rich:modalPanel id="panelModifCompte" autosized="true">
			<f:facet name="header">
				<h:panelGroup>
		        	<h:outputText value="Modification des informations"></h:outputText>
		        </h:panelGroup>
		    </f:facet>
		    <f:facet name="controls">
		        <h:panelGroup>
		        	<h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelinkModifCompte"/>
		            <rich:componentControl for="panelModifCompte" attachTo="hidelinkModifCompte" operation="hide" event="onclick"/>
				</h:panelGroup>
		    </f:facet>
		    <a4j:form id="formModifCompte" ajaxSubmit="true">
		 		<rich:messages layout="list" showSummary="true" style="color:Red;">
					<f:facet name="errorMarker">
						<h:graphicImage value="/resources/images/error.gif" style="padding-right:7px;"/>   
					</f:facet>
		 		</rich:messages>
		      	<h:panelGrid columns="2">
		    		<h:outputLabel id="pseudoLabelModif" for="pseudoModif" value="Pseudo" />
		    		<h:inputText id="pseudoModif" value="#{membreControlleur.membre.pseudo}" >
		    			<rich:ajaxValidator event="onsubmit" />
		    		</h:inputText>
		
		    		<h:outputLabel id="passwordLabel" for="password" value="Choisissez votre mot de passe" />
		    		<h:inputSecret id="password" value="#{membreControlleur.membre.password}" />
		
		    		<h:outputLabel id="password2Label" for="password2" value="Réinsérez votre mot de passe" />
		    		<h:inputSecret id="password2" value="#{membreControlleur.password2}" />
		
					<h:outputLabel id="nomLabel" for="nom" value="Nom" />
					<h:inputText id="nom" value="#{membreControlleur.membre.nom}">
		    			<rich:ajaxValidator event="onsubmit" />
		    		</h:inputText>
		
		   			<h:outputLabel id="prenomLabel" for="prenom" value="Prénom" />
		    		<h:inputText id="prenom" value="#{membreControlleur.membre.prenom}">
		    			<rich:ajaxValidator event="onsubmit" />
		    		</h:inputText>
		    		
		    		<h:outputLabel id="emailLabel" for="email" value="Email" />
		    		<h:inputText id="email" value="#{membreControlleur.membre.email}">
		    			<rich:ajaxValidator event="onsubmit" />
		    		</h:inputText>
		
		 			<f:facet name="footer">
		  				<h:panelGroup style="display:block; text-align:center">
							<a4j:commandButton value="Ok" action="#{membreControlleur.updateMembre}" id="formModifInfoButton" 
								oncomplete="if('#{membreControlleur.closePanelModifInfo}' == 'true'){javascript:Richfaces.hideModalPanel('panelModifCompte');}"/>
						</h:panelGroup>
					</f:facet>
		 		</h:panelGrid>
			</a4j:form>
		</rich:modalPanel>
		<!-- Fin popup de modification du compte -->
		
	</h:panelGroup>
	<!-- Fin menu de connexion -->
	
	<!-- Debut corps de la page -->
	<center style="height: 100%;">
		<div style="text-align: left; width: 70%; margin-top:20px;">
			<a href="/gazouilleurWEB/">
				<h:graphicImage value="/resources/images/gazouilleur.png" alt="Gazouilleur" height="30" width="250" />
			</a>
		</div>
		<rich:tabPanel style="margin-top:20px; width: 70%;" switchType="client" id="tabPanel">
		
		<!-- Debut onglet principal -->
	        <rich:tab label="Home" id="tabHome">
	        	<a4j:support event="ontabenter" action="#{membreControlleur.setOngletHomeActifToTrue}" reRender="poll"/>
				<a4j:support event="ontableave" action="#{membreControlleur.setOngletHomeActifToFalse}" reRender="poll"/>
	        	<rich:spacer height="15px" width="100%"/>
	        	
	        	<h:panelGroup rendered="#{membreControlleur.estConnecte == false}">
	        		<div style="padding-left: 20px; padding-right: 20px;">	
		        		<h1 style="font-size: 25px;">Qu'est ce que Gazouilleur ?</h1>
		        		<div style="text-align: center;">
		        			<h:graphicImage value="/resources/images/wind-up-bird.png" alt="oiseau" />
		        		</div>
		        		<p style="font-size: 16px;">
		        			Gazouilleur est un service pour les amis, la famille, et les collègues pour communiquer et rester connecter 
		        			grâce à des échanges de réponses rapides et fréquentes à une seule question simple : <strong>Que fais-tu ?</strong>
		        		</p>
		        		<a4j:form id="join_big">
			        		<a4j:commandLink value="Rejoindre Gazouilleur!" id="lienInscription2">
							     <rich:componentControl for="panel_inscription" attachTo="lienInscription2" operation="show" event="onclick"/>
							</a4j:commandLink>
						</a4j:form>
		        	</div>
	        	</h:panelGroup>
	        	
	        	<h:panelGroup rendered="#{membreControlleur.estConnecte == true}">
					<a4j:form id="message_form" style="text-align:center;">
						<rich:messages layout="list" showSummary="true" style="color:Red;" for="message_form">
							<f:facet name="errorMarker">
								<h:graphicImage value="/resources/images/error.gif" style="padding-right:7px;"/>   
							</f:facet>
				 		</rich:messages>
						<div style="text-align: right; padding-right: 20px;">
							<h:outputText value="140" id="nbCarMessage" style="color: silver; font-size: 2.5em;" /><h:outputText value=" caractères restants" style="color: silver; font-size: 2em;" />
						</div>
						<h:inputTextarea id="message_text_area" style=" width : 95%;" 
							value="#{membreControlleur.messagePublic}"
							onkeyup="document.getElementById('message_form:nbCarMessage').innerHTML = (140 - this.textLength);" 
							onkeypress="if(this.textLength > 139) this.value=this.value.substr(0,139);"/>
						<a4j:commandButton id="message_bouton_envoyer" value="Envoyer" style="margin:5px;"
							action="#{membreControlleur.publierMessagePublic}"
							oncomplete="document.getElementById('message_form:message_text_area').value='';document.getElementById('message_form:nbCarMessage').innerHTML = '140'"
							reRender="listeMessagesPerso,listeMessagesPublics"/>
						<a4j:commandButton id="message_bouton_effacer" value="Effacer" style="margin:5px;" 
							onclick="document.getElementById('message_form:message_text_area').value='';document.getElementById('message_form:nbCarMessage').innerHTML = '140'" 
							immediate="true" ajaxSingle="true"/>	
					</a4j:form>
					<a4j:region>
				        <a4j:form>
				            <a4j:poll id="poll" interval="5000" limitToList="true"
				            	enabled="#{membreControlleur.ongletHomeActif == true}"
				                action="#{membreControlleur.recupererMessagesPublics}" 
				                reRender="poll,listeMessagesPublics" />
				        </a4j:form>
				    </a4j:region>
			        <rich:spacer height="25px" width="100%"/>
					
					<rich:tabPanel switchType="client" id="tabPanelMessagesPublics">
						<rich:tab label="Tous" id="tabMessagesPublicsTous">
							<h:panelGroup id="listeMessagesPublics">
								<a4j:form>
									<rich:spacer height="30" />
									<rich:datascroller align="left" for="messagesPublicsTable" maxPages="50"
	            						id="messagesPublicsTableScroller1" reRender="messagesPublicsTableScroller2"
	            						page="#{membreControlleur.pageMessagesPublicsTableScroller}"/>
	            					<rich:spacer height="10" />
									<rich:dataTable value="#{membreControlleur.messagesPublics}" 
									var="messagePublic" width="100%" border="0" 
									rows="10" id="messagesPublicsTable">
										<h:column>
											<rich:simpleTogglePanel switchType="client" label="#{messagePublic.emetteur.pseudo} - #{messagePublic.formatDate}" style="margin:10px 10px 10px 10px;">
											    <h:outputText value="#{messagePublic.message}" />             
											</rich:simpleTogglePanel>
										</h:column>
									</rich:dataTable>
									<rich:spacer height="10" />
									<rich:datascroller align="left" for="messagesPublicsTable" maxPages="50"
	            						id="messagesPublicsTableScroller2" reRender="messagesPublicsTableScroller1" 
	            						page="#{membreControlleur.pageMessagesPublicsTableScroller}"/>
	            					<rich:spacer height="30" />
								</a4j:form>
							</h:panelGroup>
						</rich:tab>
						<rich:tab label="Moi" id="tabMessagesPublicsMoi">
							<h:panelGroup id="listeMessagesPerso">
								<a4j:form>
									<rich:spacer height="30" />
									<rich:datascroller align="left" for="messagesPersosTable" maxPages="50"
	            						id="messagesPersosTableScroller1" reRender="messagesPublicsTableScroller2"
	            						page="#{membreControlleur.pageMessagesPersosTableScroller}"/>
	            					<rich:spacer height="10" />
									<rich:dataTable value="#{membreControlleur.messagesPerso}" 
									var="messagePerso" width="100%" border="0" 
									rows="10" id="messagesPersosTable">
										<h:column>
											<rich:simpleTogglePanel switchType="client" label="#{messagePerso.emetteur.pseudo} - #{messagePerso.formatDate}" style="margin:10px 10px 10px 10px;">
											    <h:outputText value="#{messagePerso.message}" />             
											</rich:simpleTogglePanel>
										</h:column>
									</rich:dataTable>
									<rich:spacer height="10" />
									<rich:datascroller align="left" for="messagesPersosTable" maxPages="50"
	            						id="messagesPersosTableScroller2" reRender="messagesPersosTableScroller1" 
	            						page="#{membreControlleur.pageMessagesPersosTableScroller}"/>
	            					<rich:spacer height="30" />
								</a4j:form>
							</h:panelGroup>
						</rich:tab>
						<rich:tab label="Recherche" id="tabMessagesPublicsRecherche">
							<rich:spacer height="15px" width="100%"/>
							<a4j:form id="recherche_public_form" style="text-align:center;">
								<h:outputLabel for="recherche_public_text_area" value="Saisissez un ou plusieurs mots clés : " style="margin:7px;" />
								<h:inputText id="recherche_public_text_area" style=" width : 20%;margin:7px;"
									value="#{membreControlleur.motsClesRecherche}"/>
								<a4j:commandButton id="recherche_public_bouton_envoyer" value="Envoyer" style="margin:5px;"
									action="#{membreControlleur.rechercherMessagesPublics}"
									reRender="listeMessagesRecherche"/>
							</a4j:form>
							<h:panelGroup id="listeMessagesRecherche">
								<a4j:form>
									<rich:spacer height="30" />
									<rich:datascroller align="left" for="messagesPublicsRechercheTable" maxPages="50"
	            						id="messagesPublicsRechercheTableScroller1" reRender="messagesPublicsRechercheTableScroller2"
	            						page="#{membreControlleur.pageMessagesPublicsRechercheTableScroller}"
	            						renderIfSinglePage="false" />
	            					<rich:spacer height="10" />
									<rich:dataTable value="#{membreControlleur.messagesPublicsRecherche}" 
									var="messagePublicRecherche" width="100%" border="0" 
									rows="10" id="messagesPublicsRechercheTable">
										<h:column>
											<rich:simpleTogglePanel switchType="client" label="#{messagePublicRecherche.emetteur.pseudo} - #{messagePublicRecherche.formatDate}" style="margin:10px 10px 10px 10px;">
											    <h:outputText value="#{messagePublicRecherche.message}" />             
											</rich:simpleTogglePanel>
										</h:column>
									</rich:dataTable>
									<rich:spacer height="10" />
									<rich:datascroller align="left" for="messagesPublicsRechercheTable" maxPages="50"
	            						id="messagesPublicsRechercheTableScroller2" reRender="messagesPublicsRechercheTableScroller1" 
	            						page="#{membreControlleur.pageMessagesPublicsRechercheTableScroller}"
	            						renderIfSinglePage="false" />
	            					<rich:spacer height="30" />
								</a4j:form>
							</h:panelGroup>
						</rich:tab>
					</rich:tabPanel>
				</h:panelGroup>
				<rich:spacer height="15px" width="100%"/>
	        </rich:tab>
	    <!-- Fin onglet principal -->
	    
	    <!-- Debut onglet suiveur -->
	        <rich:tab label="Suiveurs/Suivis" id="tabSuiveurs" disabled="#{membreControlleur.estConnecte ==  false}">
	        	<a4j:support event="ontabenter" action="#{membreControlleur.setOngletSuiveurActifToTrue}" reRender="pollSuivis,pollSuiveurs"/>
				<a4j:support event="ontableave" action="#{membreControlleur.setOngletSuiveurActifToFalse}" reRender="pollSuivis,pollSuiveurs"/>
				<style>
					.cur { cursor: pointer; }
				</style>
				<rich:spacer height="15px" width="100%"/>
			<center>
		
			<!-- Debut ajout d'un suivi -->
				<a4j:form id="ajoutSuiviForm">
					<rich:messages layout="list" showSummary="true" style="color:Red;" for="ajoutSuiviForm">
						<f:facet name="errorMarker">
							<h:graphicImage value="/resources/images/error.gif" style="padding-right:7px;"/>   
						</f:facet>
			 		</rich:messages>
			    	<h:outputLabel id="ajoutSuiviLabel" for="ajoutSuivi" value="Suivez un ami" />
				    <rich:comboBox id="ajoutSuivi" value="#{membreControlleur.ajoutSuivi}"
				    	suggestionValues="#{membreControlleur.listeMembres}"
				    	directInputSuggestions="true" >
				    	<a4j:support event="onfocus" action="#{membreControlleur.setSuivisPollEnabledToFalse}" reRender="pollSuivis,pollSuiveurs"/>
				    	<a4j:support event="onblur" action="#{membreControlleur.setSuivisPollEnabledToTrue}" reRender="pollSuivis,pollSuiveurs"/>
				    </rich:comboBox> 
				    <rich:spacer height="15px" width="100%"/>
				   	<a4j:commandButton action="#{membreControlleur.ajouterAmi}" value="Suivre" 
				   		reRender="suivisTable"/>
				</a4j:form>
			<!-- Fin ajout d'un suivi -->
			
			<rich:spacer height="15px" width="100%"/>
			
			<!-- Debut poll d'actualisation de la liste des membre -->
				<a4j:region>
			        <h:form>
			            <a4j:poll id="pollSuivis" interval="5000" limitToList="true"
			            	enabled="#{membreControlleur.ongletSuiveurActif && membreControlleur.suivisPollEnabled}"
			            	action="#{membreControlleur.listerMembres}"
			                reRender="pollSuivis,ajoutSuivi" />
			        </h:form>
			    </a4j:region>
			    
			    <!-- Fin poll d'actualisation de la liste des membres -->
				
			<!-- Debut liste des suivis -->
				
				<!-- Debut menu contextuel des suivis -->
				<a4j:form>
					<rich:contextMenu attached="false" id="suivisMenu" submitMode="ajax">
						<rich:menuItem>
							Ne plus suivre {pseudo}
							<a4j:actionparam assignTo="#{membreControlleur.supprSuivi}" value="{pseudo}" />
							<a4j:support action="#{membreControlleur.supprimerAmi}" event="oncomplete"
								reRender="suivisTable" requestDelay="500"/>
						</rich:menuItem>
					</rich:contextMenu>
				</a4j:form>
				<!-- Fin menu contextuel des suivis -->
				
				<h:panelGrid columns="2" style="width:100%;">
				
				<!-- Debut liste des suivis -->
					<rich:dataTable id="suivisTable" value="#{membreControlleur.membre.listSuivis}"
						var="suivi" rowClasses="cur">
						<f:facet name="header">
							<rich:columnGroup>
								<rich:column>
									Suivis
								</rich:column>
							</rich:columnGroup>
						</f:facet>
						<rich:column>
							<h:outputText value="#{suivi.pseudo}" />
						</rich:column>
						<rich:componentControl event="onRowClick" for="suivisMenu" operation="show">
							<f:param value="#{suivi.pseudo}" name="pseudo"/>
						</rich:componentControl>
					</rich:dataTable>
				<!-- Fin liste des suivis -->
				
				<!-- Debut liste des suiveurs -->
					<rich:dataTable id="suiveursTable" value="#{membreControlleur.membre.listSuiveurs}"
						var="suiveur">
						<f:facet name="header">
							<rich:columnGroup>
								<rich:column>
									Suiveurs
								</rich:column>
							</rich:columnGroup>
						</f:facet>
						<rich:column>
							<h:outputText value="#{suiveur.pseudo}" />
						</rich:column>
					</rich:dataTable>
				<!-- fin liste des suiveurs -->
				
				</h:panelGrid>
				
				<!-- Debut poll d'actualisation de la liste des suiveurs -->
				<a4j:region>
			        <h:form>
			            <a4j:poll id="pollSuiveurs" interval="5000" limitToList="true"
			            	enabled="#{membreControlleur.ongletSuiveurActif && membreControlleur.suivisPollEnabled}"
			            	action="#{membreControlleur.listerSuiveurs}"
			                reRender="pollSuiveurs,suiveursTable" />
			        </h:form>
			    </a4j:region>
			    
			    <!-- Fin poll d'actualisation de la liste des suiveurs -->
			    
			</center>
			<p style="text-align: left; padding-left: 20px;"><i>Cliquez sur un membre suivi pour ne plus le suivre.</i></p>
			
			<rich:spacer height="15px" width="100%"/>
	        </rich:tab>
	        <!-- Fin onglet suiveurs -->
	        
	        <!-- Debut onglet messages prives -->
	        <rich:tab label="Messages" id="tabMessages" disabled="#{membreControlleur.estConnecte ==  false}">
	        	<a4j:support event="ontabenter" action="#{membreControlleur.setOngletMessageActifToTrue}" reRender="pollMessagesPrivesRecus,pollComboBoxMessage"/>
				<a4j:support event="ontableave" action="#{membreControlleur.setOngletMessageActifToFalse}" reRender="pollMessagesPrivesRecus,pollComboBoxMessage"/>
	        	<rich:spacer height="15px" width="100%"/>
				<a4j:form id="messagesPrivesForm" style="text-align:center;">
					<rich:messages layout="list" showSummary="true" style="color:Red;" for="messagesPrivesForm">
						<f:facet name="errorMarker">
							<h:graphicImage value="/resources/images/error.gif" style="padding-right:7px;"/>   
						</f:facet>
			 		</rich:messages>
					<div style="text-align: right; padding-right: 20px;">
						<h:outputText value="140" id="nbCarMessagesPrives" style="color: silver; font-size: 2.5em;" /><h:outputText value=" caractères restants" style="color: silver; font-size: 2em;" />
					</div>
					<h:inputTextarea id="messagesPrivesTextArea" style=" width : 95%;" 
						value="#{membreControlleur.messagePrive}"
						onkeyup="document.getElementById('messagesPrivesForm:nbCarMessagesPrives').innerHTML = (140 - this.textLength);" 
						onkeypress="if(this.textLength > 139) this.value=this.value.substr(0,139);"/>
					<br /><h:outputLabel value="Envoyer à" for="destinataireMessagePrive" /><br />
				    <rich:comboBox id="destinataireMessagePrive" value="#{membreControlleur.destinataireMessagePrive}"
				    	suggestionValues="#{membreControlleur.listeMembres}"
				    	directInputSuggestions="true" style="margin: auto;">
				    	<a4j:support event="onfocus" action="#{membreControlleur.setDestinatairesPollEnabledToFalse}" reRender="pollComboBoxMessage"/>
				    	<a4j:support event="onblur" action="#{membreControlleur.setDestinatairesPollEnabledToTrue}" reRender="pollComboBoxMessage"/>
				    </rich:comboBox> 
				    <rich:spacer height="15px" width="100%"/>
					<a4j:commandButton id="messagesPrivesBoutonEnvoyer" value="Envoyer" style="margin:5px;"
						action="#{membreControlleur.publierMessagePrive}"
						oncomplete="document.getElementById('messagesPrivesForm:messagesPrivesTextArea').value='';document.getElementById('messagesPrivesForm:nbCarMessagesPrives').innerHTML = '140'"
						reRender="listeMessagesPrivesRecus,listeMessagesPrivesEmis"/>
					<a4j:commandButton id="messagesPrivesBoutonEffacer" value="Effacer" style="margin:5px;" 
						onclick="document.getElementById('messagesPrivesForm:messagesPrivesTextArea').value='';document.getElementById('messagesPrivesForm:nbCarMessagesPrives').innerHTML = '140'" 
						immediate="true" ajaxSingle="true"/>	
				</a4j:form>
				<!-- Debut poll d'actualisation de la liste des membre -->
				<a4j:region>
			        <h:form>
			            <a4j:poll id="pollComboBoxMessage" interval="5000" limitToList="true"
			            	enabled="#{membreControlleur.ongletMessageActif && membreControlleur.destinatairesPollEnabled}"
			            	action="#{membreControlleur.listerMembres}"
			                reRender="pollComboBoxMessage,destinataireMessagePrive" />
			        </h:form>
			    </a4j:region>
			    
			    <!-- Fin poll d'actualisation de la liste des membres -->
				<a4j:region>
			        <h:form>
			            <a4j:poll id="pollMessagesPrivesRecus" interval="5000" limitToList="true"
			            	enabled="#{membreControlleur.ongletMessageActif == true}"
			                action="#{membreControlleur.recupererMessagesPrivesRecus}" 
			                reRender="pollMessagesPrivesRecus,listeMessagesPrivesRecus" />
			        </h:form>
			    </a4j:region>
		        <rich:spacer height="25px" width="100%"/>
				
				<rich:tabPanel switchType="client" id="tabPanelMessagesPrives">
					<rich:tab label="Recus" id="tabMessagesPrivesRecus">
						<h:panelGroup id="listeMessagesPrivesRecus">
							<a4j:form>
								<rich:spacer height="30" />
								<rich:datascroller align="left" for="messagesPrivesRecusTable" maxPages="50"
            						id="messagesPrivesRecusTableScroller1" reRender="messagesPrivesRecusTableScroller2"
            						page="#{membreControlleur.pageMessagesPrivesRecusTableScroller}" />
            					<rich:spacer height="10" />
								<rich:dataTable value="#{membreControlleur.messagesPrivesRecus}" 
								var="messagePrivesRecu" width="100%" border="0" 
								rows="10" id="messagesPrivesRecusTable">
									<h:column>
										<rich:simpleTogglePanel switchType="client" label="#{messagePrivesRecu.emetteur.pseudo} - #{messagePrivesRecu.formatDate}" style="margin:10px 10px 10px 10px;">
										    <h:outputText value="#{messagePrivesRecu.message}" />             
										</rich:simpleTogglePanel>
									</h:column>
								</rich:dataTable>
								<rich:spacer height="10" />
								<rich:datascroller align="left" for="messagesPrivesRecusTable" maxPages="50"
            						id="messagesPrivesRecusTableScroller2" reRender="messagesPrivesRecusTableScroller1" 
            						page="#{membreControlleur.pageMessagesPrivesRecusTableScroller}" />
            					<rich:spacer height="30" />
							</a4j:form>
						</h:panelGroup>
					</rich:tab>
					<rich:tab label="Emis" id="tabMessagesPrivesEmis">
						<h:panelGroup id="listeMessagesPrivesEmis">
							<a4j:form>
								<rich:spacer height="30" />
								<rich:datascroller align="left" for="messagesPrivesEmisTable" maxPages="50"
            						id="messagesPrivesEmisTableScroller1" reRender="messagesPrivesEmisTableScroller2"
            						page="#{membreControlleur.pageMessagesPrivesEmisTableScroller}" />
            					<rich:spacer height="10" />
								<rich:dataTable value="#{membreControlleur.messagesPrivesEmis}" 
								var="messagePrivesEmi" width="100%" border="0" 
								rows="10" id="messagesPrivesEmisTable">
									<h:column>
										<rich:simpleTogglePanel switchType="client" label="#{messagePrivesEmi.emetteur.pseudo} - #{messagePrivesEmi.formatDate}" style="margin:10px 10px 10px 10px;">
										    <h:outputText value="#{messagePrivesEmi.message}" />             
										</rich:simpleTogglePanel>
									</h:column>
								</rich:dataTable>
								<rich:spacer height="10" />
								<rich:datascroller align="left" for="messagesPrivesEmisTable" maxPages="50"
            						id="messagesPrivesEmisTableScroller2" reRender="messagesPrivesEmisTableScroller1" 
            						page="#{membreControlleur.pageMessagesPrivesEmisTableScroller}" />
            					<rich:spacer height="30" />
							</a4j:form>
						</h:panelGroup>
					</rich:tab>
					<rich:tab label="Recherche" id="tabMessagesPrivesRecherche">
						<rich:spacer height="15px" width="100%"/>
						<a4j:form id="recherche_prive_form" style="text-align:center;">
							<h:outputLabel for="recherche_prive_text_area" value="Saisissez un ou plusieurs mots clés : " style="margin:7px;" />
							<h:inputText id="recherche_prive_text_area" style=" width : 20%;margin:7px;"
								value="#{membreControlleur.motsClesRecherche}"/>
							<a4j:commandButton id="recherche_prive_bouton_envoyer" value="Envoyer" style="margin:5px;"
								action="#{membreControlleur.rechercherMessagesPrives}"
								reRender="listeMessagesPrivesRecherche"/>
						</a4j:form>
						<h:panelGroup id="listeMessagesPrivesRecherche">
							<a4j:form>
								<rich:spacer height="30" />
								<rich:datascroller align="left" for="messagesPrivesRechercheTable" maxPages="50"
            						id="messagesPrivesRechercheTableScroller1" reRender="messagesPrivesRechercheTableScroller2"
            						page="#{membreControlleur.pageMessagesPrivesRechercheTableScroller}" 
            						renderIfSinglePage="false" />
            					<rich:spacer height="10" />
								<rich:dataTable value="#{membreControlleur.messagesPrivesRecherche}" 
								var="messagePrivesRecherche" width="100%" border="0" 
								rows="10" id="messagesPrivesRechercheTable">
									<h:column>
										<rich:simpleTogglePanel switchType="client" label="#{messagePrivesRecherche.emetteur.pseudo} - #{messagePrivesRecherche.formatDate}" style="margin:10px 10px 10px 10px;">
										    <h:outputText value="#{messagePrivesRecherche.message}" />             
										</rich:simpleTogglePanel>
									</h:column>
								</rich:dataTable>
								<rich:spacer height="10" />
								<rich:datascroller align="left" for="messagesPrivesRechercheTable" maxPages="50"
            						id="messagesPrivesRechercheTableScroller2" reRender="messagesPrivesRechercheTableScroller1" 
            						page="#{membreControlleur.pageMessagesPrivesRechercheTableScroller}" 
            						renderIfSinglePage="false" />
            					<rich:spacer height="30" />
							</a4j:form>
						</h:panelGroup>
					</rich:tab>
				</rich:tabPanel>
				<rich:spacer height="15px" width="100%"/>
	        </rich:tab>
	    </rich:tabPanel>
    </center>
    
    <!-- Debut popup d'expiration de session -->
    <rich:modalPanel id="sessionExpiredPanel">
    	<f:facet name="header">Session expired</f:facet>
    	<f:facet name="controls">
            <h:panelGroup>
                <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelinkSessionExpiredPanel"/>
                <rich:componentControl for="sessionExpiredPanel" attachTo="hidelinkSessionExpiredPanel" operation="hide" event="onclick"/>
            </h:panelGroup>
        </f:facet>
    	<rich:panel style="border:0;text-align:center;">
    		<h:outputText value="Votre session a expiré!" />
    	</rich:panel>
    </rich:modalPanel>
    <!-- fin popup d'expiration de session -->
    
    <!-- Debut poll de gestion d'expiration de session -->
    <a4j:region>
    	<a4j:form>
    		<a4j:poll id="sessioncheck" interval="86400000" reRender="sessioncheck" enabled="false"/>
    	</a4j:form>
    	<script type="text/javascript">
    		A4J.AJAX.onExpired = function(loc,expiredMsg){
    			Richfaces.showModalPanel('sessionExpiredPanel',{left:'auto',top:'auto'});
    		}
    	</script>
    </a4j:region>
    <!-- fin poll de gestion d'expiration de session -->
    
    </f:view>
</body>
</html>
