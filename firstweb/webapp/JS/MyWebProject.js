/**
 * Javascript Document
 */

const SERVICE_URL = "http://localhost:8080/Lab-1-DynamicWebProject/Check"; 

document.addEventListener('DOMContentLoaded', function(event) {
	
	// Find Element by ID when DOM is ready
	const checkButton = document.getElementById('checkData');
	
	// Add Click Event Listener
	checkButton.addEventListener("click", function(){
		console.log("Script loaded and event listener added.");
		// Get Data From Input Fields (not have an ID)
		const username = document.querySelector('input[name=username]').value
		
		// Prepare URL
		var checkhUrl = new URL(SERVICE_URL);
		checkhUrl.searchParams.set('username', username);
		
		// Asynchronous HTTP GET request
		var xhr = new XMLHttpRequest();
		xhr.open("GET", checkhUrl, true);
		xhr.send(null);

		xhr.onreadystatechange  = function () {
			// On HTTP Request Success
			if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200 ) { 
				// Get Response
				const checkResponse = xhr.responseText;
				// Update Div Area
				const checkOutputDiv = document.getElementById('checkOutput');
				checkOutputDiv.innerHTML = checkResponse;
				// Update Color
				var c = checkResponse.charAt(0);
				if (c == 'A') 
					checkOutputDiv.style.color = "darkgreen";
				else if (c == 'N')
					checkOutputDiv.style.color = "darkred";
				else 
					checkOutputDiv.style.color = "black";
			}
		};
	});
	
	// Find Element by ID when DOM is ready
	const pass1InputField = document.querySelector('input[name=password]');
	const pass2InputField = document.querySelector('input[name=password2]');
	
	// Add Click Event Listener
	pass2InputField.addEventListener("keyup", function(){
		var pass1 = pass1InputField.value;
		var pass2 = pass2InputField.value;
		// Check
		if (pass1 != pass2)
			pass2InputField.style.backgroundColor = "red";
		else
			pass2InputField.style.backgroundColor = "green";
	});	
	
})
