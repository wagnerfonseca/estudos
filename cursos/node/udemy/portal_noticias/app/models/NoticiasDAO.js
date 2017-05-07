var NoticiasDAO = function(connection) {
    this._connection = connection;
}

NoticiasDAO.prototype.getNoticias = function(callback){
    this._connection.query('select * from noticias', callback);
}

NoticiasDAO.prototype.getNoticia = function(callback){
    this._connection.query('select * from noticias where id_noticias = 2', callback);
}

NoticiasDAO.prototype.save = function(src, callback) {        
    this._connection.query('insert into noticias set ? ', src, callback);
}

module.exports = function() {
    return NoticiasDAO;
};