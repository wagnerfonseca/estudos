var NoticiasDAO = function(connection) {
    this._connection = connection;
}

NoticiasDAO.prototype.getNoticias = function(callback){
    this._connection.query('select * from noticias order by data_criacao desc', callback);
}

NoticiasDAO.prototype.getNoticia = function(id, callback){
    // vem um obejto id.'id_noticia' é o nome do parametro
    this._connection.query('select * from noticias where id_noticias = ' + id.id_noticia, callback);
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