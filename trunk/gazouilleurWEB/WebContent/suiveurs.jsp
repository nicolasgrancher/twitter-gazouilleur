<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>


<style>
	.cur { cursor: pointer; }
</style>
<center>
	<a4j:form>
	    <h:outputLabel id="ajoutSuiviLabel" for="ajoutSuivi" value="Suivez un ami" />
	     	<h:inputText id="ajoutSuivi" value="#{membreControlleur.ajoutSuivi}" />
	   	<a4j:commandButton action="#{membreControlleur.ajouterAmi}" value="Suivre" >
	   	</a4j:commandButton>
	</a4j:form>
	<h:panelGrid columns="2" style="width:100%;">
		<a4j:form>
			<rich:contextMenu attached="false" id="suivisMenu" submitMode="ajax">
				<rich:menuItem ajaxSingle="true">
					Ne plus suivre {pseudo}
					<a4j:actionparam assignTo="#{membreControlleur.ajoutSuivi}" value="{pseudo}"/>
					<a4j:support action="#{membreControlleur.supprimerAmi}" event="oncomplete"/>
				</rich:menuItem>
			</rich:contextMenu>
		</a4j:form>
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
		<rich:dataTable id="suiveursTable" value="#{membreControlleur.membre.listSuiveurs}"
			var="suiveur">
			<f:facet name="header">
				Suiveurs
			</f:facet>
			<rich:column>
				<h:outputText value="#{suiveur.pseudo}" />
			</rich:column>
		</rich:dataTable>
	</h:panelGrid>
</center>
