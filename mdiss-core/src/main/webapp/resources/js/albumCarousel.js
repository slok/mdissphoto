$(document).ready(function() {
	
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
			t=setTimeout(function() {
				rebootCarousel(id, true);},3000);
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
			visible: 1,
			step: 1,
			auto: {enabled: start, interval:1500},
			width: 370, 
			height: 250
		});	
	}
	
});

//Function to dynamically initialize the album carousels
function initCarousel(num){
	var i;
	for (i=0;i<num;i++)
	{
		var albumID = "#alCar" + i;
		
		//alert(albumID); 
		
		$(albumID).rcarousel({
			visible: 1,
			step: 1,
			auto: {enabled: false},
			width: 370, 
			height: 250
		});			
	}
}