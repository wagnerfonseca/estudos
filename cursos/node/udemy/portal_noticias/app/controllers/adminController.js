module.exports.add = function(app, req, res) {

    res.render('admin/form_add_noticia', {errors: {}, noticia: {}});

}

module.exports.save = function(app, req, res) {
    var noticia = req.body;

    req.assert('titulo','Titulo é obrigatório').notEmpty();
    req.assert('resumo', 'O resumo é obrigatorio').notEmpty();
    req.assert('resumo', 'O resumo deve conter entra 10 e 100 caracteres').len('10', '100');
    req.assert('autor', 'Autor é obrigatório').notEmpty();
    req.assert('data_noticia', 'Data é obrigatórtio' ).notEmpty().isDate({format: 'YYYY-MM-DD'});
    req.assert('noticia', 'O assunto da notícia é obrigatório').notEmpty();

    var errors =  req.validationErrors();

    if (errors) {         
        res.render('admin/form_add_noticia', {errors: errors, noticia: noticia});
        return;
    }

    var connection = app.config.dbConnection();
    var dao = new app.app.models.NoticiasDAO(connection);

    dao.save(noticia, function(err, result){
        res.redirect('/noticias');
    });
}