var app = require('./config/server');


app.get('/', (req, res) => {
    res.render('home/index');
});

var PORT = 3000;
app.listen(PORT, () => {
    console.log('Este App esta ouvindo a porta %s!', PORT);
});