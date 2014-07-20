package br.ufpi.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@ApplicationScoped
@Component
public class ApplicationPath {
	public static String PATH;
	
	public static String getFilePath(String filePath) throws UnsupportedEncodingException {
		return getPath("/../../"+filePath);
	}
	
	public static String getPath(String filePath) throws UnsupportedEncodingException {
		if (PATH == null) {
			URL r = ApplicationPath.class.getResource("/");
			String decoded = URLDecoder.decode(r.getFile(), "UTF-8");
			if (decoded.startsWith("/")) {
			    decoded = decoded.replaceFirst("/", "");
			}
			File f = new File(decoded);
			PATH = "/" + f.getAbsolutePath();
		}
		return PATH + filePath;
	}
}
