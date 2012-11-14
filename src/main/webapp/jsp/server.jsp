<%@ page import="java.io.*, java.net.*" %>
<jsp:useBean id="infoGetter" class="sock.InfoGetter" scope="session" />
<html>
<head></head>
<body>
	<%
		infoGetter.RequestDetailInfo("4120120008827");
		out.print( infoGetter.getInfoString() );
	%>
</body>
</html>