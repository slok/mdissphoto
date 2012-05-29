$(document).ready(function() {
	var albumID;
	var t;
	var id;
	
	$('.box').masonry({
	    // options
	    itemSelector : '.thumb',
	    gutterWidth : 20,
	    isResizable : true,
	    isFitWidth : true
	});
	
	$('.alCar').hover(
		function () {
			id = this.id;
			t = setTimeout(function() {
				rebootCarousel(id, true);},1200);
		}, 
		function () {
			rebootCarousel(this.id, false);
			clearTimeout(t);
		}
	);
	
	//Small hack to make the rcarousel behave oppositely
	function rebootCarousel(elem, start){
		var elemId = "#" + elem; 
		
		$(elemId).rcarousel({
			auto: {enabled: start, interval:1000},
		});	
	}
	
});

function triggerAlbumIdSetter(albumNum){
	 var id = $(".cmdlink" + albumNum).attr('id');
	 document.getElementById(id).onclick();
}

//Function to dynamically initialize the album carousels
function initCarousel(num){
	var i;
	
	//Get the width and height values from the CSS
	var wCssValue = $('.thumb').css('width');
	var hCssValue = $('.thumb').css('height');
	
	//Format properly the value to use it in the initialization options
	var w = wCssValue.substr(0,wCssValue.indexOf("px"));
	w = parseInt(w);
	var h = hCssValue.substr(0,hCssValue.indexOf("px"));
	h = parseInt(h);
	
	for (i=0;i<num;i++)
	{
		var albumID = "#alCar" + i;
		
		$(albumID).rcarousel({
			visible: 1,
			step: 1,
			auto: {enabled: false},
			width: w, 
			height: h
		});	
	}
}