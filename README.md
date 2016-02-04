# gwtjsonserializer
Automatically exported from code.google.com/p/gwtjsonserializer

This project aims to (de)serialize Java POJO to JSON strings in GWT environment. It can be useful in case of non Java server side programming utilizing GWT or Java non GWT-RPC server side programming.

It can be useful in case of non Java server side programming utilizing GWT or Java non GWT-RPC server side programming.

Any POJO implementing marker interface Serializable org.sagarius.gwt.json.client.Serializable can be serialized. Only the fields of the following types are serialized:
- Primitives
- Wrapper classes
- java.lang.String
- java.util.Date
- Classes implementing Serializable
- Collections of the above types
Only the following collection types are serialized
- List
- ArrayList
- LinkedList
- Stack
- Vector
- Set
- HashSet
- SortedSet
- TreeSet
- LinkedHashSet
Getters and setters following Sun standards has to provided for such fields. Classes are deep serialized in case of inheritance.

An example GWT module with serialization of POJOs with fields with almost all of the above types is also provided. If you are going to use for Java non GWT-RPC, this package is designed to be compatible with FlexJson serializer http://flexjson.sourceforge.net/

Future plans:

This project was aimed to be used in GWT-GAE-Rest environment and hence serialization of the following types should be done:

- Google Key
- Blob
