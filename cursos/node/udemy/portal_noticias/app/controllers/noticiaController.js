module.exports.findOne = function(app, req, res) {

    var connection = app.config.dbConnection();
    var dao = new app.app.models.NoticiasDAO(connection);    

    // Recuperando os dados enviados por paramestros na URL
    var id = req.query; // req.query é um objeto que possui em suas propriedades os paramtros

    dao.getNoticia(id, function(err, result) {
        res.render('noticias/noticia', {noticia: result});
    });
}

module.exports.findAll = function(app, req, res) {
    
    var connection = app.config.dbConnection();
    var dao = new app.app.models.NoticiasDAO(connection);

    dao.getNoticias(function(err, results) {
        res.render('noticias/noticias', {noticias: results});
    });
}