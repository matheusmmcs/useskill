
package br.ufpi.util;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Component
@SessionScoped
public class WebCliente {

    private String url;
    private WebClient webClient;
    private HtmlPage htmlPage;
    private StringBuilder conteudo;

    public WebCliente() {
        webClient = new WebClient();
        webClient.getBrowserVersion().setBrowserLanguage("pt-br");
    }

    public void removeBarra() {
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
    }

    public StringBuilder getPaginaCarregada(String url) {
        if (url != null) {
            if (!url.equals("")) {
                if (url.indexOf("http") == 0) {
                    this.url = url;
                    removeBarra();
                    try {
                        htmlPage = webClient.getPage(this.url);
                        conteudo = new StringBuilder(htmlPage.asXml());
                    } catch (Exception ex) {
                        return new StringBuilder(ex.getMessage());
                    }
                    return conteudo;
                } else {
                    return new StringBuilder("Variável url necessita de um protocolo...");
                }
            } else {
                return new StringBuilder("Variável url vazia...");
            }

        } else {
            return new StringBuilder("Variável url não recebida...");
        }
    }
    
    
    //problema encontrado: http://sistemaseasy.ufpi.br/easy/css/estilo.css
//String urlPagina = "http://sistemaseasy.ufpi.br/easy/";
//String urlPagina = "http://sistemaseasy.ufpi.br/redmine/projects/projeto11/issues/report";
//String urlPagina = "http://ticketscead.uapi.edu.br/redmine/projects/subcleiton/issues/new";
//FailingHttpStatusCodeException -> não encontrou um arquivo, como: http://www.cidadeverde.com/Scripts/swfobject_modified.js

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
