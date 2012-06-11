
	       function showGraphic(mark) {
	        var canvas = document.getElementById("myCanvas");

	        var context = canvas.getContext("2d");
	        var context2 = canvas.getContext("2d");
	        var context3 = canvas.getContext("2d");
	        var x = 10;
	        var y = 10;
	        var radius = 60;
	        var startAngle = 0 * Math.PI;
	        var endAngle = 0.5 * Math.PI;
	        var startAngleVar = (mark/20) * Math.PI;
	        var counterClockwise = false;
	        //green
	        context2.beginPath();
	        context2.arc(x, y, radius, startAngleVar, endAngle, counterClockwise);
			context2.arc(x+20, y, radius+5, endAngle, startAngleVar, true);
	        context2.lineWidth = 3;
			context2.fillStyle = "#008000";
	        context2.fill();
	        //red
	        context3.beginPath();
	        context3.arc(x, y, radius, startAngle, startAngleVar, counterClockwise);
			context3.arc(x+20, y, radius+5, startAngleVar, startAngle, true);
	        context3.lineWidth = 3;
			context3.fillStyle = "#FF0000";
	        context3.fill();

	        //linea
	        context.beginPath();
	        context.arc(x, y, radius, startAngle, endAngle, counterClockwise);
			context.arc(x+20, y, radius+5, endAngle, startAngle, true);
			context.lineTo(x+radius-1,y);
	        context.lineWidth = 3;
	        context.shadowColor = "#999";
	        context.shadowBlur = 15;
	        context.shadowOffsetX = 5;
	        context.shadowOffsetY = 5;
	        context.strokeStyle = "black";
	        context.stroke();
	      }
