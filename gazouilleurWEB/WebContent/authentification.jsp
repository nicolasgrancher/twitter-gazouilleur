<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<f:subview id="authentification">
	<rich:modalPanel id="panel" width="300" height="100">
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
        <h:form>
	        <h:panelGrid columns="2">
		     	<h:outputLabel id="loginLabel" for="login" value="Login" />
		     	<h:inputText id="login" value="login" />
		     	<h:outputLabel id="passwordLabel" for="password" value="Mot de passe" />
		     	<h:inputSecret id="password" value="password"/>
		  		<f:facet name="footer">
	    			<h:panelGroup style="display:block; text-align:center">
	    				<!-- Modifier la valeur onclick pour ajouter le script de connexion -->
						<h:commandButton value="Ok" onclick="#{rich:component('panel')}.hide();" id="formButton"/>
					</h:panelGroup>
				</f:facet>
		  	</h:panelGrid>
	  	</h:form>
	</rich:modalPanel>
	<h:outputLink value="#" id="link">
        Se connecter
        <rich:componentControl for="panel" attachTo="link" operation="show" event="onclick"/>
    </h:outputLink>
</f:subview>