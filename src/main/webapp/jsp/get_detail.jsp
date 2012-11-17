<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.sf.json.JSONObject" %>
<jsp:useBean id="infoGetter" class="sock.InfoGetter" scope="session" />
<%
	String reqAppNo = request.getParameter("reqAppNo");
    System.out.println("\n requested appNo. : " + reqAppNo);

	infoGetter.RequestDetailInfo( reqAppNo );
	JSONObject obj = infoGetter.parseDataFromHtmlString();

	response.setContentType("application/json");
	System.out.println(obj.toString());
	response.getWriter().write(obj.toString());
%>