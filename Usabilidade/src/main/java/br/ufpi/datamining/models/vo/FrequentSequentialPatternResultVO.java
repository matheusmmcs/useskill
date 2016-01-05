package br.ufpi.datamining.models.vo;

import java.util.List;
import java.util.Set;

import ca.pfv.spmf.algorithms.sequentialpatterns.BIDE_and_prefixspan.SequentialPattern;
import ca.pfv.spmf.patterns.itemset_list_integers_without_support.Itemset;

public class FrequentSequentialPatternResultVO {

	//padrao + tamanho + suporte + sequenciasid;
	private List<Itemset> itemsets;
	private Set<Integer> sequencesIds;
	
	private int itemsetsSize;
	private int supportSize;
	
	public FrequentSequentialPatternResultVO(SequentialPattern pattern) {
		this.setItemsets(pattern.getItemsets());
		this.setSequencesIds(pattern.getSequenceIDs());
		this.setSupportSize(pattern.getAbsoluteSupport());
		this.setItemsetsSize(pattern.getItemsets().size());
	}
	
	
	public List<Itemset> getItemsets() {
		return itemsets;
	}
	public void setItemsets(List<Itemset> itemsets) {
		this.itemsets = itemsets;
	}
	public Set<Integer> getSequencesIds() {
		return sequencesIds;
	}
	public void setSequencesIds(Set<Integer> sequencesIds) {
		this.sequencesIds = sequencesIds;
	}
	public int getItemsetsSize() {
		return itemsetsSize;
	}
	public void setItemsetsSize(int itemsetsSize) {
		this.itemsetsSize = itemsetsSize;
	}
	public int getSupportSize() {
		return supportSize;
	}
	public void setSupportSize(int supportSize) {
		this.supportSize = supportSize;
	}
	
	
	
	
}
