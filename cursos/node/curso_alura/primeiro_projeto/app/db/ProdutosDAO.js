// Utilizar 'closures' para preservar o escopo da variavel 'connection'
// Para não haver necessidade de recria-la todas vez que for utilizar
var ProdutosDAO = function(connection) {
    this.connection = connection;
};

// crio uma nova propriedade para o meu produtos banco chamado 'lista'
ProdutosDAO.prototype.lista = function(callback) {
    this.connection.query('select * from livros', callback);
}


module.exports = () => {
    return ProdutosDAO;
}