var jsonObj = (function(){
	var json = null;
	jQuery.ajax({
		async: false,
		global: false,
		url: getURL,
		dataType: "json",
		success: function(data){ json = data; }
	});
	return json;
})();

var getURL = (function(){
	if ( document.getElementById("script").value == "result" ){ return document.getElementById("result"); }
	else { return document.getElementById("corr"); }
})();

var vis_json = JSON.stringify(jsonObj, null, "\t");
document.getElementById("jsonOutput").value = syntaxHighlight(vis_json);
