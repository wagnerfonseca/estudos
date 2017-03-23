// 0 é false
if (0) {
	console.log("true");
} else {
	console.log("0 é false");
}

// como descrobir o valor boleando 

console.log(!0);
console.log(!!0);
console.log(!1);
console.log(!'');
console.log(!!NaN);
console.log(!!null);
console.log(!!undefined);

var valor = 0;

if (!valor)
	console.log('o valor deve ser maior que 0');


var numero = 10;
if (numero)
	console.log('o valo do numero somado a 10 é', numero + 10);
