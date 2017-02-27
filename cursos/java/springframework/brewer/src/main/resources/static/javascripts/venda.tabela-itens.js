/** Aula 23.5 - Itens de vendas 
 * Script responsavel pelos itens da venda
 * #CadastroMestreDetalhe */

Brewer.TabelaItens = (function () {
	
	function TabelaItens(autocomplete) {
		this.autocomplete = autocomplete;
		this.tabelaCervejasContainer = $('.js-tabela-cervejas-container');
		this.uuid = $('#uuid').val();
	}
	
	TabelaItens.prototype.iniciar =  function() {
		// quero escutar o evento que foi criado em Brewer.Autocomplete
		this.autocomplete.on('item-selecionado', onItemSelecionado.bind(this));		
	}
	
	/**
	 * Adicionar itens
	 * */
	function onItemSelecionado(evento, item) {
		// "item" é referente aos dados enviados 
		// console.log('item', item);
		
		var resposta = $.ajax({
			// Declarando dessa forma ele retira o "localhost:8080:/brewer/venda/nova" e substitui por "localhost:8080:/brewer/venda/item"
			// Se colocar "/item" vai chamar localhost:8080:/brewer/item
			url: 'item',
			method: 'POST',
			data: {
				codigoCerveja: item.codigo,
				uuid: this.uuid
			}
		});
		
		resposta.done(onItemAdicionadoServidor.bind(this));
	}
	
	function onItemAdicionadoServidor(html) {
		// Renderiza o HTML dos itens
		this.tabelaCervejasContainer.html(html);
		
		// Alterar quantidade do item de venda
		$('.js-tabela-cerveja-quantidade-item').on('change', onQuantidadeItemAlterado.bind(this));
		
		// Deletar item de venda - botao
		$('.js-tabela-item').on('dblclick', onDoubleClickShowDeleteItem);
		// Deletar item de venad - comando
		$('.js-exclusao-item-btn').on('click', onExclusaoItemClick.bind(this));
		
	}
	
	/**
	 * Alterar Itens
	 * */
	function onQuantidadeItemAlterado(evento) {
		var input = $(evento.target); // o input que disparou o evento
		var quantidade = input.val(); 
		var codigoCerveja = input.data('codigo-cerveja');
		
		var resposta = $.ajax({
			url: 'item/' + codigoCerveja,
			method: 'PUT',
			data: {
				quantidade: quantidade,
				uuid: this.uuid
			}			
		});
		
		resposta.done(onItemAdicionadoServidor.bind(this));
		
	}
	
	function onDoubleClickShowDeleteItem(evento) {
		// evento.target onde eu cliquei
		// evento.currentTarget <- quem escuta o meu envento que neste caso é a tabela de itens ->> "js-tabela-item"
		
		// var item = $(evento.currentTarget);       >>> 
		// item.toggleClass('solicitando-exclusao')  >>>  $(this).toggleClass('solicitando-exclusao')
		
		$(this).toggleClass('solicitando-exclusao');
	}
	
	/**
	 * Delete itens
	 * */
	function onExclusaoItemClick(evento) {
		var codigoCerveja = $(evento.target).data('codigo-cerveja'); 
		
		var resposta = $.ajax({
			url: 'item/' + this.uuid + '/' + codigoCerveja,
			method: 'DELETE'					
		});
		
		resposta.done(onItemAdicionadoServidor.bind(this));
	}
	
	return TabelaItens;
	
}());

$(function(){
	var autocomplete =  new Brewer.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaItens =  new Brewer.TabelaItens(autocomplete);
	tabelaItens.iniciar();
});