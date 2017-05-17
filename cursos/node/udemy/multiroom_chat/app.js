/* Importar as configurações do servidor */
var app = require('./config/server')();

var PORT = 3000;
var server = app.listen(PORT,()=>{
    console.log('O servidor esta rodando na porta %s.', PORT);
});

var io = require('socket.io').listen(server);

// definindo uma variavel para o escopo global
app.set('io', io);

/* criar a conexao por websocket */
io.on('connection', function(socket) {
    console.log('o usuário acabou de conectar!');


    socket.on('disconnect', function() {
        console.log('o usuário deixou a conexao websocket....');
    });

    socket.on('msgParaServidor', function(data) {
        // so para o cliente que requisitou
        socket.emit('msgParaCliente', {apelido: data.apelido, mensagem: data.mensagem });

        // para os outros usuarios
        socket.broadcast.emit('msgParaCliente', {apelido: data.apelido, mensagem: data.mensagem });
    });

});