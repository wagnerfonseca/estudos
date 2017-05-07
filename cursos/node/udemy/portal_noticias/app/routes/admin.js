module.exports = function(app) {

    app.get('/add', function(req, res) {
        res.render('admin/form_add_noticia');
    });

    app.post('/noticias/salvar', function(req, res) {

        var noticia = req.body;

        var connection = app.config.dbConnection();
        var dao = new app.app.models.NoticiasDAO(connection);

        dao.save(noticia, function(err, result){
            res.redirect('/noticias');
        });

    });

};