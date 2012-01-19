/**
 * 
 */
package org.hyperdata.common.describe;

/**
 * @author danny
 *
 */
public class TestDescriber implements Described {

	public String describe(){
		return Describer.getDescription(this);
	}
	
	public static void main(String[] args){
		System.out.println(Describer.namespaces);
		System.out.println(Describer.getDescription(new TestDescriber()));
	}
}
