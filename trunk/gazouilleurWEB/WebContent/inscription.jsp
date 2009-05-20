<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>

<f:subview id="inscription">
	<rich:modalPanel id="panel_inscription" width="300" height="100">
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
        <a4j:form id="inscription" ajaxSubmit="true" >
	        <h:panelGrid columns="2">
		     	<h:outputLabel id="loginLabel" for="login" value="Login" />
		     	<h:inputText id="login" value="#{membre.pseudo}" />
		     	<h:outputLabel id="passwordLabel" for="password" value="Mot de passe" />
		     	<h:inputSecret id="password" value="password"/>
		  		<f:facet name="footer">
	    			<h:panelGroup style="display:block; text-align:center">
	    				<!-- Modifier la valeur onclick pour ajouter le script de connexion -->
						<h:commandButton value="Ok" onclick="#{rich:component('panel_inscription')}.hide();" id="formButton"/>
					</h:panelGroup>
				</f:facet>
		  	</h:panelGrid>
	  	</a4j:form>
	</rich:modalPanel>
	<h:outputLink value="#" id="link">
        Rejoindre Gazouilleur!
        <rich:componentControl for="panel_inscription" attachTo="link" operation="show" event="onclick"/>
    </h:outputLink>
</f:subview>