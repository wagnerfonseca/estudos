var http = require('http');

http.createServer(function(req, res){

	if (req.url === '/produtos') {
		res.end('<html><body><p>listando produtos</p></body></html>');
	} else {
		res.end('<html><body><p>home</p></body></html>');
	}

}).listen(3000);

console.log('servidor rodando ....'); 