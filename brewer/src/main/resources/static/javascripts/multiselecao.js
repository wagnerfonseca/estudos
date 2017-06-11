Brewer = Brewer || {};

Brewer.MultiSelecao = (function(){
	
	function MultiSelecao() {
		this.statusBtn = $('.js-status-btn');
		this.selecaoCheckbox = $('.js-selecao');
		this.selecaoTodosCheckbox = $('.js-selecao-todos');
	}
	
	MultiSelecao.prototype.iniciar = function() {
		this.statusBtn.on('click', onStatusBtnClicado.bind(this));
		
		// Checa todos com quando clicar no check box do cabeçalho
		this.selecaoTodosCheckbox.on('click', onSelecaoTodosClicado.bind(this));
		
		this.selecaoCheckbox.on('click', onSelecaoClicado.bind(this));
	}	
	
	function onStatusBtnClicado(event) {
		// Aula 21.4
		var botaoClicado = $(event.currentTarget);
		var status = botaoClicado.data('status');
		var url = botaoClicado.data('url');
		// console.log(botaoClicado.data('status'));
		
		var checkBoxSelecionados = this.selecaoCheckbox.filter(':checked');
		// montar um array de codigo selecionados
		var codigos = $.map(checkBoxSelecionados, function(c) {
			return $(c).data('codigo');
		});
		
		//console.log(codigos);
		
		if (codigos.length > 0) {
			$.ajax({			
				//url: '/brewer/usuarios/status', // enviando a url para o botão
				url: url,
				method: 'PUT',
				data: {
					codigos: codigos,
					status: status
				},
				success: function() {
					//Atualizar pagina. refazer  a requisicao de pagina
					window.location.reload();
				}
			});
		}
		
	}
	
	
	// Aula 21.5
	function onSelecaoTodosClicado() {
		var status = this.selecaoTodosCheckbox.prop('checked'); // captura a propriedade cheched do checkbox
		this.selecaoCheckbox.prop('checked', status);
		statusBotaoAcao.call(this, status);
	}
	
	function onSelecaoClicado() {
		var selecaoCheckboxChecados= this.selecaoCheckbox.filter(':checked');
		// console.log('quantidade de checkboxes selecionados:', selecaoCheckboxChecados.length);
		this.selecaoTodosCheckbox.prop('checked', selecaoCheckboxChecados.length >= this.selecaoCheckbox.length);
		statusBotaoAcao.call(this, selecaoCheckboxChecados.length);
	}
	
	// Para ativar e desativar os botões de ATIVAR e DESATIVAR
	function statusBotaoAcao(ativar) {
		ativar ? this.statusBtn.removeClass('disabled') : this.statusBtn.addClass('disabled');
	}
	
	return MultiSelecao;
	
}());

$(function(){
	var multiSelecao = new Brewer.MultiSelecao();
	multiSelecao.iniciar();
});