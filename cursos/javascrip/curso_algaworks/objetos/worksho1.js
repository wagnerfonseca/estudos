// Modulo

// NAMESPACE
var Democrata = Democrata || {};

Democrata.TesteModulo = (function(){

	// FUNCAO CONTRUTORA
	function TesteModulo() {

	}

	var contador = 0;

	TesteModulo.prototype.incrementar = function () {
			return ++contador;
		}

	TesteModulo.prototype.resetar = function () {
			console.log('O valor do contador era de:', contador);
			contador = 0;
		}

	return TesteModulo;

})();

var testeModulo =  new Democrata.TesteModulo()
testeModulo.incrementar();
testeModulo.incrementar();
testeModulo.incrementar();
testeModulo.resetar();

