/**
 * 
 */

 document.addEventListener('DOMContentLoaded', function(event) {
	
	// Find Element by ID when DOM is ready
	const username = document.querySelector('input[name=username]');
	const password = document.querySelector('input[name=password]');
	const passdiv = document.getElementById('passdiv');
	var but = document.getElementById("sub");
	but.disabled = true;
	
	
	const reg  = new RegExp(/^[a-z0-9]+$/i);
	
	// Add Click Event Listener
	username.addEventListener("keyup", function(){
		if(username.value.trim()===""){   
			but.disabled = true;
		}
		else{
			but.disabled = false;
		}
	});	
	
	password.addEventListener("keyup",function(){
		var pass = password.value;
		if(pass.length === 0){
			passdiv.innerHTML = "";
		}
		else if(reg.test(pass)){
			passdiv.innerHTML = "Easy";
		}
		else if (pass.length < 8){
			passdiv.innerHTML = "Medium";
		}
		else{
			passdiv.innerHTML = "Hard";
		}
	
})

})