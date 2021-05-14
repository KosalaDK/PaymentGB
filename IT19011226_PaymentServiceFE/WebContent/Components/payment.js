$(document).on("click", "#btnSave", function(event){ 
	
	// Clear alerts---------------------
	 $("#alertSuccess").text(""); 
	 $("#alertSuccess").hide(); 
	 $("#alertError").text(""); 
	 $("#alertError").hide(); 
 
	 
	// Form validation-------------------
	var status = validatePayForm(); 
	if (status != true) 
	 { 
	 $("#alertError").text(status); 
	 $("#alertError").show(); 
	 
 return; 
} 


// If valid------------------------
var type = ($("#hidpid").val() == "") ? "POST" : "PUT"; 
	$.ajax( 
	{ 
	 url : "PaymentAPI", 
	 type : type, 
	 data : $("#formPay").serialize(), 
	 dataType : "text", 
	 complete : function(response, status) { 
		 
		 onPaySaveComplete(response.responseText, status); 
	 } 
	}); 
});

function onPaySaveComplete(response, status){ 
	if (status == "success") {
		
		 var resultSet = JSON.parse(response); 
		 if (resultSet.status.trim() == "success") { 
			 
			 $("#alertSuccess").text("Successfully saved."); 
			 $("#alertSuccess").show(); 
			 $("#divItemsGrid").html(resultSet.data); 
		 } 
		 else if (resultSet.status.trim() == "error") {
			 
			 $("#alertError").text(resultSet.data); 
			 $("#alertError").show(); 
		 } 
	} 
	else if (status == "error") { 
		
		 $("#alertError").text("Error while saving."); 
		 $("#alertError").show(); 
	} else{ 
		
		 $("#alertError").text("Unknown error while saving.."); 
		 $("#alertError").show(); 
		}
	$("#hidpid").val(""); 
	$("#formPay")[0].reset(); 
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event){ 
		
		 $("#hidpid").val($(this).data("payID")); 
		 $("#orderid").val($(this).closest("tr").find('td:eq(1)').text()); 
		 $("#amount").val($(this).closest("tr").find('td:eq(3)').text()); 
		 //$("#hidpid").val($(this).closest("tr").find('td:eq(3)').text()); 
		
		 
});




// DELETE==========================================
$(document).on("click", ".btnRemove", function(event) { 
	 $.ajax( 
	 { 
	 	url : "PaymentAPI", 
	 	type : "DELETE", 
	 	data : "pid=" + $(this).closest("tr").find('td:eq(0)').text(),
	 	dataType : "text", 
	 	complete : function(response, status) { 
	 		onPayDeleteComplete(response.responseText, status); 
	 	} 
	}); 
})
	


function onPayDeleteComplete(response, status){
	
	if (status == "success") {
		
		var resultSet = JSON.parse(response); 
			if (resultSet.status.trim() == "success"){
			
				$("#alertSuccess").text("Successfully deleted."); 
				$("#alertSuccess").show(); 
				$("#divItemsGrid").html(resultSet.data); 
				
			} else if (resultSet.status.trim() == "error") { 
				
				$("#alertError").text(resultSet.data); 
				$("#alertError").show(); 
		} 
	} 
	else if (status == "error") { 
		$("#alertError").text("Error while deleting."); 
		$("#alertError").show(); 
	} 
	else { 
		$("#alertError").text("Unknown error while deleting.."); 
		$("#alertError").show(); 
	} 
}

// CLIENT-MODEL================================================================
function validatePayForm(){
	// CODE

	
// OrderID-------------------------------
if ($("#orderid").val().trim() == ""){
	
	return "Insert Item title.";
}

		



	return true;
}