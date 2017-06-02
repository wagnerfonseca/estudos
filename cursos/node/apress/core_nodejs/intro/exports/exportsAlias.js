/**

O Node.js  nos auxilia criando um alias para (module.exports = exports) 

E todas as vezes que você quiser exportar alguma coisa, você pode utilizar

exports.a ou exports.b

cuidado para não quebrar a referencia de exports. não declare exports = 123;
Não se esqueça, de sempre usar exports  sempre com alguma coisa 

se você quiser usar uma simples coisas use o module.exports = { ... }
mas quando ousar em utilizar mais de um exportação, use o exports.algumaCoisa

 */

// omitido = module.exports
exports.a = function() {
    console.log('a called - exemplo alias');
}

exports.b = function() {
    console.log('b called - exemplo alias');
}