package br.ufpi.datamining.analisys;

import java.io.IOException;
import java.util.List;

import ca.pfv.spmf.algorithms.sequentialpatterns.BIDE_and_prefixspan.AlgoPrefixSpan;
import ca.pfv.spmf.algorithms.sequentialpatterns.BIDE_and_prefixspan.SequentialPattern;
import ca.pfv.spmf.algorithms.sequentialpatterns.BIDE_and_prefixspan.SequentialPatterns;
import ca.pfv.spmf.input.sequence_database_list_integers.SequenceDatabase;

public class FrequentSequentialPatternMining {
	
	public static void main(String[] args) throws IOException {
		String teste = "1 -1 2 -1 3 -1 4 -1 5 -1 6 -1 7 -1 8 -1 9 -1 10 -1 11 -1 12 -1 13 -1 14 -1 15 -1 14 -1 16 -1 17 -1 18 -1 14 -1 19 -1 14 -1 20 -1 14 -1 -2\n";
		teste += "1 -1 2 -1 3 -1 4 -1 5 -1 6 -1 7 -1 8 -1 11 -1 10 -1 12 -1 14 -1 15 -1 14 -1 21 -1 22 -1 23 -1 23 -1 24 -1 24 -1 1 -1 -2\n";
		teste += "1 -1 2 -1 3 -1 4 -1 5 -1 6 -1 7 -1 8 -1 10 -1 11 -1 10 -1 11 -1 12 -1 14 -1 -2\n";
		teste += "1 -1 2 -1 3 -1 4 -1 5 -1 6 -1 7 -1 8 -1 14 -1 -2\n";
		teste += "1 -1 2 -1 3 -1 4 -1 31 -1 32 -1 33 -1 34 -1 5 -1 6 -1 7 -1 8 -1 10 -1 11 -1 8 -1 14 -1 -2\n";
		teste += "1 -1 23 -1 2 -1 3 -1 4 -1 31 -1 32 -1 5 -1 6 -1 7 -1 8 -1 10 -1 11 -1 12 -1 35 -1 8 -1 36 -1 37 -1 10 -1 11 -1 12 -1 38 -1 27 -1 28 -1 29 -1 39 -1 -2\n";
		teste += "1 -1 2 -1 3 -1 4 -1 5 -1 6 -1 7 -1 8 -1 10 -1 11 -1 12 -1 25 -1 26 -1 27 -1 28 -1 29 -1 30 -1 -2\n";
		teste += "1 -1 3 -1 2 -1 4 -1 5 -1 7 -1 6 -1 4 -1 5 -1 6 -1 7 -1 4 -1 31 -1 32 -1 5 -1 6 -1 7 -1 8 -1 10 -1 11 -1 12 -1 25 -1 26 -1 27 -1 28 -1 29 -1 30 -1 -2";
		
		FrequentSequentialPatternMining f = new FrequentSequentialPatternMining();
		f.analyze(teste, .5, null, null);
	}

	public void analyze(String sessions, double minsup, Integer maxPatternLength, Integer minPatternLength) throws IOException {
		// Load a sequence database
		SequenceDatabase sequenceDatabase = new SequenceDatabase(); 
		
		// From String
		sequenceDatabase.loadFromString(sessions, "\n");
		
		// print the database to console
		sequenceDatabase.print();
		
		// Create an instance of the algorithm 
		AlgoPrefixSpan algo = new AlgoPrefixSpan();
		if (maxPatternLength != null) {
			algo.setMaximumPatternLength(maxPatternLength);
		}		
		
		// execute the algorithm with minsup = 50 %
		SequentialPatterns patterns = algo.runAlgorithm(sequenceDatabase, minsup, null);  
		algo.printStatistics(sequenceDatabase.size());
		
		System.out.println(" == PATTERNS ==");
		for(List<SequentialPattern> level : patterns.levels) {
			for(SequentialPattern pattern : level){
				if (minPatternLength != null) {
					if (pattern.size() >= minPatternLength) {
						System.out.println(pattern + " support : " + pattern.getAbsoluteSupport());
					}
				} else {
					System.out.println(pattern + " support : " + pattern.getAbsoluteSupport());
				}
			}
		}
	}
	
}
