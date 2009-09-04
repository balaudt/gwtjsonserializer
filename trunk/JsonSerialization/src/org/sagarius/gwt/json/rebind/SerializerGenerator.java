package org.sagarius.gwt.json.rebind;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

public class SerializerGenerator extends Generator {
	private TypeOracle oracle;
	private JClassType baseType;
	private PrintWriter printWriter;
	private String packageName;
	private String genClassname;
	private Set<String> importsList = new HashSet<String>();

	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		oracle = context.getTypeOracle();
		try {
			baseType = oracle.getType(typeName);
			packageName = baseType.getPackage().getName();
			genClassname = baseType.getSimpleSourceName() + "SerializerImpl";
			printWriter = context.tryCreate(logger, packageName, genClassname);

			// Add imports
			// Java imports
			importsList.add("java.util.Collection");
			importsList.add("java.util.List");
			importsList.add("java.util.ArrayList");
			importsList.add("java.util.LinkedList");
			importsList.add("java.util.Stack");
			importsList.add("java.util.Vector");
			importsList.add("java.util.Set");
			importsList.add("java.util.TreeSet");
			importsList.add("java.util.HashSet");
			importsList.add("java.util.LinkedHashSet");
			importsList.add("java.util.SortedSet");
			importsList.add("java.util.Date");
			// GWT imports
			importsList.add("com.google.gwt.core.client.GWT");
			importsList.add("com.google.gwt.json.client.JSONNull");
			importsList.add("com.google.gwt.json.client.JSONNumber");
			importsList.add("com.google.gwt.json.client.JSONString");
			importsList.add("com.google.gwt.json.client.JSONValue");
			importsList.add("com.google.gwt.json.client.JSONObject");
			importsList.add("com.google.gwt.json.client.JSONArray");
			importsList.add("com.google.gwt.json.client.JSONBoolean");
			importsList.add("com.google.gwt.json.client.JSONParser");
			importsList.add("com.google.gwt.json.client.JSONException");
			// Module imports
			importsList.add("org.sagarius.gwt.json.client.Serializer");
			importsList.add("org.sagarius.gwt.json.client.Serializable");
			importsList.add("org.sagarius.gwt.json.client.IncompatibleObjectException");
			importsList.add("org.sagarius.gwt.json.client.SerializerHelper");
			importsList.add("org.sagarius.gwt.json.client.DeserializerHelper");
			// Type specific imports
			importsList.add(typeName);

			String defaultSerializationString = generateDefaultSerialization();
			String typeSerializationString = generateTypeSerialization();
			String defaultDeserializationString = generateDefaultDeserialization();
			String tyepDeserializationString = generateTyepDeserialization();

			StringBuffer buffer = new StringBuffer();
			buffer.append("package " + packageName + ";");
			for (String str : importsList) {
				buffer.append("import " + str + ";");
			}
			buffer.append("public class " + genClassname + " implements Serializer{");
			buffer.append(defaultSerializationString);
			buffer.append(typeSerializationString);
			buffer.append(defaultDeserializationString);
			buffer.append(tyepDeserializationString);
			buffer.append("}");
			printWriter.append(buffer.toString());
			context.commit(logger, printWriter);
			return packageName + "." + genClassname;
		} catch (NotFoundException e) {
			e.printStackTrace();
			throw new UnableToCompleteException();
		}
	}

	private String generateTyepDeserialization() throws NotFoundException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("public Object deSerialize(JSONValue jsonValue) throws JSONException{");

		// Return null if the given object is null
		buffer.append("if(jsonValue instanceof JSONNull){");
		buffer.append("return null;");
		buffer.append("}");

		// Throw Incompatible exception is JsonValue is not an instance of
		// JsonObject
		buffer.append("if(!(jsonValue instanceof JSONObject)){");
		buffer.append("throw new IncompatibleObjectException();");
		buffer.append("}");

		// Initialise JsonObject then
		String baseTypeName = baseType.getSimpleSourceName();
		buffer.append("JSONObject jsonObject=(JSONObject)jsonValue;");
		buffer.append(baseTypeName + " mainResult=new " + baseTypeName + "();");
		buffer.append("Serializer serializer;");
		buffer.append("JSONArray inputJsonArray=null;");
		buffer.append("int inpJsonArSize=0;");
		buffer.append("JSONValue fieldJsonValue=null;");

		// Start deSerialisation
		List<JField> allFields = new ArrayList<JField>();
		JField[] fields = baseType.getFields();
		allFields.addAll(Arrays.asList(fields));
		if (baseType.isAssignableTo(oracle.getType("org.sagarius.gwt.json.client.Serializable"))) {
			boolean flag = true;
			JClassType superClassType = baseType;
			while (flag) {
				superClassType = superClassType.getSuperclass();
				if (superClassType.isAssignableTo(oracle.getType("org.sagarius.gwt.json.client.Serializable"))) {
					allFields.addAll(Arrays.asList(superClassType.getFields()));
				} else {
					flag = false;
				}
			}
		}
		fields = new JField[allFields.size()];
		allFields.toArray(fields);

		for (JField field : fields) {
			JType fieldType = field.getType();
			String fieldName = field.getName();
			String fieldNameForGS = getNameForGS(fieldName);
			buffer.append("fieldJsonValue=jsonObject.get(\"" + fieldName + "\");");
			if (fieldType.isPrimitive() != null) {
				JPrimitiveType fieldPrimitiveType = (JPrimitiveType) fieldType;
				JClassType fieldBoxedType = oracle.getType(fieldPrimitiveType.getQualifiedBoxedSourceName());
				if (fieldBoxedType.getQualifiedSourceName().equals("java.lang.Short")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getShort(fieldJsonValue));");
				} else if (fieldBoxedType.getQualifiedSourceName().equals("java.lang.Byte")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getByte(fieldJsonValue));");
				} else if (fieldBoxedType.getQualifiedSourceName().equals("java.lang.Long")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getLong(fieldJsonValue));");
				} else if (fieldBoxedType.getQualifiedSourceName().equals("java.lang.Integer")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getInt(fieldJsonValue));");
				} else if (fieldBoxedType.getQualifiedSourceName().equals("java.lang.Float")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getFloat(fieldJsonValue));");
				} else if (fieldBoxedType.getQualifiedSourceName().equals("java.lang.Double")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getDouble(fieldJsonValue));");
				} else if (fieldBoxedType.getQualifiedSourceName().equals("java.lang.Boolean")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getBoolean(fieldJsonValue));");
				} else if (fieldBoxedType.getQualifiedSourceName().equals("java.lang.Character")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getShort(fieldJsonValue));");
				}
			} else {
				JClassType fieldClassType = (JClassType) fieldType;
				if (fieldClassType.getQualifiedSourceName().equals("java.lang.Short")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getShort(fieldJsonValue));");
				} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Byte")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getByte(fieldJsonValue));");
				} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Long")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getLong(fieldJsonValue));");
				} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Integer")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getInt(fieldJsonValue));");
				} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Float")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getFloat(fieldJsonValue));");
				} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Double")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getDouble(fieldJsonValue));");
				} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Boolean")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getBoolean(fieldJsonValue));");
				} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Character")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getShort(fieldJsonValue));");
				} else if (fieldClassType.getQualifiedSourceName().equals("java.util.Date")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getDate(fieldJsonValue));");
				} else if (fieldClassType.isAssignableTo(oracle.getType("org.sagarius.gwt.json.client.Serializable"))) {
					importsList.add(fieldClassType.getQualifiedSourceName());
					buffer.append("serializer = GWT.create(" + fieldClassType.getSimpleSourceName() + ".class);");
					buffer.append("mainResult.set" + fieldNameForGS + "((" + fieldClassType.getSimpleSourceName() + ")serializer.deSerialize(fieldJsonValue));");
				} else if (fieldClassType.isAssignableTo(oracle.getType("java.util.Collection"))) {
					deserializeCollection(buffer, fieldClassType, fieldNameForGS, fieldName);
				} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.String")) {
					buffer.append("mainResult.set" + fieldNameForGS + "(DeserializerHelper.getString(fieldJsonValue));");
				}
			}
		}

		buffer.append("return mainResult;");
		buffer.append("}");
		return buffer.toString();
	}

	private void deserializeCollection(StringBuffer buffer, JClassType fieldClassType, String fieldNameForGS, String fieldName) throws NotFoundException {
		// Return null if JSON object is null
		buffer.append("if(fieldJsonValue==null){");
		buffer.append("mainResult.set" + fieldNameForGS + "(null);");
		buffer.append("}");

		// Throw Incompatible exception if the JSON object is not a collection
		buffer.append("if(!(fieldJsonValue instanceof JSONArray)){");
		buffer.append("throw new IncompatibleObjectException();");
		buffer.append("}");

		// Start deSerilisation
		buffer.append("inputJsonArray=(JSONArray)fieldJsonValue;");
		buffer.append("inpJsonArSize=inputJsonArray.size();");

		String fieldTypeQualifiedName = fieldClassType.getQualifiedSourceName();
		JParameterizedType parameterizedType = (JParameterizedType) fieldClassType;
		fieldClassType = parameterizedType.getTypeArgs()[0];
		String parameterSimpleName = fieldClassType.getSimpleSourceName();
		String fieldColName = fieldName + "Col";// Field Collection Result
		// Object Name
		importsList.add(fieldClassType.getQualifiedSourceName());
		if (fieldTypeQualifiedName.equals("java.util.List") || fieldTypeQualifiedName.equals("java.util.ArrayList")) {
			buffer.append("ArrayList<" + parameterSimpleName + "> " + fieldColName + " = new ArrayList<" + parameterSimpleName + ">();");
		} else if (fieldTypeQualifiedName.equals("java.util.Set") || fieldTypeQualifiedName.equals("java.util.HashSet")) {
			buffer.append("HashSet<" + parameterSimpleName + "> " + fieldColName + " = new HashSet<" + parameterSimpleName + ">();");
		} else if (fieldTypeQualifiedName.equals("java.util.SortedSet") || fieldTypeQualifiedName.equals("java.util.TreeSet")) {
			buffer.append("TreeSet<" + parameterSimpleName + "> " + fieldColName + " = new TreeSet<" + parameterSimpleName + ">();");
		} else if (fieldTypeQualifiedName.equals("java.util.LinkedList")) {
			buffer.append("LinkedList<" + parameterSimpleName + "> " + fieldColName + " = new LinkedList<" + parameterSimpleName + ">();");
			buffer.append("mainResult.set" + fieldNameForGS + "(" + fieldColName + ");");
		} else if (fieldTypeQualifiedName.equals("java.util.Stack")) {
			buffer.append("Stack<" + parameterSimpleName + "> " + fieldColName + " = new Stack<" + parameterSimpleName + ">();");
		} else if (fieldTypeQualifiedName.equals("java.util.Vector")) {
			buffer.append("Vector<" + parameterSimpleName + "> " + fieldColName + " = new Vector<" + parameterSimpleName + ">();");
		} else if (fieldTypeQualifiedName.equals("java.util.LinkedHashSet")) {
			buffer.append("LinkedHashSet<" + parameterSimpleName + "> " + fieldColName + "=new LinkedHashSet<" + parameterSimpleName + ">();");
		}
		buffer.append("for(int ij=0;ij<inpJsonArSize;ij++){");
		// DeSerialise individual elements
		buffer.append("fieldJsonValue=inputJsonArray.get(ij);");
		if (fieldClassType.getQualifiedSourceName().equals("java.lang.Short")) {
			buffer.append(fieldColName + ".add(DeserializerHelper.getShort(fieldJsonValue));");
		} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Byte")) {
			buffer.append(fieldColName + ".add(DeserializerHelper.getByte(fieldJsonValue));");
		} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Long")) {
			buffer.append(fieldColName + ".add(DeserializerHelper.getLong(fieldJsonValue));");
		} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Integer")) {
			buffer.append(fieldColName + ".add(DeserializerHelper.getInt(fieldJsonValue));");
		} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Float")) {
			buffer.append(fieldColName + ".add(DeserializerHelper.getFloat(fieldJsonValue));");
		} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Double")) {
			buffer.append(fieldColName + ".add(DeserializerHelper.getDouble(fieldJsonValue));");
		} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Boolean")) {
			buffer.append(fieldColName + ".add(DeserializerHelper.getBoolean(fieldJsonValue));");
		} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Character")) {
			buffer.append(fieldColName + ".add(DeserializerHelper.getShort(fieldJsonValue));");
		} else if (fieldClassType.getQualifiedSourceName().equals("java.util.Date")) {
			buffer.append(fieldColName + ".add(DeserializerHelper.getDate(fieldJsonValue));");
		} else if (fieldClassType.isAssignableTo(oracle.getType("org.sagarius.gwt.json.client.Serializable"))) {
			importsList.add(fieldClassType.getQualifiedSourceName());
			buffer.append("serializer = GWT.create(" + fieldClassType.getSimpleSourceName() + ".class);");
			buffer.append(fieldColName + ".add((" + fieldClassType.getSimpleSourceName() + ")serializer.deSerialize(fieldJsonValue));");
		} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.String")) {
			buffer.append(fieldColName + ".add(DeserializerHelper.getString(fieldJsonValue));");
		}
		buffer.append("}");
		buffer.append("mainResult.set" + fieldNameForGS + "(" + fieldColName + ");");
	}

	private String generateDefaultDeserialization() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("public Object deSerialize(String jsonString) throws JSONException{");
		buffer.append("return deSerialize(JSONParser.parse(jsonString));");
		buffer.append("}");
		return buffer.toString();
	}

	private String generateTypeSerialization() throws NotFoundException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("public JSONValue serializeToJson(Object object){");

		// Return JSONNull instance if object is null
		buffer.append("if(object==null){");
		buffer.append("return JSONNull.getInstance();");
		buffer.append("}");

		// Throw Incompatible Exception if object is not of the type it claims
		// to be
		buffer.append("if(!(object instanceof " + baseType.getSimpleSourceName() + ")){");
		buffer.append("throw new IncompatibleObjectException();");
		buffer.append("}");

		// Initialise result object
		buffer.append("JSONObject mainResult=new JSONObject();");
		buffer.append("JSONValue jsonValue=null;");
		buffer.append("JSONArray jsonResultArray=null;");
		buffer.append("int index=0;");
		buffer.append("Serializer serializer=null;");
		buffer.append("Object fieldValue=null;");
		buffer.append(baseType.getSimpleSourceName() + " mainVariable=(" + baseType.getSimpleSourceName() + ")object;");

		// Serialise fields
		List<JField> allFields = new ArrayList<JField>();
		JField[] fields = baseType.getFields();
		allFields.addAll(Arrays.asList(fields));
		if (baseType.isAssignableTo(oracle.getType("org.sagarius.gwt.json.client.Serializable"))) {
			boolean flag = true;
			JClassType superClassType = baseType;
			while (flag) {
				superClassType = superClassType.getSuperclass();
				if (superClassType.isAssignableTo(oracle.getType("org.sagarius.gwt.json.client.Serializable"))) {
					allFields.addAll(Arrays.asList(superClassType.getFields()));
				} else {
					flag = false;
				}
			}
		}
		fields = new JField[allFields.size()];
		allFields.toArray(fields);
		for (JField field : fields) {
			JType fieldType = field.getType();
			String fieldName = field.getName();
			String fieldNameForGS = getNameForGS(fieldName);
			// Get field value for object
			buffer.append("fieldValue=mainVariable.get" + fieldNameForGS + "();");

			if (fieldType.isPrimitive() != null) {
				JPrimitiveType fieldPrimitiveType = (JPrimitiveType) fieldType;
				JClassType fieldBoxedType = oracle.getType(fieldPrimitiveType.getQualifiedBoxedSourceName());
				if (fieldBoxedType.getQualifiedSourceName().equals("java.lang.Boolean")) {
					buffer.append("jsonValue=SerializerHelper.getBoolean((Boolean)fieldValue);");
					buffer.append("mainResult.put(\"" + fieldName + "\",jsonValue);");
				} else if (fieldBoxedType.getQualifiedSourceName().equals("java.lang.Character")) {
					buffer.append("jsonValue=SerializerHelper.getChar((Character)fieldValue);");
					buffer.append("mainResult.put(\"" + fieldName + "\",jsonValue);");
				} else if (fieldBoxedType.isAssignableTo(oracle.getType("java.lang.Number"))) {
					buffer.append("jsonValue=SerializerHelper.getNumber((Number)fieldValue);");
					buffer.append("mainResult.put(\"" + fieldName + "\",jsonValue);");
				}
			} else {
				JClassType fieldClassType = (JClassType) fieldType;
				if (fieldClassType.isAssignableTo(oracle.getType("java.util.Collection"))) {
					// Serialise collection
					JParameterizedType parameterizedType = (JParameterizedType) fieldClassType;
					fieldClassType = parameterizedType.getTypeArgs()[0];
					importsList.add(fieldClassType.getQualifiedSourceName());
					String fieldSimpleName = fieldClassType.getSimpleSourceName();
					buffer.append("Collection<" + fieldSimpleName + "> " + fieldSimpleName.toLowerCase() + "ColValue=(Collection<" + fieldSimpleName + ">)fieldValue;");
					buffer.append("jsonResultArray=new JSONArray();");
					buffer.append("index=0;");
					buffer.append("for(" + fieldSimpleName + " dummy : " + fieldSimpleName.toLowerCase() + "ColValue){");
					if (fieldClassType.getQualifiedSourceName().equals("java.lang.String")) {
						buffer.append("jsonValue=SerializerHelper.getString((String)dummy);");
						buffer.append("jsonResultArray.set(index++,jsonValue);");
					} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Boolean")) {
						buffer.append("jsonValue=SerializerHelper.getBoolean((Boolean)dummy);");
						buffer.append("jsonResultArray.set(index++,jsonValue);");
					} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Character")) {
						buffer.append("jsonValue=SerializerHelper.getChar((Character)dummy);");
						buffer.append("jsonResultArray.set(index++,jsonValue);");
					} else if (fieldClassType.isAssignableTo(oracle.getType("java.lang.Number"))) {
						buffer.append("jsonValue=SerializerHelper.getNumber((Number)dummy);");
						buffer.append("jsonResultArray.set(index++,jsonValue);");
					} else if (fieldClassType.getQualifiedSourceName().equals("java.util.Date")) {
						buffer.append("jsonValue=SerializerHelper.getDate((Date)dummy);");
						buffer.append("jsonResultArray.set(index++,jsonValue);");
					} else if (fieldClassType.isAssignableTo(oracle.getType("org.sagarius.gwt.json.client.Serializable"))) {
						importsList.add(fieldClassType.getQualifiedSourceName());
						buffer.append("serializer = GWT.create(" + fieldClassType.getSimpleSourceName() + ".class);");
						buffer.append("jsonResultArray.set(index++,serializer.serializeToJson(dummy));");
					}
					buffer.append("}");
					buffer.append("mainResult.put(\"" + fieldName + "\",jsonResultArray);");
				} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.String")) {
					buffer.append("jsonValue=SerializerHelper.getString((String)fieldValue);");
					buffer.append("mainResult.put(\"" + fieldName + "\",jsonValue);");
				} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Boolean")) {
					buffer.append("jsonValue=SerializerHelper.getBoolean((Boolean)fieldValue);");
					buffer.append("mainResult.put(\"" + fieldName + "\",jsonValue);");
				} else if (fieldClassType.getQualifiedSourceName().equals("java.lang.Character")) {
					buffer.append("jsonValue=SerializerHelper.getChar((Character)fieldValue);");
					buffer.append("mainResult.put(\"" + fieldName + "\",jsonValue);");
				} else if (fieldClassType.isAssignableTo(oracle.getType("java.lang.Number"))) {
					buffer.append("jsonValue=SerializerHelper.getNumber((Number)fieldValue);");
					buffer.append("mainResult.put(\"" + fieldName + "\",jsonValue);");
				} else if (fieldClassType.getQualifiedSourceName().equals("java.util.Date")) {
					buffer.append("jsonValue=SerializerHelper.getDate((Date)fieldValue);");
					buffer.append("mainResult.put(\"" + fieldName + "\",jsonValue);");
				} else if (fieldClassType.isAssignableTo(oracle.getType("org.sagarius.gwt.json.client.Serializable"))) {
					importsList.add(fieldClassType.getQualifiedSourceName());
					buffer.append("serializer = GWT.create(" + fieldClassType.getSimpleSourceName() + ".class);");
					buffer.append("mainResult.put(\"" + fieldName + "\",serializer.serializeToJson(fieldValue));");
				}

			}
		}

		// Put class type for compatibility with flex JSON [de]serialisation
		buffer.append("mainResult.put(\"class\",new JSONString(\"" + baseType.getQualifiedSourceName() + "\"));");

		// Return statement
		buffer.append("return mainResult;");
		buffer.append("}");
		return buffer.toString();
	}

	private String generateDefaultSerialization() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("public String serialize(Object pojo){");
		buffer.append("return serializeToJson(pojo).toString();");
		buffer.append("}");
		return buffer.toString();
	}

	private static String getNameForGS(String name) {
		StringBuffer buffer = new StringBuffer(name);
		buffer.setCharAt(0, new String(new char[] { name.charAt(0) }).toUpperCase().charAt(0));
		return buffer.toString();
	}
}
