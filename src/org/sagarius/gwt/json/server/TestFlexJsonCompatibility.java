package org.sagarius.gwt.json.server;

import java.util.ArrayList;
import java.util.Date;

import org.sagarius.gwt.json.example.client.pojo.Address;
import org.sagarius.gwt.json.example.client.pojo.Contact;
import org.sagarius.gwt.json.example.client.pojo.PhoneNumber;
import org.sagarius.gwt.json.example.client.pojo.University;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class TestFlexJsonCompatibility {
	public static void main(String[] args) {
		JSONSerializer serializer = new JSONSerializer();
		University dummyUniversity = getDummyUniversity();
		String jsonString = serializer.deepSerialize(dummyUniversity);
		System.out.println(jsonString);
		JSONDeserializer<University> deserializer = new JSONDeserializer<University>();
		University university = deserializer
				.deserialize("{\"forVerification\":\"Really for verification\", \"refIdKey\":null, \"refId\":\"cms\", \"schoolName\":\"CMS\", \"schoolShortName\":null, \"schoolUrl\":\"http://cms.in\", \"status\":11, \"gradeLevels\":[\"12\",\"11\"], \"contactInfo\":[{\"refId\":null, \"address\":{\"line1\":\"Ganapathy\", \"line2\":null, \"city\":\"Coimbatore\", \"state\":\"Tamilnadu\", \"country\":\"India\", \"zipCode\":\"641030\", \"class\":\"org.sagarius.gwt.json.example.client.pojo.Address\"}, \"phoneNumber\":{\"number\":\"00-000\", \"ext\":null, \"type\":null, \"listedStatus\":null, \"class\":\"org.sagarius.gwt.json.example.client.pojo.PhoneNumber\"}, \"class\":\"org.sagarius.gwt.json.example.client.pojo.Contact\"},{\"refId\":null, \"address\":{\"line1\":\"Dhandapani LO\", \"line2\":null, \"city\":\"Udumalpet\", \"state\":\"Tamilnadu\", \"country\":\"India\", \"zipCode\":\"642120\", \"class\":\"org.sagarius.gwt.json.example.client.pojo.Address\"}, \"phoneNumber\":{\"number\":\"04252-210014\", \"ext\":null, \"type\":null, \"listedStatus\":null, \"class\":\"org.sagarius.gwt.json.example.client.pojo.PhoneNumber\"}, \"class\":\"org.sagarius.gwt.json.example.client.pojo.Contact\"}], \"startDate\":1252046722043, \"class\":\"org.sagarius.gwt.json.example.client.pojo.University\"}");
		System.out.println(university);
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
