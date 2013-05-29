$.get(graph_data_url,function(data,status){

				var array = typeof data != 'object' ? JSON.parse(data) : data;
				
				function custom_sort(a, b) {
 					return new Date(a.date).getTime() - new Date(b.date).getTime();
 				}
 				array.sort(custom_sort);
 				
 				var converted = ConvertToCSV(array);

				/* Visualization of CSV * with dygraph library */
				g2 = new Dygraph(
					document.getElementById("graphdiv2"),
					converted, // path to CSV object
					{ 
						'volume':{ axis:{} },           
						labelsKMB: true,
						ylabel: 'Number of Tweets',
						y2label: 'Volume in Mio $',
						digitsAfterDecimal:0,
					}  
				);
  });