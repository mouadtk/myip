var _data;

$(document).ready(function(){
	/**
	 * Refresh page every 10sc 
	 **/
	(function countdown(remaining) {
	    if(remaining <= 0)
	        location.reload(true);
	    setTimeout(function(){ countdown(remaining - 1); }, 1000);
	})(60);
	/**
	 * 
	 **/
	
});