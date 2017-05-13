module.exports = function(app) {

    app.get('/add', function(req, res) {
        //res.render('admin/form_add_noticia', {errors: {}, noticia: {}});
        app.app.controllers.adminController.add(app, req, res);
    });

    app.post('/noticias', function(req, res) {

        app.app.controllers.adminController.save(app, req, res);

    });

};