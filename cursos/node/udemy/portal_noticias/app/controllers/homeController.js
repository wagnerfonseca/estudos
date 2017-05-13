module.exports.index = (app, req, res) => {

    var connection = app.config.dbConnection();
    var dao = new app.app.models.NoticiasDAO(connection);

    dao.findLastNoticias(function(err, results){
        res.render('home/index', {noticias: results});
    });

    
}