/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.models;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Cleiton
 */
@Entity
public class Acao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Fluxo fluxo;
    private String tipoAcao;
    /**
     * Tempo em que uma ação foi clicada depois que a págian foi aberta.
     */
    private Double tempo;
    private String url;
    private String conteudo;
    private String tag;
    private String tagId;
    private String tagClass;
    private String tagName;
    private String tagValue;
    private int posicaoPaginaY;
    private int posicaoPaginaX;
    @Column(length = 20)
    private String tagType;

    public int getPosicaoPaginaX() {
        return posicaoPaginaX;
    }

    public void setPosicaoPaginaX(int posicaoPaginaX) {
        this.posicaoPaginaX = posicaoPaginaX;
    }

    public int getPosicaoPaginaY() {
        return posicaoPaginaY;
    }

    public void setPosicaoPaginaY(int posicaoPaginaY) {
        this.posicaoPaginaY = posicaoPaginaY;
    }

    public Fluxo getFluxo() {
        return fluxo;
    }

    public void setFluxo(Fluxo fluxo) {
        this.fluxo = fluxo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagClass() {
        return tagClass;
    }

    public void setTagClass(String tagClass) {
        this.tagClass = tagClass;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public Double getTempo() {
        return tempo;
    }

    public void setTempo(Double tempo) {
        this.tempo = tempo;
    }

    public String getTipoAcao() {
        return tipoAcao;
    }

    public void setTipoAcao(String tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
