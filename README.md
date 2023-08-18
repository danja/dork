# dork

_Description of Runtime Klasses_

dork is a simple system for getting descriptions of running Java code

## Status

### 2023 - rediscovered after 11 years!

I've not looked closely yet, but the idea seems kinda interesting. I'm currently updating old code, have to add this to the list. I haven't done a new Java project in years, more inclined towards Javascript & Python these days, so must also have a look at porting the behaviour (probably already exists).

[Connolly's Bane](https://www.madmode.com/) states :

> **The bane of my existence is doing things I know the computer could do for me.**

I am terrible at managing the things I have to do, organising associated resources, timekeeping etc. I use loads of different apps - desktop, mobile, web & native but it's seriously frustrating that there aren't facilities for transparently sharing information across them. (eg. if there's been an electronic circuit I've been looking at, discover/attach reference links to the project, create draft write-ups via ChatGPT etc etc).

But I very much like [Resource Description Framework](https://en.wikipedia.org/wiki/Resource_Description_Framework) as a lingua franca. It's gained a lot of adoption over the past couple of decades though often slips under the radar. Like HTML pages get riddled with metadata for SEO that shares the same underlying model (eg. [Google Rich Search](https://developers.google.com/search/docs/appearance/structured-data/sd-policies) uses JSON-LD (recommended), Microdata or RDFa - those formats are RDF, RDF-compatible and RDF), lots of big datasets are available as Linked Data, but few of the interfaces are particularly general user-facing.

For my purposes the RDF model offers a great way of organizing data and content, extending the facilities of the Web beyond content into distributed data. So in my bane-mitigation efforts I've being intermittently working on things I can use in my own workflow. Latest versions under the umbrella of [HKMS](https://hyperdata.it/hkms/). **Dork** appears to be one I forgot.

I've put a holding page at

## Usage

_*Either*_
have your classes implement the Described interface
this defines one method:

    public String describe()

which should return a Turtle syntax RDF description of the class

_*and/or*_
call the static utility method (in org.hyperdata.common.describe) :

DefaultDescriber.getDescription(Object object)

with an instance of the object you wish to describe

_*and/or*_

// NB. not altogether sure how to deal with the describe() string return value yet - need to try in practice

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
see _run.sh_ for details

### Dependencies

dork itself has _no dependencies_ except for Java

generation of diagrams requires dot, a SPARQL engine (e.g. Redland/roqet) and an XSLT engine (e.g. xsltproc)

(ignore lib/\* for now, that's for an RDF doclet, when I get around to it)

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
