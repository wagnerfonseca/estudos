var Carro = function(modelo, fabricante) {
	this.modelo = modelo;
	this.fabricante = fabricante;

	
}


var fox = new Carro('Fox', 'VW');

var gol = {};
Carro.apply(gol, ['Gol', 'VW']);
console.log(fox);
console.log(gol);


var chevette = new Carro('Chevette', 'Chevrolet');
var fusca = new Carro('Fusca', 'VW');


var carros = [];

carros.push(fox);
carros.push(gol);
carros.push(chevette);
carros.push(fusca);

carros.forEach(function(e){
	console.log(e.modelo);
}); 
