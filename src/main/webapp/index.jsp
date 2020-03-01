<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Strona główna</title>
</head>
<body>
    <%@include file="header.jsp"%>
    <main class="container">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Active orders</th>
            </tr>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Registration number</th>
                <th scope="col">Model</th>
                <th scope="col">Problem</th>
                <th scope="col">Assigned employer</th>
                <th scope="col">Status</th>
            </tr>
            </thead>
            <tbody>
            <tr data-id="id">
                <th scope="row">1</th>
                <td>DW6CU657</td>
                <td>C30</td>
                <td>AC system failure</td>
                <td>Marek Wójcik</td>
                <td>Accepted - 10.02.2020, g. 17:25</td>
            </tr>
            </tbody>
        </table>
    </main>
    <%@include file="footer.jsp"%>
</body>
</html>
