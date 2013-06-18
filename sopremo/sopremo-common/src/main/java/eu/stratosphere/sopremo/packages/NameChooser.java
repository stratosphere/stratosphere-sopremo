package eu.stratosphere.sopremo.packages;


public interface NameChooser {
	public String choose(String[] nouns, String[] verbs, String[] adjectives, String[] prepositions);
}