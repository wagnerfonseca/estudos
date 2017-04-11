

// Meu objeto
var animal = {};


var Animal = function (nome, idade){
	this.nome = nome;
	this.idade = idade;
};

animal['gato'] = new Animal('gato', 6);
animal['cachorro'] = new Animal('cachorro', 13);
animal['vaca'] = new Animal('vaca', 3);
animal['hamster'] = new Animal('hamster', 2);

console.log(animal);
console.log('----------------------------------------------');


for(var x in animal) {
	console.log('Key: ', x );
	var values = animal[x];
	console.log('values: ');
	for (var y in values) {
		console.log( y, ' -> ', values[y])
	}

}

