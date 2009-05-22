<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<h:panelGrid id="variables" columns="2">
	<h:outputLabel id="membreLabel" for="membre" value="Membre :" />
	<h:outputText id="membre" value="#{membreControlleur.membre}"/>
	<h:outputLabel id="estConnecteLabel" for="estConnecte" value="estConnecte :" />
	<h:outputText id="estConnecte" value="#{membreControlleur.estConnecte}"/>
	<h:outputLabel id="closePanelConnexionLabel" for="closePanelConnexion" value="closePanelConnexion :" />
	<h:outputText id="closePanelConnexion" value="#{membreControlleur.closePanelConnexion}"/>
	<h:outputLabel id="testLabel" for="test" value="test :" />
	<h:outputText id="test" value="#{membreControlleur.test}"/>
</h:panelGrid>

