package org.sagarius.gwt.json.server;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sagarius.gwt.json.example.client.pojo.Address;
import org.sagarius.gwt.json.example.client.pojo.Contact;
import org.sagarius.gwt.json.example.client.pojo.PhoneNumber;
import org.sagarius.gwt.json.example.client.pojo.University;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class Dummy {
	public static void main(String[] args) {
		testFlexCompatibility();
	}

	public static void testFlexCompatibility() {
		JSONSerializer serializer = new JSONSerializer();
		University dummyUniversity = getDummyUniversity();
		String jsonString = serializer.deepSerialize(dummyUniversity);
		System.out.println(jsonString);
		JSONDeserializer<University> deserializer = new JSONDeserializer<University>();
		University university = deserializer
				.deserialize("{\"forVerification\":\"Really for verification\", \"refIdKey\":null, \"refId\":\"cms\", \"schoolName\":\"CMS\", \"schoolShortName\":null, \"schoolUrl\":\"http://cms.in\", \"status\":11, \"gradeLevels\":[\"12\",\"11\"], \"contactInfo\":[{\"address\":{\"line1\":\"Ganapathy\", \"line2\":null, \"city\":\"Coimbatore\", \"state\":\"Tamilnadu\", \"country\":\"India\", \"zipCode\":\"641030\", \"class\":\"org.sagarius.gwt.json.example.client.pojo.Address\"}, \"phoneNumber\":{\"number\":\"00-000\", \"ext\":null, \"type\":null, \"listedStatus\":null, \"class\":\"org.sagarius.gwt.json.example.client.pojo.PhoneNumber\"}, \"class\":\"org.sagarius.gwt.json.example.client.pojo.Contact\"},{\"address\":{\"line1\":\"Dhandapani LO\", \"line2\":null, \"city\":\"Udumalpet\", \"state\":\"Tamilnadu\", \"country\":\"India\", \"zipCode\":\"642120\", \"class\":\"org.sagarius.gwt.json.example.client.pojo.Address\"}, \"phoneNumber\":{\"number\":\"04252-210014\", \"ext\":null, \"type\":null, \"listedStatus\":null, \"class\":\"org.sagarius.gwt.json.example.client.pojo.PhoneNumber\"}, \"class\":\"org.sagarius.gwt.json.example.client.pojo.Contact\"}], \"startDate\":1252031145328, \"class\":\"org.sagarius.gwt.json.example.client.pojo.University\"}");
		System.out.println(university);
	}

	public static String generateToString(Class<?> clazz) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("@Override\n");
		buffer.append("public String toString(){\n");
		buffer.append("StringBuffer buffer = new StringBuffer();\n");
		buffer.append("buffer.append(\"{\");\n");
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			Class<?> fieldType = field.getType();
			if (fieldName.startsWith("jdo") || fieldName.equals("class")) {
				continue;
			}
			buffer.append("buffer.append(\"" + fieldName + ":\");\n");
			if (fieldType.equals(List.class) || fieldType.isArray()) {
				Class<?> componentType = null;
				if (fieldType.isArray()) {
					componentType = fieldType.getComponentType();
				} else {
					ParameterizedType genericType = (ParameterizedType) field.getGenericType();
					componentType = (Class<?>) genericType.getActualTypeArguments()[0];
				}
				buffer.append("buffer.append(\"[\");\n");
				buffer.append("if(" + fieldName + "!=null){\n");
				buffer.append("for(" + componentType.getSimpleName() + " i : " + fieldName + "){\n");
				buffer.append("buffer.append(i+\",\");\n");
				buffer.append("}\n");
				buffer.append("}\n");
				buffer.append("buffer.append(\"],\");\n");
			} else {
				buffer.append("buffer.append(" + fieldName + "+\",\");\n");
			}
		}
		buffer.append("buffer.append(\"}\");\n");
		buffer.append("return buffer.toString();\n");
		buffer.append("}");
		return buffer.toString();
	}

	private static University getDummyUniversity() {
		University school = new University();
		school.setRefId("cms");
		school.setSchoolName("CMS");
		school.setSchoolUrl("http://cms.in");
		school.setStatus(11);
		ArrayList<String> gradeLevels = new ArrayList<String>();
		gradeLevels.add("12");
		gradeLevels.add("11");
		school.setGradeLevels(gradeLevels);
		Contact contactInfo = new Contact();
		Address address = new Address();
		address.setCity("Coimbatore");
		address.setCountry("India");
		address.setLine1("Ganapathy");
		address.setState("Tamilnadu");
		address.setZipCode("641030");
		contactInfo.setAddress(address);
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setNumber("00-000");
		contactInfo.setPhoneNumber(phoneNumber);
		ArrayList<Contact> contact = new ArrayList<Contact>();
		contact.add(contactInfo);
		contactInfo = new Contact();
		address = new Address();
		address.setCity("Udumalpet");
		address.setCountry("India");
		address.setLine1("Dhandapani LO");
		address.setState("Tamilnadu");
		address.setZipCode("642120");
		contactInfo.setAddress(address);
		phoneNumber = new PhoneNumber();
		phoneNumber.setNumber("04252-210014");
		contactInfo.setPhoneNumber(phoneNumber);
		contact.add(contactInfo);
		school.setContactInfo(contact);
		school.setStartDate(new Date());
		school.setForVerification("Really for verification");
		return school;
	}
}
