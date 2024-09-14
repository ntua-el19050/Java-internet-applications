/**
 * 
 */

 document.addEventListener('DOMContentLoaded', function(event) {
	
	// Find Element by ID when DOM is ready
	const password = document.querySelector('input[name=newPassword]');
	const passdiv = document.getElementById('passdiv');
	// ^ -> arxh, $ ->telos, + osa na nai alla toulaxiston 1
	const reg1  = new RegExp(/^[a-z]+$/i);
	//const reg2  = new RegExp(/^[a-zA-Z0-9]+$/g);
	const reg3 = new RegExp(/^[a-z0-9]+$/i);
	
	
	password.addEventListener("keyup",function(){
		var pass = password.value;
		if(reg1.test(pass)){
			passdiv.innerHTML = "Easy";
		}
		else if (reg3.test(pass)){
			passdiv.innerHTML = "Medium";
		}
		else{
			passdiv.innerHTML = "Hard";
		}
			
		
	})
	
	
})