/**
 * 
 */
package org.hyperdata.common.describe;

/**
 * @author danny
 * 
 */
public interface Named {
	
	// these should be deprecated in favour of URIs
	// maybe maintain as localName?
	public void setName(String name);

	public String getName();

	public void setURI(String uri);
	
	public String getURI();
	
	
}
