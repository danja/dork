# dork
_Description of Runtime Klasses_

dork is a simple system for getting descriptions of running code. 

*_Either_*
have your classes implement the Described interface 
this defines one method:

	public String describe()

which should return a Turtle syntax RDF description of the class

*_and/or_*
call the static utility method (in org.hyperdata.common.describe) :

DefaultDescriber.getDescription(Object object)

with an instance of the object you wish to describe

*_and/or_*

// NB. not sure how to handle the string return value yet - need to try in practice

(following the [Visitor](http://en.wikipedia.org/wiki/Visitor_pattern) pattern)  

have your classes implement the Describable interface:
this defines one method:

	public void acceptDescriber(Describer describer);

and create implementations of Describer, supporting the following this interface:

	public String describe(Object object);

Atomic elements:
public void acceptDescriber(Describer describer) {
describer.describe(this);
}

For composite elements:
public void acceptDescriber(Describer describer) {
for(Describable element : this.getElements()) {
element.acceptDescriber(describer);
}
describer.describe(this);
}

## Example

see Example.java

## Utilities
In utils/ there is a SPARQL query and an XSLT transform for converting dork-generated RDF in dot format 
which may be used to generate Java class diagrams
see *run.sh* for details

### Dependencies
dork itself has *no dependencies* except for Java

generation of diagrams requires dot, a SPARQL engine (e.g. Redland/roqet) and an XSLT engine (e.g. xsltproc)

(ignore lib/* for now, that's for an RDF doclet, when I get around to it)

## Issues
not sure how best to do rdf:Lists (and this is probably an issue with other structures)
e.g. ThingList is a list of Thing, really want Thing to return both :
a standalone description
a [description of this node] for inclusion in ThingList

## License
Apache 2.0 or similar
(use as you like, attribution/link appreciated)

Danny Ayers 2012-01-19
http://dannyayers.com
@danja
