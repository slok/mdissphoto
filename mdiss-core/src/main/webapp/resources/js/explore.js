$(document).ready(function() {
		
	var $container = $('.photoBox');

	$container.imagesLoaded( function(){
	  $container.masonry({
	    itemSelector : '.polaroid2',
	    isFitWidth : 'true',
	    gutterWidth : 4,
	    columnWidth: function(containerWidth) {
	    	return containerWidth / 50
	    }
	  });
	});
	
});