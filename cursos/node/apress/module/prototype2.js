/**
 * Prototype compartilha a mesma funcionalidade entre os objetos 
 */

function animal() {};

animal.prototype.nome = 'Animal';

var cachorro = new animal();
var gato = new animal();

console.log(cachorro.nome);
console.log(gato.nome);

animal.prototype.nome = 'Mamifero';

// Se altera a propriedade
cachorro.nome = 'Um nome'; // isso não é o prototype, agora é uma propriedade
console.log(cachorro.nome);
console.log(gato.nome);

// Apenas visualiza a propriedade do obejto
Object.getOwnPropertyNames(gato).forEach(function(val, idx, array) {
  console.log(val + ' -> ' + gato[val] );
});

//
console.log('valor do prototype', Object.getPrototypeOf(cachorro));