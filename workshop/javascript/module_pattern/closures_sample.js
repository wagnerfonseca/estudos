var criarCarro = function() {
	var modelo;
	var ano;
	var portencia;

	function ligarMotor() {
		return "motor ligado";
	}

	return {
		getModelo: function() {
			return modelo;
		},
		setModelo: function(novoModelo) {
			modelo = novoModelo;
		},
		ligarMotor


	}

}

var civic = criarCarro();
civic.setModelo('Honda Civic');
console.log(civic.getModelo());
console.log(civic.ligarMotor());