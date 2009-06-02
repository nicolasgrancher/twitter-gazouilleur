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
	-- onglets manquants --

popup d'expiration de session
poll de gestion d'expiration de session

-->

<title>Gazouilleur</title>
<link href="<%=request.getContextPath()%>/resources/stylesheets/screen.css" media="screen, projection" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resources/stylesheets/master.css" media="screen, projection" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resources/stylesheets/connexionMenu.css" media="screen, projection" rel="stylesheet" type="text/css" />
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
	
	<h:graphicImage value="/resources/images/gazouilleur.png" alt="Gazouilleur" height="30" width="250"/>
	
	<!-- Debut menu de connexion -->
	<h:panelGroup id="connexionMenu">
	
		<!-- Debut lien de connexion affichant la popup de connexion -->
		<h:panelGroup id="panelGroupConnexion">	
			<a4j:commandLink value="Se connecter" id="lienConnexion" rendered="#{membreControlleur.estConnecte == false}">
		        <rich:componentControl for="panel_connexion" attachTo="lienConnexion" operation="show" event="onclick"/>
		    </a4j:commandLink>
		<!-- Fin lien de connexion -->
	    
		<!-- Debut lien de deconnexion -->
		    <a4j:form>
				<a4j:commandLink value="Se d�connecter" id="lienDeconnexion" 
					rendered="#{membreControlleur.estConnecte}" action="#{membreControlleur.deconnexion}"
					reRender="panelGroupConnexion,panelGroupInscription,tabPanel,variables"/>
			</a4j:form>
		<!-- Fin lien de deconnexion -->
		</h:panelGroup>
		
		<!-- Debut lien d'inscription affichant la popup d'inscription-->
		<h:panelGroup id="panelGroupInscription">
			<a4j:commandLink value="Rejoindre Gazouilleur!" id="lienInscription" rendered="#{membreControlleur.estConnecte ==  false}">
			     <rich:componentControl for="panel_inscription" attachTo="lienInscription" operation="show" event="onclick"/>
			</a4j:commandLink>
		</h:panelGroup>
		<!-- Fin lien d'inscription -->
	
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
						<h:graphicImage value="/resources/images/error.gif" />   
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
								reRender="panelGroupConnexion,panelGroupInscription,tabPanel,variables">
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
		
		    		<h:outputLabel id="password2Label" for="password2" value="R�ins�rez votre mot de passe" />
		    		<h:inputSecret id="password2" value="#{membreControlleur.password2}" />
		
					<h:outputLabel id="nomLabel" for="nom" value="Nom" />
					<h:inputText id="nom" value="#{membreControlleur.membre.nom}">
		    			<rich:ajaxValidator event="onsubmit" />
		    		</h:inputText>
		
		   			<h:outputLabel id="prenomLabel" for="prenom" value="Pr�nom" />
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
								reRender="panelGroupConnexion,panelGroupInscription,tabPanel,variables"/>
						</h:panelGroup>
					</f:facet>
		 		</h:panelGrid>
			</a4j:form>
		</rich:modalPanel>
		<!-- Fin popup d'inscription -->
		
	</h:panelGroup>
	<!-- Fin menu de connexion -->
	
	<!-- Debut corps de la page -->
	<center>
		<rich:tabPanel style="margin-top:30px;" width="70%" switchType="client" id="tabPanel">
		
		<!-- Debut onglet principal -->
	        <rich:tab label="Home" id="tabHome">
	        	<rich:spacer height="15px" width="100%"/>
				<a4j:form id="message_form" style="text-align:center;">
					<h:inputTextarea id="message_text_area" style=" width : 95%;" />
					<a4j:commandButton id="message_bouton_envoyer" value="Envoyer" style="margin:5px;"/>
					<h:commandButton id="message_bouton_effacer" value="Effacer" style="margin:5px;" onclick="this.form.elements[0].value=''" immediate="true"/>	
				</a4j:form>
		        <rich:spacer height="25px" width="100%"/>
		
				<%
					for(int i=0; i<6; i++){
						//voir a4j:repeat
					%>
				<rich:simpleTogglePanel switchType="client" label="Pseudo - date" style="margin:10px 10px 10px 10px;">
				    140 characteres             
				</rich:simpleTogglePanel>
					<%
					}
				%>
	        </rich:tab>
	    <!-- Fin onglet principal -->
	    
	    <!-- Debut onglet suiveur -->
	        <rich:tab label="Suiveurs/Suivis" id="tabSuiveurs" disabled="#{membreControlleur.estConnecte ==  false}">
				<style>
					.cur { cursor: pointer; }
				</style>
			<center>
		
			<!-- Debut ajout d'un suivi -->
				<a4j:form>
				    <h:outputLabel id="ajoutSuiviLabel" for="ajoutSuivi" value="Suivez un ami" />
				     	<h:inputText id="ajoutSuivi" value="#{membreControlleur.ajoutSuivi}" />
				   	<a4j:commandButton action="#{membreControlleur.ajouterAmi}" value="Suivre" 
				   		reRender="suivisTable"/>
				</a4j:form>
			<!-- Fin ajout d'un suivi -->	
				
			<!-- Debut liste des suivis -->
			
				<h:panelGrid columns="2" style="width:100%;">
				
				<!-- Debut menu conextuel des usivis -->
					<a4j:form>
						<rich:contextMenu attached="false" id="suivisMenu" submitMode="ajax">
							<rich:menuItem>
								Ne plus suivre {pseudo}
								<a4j:actionparam assignTo="#{membreControlleur.ajoutSuivi}" value="{pseudo}" />
								<a4j:support action="#{membreControlleur.supprimerAmi}" event="oncomplete"
									reRender="suivisTable" requestDelay="500"/>
							</rich:menuItem>
						</rich:contextMenu>
					</a4j:form>
				<!-- Fin menu conextuel des suivis -->
				
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
			</center>
	        </rich:tab>
	        <!-- Fin onglet suiveurs -->
	        
	        <rich:tab label="Messages" id="tabMessages" disabled="#{membreControlleur.estConnecte ==  false}">
	        	<!--  4j:include viewId="/messages.jsp" / -->
	        </rich:tab>
	        <rich:tab label="Variables" id="tabVariables">
	        	<!-- a4j:include viewId="/variables.jsp" / -->
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
    		<h:outputText value="Votre session a expir�!" />
    	</rich:panel>
    </rich:modalPanel>
    <!-- fin popup d'expiration de session -->
    
    <!-- Debut poll de gestion d'expiration de session -->
    <a4j:region>
    	<a4j:form>
    		<a4j:poll id="sessioncheck" interval="86400000" reRender="sessioncheck" />
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