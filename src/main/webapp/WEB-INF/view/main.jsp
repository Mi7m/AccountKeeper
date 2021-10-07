<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    
<style>
  table {
    border-collapse: collapse;
    margin: 0px auto;
  }
  th, td {
    border: solid 1px #000;
    padding: 5px;
  }
</style>

<style>
  td:nth-child (odd) {
    background-color: lightcyan;
  }
  tr:hover td{
    background: lightcyan;
  }
  td:nth-child (even) {
    background-color: paleturquoise;
  }
</style>    
    
<body>
    
<h2 align="center">Password keeper</h2>    
<br>
<h3 align="center">Data loaded from: ${filename}</h3>    
<br>


<table  border="1" rules="all" cellpadding="2">
    <tr bgcolor="#B0C4DE">
        <th width="50">Id</th>
        <th width="250">Account Name</th>
        <th width="250">Username</th>
        <th width="250">Password</th>
        <th width="400">Comments</th>
        <th width="150">Operations</th>
    </tr>

    <c:forEach var="record" items="${dataRecords}">
        
        <c:url var="updateButton" value="/editRecord">
            <c:param name="id" value="${record.id}"/>
        </c:url>
                
        <c:url var="deleteButton" value="/deleteRecord">
            <c:param name="id" value="${record.id}"/>
        </c:url>
        
        <tr>
            <td>${record.id}</td>
            <td><a href=${record.www}>${record.name}</a></td>
            <td>${record.username}</td>
            <td>${record.password}</td>
            <td>${record.comment}</td>
            <td align="center">
                <input type="button" value="Edit" onclick="window.location.href='${updateButton}'"/>
                <input type="button" value="Delete" onclick="window.location.href='${deleteButton}'"/>
            </td>
        </tr>
    </c:forEach>
</table>

<br>



<form action="search" method="GET">
<div align="center">
    <input type="text" name="SearchText" size="30" placeholder="search text"/>
    <input type="submit" value="Search"/>
    <input type="button" value="Reload"
           onclick="window.location.href = 'reloadData'"/>

    <input type="button" value="Add"
           onclick="window.location.href = 'addRecord'"/>
</div>
</form>
<br>
<div align="center">
<form:form action="saveData" modelAttribute="saveMode">
    Choose save mode:
    <form:select path="saveModeName">
        <form:option value="YANDEX_DISK" label="Yandex" />
        <form:option value="LOCAL_DISK" label="Local" />
        <form:option value="UNCODED_FILE_ON_LOCAL_DISK" label="Uncoded" />
        <form:option value="DATABASE" label="DB" />
    </form:select>
    <input type="submit" value="Save"/>
</form:form>
</div>
</body>    
</html>
