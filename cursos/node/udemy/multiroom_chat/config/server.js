/* Importar o modulo do framework Express */
var express = require('express');
/* auto-loader */
var consign = require('consign');
/* parser de paginas HTML */
var bodyParser = require('body-parser');
/* validator */
var expressValidator = require('express-validator');

module.exports = function(){

    var app = express();

    /* setar as variaveis */
    /* view engine */
    app.set('view engine', 'ejs');
    /* local das views */
    app.set('views', './app/views');

    /* static */
    app.use(express.static('./app/public'));

    /* midleware body-parser */
    app.use(bodyParser.urlencoded({ extended: true}));

    /* midleware express-validator */
    app.use(expressValidator());

    /* auto-loader consign */
    consign()
        .include('app/routes')
        .then('app/models')
        .then('app/controllers')
    .into(app);
    

    return app;
}