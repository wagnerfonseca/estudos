var incrementar = (function () {
	var valor = 0;

	return function() {
		return ++valor;
	}
})();




console.log(incrementar());
console.log(incrementar());
console.log(incrementar());




/*var valor = 0;

function incrementar() {	
	return ++valor;
}

console.log(incrementar());
console.log(incrementar());
console.log(incrementar());*/