<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:rich="http://richfaces.org/rich"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:a4j="http://richfaces.org/a4j">
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta content="fr-fr" http-equiv="Content-Language" />
<title>Gazouilleur</title>
<link href="<%=request.getContextPath()%>/resources/stylesheets/screen.css" media="screen, projection" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resources/stylesheets/master.css" media="screen, projection" rel="stylesheet" type="text/css" />
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
.hentry .actions .non-fav {
	background-image: url('resources/images/icon_star_empty.gif');
}
.hentry .actions .fav-throb, .hentry .actions a.del-throb {
	background-image: url('resources/images/icon_throbber.gif');
}
.hentry .actions .del {
	background-image: url('resources/images/icon_trash.gif');
}
body#show .reply, .hentry .actions .reply {
	background-image: url('resources/images/icon_reply.gif');
}
.direct_message .actions .reply {
	background-image: url('resources/images/icon_direct_reply.gif');
}
.direct_message .actions .del {
	background-image: url('resources/images/icon_trash.gif');
}
.notify {
	background-image: url('resources/images/girl.gif');
}
.promotion, ul#tabMenu a#keyword_search_tab.hover, ul#tabMenu a:hover {
	background-image: url('resources/images/pale.png');
	background-color: transparent;
}
div#follow-toggle.closed {
	background-image: url('resources/images/toggle_closed.gif');
}
div#follow-toggle.opened {
	background-image: url('resources/images/toggle_opened.gif');
}
.follow-actions .following {
	background-image: url('resources/images/checkmark.gif');
}
.loading {
	background-image: url('resources/images/loader.gif');
}
#more {
	background-image: url('resources/images/more.gif');
}
#more.loading {
	background-image: url('resources/images/ajax.gif');
}
body#show .protected {
	background-image: url('resources/images/icon_lock.gif');
}
#side .promotion {
	background-image: url('resources/images/pale.png');
}
.rss {
	background-image: url('resources/images/rss.gif');
}
#flash {
	background-image: url('resources/images/girl.gif');
}
</style>
<style type="text/css">
		  #header {
			text-align: left;
			margin: 8px 0 0 0;
		  }
		
		  .navigation {
			position: absolute;
			display: block;
			float: right;
			top: 0;
			right: 0;
			height: 1.5em;
			font-size: 105%;
			text-align: right; 
			white-space: nowrap;
			display: block;
			background-color: #FFF;
			padding: 7px 6px 7px 3px;
		  }
		
		  .navigation a {
			display: inline;
			margin-left: 6px;
		  }
		
		  .top-nav li.last {
			margin-right: 6px;
		  }
		
		  .navigation a.signup-link {
			margin: 0 3px;
		  }
		  
	</style>
</head>
<body class="sessions firefox-windows" id="new">
	<f:view locale="fr">
		<h1 id="header">
			<h:outputLink value="#{facesContext.externalContext.requestContextPath}/index.jsf" id="logo" accesskey="1" title="Gazouilleur: accueil">
				<h:graphicImage value="/resources/images/gazouilleur.png" alt="Gazouilleur" height="30" width="250"/>
			</h:outputLink>
		</h1>
		<h:panelGroup styleClass="navigation">
			<a4j:include viewId="authentification.jsp" />
			<a4j:include viewId="inscription.jsp" />
		</h:panelGroup>
		<f:verbatim><div class="content-bubble-arrow"></div></f:verbatim>
		<center>
			<rich:tabPanel style="margin-top:30px;" width="70%" switchType="client">
		        <rich:tab label="Home">
		        	<a4j:include viewId="principale.jsp" />
		        </rich:tab>
		        <rich:tab label="Suiveurs/Suivis">
		        	<a4j:include viewId="suiveurs.jsp" />
		        </rich:tab>
		        <rich:tab label="Messages">
		        	<a4j:include viewId="messages.jsp" />
		        </rich:tab>
		    </rich:tabPanel>
	    </center>
	</f:view>
</body>
</html>
