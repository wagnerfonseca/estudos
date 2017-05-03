var express = require('express');
var load = require('express-load');


module.exports = function() {

	var app = express();
	// Template de páginas dinâmica
	app.set('view engine', 'ejs'); // variaveis 'ambiente' do express

	// qual a pasta padrão de views
	app.set('views', './app/views'); // ../ para voltar a pasta não funciona

	// auto carregamento 
	// cwd -> restringe a procura do node pelas pasta 'routes e db'
	load('routes', {cwd: 'app'})
		.then('db')
		.into(app);

	return app;
}