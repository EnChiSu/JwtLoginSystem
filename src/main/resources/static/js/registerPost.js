$( document ).ready(function() {

	// SUBMIT FORM
    $("#registerForm").submit(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		ajaxPost();
	});


    function ajaxPost(){

    	// PREPARE FORM DATA
    	var formData = {
    		Username : $("#username").val(),
    		Password :  $("#password").val()
    	}

    	// DO POST
    	$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "http://localhost:8888/registeration",
			data : JSON.stringify(formData),
			dataType : 'json',
			success : function(result) {
				if(result.status == "Done"){
					$("#postResultDiv").html("" +
												"Post Successfully! " +
												" Username: " +
												result.data.username + " ,Password: " + result.data.password + "");
				}else{
					$("#postResultDiv").html("Error");
				}
				console.log(result);
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});

    	// Reset FormData after Posting
    	resetData();

    }

    function resetData(){
    	$("#username").val("");
    	$("#password").val("");
    }
})