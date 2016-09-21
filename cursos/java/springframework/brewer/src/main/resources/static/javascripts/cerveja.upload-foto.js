var Brewer = Brewer || {};

Brewer.UploadFoto = (function() {
	
	function UploadFoto() {		
		this.inputNomeFoto = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');		
		this.htmlFotoCervejaTemplate = $('#foto-cerveja').html();
		this.template = Handlebars.compile(this.htmlFotoCervejaTemplate);		
		this.containerFotoCerveja = $('.js-container-foto-cerveja');		
		this.uploadDrop = $('#upload-drop');		
		this.urlFotos = this.containerFotoCerveja.data('url-fotos');		
	}
	
	UploadFoto.prototype.iniciar = function() {
		var settings = {
				type: 'json',
				filelimit: 1,
				allow: '*.(jpg|jpeg|png)',
				// acessar o atributo que contem a url da action para ser executada
				action: this.urlFotos,
				// 'this' eu passo como parametro para para fazer um vinculo com o contexto do objeto que esta em execução.
				// e assim também ter acesso a todas as propriedades deste objeto
				complete: onUploadCompleto.bind(this) 
		}
		
		UIkit.uploadSelect($('#upload-select'), settings);
		UIkit.uploadDrop(this.uploadDrop, settings);
		
		// a função call obriga this a ser executado dentro do contexto
		if (this.inputNomeFoto.val()) {
			onUploadCompleto.call(this, { nome:  this.inputNomeFoto.val(), contentType: this.inputContentType.val()});
		}
	}
	
	function onUploadCompleto(resposta) {
		this.inputNomeFoto.val(resposta.nome);
		this.inputContentType.val(resposta.contentType);
		
		this.uploadDrop.addClass('hidden');
		var htmlFotoCerveja = this.template({nomeFoto: resposta.nome});
		this.containerFotoCerveja.append(htmlFotoCerveja);
		
		// realizo o seletor nesta parte do codigo por este so ira estar acessivel a partir do carregamento do template	
		$('.js-remove-foto').on('click', onRemoverFoto.bind(this, resposta.nome));
	}
	
	function onRemoverFoto(nome) {
		$.ajax({
	        url: this.urlFotos + '/temp/' + nome,
	        method: 'DELETE'
	    });
		
		$('.js-foto-cerveja').remove();
		this.uploadDrop.removeClass('hidden');
		this.inputNomeFoto.val('');
		this.inputContentType.val('');
	}	
	
	return UploadFoto;
	
})();

$(function() {
	
	var uploadFoto = new Brewer.UploadFoto();
	uploadFoto.iniciar();	
		
}); 