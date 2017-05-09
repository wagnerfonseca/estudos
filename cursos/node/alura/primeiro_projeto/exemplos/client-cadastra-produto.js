// Mas um exemplo de como realizar o cadastro através de um cliente escrito em JavaScript.
// Para gravar dados

var http = require('http');

var config = {
	hostname: 'localhost',
	port: 3000,
	path: '/produtos',
	method: 'post',
	headers: {
		'Accept':'application/json', // produces
		'Content-type':'application/json' // consumes
	}
};


var client = http.request(config, function(res){
	console.log(res.statusCode);

	res.on('data',function(body){
		console.log('Body -> '+ body);
	});
});

var produto = {
	titulo: '',
	isbn: '9788577807000',
	preco: 96.00
}

client.end(JSON.stringify(produto));

