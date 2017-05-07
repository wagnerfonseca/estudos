
module.exports = function(app) {

    app.get('/noticias', function(req, res) {

        var connection = app.config.dbConnection();
        var noticiasModel = app.app.models.noticiasModel;

        noticiasModel.getNoticias(connection, function(err, results) {
            res.render('noticias/noticias', {noticias: results});
        });

    });

};