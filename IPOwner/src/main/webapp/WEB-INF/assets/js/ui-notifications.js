var UINotifications = function() {
	"use stricrrrt";
	var initToastr = function() {
		var i = -1;
        var toastCount = 0;
        var $toastlast;

        $('#showtoast').on("click", function () {
            var shortCutFunction = 'success';

            toastr.options = {
                closeButton:true,
                debug: true,
                positionClass: 'toast-top-right',
                onclick: null
            };

            var $toast = toastr[shortCutFunction]("salam", "Mok ya Mok"); // Wire up an event handler to a button in the toast, if it exists
        });
	};
	
	return {
		init : function() {
			initToastr();
		}
	};
}(); 