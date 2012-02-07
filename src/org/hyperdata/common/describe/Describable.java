/**
 * 
 */
package org.hyperdata.common.describe;

/**
 * @author danny
 *
 */
public interface Describable { // using Visitor pattern
	public void acceptDescriber(Describer describer);
}
