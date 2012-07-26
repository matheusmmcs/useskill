package br.ufpi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped
public class CookieManager {
	private List<HttpCookie> COOKIES;

	public CookieManager() {
		COOKIES = Collections.synchronizedList(new ArrayList<HttpCookie>());
	}

	private void removeOldCookies() {
		for (Iterator<HttpCookie> iterator = COOKIES.iterator(); iterator
				.hasNext();) {
			HttpCookie cookie = iterator.next();
			if (cookie.hasExpired()) {
				iterator.remove();
			}
		}
	}

	public void setCookies(String cookieString) {
		setCookies(cookieString, null);
	}

	public void setCookies(String cookieString, String defaultHost) {
		List<HttpCookie> novosCookies = HttpCookie.parse(cookieString);

		System.out.println("SETCOOKIES -> COOKIESTR: " + cookieString
				+ " /nDEFAULTHOST: " + defaultHost);

		for (int i = 0, max = COOKIES.size(); i < max; i++) {
			for (Iterator<HttpCookie> iterator = novosCookies.iterator(); iterator
					.hasNext();) {
				HttpCookie httpCookie = iterator.next();

				if (httpCookie.getDomain() == null && defaultHost != null) {
					httpCookie.setDomain(defaultHost);
				}

				if (COOKIES.get(i).equals(httpCookie)) {
					COOKIES.set(i, httpCookie);
					iterator.remove();
				}
			}
		}

		if (!novosCookies.isEmpty()) {
			COOKIES.addAll(novosCookies);
		}
	}

	public List<HttpCookie> getCookies(String domain) {
		List<HttpCookie> cookies = new LinkedList<HttpCookie>();

		removeOldCookies();

		for (HttpCookie httpCookie : COOKIES) {
			if (HttpCookie.domainMatches(httpCookie.getDomain(), domain)) {
				cookies.add(httpCookie);
			}
		}

		return cookies;
	}

	public List<HttpCookie> getCookies() {
		return COOKIES;
	}

	private static final int REDIRECT_LIMIT = 5;
	private static final int[] REDIRECT_RESPONSE_CODES = {
			HttpURLConnection.HTTP_MOVED_PERM,
			HttpURLConnection.HTTP_MOVED_TEMP };
	private static final String REDIRECT_HEADER = "Location";

	public static String getContent(String url, String upperDomain,
			CookieManager cookieManager) throws IOException {
		return getContent(url, upperDomain, cookieManager, null, null, 0);
	}

	public static String getContent(String url, String upperDomain,
			CookieManager cookieManager, String postParams) throws IOException {
		return getContent(url, upperDomain, cookieManager, postParams, null, 0);
	}

	public static String getContent(String url, String upperDomain,
			CookieManager cookieManager, Map<String, String> headers)
			throws IOException {
		return getContent(url, upperDomain, cookieManager, null, headers, 0);
	}

	public static String getContent(String url, String upperDomain,
			CookieManager cookieManager, String postParams,
			Map<String, String> headers) throws IOException {
		return getContent(url, upperDomain, cookieManager, postParams, headers,
				0);
	}

	private static String getContent(String url, String upperDomain,
			CookieManager cookieManager, String postParams,
			Map<String, String> headers, int redirects) throws IOException {
		if (redirects == REDIRECT_LIMIT) {
			throw new IOException("Limite de redirecionamentos excedido: "
					+ REDIRECT_LIMIT);
		}

		HttpURLConnection.setFollowRedirects(false);

		URL urlObj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
		if (cookieManager != null) {
			List<HttpCookie> cookies = cookieManager.getCookies();

			if (!cookies.isEmpty()) {
				StringBuilder str = new StringBuilder();
				for (int i = 0, max = cookies.size(); i < max; i++) {
					str.append(cookies.get(i));
					if (i < max - 1) {
						str.append("; ");
					}
				}
				//System.out.println("Cookie: " + str.toString());
				conn.addRequestProperty("Cookie", str.toString());
			}
		}

		// inserindo os headers da requisição
		if (headers != null && !headers.isEmpty()) {
			for (Entry<String, String> entry : headers.entrySet()) {
				conn.addRequestProperty(entry.getKey(), entry.getValue());
			}
		}

		// inserindo parametros POST
		if (postParams != null && postParams.trim().length() > 0) {
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8");
			writer.write(postParams);
			writer.flush();
			writer.close();
			//System.out.println("adicionados post params -> " + postParams);
		}

		// definir os cookies da requisição
		if (cookieManager != null) {
			String setCookies = conn.getHeaderField("Set-Cookie");
			if (setCookies == null) {
				setCookies = conn.getHeaderField("Set-Cookie2");
			}
			if (setCookies != null) {
				cookieManager.setCookies(setCookies, urlObj.getHost());
			}
		}

		int responseCode = conn.getResponseCode();
		String urlConn;
		for (int redirectResponseCode : REDIRECT_RESPONSE_CODES) {
			// verifica se é uma das respostas de redirect para tentar carregar
			// a página novamente
			if (responseCode == redirectResponseCode) {
				// verificar se a url começa com um protocolo, ou apenas o /,
				// faltando inserir o upperDomain
				urlConn = conn.getHeaderField(REDIRECT_HEADER);
				if (urlConn.startsWith("/")) {
					urlConn = upperDomain + urlConn;
				}
				System.out.println("REDIRECT TO: " + urlConn);
				return getContent(urlConn, upperDomain, cookieManager,
						postParams, headers, redirects + 1);
			}
		}

		// resposta do HTML
		String line;
		StringBuilder str = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));

		while ((line = br.readLine()) != null) {
			str.append(line);
			str.append("\n");
		}
		str.deleteCharAt(str.length() - 1);		
		
		
		
		return str.toString();
	}

	public static void main(String[] args) throws Exception {
		CookieManager manager = new CookieManager();

		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("login", "matheus@weborn.com.br");
//		paramMap.put("senha", "novasenha");
//		paramMap.put("Entrar", "Entrar");

		String conteudo = getContent(
				"http://www.cidadeverde.com/",
				"http://www.cidadeverde.com/", manager, mapToURLMap(paramMap));
		// String conteudo = getContent("http://127.0.0.1:8888/CRQ18/public/",
		// manager);

		System.out.println("------------------------------");
		System.out.println("Cookies = " + manager.getCookies());
		System.out.println("------------------------------");

		System.out.println(conteudo);
		System.out.println("------------------------------");
		System.out.println("Cookies = " + manager.getCookies());
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
}