$(document).ready(function() {
		
	var $container = $('.photoBox');

	$container.imagesLoaded( function(){
	  $container.masonry({
	    itemSelector : '.imageContainer',
	    isFitWidth : 'true',
	    columnWidth: function(containerWidth) {
	    	return containerWidth / 50
	    }
	  });
	});

});
