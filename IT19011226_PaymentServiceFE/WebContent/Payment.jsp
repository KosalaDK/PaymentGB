<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/payment.js"></script>

<meta charset="ISO-8859-1">
<title>Payment Service</title>
</head>
<body>
<div class="container"><div class="row"><div class="col-6">
<h1>Payment Service</h1>

	<form id="formPay" name="formPay">
		
		Enter Order ID:
		<input id="orderid" name="orderid" type="text" class="form-control form-control-sm"><br>
		Enter Total amount:
		<input id="amount" name="amount" type="text" class="form-control form-control-sm"><br>
		
		<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
		<input type="hidden" id="hidpid" name="hidpid" value="">
	</form>
	
	<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
	<br>
	<div id="divItemGrid">
	<%
	Payment resdObj = new Payment(); 
	 out.print(resdObj.readPayments()); 
	%>
	</div>
</div> </div> </div> 

</body>
</html>