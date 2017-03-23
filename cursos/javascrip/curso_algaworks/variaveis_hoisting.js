var percentualImposto = 0.15;
valor = 100;
var totalImposto = valor * percentualImposto;

console.log(totalImposto);

var valor;

// HOISTING

/* o HOISTING coloca de forma implicita a declaração de todas as variavies
para o topo do arquivo -- Apenas a Declaração, e não a atribuição

var percentualImposto, totalImposto, valor; 

percentualImposto = 0.15;
valor = 100;
totalImposto = valor * percentualImposto;

console.log(totalImposto);


*/