/**
 * 
 */

 document.addEventListener('DOMContentLoaded', function(event) {
	
	// Find Element by ID when DOM is ready
	const username = document.querySelector('input[name=username]');
	const password = document.querySelector('input[name=password]');
	const passdiv = document.getElementById('passdiv');
	const but = document.getElementById('sub');
	// ^ -> arxh, $ ->telos, + osa na nai alla toulaxiston 1
	
	//const reg2  = new RegExp(/^[a-zA-Z0-9]+$/g);
	const reg = new RegExp(/^[a-z0-9]+$/i);
	
	
	but.addEventListener("keyup",function(){
		
		if(username.value.trim===""){
			but.disabled = true;
		}
		else{
			but.disabled = false;
		}	
		
	});
	
	
	password.addEventListener("keyup",function(){
		var pass = password.value;
		if(reg.test(pass)){
			passdiv.innerHTML = "Easy";
		}
		else if (pass.length<8){
			passdiv.innerHTML = "Medium";
		}
		else{
			passdiv.innerHTML = "Hard";
		}		
		
	});
	
	
})