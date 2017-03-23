// function declaration
function somar(a , b) {
	// {'0': [valor de a], '1', [valor de b]}
	console.log('argumentos:', arguments);
	return a + b;
}

function semParametro() {
	return arguments[0] + arguments[1];
}

// function expression
var subtrair = function(a, b) {
	return a - b;
}

console.log('tipo de uma funcao javascript:',typeof somar);
console.log(somar(1,2));
console.log(semParametro(1,2));

console.log(subtrair(4,3));