<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>



<h:panelGrid columns="2" style="width:100%;">
<!-- voir rich:dataList -->
	<rich:panel id="suiveurs" header="Suiveurs" style="margin-right:30%;width:40%;margin-left:30%;">
       	<%
       	for(int i=0; i<6; i++){
       		%>
       		<h:outputText value="suiveur" />
       		<rich:contextMenu event="oncontextmenu" attachTo="" submitMode="none">
			    <rich:menuItem value="Supprimer" />
			</rich:contextMenu>
       		<%
       	}
       	%>
	</rich:panel>
	<rich:panel header="Suivis" style="margin-right:30%;width:40%;margin-left:30%;">
      	<%
      	for(int i=0; i<6; i++){
      		%>
      		<h:outputText value="suivi" />
      		<rich:contextMenu event="oncontextmenu" attachTo="" submitMode="none">
			    <rich:menuItem value="Supprimer" />
			</rich:contextMenu>
      		<%
      	}
      	%>
	</rich:panel>
</h:panelGrid>
