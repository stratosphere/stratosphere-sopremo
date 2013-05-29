//JSON to CSV Converter
function ConvertToCSV(objArray) {
    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
	
	var keys = new Array(0);
	for (var k in array[0]) {
		keys.push(k);
	}
	
	keys.sort();
	
    var str = 'date,volume,count' + '\r\n';
    
    for (var i = 0; i < array.length; i++) {
        var line = '';
        for (var index in keys) {
            if (line != '') line += ','

            line += array[i][keys[index]];
        }

        str += line + '\r\n';
    
    }

    return str;
}