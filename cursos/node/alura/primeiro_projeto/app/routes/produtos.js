// Não há mais necessidade
// var session = require('../db/connectionFactory');

module.exports = function(app) {
    app.get('/produtos', function(req, res) {

        var connection = app.db.connectionFactory();
        // palavra reservada 'new' define um novo escopo 
        var dao = new app.db.ProdutosDAO(connection);

        dao.findAll(function(err, results) {
            // retorna o recurso serializado em json
            // res.send(results);
            res.render('produtos/lista', { lista: results });
        });

        connection.end();

        // padrão do Node é buscar as paginas de view dentro da pasta "views" na raiz da aplicação
        // quando muda o padrão deve ser configurado
        //res.render('produtos/lista');
    });

    app.get('/produtos/new', function(req, res) {
        res.render('produtos/form');
    });

    app.post('/produtos', function(req, res) {
        console.log('produtos save');

        var produto = req.body;
        
        if (!req.body) {
            console.log('erroooooooooooooooooooo');
            return res.sendStatus(400);
        }
        
    
        console.log(produto);

        // var connection = app.db.connectionFactory();
        // var dao = new app.db.ProdutosDAO(connection);

        // dao.save(produto, function(err, results){
        //      res.redirect('/produtos');    
        //   
        // });
    });
}