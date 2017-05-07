module.exports = function(app) {

    app.get('/noticia', function(req, res) {

        var connection = app.config.dbConnection();
        var dao = new app.app.models.NoticiasDAO(connection);
        

        dao.getNoticia(function(err, result) {
            console.log('realizando uma consulta ', result);
            res.render('noticias/noticia', {noticia: result});
        });

        // connection.query('select * from noticias where id_noticias = 3', function(err, results) {
        //     res.render('noticias/noticia', {noticia: results});
        // });

    });

};