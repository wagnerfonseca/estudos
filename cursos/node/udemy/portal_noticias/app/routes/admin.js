module.exports = function(app) {

    app.get('/add', function(req, res) {

        // var connection = app.config.dbConnection();
        // var noticiasModel = app.app.models.noticiasModel;
        

        // noticiasModel.getNoticia(connection, function(err, result) {
        //     console.log('realizando uma consulta ', result);
        //     res.render('noticias/noticia', {noticia: result});
        // });

        // connection.query('select * from noticias where id_noticias = 3', function(err, results) {
        //     res.render('noticias/noticia', {noticia: results});
        // });
        res.render('admin/form_add_noticia');

    });

    app.post('/noticias/salvar', function(req, res) {

        var noticia = req.body;

        res.send(noticia);

    });

};