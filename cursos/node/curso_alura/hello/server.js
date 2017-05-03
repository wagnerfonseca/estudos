// Obtem a biblioteca para iniciar um servidor node.js
var http = require('http');

var server = http.createServer(function(req, res) {
	res.end('<html><body><p>Meu primeiro servidor web com Node.js</p></body></html>');
});
server.listen(3000); // porta onde vai rodar o servidor



console.log('Servidor rodando...');