var Brewer = Brewer || {};

Brewer.ComboEstado = (function(){
	
	function ComboEstado() {
		this.combo = $("#estado");
		
		// Criar um Objeto que ira auxiliar a lançar eventos
		this.emitter = $({}); // inicializa um objeto do tipo jQuery
		this.on = this.emitter.on.bind(this.emitter); // objeto "on" que lanca eventos
	}
	
	ComboEstado.prototype.iniciar = function(){
		this.combo.on("change", onEstadoAlterado.bind(this));
	}
	
	function onEstadoAlterado() {
		// Quando um estado for alterado
		// lancar um evento que pode ser capturado "ouvido" por outro objeto do javascript
		this.emitter.trigger('alterado', this.combo.val());
	}
	
	return ComboEstado;
	
}());

Brewer.ComboCidade = (function(){
	
	function ComboCidade(comboEstado){
		this.comboEstado = comboEstado;
		this.combo = $("#cidade");
		this.imgLoading = $('.js-img-loading');
	}
	
	ComboCidade.prototype.iniciar = function() {
		reset.call(this);
		// agora eu posso acessar o atributo "on" do objeto comboEstado e "ouvir" o evento lançado 
		this.comboEstado.on('alterado', onEstadoAlterado.bind(this));
	}
	
	// Consigo capturar o valor do "id" da tabela estado dentro de um novo objeto
	function onEstadoAlterado(evento, codigoEstado) { //codigoEstado é o meu "id" da tabela estado
		if (codigoEstado) {
			var resposta = $.ajax({
				url: this.combo.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: { 'estado': codigoEstado }, 
				beforeSend: iniciarRequisicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});
			resposta.done(onBuscarCidadesFinalizado.bind(this));
		} else {
			reset.call(this);
		}
	}
	
	function onBuscarCidadesFinalizado(cidades) {
		// nao é uma boa pratica ficar colocando this.combo.append() essa operação é um pouco lenta		
		var options = [];
		cidades.forEach(function(cidade) {
			//insiro dentro de um array
			options.push('<option value"' + cidade.codigo + '">' + cidade.nome + '</option>');
		});
		// jogo o array  fanzendo um join o valor defaul é ","
		this.combo.html(options.join(''));
		this.combo.removeAttr('disabled');
	}
	
	// zerar o combo de cidade
	function reset() {
		this.combo.html('<option value="">Selecione a cidade</option>');
		this.combo.val('');
		this.combo.attr('disabled', 'disabled');
	}
	
	function iniciarRequisicao() {
		reset.call(this);
		this.imgLoading.show();
	}
	
	function finalizarRequisicao() {
		this.imgLoading.hide();
	}
	
	return ComboCidade ;
	
}());

$(function(){
	
	var comboEstado = new Brewer.ComboEstado();
	comboEstado.iniciar();
	
	var comboCidade = new Brewer.ComboCidade(comboEstado);
	comboCidade.iniciar();
	
	
});