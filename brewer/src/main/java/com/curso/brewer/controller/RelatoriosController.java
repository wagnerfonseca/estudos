package com.curso.brewer.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.curso.brewer.dto.PeriodoRelatorio;

@Controller
@RequestMapping("/relatorios")
public class RelatoriosController {
	
	@GetMapping("/vendasEmitidas")
	public ModelAndView relatorioVendasEmitidas() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioVendasEmitidas"); //chama a pagina que vai fazer o filtro para o relatorio
		mv.addObject(new PeriodoRelatorio()); //cria o objeto
		return mv;
	}
	
	@PostMapping("/vendasEmitidas")
	public ModelAndView gerarRelatorioVendasEmitidas(PeriodoRelatorio periodoRelatorio) {
		Map<String, Object> parametros = new HashMap<>();
		
		Date dataInicio = Date.from(LocalDateTime.of(periodoRelatorio.getDataInicio(), LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataFim = Date.from(LocalDateTime.of(periodoRelatorio.getDataFim(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());
		
		parametros.put("format", "pdf"); // formato do relatorio
		parametros.put("data_inicio", dataInicio); // nome exato do nome do parametro declarado bo jasper
		parametros.put("data_fim", dataFim);
		
		// informar o nome do relatorio, pq ja esta configurado o caminho(diretorio) no WebConfig
		return new ModelAndView("relatorio_vendas_emitidas", parametros); 
	}

}
