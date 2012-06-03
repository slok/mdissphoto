$(document).ready(function() {
		
	var $container = $('.photoBox');

	$container.imagesLoaded( function(){
	  $container.masonry({
	    itemSelector : '.photoContainer',
	    isFitWidth : 'true',
	    gutterWidth : 6,
	    columnWidth: function(containerWidth) {
	    	return containerWidth / 50
	    }
	  });
	});

});
