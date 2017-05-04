// Utilizar 'closures' para preservar o escopo da variavel 'connection'
// Para não haver necessidade de recria-la todas vez que for utilizar
var ProdutosDAO = function(connection) {
    this.connection = connection;
};

// crio uma nova propriedade para o meu produtos banco chamado 'lista'
ProdutosDAO.prototype.findAll = function(callback) {
    this.connection.query('select * from livros', callback);
}

ProdutosDAO.prototype.save = function(produto,callback) {
    this.connection.query('insert into livros set ?', produto, callback);
}


module.exports = () => {
    return ProdutosDAO;
}