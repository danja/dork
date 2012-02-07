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
}
