/*
 A função require é o principal caminho para importar um modulo dentro do arquivo corrente.

 Quando é usado a função 'require' sua chamada é feita com o caminho relativo
 require('./nome_do_arquivo');
 sempre pensando que a navegação vai ocorrer a partir do arquivo corrente
 ou  require('../nome_pasta/nome_do_arquivo.js');

 o require é igual a qualquer outra função do Javascript, não precisa de um tratamento especial.
 por exemplo você pode colocar o require dentro de uma condição if

 os modulos carregados com require são sincronos. O codigo abaixo do require não é executado até o modulo for carregado  e executado.

 Cacheado
 A primeira vez o require le do arquivo, a segunda vez le da memória.
 */
var foo = require('./foo');
console.log('loaded foo');
foo();


var das = require('../das');
das();