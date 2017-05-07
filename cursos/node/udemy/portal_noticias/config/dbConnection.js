var mysql =  require('mysql');

var dbConnection =  function() {
    console.log('Realizando uma conexão com o banco de dados...');
    return mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: 'n3ww0rk',
        database: 'portal_noticias'
    });
}


module.exports = function() {
    return dbConnection;    
};
  