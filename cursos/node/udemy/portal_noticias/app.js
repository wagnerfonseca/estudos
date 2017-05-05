var app = require('./config/server');



var PORT = 3000;
app.listen(PORT, () => {
    console.log('Este App esta ouvindo a porta %s!', PORT);
});