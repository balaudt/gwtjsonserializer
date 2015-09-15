# JSON Serilaizer #

**There is no active development as of now. Meanwhile you may be interested in a project with a similar goal: [Piriti](http://code.google.com/p/piriti/)**

This project aims to (de)serialize Java POJO to JSON strings in GWT environment.  It can be useful in case of non Java server side programming utilizing GWT or Java non GWT-RPC server side programming.

It can be useful in case of non Java server side programming utilizing GWT or Java non GWT-RPC server side programming.

Any POJO implementing marker interface Serializable org.sagarius.gwt.json.client.Serializable? can be serialized. Only the fields of the following types are serialized:

  1. Primitives
  1. Wrapper classes
  1. java.lang.String
  1. java.util.Date
  1. Classes implementing Serializable
  1. Collections of the above types

Only the following collection types are serialized

  1. List
  1. ArrayList
  1. LinkedList
  1. Stack
  1. Vector
  1. Set
  1. HashSet
  1. SortedSet
  1. TreeSet
  1. LinkedHashSet

Getters and setters following Sun standards has to provided for such fields. Classes are deep serialized in case of inheritance.

> An example GWT module with serialization of POJOs with fields with almost all of the above types is also provided. If you are going to use for Java non GWT-RPC, this package is designed to be compatible with FlexJson serializer http://flexjson.sourceforge.net/

Future plans:

> This project was aimed to be used in GWT-GAE-Rest environment and hence serialization of the following types should be done:

  1. Google Key
  1. Blob