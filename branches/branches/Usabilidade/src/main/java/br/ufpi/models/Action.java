/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Cleiton
 */
@Entity
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Fluxo fluxo;
    private String sActionType;
    /**
     * Tempo em que uma ação foi clicada depois que a págian foi aberta.
     */
    private Double sTime;
    private String sUrl;
    private String sContent;
    private String sTag;
    private String sTagId;
    private String sTagClass;
    private String sTagName;
    public String getsTagName() {
		return sTagName;
	}

	public void setsTagName(String sTagName) {
		this.sTagName = sTagName;
	}

	public String getsTagValue() {
		return sTagValue;
	}

	public void setsTagValue(String sTagValue) {
		this.sTagValue = sTagValue;
	}






	private String sTagType;
    private String sTagValue;
    private int sPosX;
    private int sPosY;
   


    public String getsActionType() {
		return sActionType;
	}

	public void setsActionType(String sActionType) {
		this.sActionType = sActionType;
	}

	public Double getsTime() {
		return sTime;
	}

	public void setsTime(Double sTime) {
		this.sTime = sTime;
	}

	public String getsUrl() {
		return sUrl;
	}

	public void setsUrl(String sUrl) {
		this.sUrl = sUrl;
	}

	public String getsContent() {
		return sContent;
	}

	public void setsContent(String sContent) {
		this.sContent = sContent;
	}

	public String getsTag() {
		return sTag;
	}

	public void setsTag(String sTag) {
		this.sTag = sTag;
	}

	public String getsTagId() {
		return sTagId;
	}

	public void setsTagId(String sTagId) {
		this.sTagId = sTagId;
	}

	public String getsTagClass() {
		return sTagClass;
	}

	public void setsTagClass(String sTagClass) {
		this.sTagClass = sTagClass;
	}

	public String getsTagType() {
		return sTagType;
	}

	public void setsTagType(String sTagType) {
		this.sTagType = sTagType;
	}

	public int getsPosX() {
		return sPosX;
	}

	public void setsPosX(int sPosX) {
		this.sPosX = sPosX;
	}

	public int getsPosY() {
		return sPosY;
	}

	public void setsPosY(int sPosY) {
		this.sPosY = sPosY;
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


   

   

	@Override
	public String toString() {
		/*
		return "Acao [id=" + id + ", tipoAcao=" + tipoAcao
				+ ", tempo=" + tempo + ", url=" + url + ", conteudo="
				+ conteudo + ", tag=" + tag + ", tagId=" + tagId
				+ ", tagClass=" + tagClass + ", tagName=" + tagName
				+ ", tagValue=" + tagValue + ", posicaoPaginaY="
				+ posicaoPaginaY + ", posicaoPaginaX=" + posicaoPaginaX
				+ ", tagType=" + tagType + "]"+"<br/>";
		 */
		
		return "<tr><td>" + id + "</td><td>" + sTagType
				+ "</td><td>" + sTime + "</td><td>" + sUrl + "</td><td>"
				+ sContent + "</td><td>" + sTag + "</td><td>" + sTagId
				+ "</td><td>" + sTagClass + "</td><td>" + sTagName
				+ "</td><td>" + sTagValue + "</td><td>"
				+ sPosY + "</td><td>" + sPosX
				+ "</td><td>" +sTagType + "</td></tr>";
	}
    
}
