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

Brewer.MaskPhoneNumber = (function() {
	function MaskPhoneNumber() {
		this.inputPhoneNumber = $('.js-phone-number');		
	}
	
	MaskPhoneNumber.prototype.enable = function() {
		var maskBehavior = function (val) {
		  return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		};
		
		var options = {
		  onKeyPress: function(val, e, field, options) {
		      field.mask(maskBehavior.apply({}, arguments), options);
		    }
		};

		this.inputPhoneNumber.mask(maskBehavior, options);
	}
	
	return MaskPhoneNumber;
}());

$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
	
	var maskPhoneNumber = new Brewer.MaskPhoneNumber();
	maskPhoneNumber.enable();	
});