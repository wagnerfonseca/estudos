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

            res.format({
                // Content Negociation
                html: () => {
                    // o recurso produz um resultado serializado em Html
                    res.render('produtos/lista', { lista: results });
                },
                json: () => {
                    // o recurso produz um resultado serializado em Json
                    res.json(results);
                }

            });
        });

        connection.end();

        // padrão do Node é buscar as paginas de view dentro da pasta "views" na raiz da aplicação
        // quando muda o padrão deve ser configurado
    });

    app.get('/produtos/new', function(req, res) {
        res.render('produtos/form');
    });

    app.post('/produtos', function(req, res) {
        var produto = req.body;

        // Validações
        var validadorTitulo = req.assert('titulo', 'Título é obrigatório');
        validadorTitulo.notEmpty();

        var errors = req.validationErrors();
        if (errors) {
            res.render('produto/new');
            return;
        }
        
        if (!req.body) {            
            return res.sendStatus(400);
        }
        
        var connection = app.db.connectionFactory();
        var dao = new app.db.ProdutosDAO(connection);
        
        dao.save(produto, function(err, result){               
            res.redirect('/produtos');   
        });
    });
}