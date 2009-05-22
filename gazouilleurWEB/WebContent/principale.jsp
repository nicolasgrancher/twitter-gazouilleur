<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>


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


