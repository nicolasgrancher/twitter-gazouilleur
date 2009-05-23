<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>

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
						reRender="panelGroupConnexion,panelGroupInscription,variables"/>
				</h:panelGroup>
			</f:facet>
 		</h:panelGrid>
	</a4j:form>
</rich:modalPanel>
<h:panelGroup id="panelGroupInscription">
	<a4j:commandLink value="Rejoindre Gazouilleur!" id="lienInscription" rendered="#{membreControlleur.estConnecte ==  false}">
	     <rich:componentControl for="panel_inscription" attachTo="lienInscription" operation="show" event="onclick"/>
	</a4j:commandLink>
</h:panelGroup>