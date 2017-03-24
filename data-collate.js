var fs = require('fs');
var file = JSON.parse(fs.readFileSync('collateme.json', 'utf8'));


var tasks = 8;

var printLine = (key,raw)=>{
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

var printIntentActGrid = (raw)=>{
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

var printTimeDifference = (raw)=>{
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
file.forEach((item)=>{
	printLine("COMMANDS_UNDONE_",item);
});


console.log("HELP");
file.forEach((item)=>{
	printLine("HELP_REFERS_",item);
});

console.log("INTENTACT");
file.forEach((item)=>{
	printIntentActGrid(item);
});

console.log("INVALID");
file.forEach((item)=>{
	printLine("INVALID_SUBMITS_",item);
});

console.log("ARROWS");
file.forEach((item)=>{
	printLine("TASK_ARROWS_",item);
});

console.log("CHARS");
file.forEach((item)=>{
	printLine("TASK_CHARS_",item);
});

console.log("ENTERS");
file.forEach((item)=>{
	printLine("TASK_ENTER_",item);
});

console.log("SUCCEEDS");
file.forEach((item)=>{
	printLine("TASK_SUCCEED_",item);
});

console.log("TIME");
file.forEach((item)=>{
	printTimeDifference(item);
});

