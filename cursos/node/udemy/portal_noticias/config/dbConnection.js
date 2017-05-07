var mysql =  require('mysql');

module.exports = function() {
    console.log('realizando uma conexão com o servidor...');
    return mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: 'n3ww0rk',
        database: 'portal_noticias'
    });
};
  