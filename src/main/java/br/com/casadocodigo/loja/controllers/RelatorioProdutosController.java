package br.com.casadocodigo.loja.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;

@Controller
@RequestMapping("/relatorio-produtos")
public class RelatorioProdutosController {
	
	@Autowired
	private ProdutoDAO dao;
		
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> reportJson(@RequestParam(value = "data", required = false) String data) throws ParseException{
		Map<String, Object> report = new LinkedHashMap<String, Object>();
		List<Produto> produtoList;
		
		
		if(data==null) {
			produtoList = dao.listar();
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(data);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			produtoList = dao.listarMaiorQueData(cal);
		}
	    
	    Calendar cal = Calendar.getInstance();
	    
	    report.put("dataGeracao", cal);
	    report.put("Quantidade", produtoList.size());
	    report.put("Produtos", produtoList);
	  
	    return report;
	}

}
