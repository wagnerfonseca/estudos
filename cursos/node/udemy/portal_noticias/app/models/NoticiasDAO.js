var NoticiasDAO = function(connection) {
    this._connection = connection;
}

NoticiasDAO.prototype.getNoticias = function(callback){
    this._connection.query('select * from noticias order by data_criacao desc', callback);
}

NoticiasDAO.prototype.getNoticia = function(callback){
    this._connection.query('select * from noticias where id_noticias = 2', callback);
}

NoticiasDAO.prototype.save = function(src, callback) {        
    this._connection.query('insert into noticias set ? ', src, callback);
}

NoticiasDAO.prototype.findLastNoticias = function(callback) {
    this._connection.query('select * from noticias order by data_criacao desc limit 5', callback);
}

module.exports = function() {
    return NoticiasDAO;
};