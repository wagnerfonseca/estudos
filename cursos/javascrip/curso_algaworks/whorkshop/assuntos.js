var Democrata = Democrata || {};

Democrata.NomeModulo =  (function() {

	function Contador() {
		this.i = 0;		
	}

	Contador.prototype.incrementar = function () {
		return ++this.i;
	}
	
	Contador.prototype.getValue = function () {
		return this.i;
	}


	return Contador;

})();

var cont1 = new Democrata.NomeModulo();
console.log(cont1.incrementar());
console.log(cont1.incrementar());
console.log(cont1.getValue());

var cont2 = new Democrata.NomeModulo();
console.log(cont2.getValue());