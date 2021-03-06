// Objeto para representar 
var Brewer = Brewer || {};

Brewer.MaskMoney = (function() {
	
	function MaskMoney() { //funcao que sera executada, funcao construtora
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain'); // Deixa de ser uma variavel para ser um propriedade		
	}
	
	MaskMoney.prototype.enable = function() { // Habilitar o comportamento
		/*this.decimal.maskMoney({decimal: ',', thousands: '.' });	
		this.plain.maskMoney({ precision: 0, thousands: '.'  });	*/
		this.decimal.maskNumber({decimal: ',', thousands: '.' });	
		this.plain.maskNumber({ integer: true, thousands: '.'  });	
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

Brewer.MaskCep = (function() {
	
	function MaskCep() {
		this.inputCep = $('.js-cep');
	}
	
	MaskCep.prototype.enable = function() {
		this.inputCep.mask('00.000-000');
	}
	
	return MaskCep;
	
}());

Brewer.MaskDate = (function(){
	
	function MaskDate() {
		this.inputDate = $('.js-date');
	}
	
	MaskDate.prototype.enable =  function() {
		this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation: 'bottom', 
			language: 'pt-BR',
			autoclose: 'true'
		});
	}
	
	return MaskDate;
}());

/*
 * O CSRF é necessário em todas as requisições diferente de GET, ou seja para POST, PUT e DELETE 
 * Para não ser necessário ficar repetindo o codigo referente ao Token do CSRF para os envio de dados via o comando $.ajax
 * */
Brewer.Security = (function(){
	
	function Security() {
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();
	}
	
	Security.prototype.enable = function () {
		// todas as vezes que for enviar uma requisição ajax
		$(document).ajaxSend(function(event, jqxhr, settings) {
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}	
	
	
	return Security;
	
}());


// metodo estatico
numeral.language('pt-br');

Brewer.formatarMoeda = function(valor) {
	//numeral.language('pt-br');
	return numeral(valor).format('0,0.00');
}

Brewer.recuperarValor =  function(valorFormatado) {
	//numeral.language('pt-br');
	return numeral().unformat(valorFormatado);
}


$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
	
	var maskPhoneNumber = new Brewer.MaskPhoneNumber();
	maskPhoneNumber.enable();	
	
	var maskCep = new Brewer.MaskCep();
	maskCep.enable();
	
	var  maskDate = new Brewer.MaskDate();
	maskDate.enable();
	
	var security =  new Brewer.Security();
	security.enable();
});