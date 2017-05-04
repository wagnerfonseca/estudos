var app = require('./config/express')();

// Reconhecer as rotas de nossos recursos
// o caminho é realtivo a partir do arquivo que sobe o servidor
// Isso foi configurado dentro da configuração de Express
/*var routesIndex = require('./app/routes/index')(app);
var routesProdutos = require('./app/routes/produtos')(app);*/
var PORT = 3000;
app.listen(PORT, () => {
  console.log('App ouvindo na porta %s!', PORT);
});
