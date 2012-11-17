<%@ page import="java.io.*, java.net.*" %>
<%@ page import="net.sf.json.JSONObject" %>
<jsp:useBean id="infoGetter" class="sock.InfoGetter" scope="session" />
<%
	infoGetter.RequestDetailInfo("4120120008827");
	JSONObject obj = infoGetter.parseDataFromHtmlString();
%>