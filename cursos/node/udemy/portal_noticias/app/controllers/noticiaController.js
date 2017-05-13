module.exports.findOne = function(app, req, res) {

    var connection = app.config.dbConnection();
    var dao = new app.app.models.NoticiasDAO(connection);    

    dao.getNoticia(function(err, result) {
        console.log('realizando uma consulta ', result);
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