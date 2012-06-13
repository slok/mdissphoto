
	       function showGraphic(mark) {
	    	   var canvas = document.getElementById("myCanvas");

		        var context = canvas.getContext("2d");
		        var context2 = canvas.getContext("2d");
		        var context3 = canvas.getContext("2d");
		        var x = 100;
		        var y = 100;
		        var radius = 60;
		        var startAngle = 1 * Math.PI;
		        var endAngle = 0 * Math.PI;
		        var startAngleVar;
		        if(mark==10.0)
		              startAngleVar=((9.99/10)+1) * Math.PI;
		          	else
		              startAngleVar=((mark/10)+1) * Math.PI;
		        var counterClockwise = false;
		        //green
		  		context2.beginPath();
		        context2.arc(x, y, radius, startAngle, startAngleVar, counterClockwise);
	        	context2.lineTo(x,y);
		        context2.fillStyle = "#008000";
		        context2.fill();
	        	
	        	//red
	        	context3.beginPath();
		        context3.arc(x, y, radius, startAngleVar, endAngle, counterClockwise);
	        	context3.lineTo(x,y);
		        context3.fillStyle = "#FF0000";
		        context3.fill();

		        //linea
		        context.beginPath();
		        context.arc(x, y, radius, startAngle, endAngle, counterClockwise);
	        	context.lineTo(x-(radius+1),y);
		        context.lineWidth = 3;
		        context.shadowColor = "#999";
		        context.shadowBlur = 15;
		        context.shadowOffsetX = 5;
		        context.shadowOffsetY = 5;
		        context.strokeStyle = "black";
		        context.stroke();
	      }
