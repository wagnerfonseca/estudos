// Objeto para representar 
var Brewer = Brewer || {};

Brewer.MaskMoney = (function() {
	
	function MaskMoney() { //funcao que sera executada, funcao construtora
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain'); // Deixa de ser uma variavel para ser um propriedade		
	}
	
	MaskMoney.prototype.enable = function() { // Habilitar o comportamento
		this.decimal.maskMoney({decimal: ',', thousands: '.' });	
		this.plain.maskMoney({ precision: 0, thousands: '.'  });		
	}
	
	return MaskMoney; // 
	
})(); // () <= para tornar esse modulo executavel

$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
});