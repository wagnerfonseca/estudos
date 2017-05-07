
module.exports = function(app) {

    app.get('/noticias', function(req, res) {

        var connection = app.config.dbConnection();
        var dao = new app.app.models.NoticiasDAO(connection);

        dao.getNoticias(function(err, results) {
            res.render('noticias/noticias', {noticias: results});
        });

    });

};