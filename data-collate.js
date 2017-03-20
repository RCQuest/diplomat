var fs = require('fs');
var raw = JSON.parse(fs.readFileSync('collateme.json', 'utf8'));


var tasks = 8;

var printLine = (key)=>{
	var line = "";
	for (var i = 0; i < tasks; i++) {
		var o = raw[key+i];
		if(typeof o != 'undefined'){
			line = line + o;
		}
		if(i!=tasks-1) line = line + "\t"
	}
	console.log(line);
}

var printIntentActGrid = ()=>{
	var dataRemains = true;
	var row = 0;
	while(dataRemains){
		dataRemains = false;
		var line = "";
		for (var i = 0; i < tasks; i++) {
			var list = raw["INTENT_ACT_"+i];
			if(typeof list != 'undefined'){
				var item = list[row];
				if(typeof item != 'undefined'){
					line = line + item;
					dataRemains = true;
				}
			}
			if(i!=tasks-1) line = line + "\t"
		}
		console.log(line);
		row ++;
	}
	
}

var printTimeDifference = ()=>{
	var line = "";
	for (var i = 0; i < tasks; i++) {
		var b = raw["TASK_TIME_BEGIN_"+i];
		var e = raw["TASK_TIME_END_"+i];
		if(typeof b != 'undefined' && typeof e != 'undefined'){
			line = line + (e-b);
		}
		if(i!=tasks-1) line = line + "\t"
	}
	console.log(line);
}

console.log("UNDONE");
printLine("COMMANDS_UNDONE_");

console.log("HELP");
printLine("HELP_REFERS_");

console.log("INTENTACT");
printIntentActGrid();

console.log("INVALID");
printLine("INVALID_SUBMITS_");

console.log("ARROWS");
printLine("TASK_ARROWS_");

console.log("CHARS");
printLine("TASK_CHARS_");

console.log("ENTERS");
printLine("TASK_ENTER_");

console.log("SUCCEEDS");
printLine("TASK_SUCCEED_");

console.log("TIME");
printTimeDifference();

