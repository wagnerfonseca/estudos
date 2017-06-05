var _ = require('underscore');

var arr = [1,10,50,200,900,90,40];

var results = _.filter(arr, function(item) {
    return item > 100
});

console.log(results);

// Passar por cada elemento do vetor e retorn aum novo vetor com o resultado
var numeros = [1,3,7,12];
var results1 = _.map(numeros, function(item) {
    return item * 2;
});

console.log(results1);

// Outro cenário em coleções obter  todos os elementos exceto aquele que não combina com a condição
var nums = [0,1,2,3,4,5,6,7,8,9];

var odds = _.reject(nums, function(n) {
    return n % 2 == 0;
});

console.log(odds);