/* Importar as configurações do servidor */
var app = require('./config/server')();

var PORT = 80;
app.listen(PORT,()=>{
    console.log('O servidor esta rodando na porta %s.', PORT);
});