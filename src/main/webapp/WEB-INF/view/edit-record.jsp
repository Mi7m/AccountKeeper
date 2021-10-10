<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
 
    
<body>
    
    <h3>Edit Account Details</h3>
    <form:form action="editOK" modelAttribute="record">
        
        
        <form:hidden path="id"/>
        <table border="0"  cellpadding="5px">
            
            <tr bgcolor="PowderBlue">
                <td width="150">Account name</td>  <td width="300"><form:input size="75" path="name"/></td>
            </tr>
            <tr bgcolor="SkyBlue">
            <td>Web Address</td> <td><form:input size="75" path="www"/></td>
            </tr>
            <tr bgcolor="PowderBlue">
            <td>Username</td> <td><form:input size="75" path="username"/></td>
            </tr>
            <tr bgcolor="SkyBlue">
            <td>Password</td> <td><form:input size="75" path="password"/></td>
            </tr>
            <tr bgcolor="PowderBlue">
            <td>Comments</td> <td><form:input size="75" path="comment"/></td>
            </tr>
            
            <tr>
            <td align="right"><input type="submit" value="OK"></td>
            <td><input type="button" value="Cancel"
               onclick="window.location.href = 'editCancel'"/></td>
            </tr>
        </table>
    </form:form>
    
        
</body>
</html>
