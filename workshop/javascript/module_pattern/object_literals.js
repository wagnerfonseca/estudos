 
var newObject = {}; // Mais recomentado
 
// or
var newObject = Object.create( Object.prototype );
 
// or
var newObject = new Object();



var pessoa = {

	nome: 'Joao',
	idade: 45,

	endereco: {
		rua: 'rua 10',
		cidade: 'Sao Paulo'
	},

	toString: function() {
		return "Nome: " + this.nome + ", idade: " + this.idade;
	}

}





console.log(pessoa.nome);
console.log(pessoa.toString());
console.log(pessoa.show());
console.log(pessoa.endereco.rua);


/// ----------------------
var someObject = {};
// Set properties
Object.defineProperties( someObject, {
 
  "someKey": {
    value: "Hello World",
    writable: true
  },
 
  "anotherKey": {
    value: "Foo bar",
    writable: false
  }
 
});

console.log(someObject.someKey);
someObject.someKey = 'Ola Mundão veio';
console.log(someObject.someKey);
console.log(someObject.anotherKey);
someObject.anotherKey = 'outher value';
console.log(someObject.anotherKey);