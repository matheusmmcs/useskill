(function(capt) {
    // classe Acao
    function Acao(acao, tempo, url, conteudo, tag, tagId, tagClass, tagName,
        tagValue, tagType, posicaoPaginaX, posicaoPaginaY) {
        this.tipoAcao = acao;
        this.tempo = tempo;
        this.url = url;
        this.conteudo = htmlEntities(conteudo).substr(0, 200);
        this.tag = tag;
        this.tagId = tagId;
        this.tagClass = tagClass;
        this.tagName = tagName;
        this.tagValue = tagValue;
        this.tagType = tagType;
        this.posicaoPaginaX = posicaoPaginaX;
        this.posicaoPaginaY = posicaoPaginaY;
    }
    // Denifição das variáveis
    var tempoInicial = new Date().getTime();
    var Acoes = new Array();

    capt(document)
    .bind(
    {
        click : function(e) {
            var tagName = e.target.tagName
            tagName = tagName.toUpperCase()

            var tempo = getDiferencaTempo()

            // realizar verificação de tabelas
            if (tagName == "TD") {
                // capt(e.target).css("background",
                // "red")//pintar de vermelho
                // onde clicar
                // se quiser saber do conteúdo interno do
                // elemento clicado,
                // adiciona uma nova div por html() ou prepend()
                var celulaInicial = capt(e.target).html()
                var linhaInicial = capt(e.target).parent()
                .html()

                // adiciono itemClicado na celula clicada e
                // trClicado no inicio
                // da linha clicada
                capt(e.target)
                .html(
                    celulaInicial
                    + "<div id=\"itemClicadoEaSII\"></div>")
                capt(e.target).parent().prepend(
                    "<div id=\"trClicadoEaSII\"></div>")

                // .parent().tagName não deu ->
                // tabelaClicada[0].nodeName
                // recebendo TABLE
                var tabelaClicada = capt(e.target);
                while (tabelaClicada[0].nodeName != "TABLE") {
                    tabelaClicada = tabelaClicada.parent();
                }

                var posicaoTabela = percorreHTMLTable(tabelaClicada
                    .html())

                // remover os html adicionados
                capt(e.target).html(celulaInicial)
                capt(e.target).parent().html(linhaInicial)

                Acoes.push(new Acao("click", tempo,
                    document.location.href, htmlEntities(
                        celulaInicial).substr(0, 200),
                    "[" + posicaoTabela + "] " + tagName
                    + " > TABLE", retira_(capt(
                        e.target).attr("id"))
                    + " > "
                    + retira_(tabelaClicada
                        .attr("id")),
                    retira_(capt(e.target).attr("class"))
                    + " > "
                    + retira_(tabelaClicada
                        .attr("class")),
                    retira_(capt(e.target).attr("name"))
                    + " > "
                    + retira_(tabelaClicada
                        .attr("name")),
                    retira_(capt(e.target).attr("value"))
                    + " > "
                    + retira_(tabelaClicada
                        .attr("value")),
                    retira_(capt(e.target).attr("type"))
                    + " > "
                    + retira_(tabelaClicada
                        .attr("type")),
                    e.pageX, e.pageY))

            } else {
                Acoes.push(new Acao("click", tempo,
                    document.location.href, capt(e.target)
                    .html(), tagName, retira_(capt(
                        e.target).attr("id")),
                    retira_(capt(e.target).attr("class")),
                    retira_(capt(e.target).attr("name")),
                    retira_(capt(e.target).attr("value")),
                    retira_(capt(e.target).attr("type")),
                    e.pageX, e.pageY))
            }
            var href;
            // no caso de links que possuem conteúdos internos
            var tagPaiClick = capt(e.target).parent();
            if (tagPaiClick[0].nodeName == "A") {
                href = tagPaiClick.attr("href")
                if (href != "" && href.indexOf("#") != 0) {
                    //verificar se o botao clicado é o de finalizar
                    
                    enviaJson(getResultadoJson(),false)
                }
            }

            // se for tag A
            if (tagName == 'A') {
                href = capt(e.target).attr("href")
                if (href != "" && href.indexOf("#") != 0) {
                    //verificar se o botao clicado é o de finalizar
                    enviaJson(getResultadoJson(),false)
                }
            }

            // se for input submit
            if (tagName == 'INPUT') {
                if (capt(e.target).attr("type").toUpperCase() == "SUBMIT") {
                    //verificar se o botao clicado é o de finalizar
                    enviaJson(getResultadoJson(),false)
                }
            }
        },
        mousemove : function(e) {
            capt(".1").text(
                "Página (X,Y) - " + e.pageX + " - "
                + e.pageY);
            capt(".2").text(
                "Tela (X,Y)   - " + e.clientX + " - "
                + e.clientY);
        },
        focusout : function(e) {
            var tagName = e.target.tagName
            tagName = tagName.toUpperCase()
            if (tagName == "INPUT") {
                var tempo = getDiferencaTempo()
                Acoes.push(new Acao("focusout", tempo,
                    document.location.href, capt(e.target)
                    .html(), tagName, retira_(capt(
                        e.target).attr("id")),
                    retira_(capt(e.target).attr("class")),
                    retira_(capt(e.target).attr("name")),
                    retira_(capt(e.target).attr("value")),
                    retira_(capt(e.target).attr("type")),
                    e.pageX, e.pageY))
            }
        }
    });

    /*
	 * Método para percorrer o html passado, observando a qual linha/coluna o
	 * objeto pertence
	 */
    function percorreHTMLTable(html) {
        var contTR = 0
        var contTD = 0
        var matches

        // remover espaços indesejados
        html = html.replace(/(\s|\n|\t|\f|\r)/g, '')

        // expressão regular para encontrar até o itemClicadoEaSII
        var regex = /(.*)itemClicadoEaSII/g
        var input = html
        var retorno = ""
        if (regex.test(input)) {
            matches = input.match(regex);
            for ( var match1 in matches) {
                retorno += matches[match1]
            }
        }

        // expressão regular para encontrar o <tr>
        regex = /(\<tr\>)/g
        input = retorno
        if (regex.test(input)) {
            matches = input.match(regex);
            for ( var match2 in matches) {
                contTR++;
            }
        }

        regex = /(trClicadoEaSII.*)/g
        input = retorno
        retorno = ""
        if (regex.test(input)) {
            matches = input.match(regex)
            for ( var match3 in matches) {
                retorno += matches[match3]
            }
        }

        regex = /(\<td\>)/g;
        input = retorno;
        if (regex.test(input)) {
            matches = input.match(regex);
            for ( var match4 in matches) {
                contTD++;
            }
        }

        return contTR + ":" + contTD
    }

    function htmlEntities(str) {
        return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;')
        .replace(/>/g, '&gt;').replace(/"/g, '&quot;');
    }

    function retira_(obj) {
        if (obj == undefined) {
            obj = "_";
        }
        return obj;
    }

    function getResultadoJson() {
        return JSON.stringify(Acoes)
    }

    function getDiferencaTempo() {
        var tempo = new Date().getTime();
        var dif = tempo - tempoInicial;
        var seg = 1000;// var min = seg*60;
        return dif / seg;
    }

    function enviaJson(dados, bool) {
        capt.ajax({
            url : '/Usabilidade/tarefa/save/fluxo/ideal',
            type : "POST",
            dataType : 'json',
            data : {
                'dados': dados, 'completo':bool
            },
            success : function(json) {
                alert("foi");
            },
            error : function(e) {
                console.log(e);
                e.preventDefault()
            },
            statusCode : {
                404 : function() {
                    alert('page not found');
                }
            }
        });
    /*
		 * capt.ajax({ url: "/Usabilidade/tarefa/save/fluxo/ideal", type:
		 * "POST", data: { 'data': eval(dados) }, dataType: "html"
		 * }).done(function( msg ) { alert("Dados: " + msg); });
		 */
    // alert(dados);
    // window.open('http://brunomarota.blogspot.com/2011/03/como-abrir-uma-nova-aba-com-javascript.html','_blank')
    }
})(jQuery);