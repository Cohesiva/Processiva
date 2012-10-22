
function startProcess(_id) {
	var identifier = _id;

	$.ajax({
		url : 'start_process',
		data : ({
			processId : identifier
		}),
		success : function(response) {
			$("#body").html(response);
		}
	});
}

function loadPage(mapping) {	
	$.ajax({
		url : mapping,
		success : function(response) {
			$("#body").html(response);
		}
	});
}

$('.nav-collapse .nav li').click(function(e) {
	$('.nav-collapse .nav li').removeClass('active');
	var $this = $(this);
	if (!$this.hasClass('active')) {
	$this.addClass('active');
	}
	//e.preventDefault();
	});

