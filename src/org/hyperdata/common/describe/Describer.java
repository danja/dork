/**
 * 
 */
package org.hyperdata.common.describe;

/**
 * @author danny
 * 
 */
public interface Describer extends Described { // is a Visitor
	
	public String describe(Object object); // default fallback
	
	// uses slash URIs - final slash left off declaration here
	public static final String defaultBase = "http://purl.org/stuff/code/java";
	
	public final static String vocab = "java:implements a rdf:Property ;\n"
			+ "    rdfs:label \"implements\" .\n"
			+ "java:extends a rdf:Property ;\n"
			+ "    rdfs:label \"extends\" .\n";
	
	public final static String namespaces = ""
			+ "@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
			+ "@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#> .\n"
			+ "@prefix owl:     <http://www.w3.org/2002/07/owl#> .\n"
			+ "@prefix xsd:     <http://www.w3.org/2001/XMLSchema#> .\n"
			+ "@prefix dc:      <http://purl.org/dc/terms/> .\n"
			+ "@prefix foaf:    <http://xmlns.com/foaf/0.1/> .\n"
			+ "@prefix java:    <http://purl.org/stuff/java/> .\n"
			+ "@prefix stuff:       <http://purl.org/stuff/> .\n\n";


}
