package br.ufpi.util;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@ApplicationScoped
@Component
public class BaseUrl {

	public static String BASEURL;
	public static String getInstance(HttpServletRequest request) {
		if (BASEURL == null) {
			String header;
			String[] split = request.getRequestURL().toString().split(request.getContextPath());
			header=split[0]+request.getContextPath();
			BASEURL = header;
		}
		return BASEURL;

	}
}
