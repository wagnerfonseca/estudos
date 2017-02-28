/** Aula 23.5 - Itens de vendas 
 * Script responsavel pelos itens da venda
 * #CadastroMestreDetalhe */

Brewer.TabelaItens = (function () {
	
	function TabelaItens(autocomplete) {
		this.autocomplete = autocomplete;
		this.tabelaCervejasContainer = $('.js-tabela-cervejas-container');
		this.uuid = $('#uuid').val();
		
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
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
		
		resposta.done(onItemAtualizadoServidor.bind(this));
	}
	
	
	/**
	 * Alterar Itens
	 * */
	function onQuantidadeItemAlterado(evento) {
		var input = $(evento.target); // o input que disparou o evento
		var quantidade = input.val(); 
		
		if (quantidade <= 0) {
			input.val(1);
			quantidade = 1;
		}	
		
		var codigoCerveja = input.data('codigo-cerveja');
		
		var resposta = $.ajax({
			url: 'item/' + codigoCerveja,
			method: 'PUT',
			data: {
				quantidade: quantidade,
				uuid: this.uuid
			}			
		});
		
		resposta.done(onItemAtualizadoServidor.bind(this));
		
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
		
		resposta.done(onItemAtualizadoServidor.bind(this));
	}
	
	
	function onDoubleClickShowDeleteItem(evento) {
		// evento.target onde eu cliquei
		// evento.currentTarget <- quem escuta o meu envento que neste caso é a tabela de itens ->> "js-tabela-item"
		
		// var item = $(evento.currentTarget);       >>> 
		// item.toggleClass('solicitando-exclusao')  >>>  $(this).toggleClass('solicitando-exclusao')
		
		$(this).toggleClass('solicitando-exclusao');
	}
	
	/**
	 * Atualiza o html quem vem do arquivo "TabelaItensVenda.html"
	 * e renderiza dentro do container
	 * */	
	function onItemAtualizadoServidor(html) {
		// Renderiza o HTML dos itens
		this.tabelaCervejasContainer.html(html);
		
		// Alterar quantidade do item de venda
		var quantidadeItemInput = $('.js-tabela-cerveja-quantidade-item'); 
		quantidadeItemInput.on('change', onQuantidadeItemAlterado.bind(this));
		quantidadeItemInput.maskMoney({ precision: 0, thousands: ''});
		
		// Deletar item de venda - botao
		var tabelaItem = $('.js-tabela-item'); 
		tabelaItem.on('dblclick', onDoubleClickShowDeleteItem);
		// Deletar item de venda - comando
		$('.js-exclusao-item-btn').on('click', onExclusaoItemClick.bind(this));
		
		
		
		// Criando um evento que pode ser escutado
		// Um emissor envia dados para outros scripts
		this.emitter.trigger('tabela-itens-atualizada', tabelaItem.data('valor-total'));
		
	}
	
	return TabelaItens;
	
}());