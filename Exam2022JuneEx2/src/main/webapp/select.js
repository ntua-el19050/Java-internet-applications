/**
 * 
 */

 document.addEventListener('DOMContentLoaded', function(event) {
	
	// Find Element by ID when DOM is ready
	const location = document.querySelector('input[name= location]');
	const repid = document.querySelector('input[name= repID]');
	const select = document.getElementById('method');
	
	
	// Add Click Event Listener
	select.addEventListener("click", function(){
		
		if(select.value==1){
			location.disabled = true;
			repid.disabled = true;
		}
		
		if(select.value==2){
			location.disabled = false;
			repid.disabled = true;
		}
		
		if(select.value==3){
			location.disabled = true;
			repid.disabled = false;
		}
		
	});	
	

})