# dork
_Description of Runtime Klasses_

dork is a simple system for getting descriptions of running code. 

*_Either_*
have your classes implement the org.hyperdata.common.describe.Described interface 
this defines one method:

    public String describe()

which should return a Turtle syntax RDF description of the class

*_and/or_*
call the static utility method (in org.hyperdata.common.describe) :

Describer.getDescription(Object object)

with an instance of the object you wish to describe

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
