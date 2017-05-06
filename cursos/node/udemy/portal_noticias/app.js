var app = require('./config/server');

var routesNoticias = require('./app/routes/noticias')(app);

var PORT = 3000;
app.listen(PORT, () => {
    console.log('Este App esta ouvindo a porta %s!', PORT);
});