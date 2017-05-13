
module.exports = function(app) {

    app.get('/noticias', function(req, res) {

        app.app.controllers.noticiaController.findAll(app, req, res);

    });

    app.get('/noticia', function(req, res) {

        app.app.controllers.noticiaController.findOne(app, req, res);

        // connection.query('select * from noticias where id_noticias = 3', function(err, results) {
        //     res.render('noticias/noticia', {noticia: results});
        // });

    });

};