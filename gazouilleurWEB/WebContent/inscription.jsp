<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>

<rich:modalPanel id="panel_inscription">
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
     <a4j:form id="inscription" ajaxSubmit="true">
      <h:panelGrid columns="2">
    	<h:outputLabel id="pseudoLabel" for="pseudo" value="Pseudo" />
    	<h:inputText id="pseudo" required="true" value="#{membreControlleur.membre.pseudo}" />

    	<h:outputLabel id="passwordLabel" for="password" value="Choisissez votre mot de passe" />
    	<h:inputSecret id="password" value="#{membreControlleur.membre.password}"/>

    	<h:outputLabel id="password2Label" for="password2" value="Réinsérez votre mot de passe" />
    	<h:inputSecret id="password2" value="#{membreControlleur.password2}"/>

    	<h:outputLabel id="nomLabel" for="nom" value="Nom" />
    	<h:inputText id="nom" value="#{membreControlleur.membre.nom}"/>

    	<h:outputLabel id="prenomLabel" for="prenom" value="Prénom" />
    	<h:inputText id="prenom" value="#{membreControlleur.membre.prenom}"/>

 		<f:facet name="footer">
  			<h:panelGroup style="display:block; text-align:center">
  				<!-- Modifier la valeur onclick pour ajouter le script de connexion -->
			<a4j:commandButton value="Ok" action="#{membreControlleur.creerMembre}" id="formButton" 
				oncomplete="if('#{membreControlleur.closePanelInscription}' == 'true'){javascript:Richfaces.hideModalPanel('panel_inscription');}"
				reRender="connexionMenu"/>
		</h:panelGroup>
	</f:facet>
 	</h:panelGrid>
	</a4j:form>
</rich:modalPanel>
<a4j:commandLink value="Rejoindre Gazouilleur!" id="lienInscription" rendered="#{membreControlleur.estConnecte ==  false}">
     <rich:componentControl for="panel_inscription" attachTo="lienInscription" operation="show" event="onclick"/>
</a4j:commandLink>