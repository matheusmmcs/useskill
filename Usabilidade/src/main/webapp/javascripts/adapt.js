(function (adpt) {
    var linkPagina = removeUltimaBarra(adpt("#usabilidadeApelation").html())
    var linkRaizPagina = removeUltimaBarra(getRaizPagina())//+"/easy"
    var linkServidor = removeUltimaBarra(location.href)
    var linkRaizServidor = removeUltimaBarra(getPaginaRaizServidor())
    var i;            
       
    var links = adpt("link[href]")
    corrigeRedirect(links, "href")
       
    var scripts = adpt("script[src]")
    corrigeRedirect(scripts, "src")
    
    var imgs = adpt("img[src]")
    corrigeRedirect(imgs, "src")
       
    //Setar páginas -> GET, modificando o link e inserindo o meu antes
    var a = adpt("a[href]")
    corrigeRedirect(a, "href")
    for(var i=0; i<a.length; i++){
        a[i].href = "http://localhost:8084/Crawler/?url="+a[i].href
    }                
                
    var forms = adpt("form[action]")
    for(i=0; i<forms.length; i++){
        //idealmente para / no inicio da action... caso haja outro tipo, fazer diferenciação
        var naction = forms[i].action.replace(window.location.origin+"/", "")
        forms[i].action = linkRaizPagina+naction
    }
    
    /*
    *Função padrão para correção de links, scripts, imgs...
    *
    */
    function corrigeRedirect(array,tipo){
        switch(tipo){
            case "href":
                for(i=0; i<array.length; i++){
                    //alert("0 - "+array[i].href)
                    //os links estão surgindo com o http://localhost:8080 antes dele
                    if(array[i].href.indexOf(linkRaizServidor)==0){
                        //verificacao se possui path para ser removido
                        if(array[i].href.indexOf(linkServidor)==0){
                            array[i].href =  array[i].href.replace(linkServidor,linkRaizPagina);
                        //alert("1.1 - "+array[i].href)
                        }else{
                            array[i].href =  array[i].href.replace(linkRaizServidor,linkRaizPagina);
                        //alert("1.2 - "+array[i].href)
                        }
                    //sai corretamente, com o endereço raiz da pagina original
                    }
           
                    //nao possui o endereco raiz da pagina original no inicio do link css
                    if(array[i].href.indexOf(linkRaizPagina)!=0){
                        //verifica se o primeiro caractere é /, pois pode ser de outro dominio... exemplo: jquery serv. google
                        if(array[i].href.charAt(0)=="/"){
                            array[i].href = linkRaizPagina+array[i].href;
                        //alert("2.1 - "+array[i].href)
                        }
                    }
                //alert("FIM - "+array[i].href)
                }
                return true;
                break;
            case "src":
                for(i=0; i<array.length; i++){
                    //os links estão surgindo com o http://localhost:8080 antes dele
                    if(array[i].src.indexOf(linkRaizServidor)==0){
                        //verificacao se possui path para ser removido
                        if(array[i].src.indexOf(linkServidor)==0){
                            array[i].src =  array[i].src.replace(linkServidor,linkRaizPagina);
                        }else{
                            array[i].src =  array[i].src.replace(linkRaizServidor,linkRaizPagina);
                        }
                    //sai corretamente, com o endereço raiz da pagina original
                    }
           
                    //nao possui o endereco raiz da pagina original no inicio do link css
                    if(array[i].src.indexOf(linkRaizPagina)!=0){
                        //verifica se o primeiro caractere é /, pois pode ser de outro dominio... exemplo: jquery serv. google
                        if(array[i].src.charAt(0)=="/"){
                            array[i].src = linkRaizPagina+array[i].src;
                        }
                    }
                }
                return true;
                break;
                
            default:
                return false;
                break;
        }
    }
     
    /*
    * transforma o link da pagina carregada para ser trabalhada com css e scrits, obetendo até a raiz:
    * original = http://www.google.com/blablabla/teste.do 
    * retorno = http://www.google.com
    */
    function getRaizPagina(){
        var paginaCarregada = adpt("#usabilidadeApelation").html()
        var regex = new RegExp('(http://[a-z|.|0-9]*)')
        if(regex.test(paginaCarregada)){
            return paginaCarregada.match(regex)[0]
        }else{
            return paginaCarregada
        }
    }
           
    /*
    * transforma o link do servidor para ser trabalhada com css e scripts:
    * original = http://127.0.0.1:8888/usabilidade
    * retorno = http://127.0.0.1:8888
    */
    function getPaginaRaizServidor(){
        var url = location.href
        var host = new String(location.host)
        var pos = url.indexOf(host)
        var tam = host.length
        var tamtot = pos+tam
        return substring = url.substr(0,tamtot)
    }
  
    /*
    * remove a última barra, caso possua
    * original = http://127.0.0.1:8888/
    * retorno = http://127.0.0.1:8888
    */
    function removeUltimaBarra(string){
        if(string.substring(string.length-1, string.length)=="/"){
            return string.substring(0, string.length-1)
        }else{
            return string
        }
    }
})(jQuery);