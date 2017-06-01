/**
 * No JavaScript não tem a palavra reservado class para criar classes.
 * 
 * Mas as funções são muitas poderosa para ser usadas para imitar os principios da orientação a objetos.
 * 
 * Dois principios, a palavra reservada new e prototype.
 * 
 * Todo objeto em java tem um link interno para outro objeto chamado de prototype.
 * 
 * 
 */



function animal() {};
animal.prototype.nome = 'Cachorro';

var cachorro = new animal();

console.log(cachorro.__proto__ === animal.prototype);
console.log(cachorro.nome);
console.log(animal.nome);