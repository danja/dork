#!/bin/sh
#
# dork (Description of Runtime Klasses) demo
#
# demo depends on:
#     (Java)
#     Redland (rasqal/roqet) - http://librdf.org/rasqal/roqet.html
#     xsltproc - http://xmlsoft.org/XSLT/xsltproc2.html
#
# both are available for recent Ubuntu via synaptic
#
# https://github.com/danja/dork
# danja 2012-01-19
#

echo generating the demo RDF...
java -cp dork.jar org.hyperdata.common.describe.Example > example/example.ttl
echo ok.

echo running SPARQL query against the RDF...
roqet -q -r xml utils/to_dot.rq -D example/example.ttl > example/example.sr
echo ok.

echo transforming query results into dot format...
xsltproc utils/to_dot.xsl example/example.sr > example/example.dot
echo ok.

echo run spring-layout on dot data and produce image...
neato -Goverlap=scale -Tpng example/example.dot > example/example.png
echo done.
echo Result is example/example.png

