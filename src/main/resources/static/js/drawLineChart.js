//$.ajax({
//	url: 'multichart',
//	success: function(result){
//		console.log(result);
//		var dataCadastro = JSON.parse(result).data;
//		console.log(dataCadastro);
//		var qtde = JSON.parse(result).qtde;
//		drawLineChart(dataCadastro, qtde);
//	}
//})

//function drawLineChart(dataCadastro, qtde){
//	Highcharts.chart('container', {
//		chart: {
//			type: 'line',
//			width: 500
//		},
//		title: {
//			text: 'Line chart'
//		},
//		xAxis: {
//			categories: dataCadastro
//		},
//		tooltip: {
//			formatter: function(){
//				return '<strong>' + this.x + ': </strong>' + this.y;
//			}
//		},
//		series: [{
//			data: qtde
//		}]
//	});
//}

