/**
 * 
 */
package org.hyperdata.common.describe;


/**
 * @author danny
 *
 */
public class Example extends TestDescriber implements Described, Named {

	public String describe(){
		String description = DefaultDescriber.getDescription(this);
		String uri = DefaultDescriber.getURI(this);
		description += "<"+uri+"> dc:description \"An example program\" .\n";
		return description;
	}
	
	public static void main(String[] args){
		Example example = new Example();
		TestDescriber td = new TestDescriber();
		
		// namespace prefixes as header
		String description = Describer.namespaces;
		
		// gives properties labels
		description += Describer.vocab;
		
		// example has a describe method, so call it
		description += example.describe();
		
		// get td's description by introspection
		description += DefaultDescriber.getDescription(td);
		
		// get unrelated class's description by introspection
		description += DefaultDescriber.getDescription(ExampleUnrelatedClass.class);
		System.out.println(description);
		DefaultDescriber.save("./example.ttl", description);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.system.Named#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.system.Named#getName()
	 */
	@Override
	public String getName() {
		return "This Example";
	}
}
