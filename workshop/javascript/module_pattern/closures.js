// 1 

function incrementar() {
	var contador = 0;
	return ++contador;
}
console.log(incrementar());
console.log(incrementar());
console.log(incrementar());


// 2
// 1 
var contador = 0;
function incrementar() {	
	return ++contador;
}

console.log(incrementar());
console.log(incrementar());
contador = 100; // isso é ruim
console.log(incrementar());

// solucao com closures

function incrementar() {
	var contador = 0;
	return function() {
		return ++contador;
	};
}

var fn = incrementar();

console.log(fn());
console.log(fn());
console.log(fn());
