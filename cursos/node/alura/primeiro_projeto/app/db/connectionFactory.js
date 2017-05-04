// trabalhar com o banco de dados mysql
var mysql = require('mysql');

// Para não executar a função diretamente no momento que a aplicação sobe com o servidor
// vou empacotar ('wrapper') a funcao

var dbConnection = function () {
	// conectar ao banco de dados
	console.log('iniciando conexao com o banco');
	return  mysql.createConnection({
		host: 'localhost',
		user: 'root',
		password: 'n3ww0rk',
		database: 'projetonode'
	});
}

module.exports = () => {
	// apenas return a funcção, não a executo
	// para executar, dbConnection(); 
	console.log('iniciando o modulo do banco');
	return dbConnection;	
}
