// Self-Invoking Functions
// funções invocadas por si mesma

/** 
A self-invoking expression is invoked (started) automatically, without being called.
Function expressions will execute automatically if the expression is followed by ().

*/

var incrementar = (function(){
	var numero = 0;

	return function() {
		return ++numero;
	}
})();


console.log(incrementar());
console.log(incrementar());
console.log(incrementar());


(function () {
    texto = "Hello! I called myself";
})();

console.log(texto);

var texto2 = '';

(function colocarValor(a , b) {
	texto2 = 'novo texto';
	
	return a + b;
})();


console.log(texto2);
// You cannot self-invoke a function declaration.
//console.log(colocarValor(1, 2));
