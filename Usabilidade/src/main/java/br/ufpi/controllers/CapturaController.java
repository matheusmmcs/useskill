package br.ufpi.controllers;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.ufpi.util.WebCliente;

@Resource
public class CapturaController {
  private final HttpServletRequest request; 
  
	public CapturaController(HttpServletRequest request) {
	super();
	this.request = request;
}

	@Get()
    public String teste() {
    	String url = request.getParameter("url");
        Map<String, String[]> parametrosRecebidos = request.getParameterMap();
        String metodo = request.getMethod();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            System.out.println(headerName);
            System.out.println("HN(HN): " + request.getHeader(headerName));
        }


        if (url != null) {
        	return WebCliente.loadPage("http://localhost:8080/Usabilidade/captura/teste", url, parametrosRecebidos, metodo);
        }else{
        	return "erro";
        }
    }
}