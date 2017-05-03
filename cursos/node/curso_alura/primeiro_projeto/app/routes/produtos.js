// Não há mais necessidade
// var session = require('../db/connectionFactory');

module.exports = function(app) {
	app.get('/produtos', function (req, res) {
		
		var connection = app.db.connectionFactory();
		// palavra reservada 'new' define um novo escopo 
		var produtos = new app.db.ProdutosDAO(connection);

		produtos.lista(function(err, results){
			// retorna o recurso serializado em json
			// res.send(results);
			res.render('produtos/lista', {lista:results});	
		});

		connection.end();

		// padrão do Node é buscar as paginas de view dentro da pasta "views" na raiz da aplicação
		// quando muda o padrão deve ser configurado
	  	//res.render('produtos/lista');
	});	
}