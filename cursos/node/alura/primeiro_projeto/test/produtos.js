var express = require('../config/express')();
var request = require('supertest')(express);

describe('ProdutosController', function(){

	it('#Listagem json', function(done) {

		request.get('/produtos')
			// envio
			.set('Accept','application/json')
			// resposta
			.expect('Content-Type', /json/)
			.expect(200, done);
			
	});

	it('#Cadastro de novo produto com dados invalidos', function(done) {
		request.post('/produtos')
			.send({titulo: '', isbn:'144930463X', preco: 96.77})
			.expect(400, done);
	});


	it('#Cadastro de novo produto com dados válidos', function(done) {
		request.post('/produtos')
			.send({titulo: 'Building and Testing with Gradle', isbn:'9972140025145', preco: 96.74})
			.expect(400, done);
	});

});
