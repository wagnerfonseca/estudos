module.exports = function(app) {
	app.get('/', function (req, res) {
	  res.send('<html><body><p>Index</p></body></html>');
	});	
}
