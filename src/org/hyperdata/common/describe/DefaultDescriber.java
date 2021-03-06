/**
 * 
 */
package org.hyperdata.common.describe;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author danny
 * 
 */
public class DefaultDescriber implements Described, Describer {
	
	public static boolean WITH_JAVA = false;

	public static Map<String, String> nsMap = new HashMap<String, String>();
	
	public static Map<String, String> typeMap = new HashMap<String, String>();

	static {
		nsMap.put("java.lang", "http://purl.org/stuff/code/java");
		nsMap.put("org.hyperdata", "http://hyperdata.org/code");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.Described#describe()
	 */
	@Override
	public String describe() {
		return getDescription(this);
	}

	// Set<String> variables = new HashSet<String>();

	public void describeVariable(Object object, String variableName) {
		try {
			Field field = object.getClass().getDeclaredField(variableName);
			System.out.println("field " + variableName + " = "
					+ field.get(object));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static String getURI(Object object) {
		return extractClassURI(object.getClass());
	}

	/**
	 * 
	 * This is partly hardcoded the length of the package name is significant
	 * 
	 * e.g. org.hyperdata.common.Describer =>
	 * http://hyperdata.org/projects/common/code/org/hyperdata/common/Describer
	 * 
	 * @param object
	 * @return
	 */
	public static String extractClassURI(Class klass) {

		String javaName = klass.getCanonicalName();
		// System.out.println("JAVA " + javaName);

		Iterator<String> keys = nsMap.keySet().iterator();
		String uri = Describer.defaultBase;

		while (keys.hasNext()) {
			String key = keys.next();
			if (javaName.startsWith(key)) {
				javaName = javaName.substring(key.length());
				uri = nsMap.get(key);
			}
		}

		String[] split = javaName.split("\\.");
		// System.out.println(split.length);

		// uri += split[1] + "." + split[0];
		// uri += "/projects/" + split[2] + "/code";
		for (int i = 1; i < split.length; i++) {
			uri += "/" + split[i];
		}
		return uri;
	}

	public static String turtleFromVariable(String name, Object variable) {
		String turtle = "# variable";
		if (variable instanceof Double) {
			turtle += "Double";
		}
		return turtle;
	}

	public static String getDescription(Object object) {
		
		Class klass = object.getClass();

		String description = "\n### describing instance of "+klass.getName()+"\n";
		description += getDescription(klass);

		Class[] interfaces = klass.getInterfaces();

		boolean implementsNamed = false;
		boolean implementsDescribed = false; // not yet used

		for (int i = 0; i < interfaces.length; i++) {
			if (extractClassURI(interfaces[i]).equals( // not used, was getting
														// into an infinite loop
					"http://hyperdata.org/code/common/describe/Described")) {
				implementsDescribed = true;
			}
			if (extractClassURI(interfaces[i]).equals(
					"http://hyperdata.org/code/common/describe/Named")) {
				implementsNamed = true;
			}
		}

		if (implementsNamed) { // FIXME never gets here - is foaf:name even approapriate???
			description += "[ foaf:name \"" + ((Named) object).getName()
					+ "\" ] a <" + extractClassURI(klass) + "> .\n";
		}
		description +="##############\n";
		return description;
	}

	public static String getDescription(Class klass) {
		if(WITH_JAVA){
			return getJavaDescription(klass);
		}
		return "";
	}
	
	public static String getJavaDescription(Class klass) {

		String description = "\n### describing Class "+klass.getName()+"\n";
		List<Class> classes = new ArrayList<Class>(); // collected for labeling

		String classURI = extractClassURI(klass);

		description += "<" + classURI + "> a java:Class ;\n";

		classes.add(klass);
		// description += "   rdfs:label \"" + klass.getSimpleName() +
		// "\" ; \n";
		Class[] interfaces = klass.getInterfaces();
		for (int i = 0; i < interfaces.length; i++) {
			description += "java:implements <" + extractClassURI(interfaces[i])
					+ "> ;\n";
			classes.add(interfaces[i]);
		}
		description += "java:extends <"
				+ extractClassURI(klass.getSuperclass()) + "> .\n\n";
		classes.add(klass.getSuperclass());

		// add labels
		for (int i = 0; i < classes.size(); i++) {
			Class c = classes.get(i);
			String uri = extractClassURI(c);
			String label = c.getSimpleName();
			description += "<" + uri + "> rdfs:label \"" + label + "\" .\n";
		}
		return description;
	}
	
	/**
	 * e.g. class java.lang.Integer -> java.lang.Integer
	 * 
	 * @param object
	 * @return
	 */
	public static String getJavaDatatype(Object object){
		return object.getClass().toString().split(" ")[1]; 
	}
	
	public static String getJavaDatatypeAsProperty(Object object){
		String javaName = getJavaDatatype(object);
		String[] split = javaName.split("\\.");
		return "jtype:"+split[split.length -1].toLowerCase();
	}
	
	/**
	 * should maybe use a map, but this'll do for now
	 * 
	 * @param object
	 * @return
	 */
	public static String getDatatypeSuffix(Object object){
		String javaName = getJavaDatatype(object);
		String[] split = javaName.split("\\.");
		String javaType = split[split.length -1].toLowerCase();
		if(javaType.equals("string")) return "";
		if(javaType.equals("integer")) return "^^xsd:int";
		return "^^xsd:"+javaType;
	}

	public static void save(String filename, String string) {
		try {
			// Create file
			FileWriter writer = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(writer);
			out.write(string);
			writer.flush();
			out.close();
		} catch (Exception e) {// Catch exception if any
			e.printStackTrace();
		}
	}
	
	public static String getTypedDescription(Named named, String type) {
		String description = DefaultDescriber.getDescription(named);
		description += "<"+named.getURI()+"> a "+type+" .";
	//	description += named.super.describe();
		
//		description += named.getClass()
//        .getSuperclass()
//        .getMethod("describe", new Class[]{} )
//        .invoke(  named.getClass().getSuperclass().newInstance() ,new Object[]{}  );
        
		return description;
	}
	

	/* (non-Javadoc)
	 * @see org.hyperdata.common.describe.Describer#describe(java.lang.Object)
	 */
	@Override
	public String describe(Object object) {
		return getDescription(object);
	}
}
