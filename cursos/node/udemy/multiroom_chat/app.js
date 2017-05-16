/* Importar as configurações do servidor */
var app = require('./config/server')();

var PORT = 3000;
var server = app.listen(PORT,()=>{
    console.log('O servidor esta rodando na porta %s.', PORT);
});

var io = require('socket.io').listen(server);

/* criar a conexao por websocket */
io.on('connection', function(socket) {
    console.log('o usuário acabou de conectar!');


    socket.on('disconnect', function() {
        console.log('o usuário deixou a conexao websocket....');
    })
});