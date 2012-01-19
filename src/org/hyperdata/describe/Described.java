/**
 * 
 */
package org.hyperdata.common.describe;

/**
 * Embeds self-descriptions in classes
 * 
 * @author danny
 * 
 */
public interface Described {

	/**
	 * @return machine-readable (Turtle/RDF) description
	 */
	public String describe();
}
