<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>


	<rich:modalPanel id="panel_connexion" width="300" height="100">
		<f:facet name="header">
            <h:panelGroup>
                <h:outputText value="Authentification"></h:outputText>
            </h:panelGroup>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
                <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelink"/>
                <rich:componentControl for="panel" attachTo="hidelink" operation="hide" event="onclick"/>
            </h:panelGroup>
        </f:facet>
        <a4j:form>
	        <h:panelGrid columns="2">
		     	<h:outputLabel id="loginLabel" for="login" value="Login" />
		     	<h:inputText id="login" value="#{membreControlleur.membre.pseudo}" />
		     	<h:outputLabel id="passwordLabel" for="password" value="Mot de passe" />
		     	<h:inputSecret id="password" value="#{membreControlleur.membre.password}"/>
		  		<f:facet name="footer">
	    			<h:panelGroup style="display:block; text-align:center">
						<a4j:commandButton value="Ok" id="formButton"
							action="#{membreControlleur.connexion}" 
							oncomplete="if('#{membreControlleur.closePanelConnexion}' == 'true'){javascript:Richfaces.hideModalPanel('panel_connexion');}"
							reRender="connexionMenu,variables"/>
					</h:panelGroup>
				</f:facet>
		  	</h:panelGrid>
	  	</a4j:form>
	</rich:modalPanel>
	<a4j:commandLink value="Se connecter" id="lienConnexion" rendered="#{membreControlleur.estConnecte == false}">
        <rich:componentControl for="panel_connexion" attachTo="lienConnexion" operation="show" event="onclick"/>
    </a4j:commandLink>
    <a4j:form>
		<a4j:commandLink value="Se déconnecter" id="lienDeconnexion" 
			rendered="#{membreControlleur.estConnecte}" action="#{membreControlleur.deconnexion}"
			reRender="connexionMenu,variables"/>
	</a4j:form>
