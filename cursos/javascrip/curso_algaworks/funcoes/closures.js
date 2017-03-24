function incrementar() {
	var valor = 0;

	return function() {
		return ++valor;
	}
}


var fn = incrementar();

console.log(fn());
console.log(fn());
console.log(fn());




/*var valor = 0;

function incrementar() {	
	return ++valor;
}

console.log(incrementar());
console.log(incrementar());
console.log(incrementar());*/