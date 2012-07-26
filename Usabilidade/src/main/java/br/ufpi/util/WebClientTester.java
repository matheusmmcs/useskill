package br.ufpi.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebClientTester {

	/**
	 * Utilizado para determinar forma de passagem de parâmetros
	 */
	public static enum HttpMethod {
		POST, GET;
	}

	/**
	 * Método que codifica uma String para um determinado charset, no caso
	 * utiliza-se UTF-8 como default
	 * 
	 * @param component
	 *            = String que será convertida
	 * @return String convertida para UTF-8
	 */
	private static String encodeURIComponent(String component) {
		try {
			return URLEncoder.encode(component, "UTF-8");
		} catch (Exception e) {
		}
		return component;
	}

	/**
	 * Converte os parametros passados para uma String no tipo: chave=valor&
	 * 
	 * @param params
	 *            = Mapa de parametros
	 * @return String com os parametros no formato chave=valor&
	 */
	private static String mapToURLMap(Map<String, Object> params) {
		if (params != null && !params.isEmpty()) {
			StringBuilder str = new StringBuilder();
			
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				str.append(entry.getKey());
				str.append("=");
				str.append(encodeURIComponent(entry.getValue().toString()));
				str.append("&");
			}
			
			str.deleteCharAt(str.length() - 1);
			return str.toString().replaceAll("\\s", "+");
		}
		return null;
	}

	/**
	 * A partir de uma url, este metodo retorna o código HTML capturado.
	 * 
	 * @param urlPagina
	 *            = url da pagina desejada
	 * @param method
	 *            = metodo utilizado para receber os conteudos de parametros
	 * @return String do conteudo capturado
	 */
	public static String getPageContent(String urlPagina, HttpMethod method, Map<String, Object> params, Map<String, Object> headers) {
		try {
			String paramsStr = mapToURLMap(params);
			// se houver parametro a ser passado pelo metodo get
			if (method.equals(HttpMethod.GET) && paramsStr != null) {
				urlPagina = urlPagina + "?" + paramsStr;
			}

			URL url = new URL(urlPagina);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			for (Map.Entry<String, Object> entry : headers.entrySet()) {
				conn.addRequestProperty(entry.getKey(), entry.getValue().toString());
			}

			if (method.equals(HttpMethod.POST)) {
				conn.setDoInput(true);
				conn.setDoOutput(true);
				OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
				writer.write(paramsStr);
			}

			Map<String, List<String>> responseHeaders = conn.getHeaderFields();
			if (responseHeaders.containsKey("Set-Cookie")) {
				String setCookies = responseHeaders.get("Set-Cookie").get(0);
				String[] parts = setCookies.split(";\\s+");
				headers.put("Cookie", parts[0]);
			}
			if (responseHeaders.containsKey("Cookie")) {
				String setCookies = responseHeaders.get("Cookie").get(0);
				String[] parts = setCookies.split(";\\s+");
				headers.put("Cookie", parts[0]);
			}

			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {

				// imprimir todos os headers do servidor
				/*
				 * for (Map.Entry<String, Object> entry : headers.entrySet()) {
				 * System.out.println("--------------HEADER-------------------"
				 * + entry.getKey() + " : " + entry.getValue().toString()); }
				 */

				StringBuilder str = new StringBuilder();
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));

				while ((line = br.readLine()) != null) {
					str.append(line);
					str.append("\n");
				}

				str.deleteCharAt(str.length() - 1);
				return str.toString();
			} else {
				throw new RuntimeException("HTTP ERROR: <br/><br/>"
						+ responseCode);
			}
		} catch (Exception ex) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PrintStream writer = new PrintStream(out);
			ex.printStackTrace(writer);
			return out.toString();
		}
	}

	/**
	 * Método que modifica os links do código html passado, inserindo o dominio antes dos mesmos
	 * @param html = html da página a ser modificada
	 * @param domain = domínio que será inserido
	 * @param upperDomain = 
	 * @return
	 */
	public static String prependLinks(String html, String domain, String upperDomain) {
		if (!domain.endsWith("/")) {
			domain = domain + "/";
		}

		StringBuffer codigo = new StringBuffer();
		String addr;
		Matcher m = Pattern
				.compile(
						"(?i)(src=|href=|url\\(\\s*)(['\\\"]?)([^'\\\"\\)]+)(['\\\"\\)]?)")
				.matcher(html);

		while (m.find()) {
			addr = m.group(3);
			if (addr.startsWith("http") || addr.startsWith("#")) {
				m.appendReplacement(codigo, "$1$2$3$4");
			} else if (addr.startsWith("/")) {
				m.appendReplacement(codigo, "$1$2" + upperDomain + "$3$4");
			} else {
				m.appendReplacement(codigo, "$1$2" + domain + "$3$4");
			}
		}
		m.appendTail(codigo);

		return codigo.toString();
	}

	public String injectCodeAtHead(String html, String code) {
		int location = html.lastIndexOf("</head>");
		html = html.substring(0, location) + code + html.substring(location);

		return html;
	}

	public String injectCodeAtBody(String html, String code) {
		int location = html.lastIndexOf("<body>");
		location += 6;
		System.out.println("INJECT AT BODY(" + location + "): " + code);
		html = html.substring(0, location) + code + html.substring(location);

		return html;
	}

	public static String converteLink(String html, Integer idTarefa, String urlFerramenta, String upperDomain) {
		String page = urlFerramenta + "?url=";
		StringBuffer codigo = new StringBuffer();
		String addr;
		Matcher m = Pattern.compile("(?i)(<a.*?href=|<form.*?action=)(['\"]?)([^'#\"]+)(['\"]?)").matcher(html);

		while (m.find()) {
			addr = m.group(3);
			if (addr.startsWith("http") || addr.startsWith("#")) {
				m.appendReplacement(codigo, "$1$2" + urlFerramenta + "?idTarefa="+ idTarefa + "&url=$3$4");
			} else if (addr.startsWith("/")) {
				//PROBLEMA NA INSERÇÃO DO ID DA TAREFA
				m.appendReplacement(codigo, "$1$2" + urlFerramenta + "?idTarefa="+ idTarefa + "&url=" + upperDomain + "$3$4");
			}
		}
		m.appendTail(codigo);

		return codigo.toString();
	}

	/**
	 * Metodo que retorna uma string com o HTML editado de uma determinada
	 * pagina.
	 * 
	 * @param urlFerramenta
	 *            = url da pagina da ferramenta
	 * @param url
	 *            = url orginal a ser carregada
	 * @param idTarefa
	 *            = id da tarefa a ser testada
	 * @param parameters
	 *            = parametros recebidos na requisicao
	 * @param method
	 *            = metodo da requisicao
	 * 
	 * @return String = String da pagina modificada
	 */
	public static String loadPage(String urlFerramenta, String url, Integer idTarefa, Map<String, String[]> parameters, String method, CookieManager cookieManager) {
		
		Map<String, Object> parametros = new HashMap<String, Object>();

		String value = "";
		for (String key : parameters.keySet()) {
			List<String> wordList = Arrays.asList(parameters.get(key));
			for (String e : wordList) {
				value += e;
			}
			parametros.put(key, value);
			value = "";
		}
		
		//definir o domain -> http://www.redmine.org/projects/redmine/roadmap
		String domain = url;

		//definir o upperDomain -> http://www.redmine.org
		String upperDomain;
		int domainEnd = domain.indexOf("/", domain.indexOf("://") + 3);
		if (domainEnd < 0) {
			upperDomain = domain;
		} else {
			upperDomain = domain.substring(0, domainEnd);
		}
		if (upperDomain.endsWith("/")) {
			upperDomain = upperDomain.substring(0, upperDomain.length() - 1);
		}
		
		//System.out.println("DOMAIN: "+domain+" - UPPERDOMAIN: "+upperDomain);
		String paramsPost = null;
		if(method.equalsIgnoreCase("POST")){
			paramsPost = mapToURLMap(parametros);
			//System.out.println("PARAMSPOST: "+paramsPost);
		}

		String html;
		try {
			html = CookieManager.getContent(url, upperDomain, cookieManager, paramsPost);
			html = prependLinks(html, domain, upperDomain);
			html = converteLink(html, idTarefa, urlFerramenta, upperDomain);
			return html;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://globo.com/");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		System.out.println("Response Code = " + conn.getResponseCode());
	}
}
