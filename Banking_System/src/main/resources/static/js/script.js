$(function() {
	var $UserregisterForm = $("#register");
	$UserregisterForm.validate({
		rules: {

			firstName: {
				required: true,
				lettersonly: true
			},
			lastName: {
				required: true,
				lettersonly: true
			},
			email: {
				required: true,
				space: true,
				email: true
			},
			phno: {
				required: true,
				space: true,
				numericOnly: true,
				minlength: 10,
				maxlength: 12
			},
			adharNum: {
				required: true,
				space: true,
				numericOnly: true,
				minlength: 15,
				maxlength: 16

			},
			address: {
				required: true,
			},
			city: {
				required: true,
			},
			state: {
				required: true,
			},
			pincode: {
				required: true,
			},
			password: {
				required: true,
			},
			cpassword:{
				required: true,
				equalTo:'#pswd'
			}
		},
		messages: {
			firstName: {
				required: 'full name must be required',
				lettersonly: 'invalid name'
			},
			lastName: {
				required: 'last name required',
				lettersonly: 'invalid name'
			},
			email: {
				required: 'email name must be required',
				space: 'space not allowed',
				email: 'Invalid email'
			},
			phno: {
				required: 'mob no  required',
				space: 'space not allowed',
				numericOnly: 'invalid mob no',
				minlength: 'min 10 digit',
				maxlength: 'max 12 digit'
			},
			adharNum: {
				required: 'Adhar num required',
				space: 'space not allowed',
				numericOnly: 'invalid Adhar',
				minlength: 'min 15 digit',
				maxlength: 'max 16 digit'
			},
			address: {
				required: 'address must be required',
			},
			city: {
				required: 'city must be required',
			},
			state: {
				required: 'state required',
			},
			pincode: {
				required: 'pincode required',
			},
			password: {
				required: 'password required',
			},
			cpassword:{
				required:"confirm password required",
				equalTo:"password mismatch"
			}
		}
	})


	jQuery.validator.addMethod('lettersonly', function(value, element) {
		return /^[^-\s][a-zA-Z_\s-]+$/.test(value);
	});


	jQuery.validator.addMethod('space', function(value, element) {
		return /^[^-\s]+$/.test(value);
	});

	jQuery.validator.addMethod('all', function(value, element) {
		return /^[^-\s][a-zA-Z0-9_,.\s-]+$/.test(value);
	});


	jQuery.validator.addMethod('numericOnly', function(value, element) {
		return /^[0-9]+$/.test(value);
	});
})




const toogleSidebar=()=>{

    if($(".sidebar").is(":visible"))
    {
        
        $(".sidebar").css("display","none");
        $(".content").css("margin-left","0%");

    }else{
        $(".sidebar").css("display","block");
        $(".content").css("margin-left","20%");
    }

};








