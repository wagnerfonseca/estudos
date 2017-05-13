var express = require('express');
var consign = require('consign');
var bodyParser = require('body-parser');
var expressValidator = require('express-validator');

var app = express();

app.set('view engine', 'ejs');
app.set('views', './app/views');

// realizar a leitura dos arquivos estáticos
app.use(express.static('./app/public'));

// Body-parser é middleware, neste caso utilizo o use
app.use(bodyParser.urlencoded({extended: true}));


app.use(expressValidator());


// Fica responsavel por realizar o load das rotas
consign()
    .include('app/routes') // inicializa os as rotas
    .then('config/dbConnection.js') // incluir a conexao de banco de dados
    .then('app/models')
    .then('app/controllers')
    .into(app);

module.exports = app;