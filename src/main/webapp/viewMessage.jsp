<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>留言板</title>
</head>
<body>
<h2>留言板</h2>

<%
  List<String> messages = (List<String>) application.getAttribute("messages");
  Integer userType = (Integer) session.getAttribute("userType");
  if (messages != null) {
    for (int i = 0; i < messages.size(); i++) {
      out.println("<p>" + messages.get(i));
      if (userType != null && userType == 1) { // 如果是管理员
        out.println(" <a href='" + request.getContextPath() + "/user/deleteMessage?index=" + i + "'>删除</a>");
      }
      out.println("</p>");
    }
  }
%>

<form action="user/postMessage" method="post">
  <textarea name="message" rows="4" cols="50"></textarea><br>
  <input type="submit" value="发表留言">
</form>

</body>
</html>