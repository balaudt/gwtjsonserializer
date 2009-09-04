package org.sagarius.gwt.json.example.client;

import java.util.ArrayList;
import java.util.Date;

import org.sagarius.gwt.json.client.Serializer;
import org.sagarius.gwt.json.example.client.pojo.Address;
import org.sagarius.gwt.json.example.client.pojo.Contact;
import org.sagarius.gwt.json.example.client.pojo.PhoneNumber;
import org.sagarius.gwt.json.example.client.pojo.University;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class TestEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		Serializer serializer = GWT.create(University.class);
		University dummyUniversity = getDummyUniversity();
		String jsonString = serializer.serialize(dummyUniversity);
		GWT.log(jsonString, null);
		dummyUniversity = (University) serializer
				.deSerialize("{\"class\":\"org.sagarius.gwt.json.example.client.pojo.University\",\"contactInfo\":[{\"address\":{\"city\":\"Coimbatore\",\"class\":\"org.sagarius.gwt.json.example.client.pojo.Address\",\"country\":\"India\",\"line1\":\"Ganapathy\",\"line2\":null,\"state\":\"Tamilnadu\",\"zipCode\":\"641030\"},\"class\":\"org.sagarius.gwt.json.example.client.pojo.Contact\",\"phoneNumber\":{\"class\":\"org.sagarius.gwt.json.example.client.pojo.PhoneNumber\",\"ext\":null,\"listedStatus\":null,\"number\":\"00-000\",\"type\":null},\"refId\":null},{\"address\":{\"city\":\"Udumalpet\",\"class\":\"org.sagarius.gwt.json.example.client.pojo.Address\",\"country\":\"India\",\"line1\":\"Dhandapani LO\",\"line2\":null,\"state\":\"Tamilnadu\",\"zipCode\":\"642120\"},\"class\":\"org.sagarius.gwt.json.example.client.pojo.Contact\",\"phoneNumber\":{\"class\":\"org.sagarius.gwt.json.example.client.pojo.PhoneNumber\",\"ext\":null,\"listedStatus\":null,\"number\":\"04252-210014\",\"type\":null},\"refId\":null}],\"forVerification\":\"Really for verification\",\"gradeLevels\":[\"12\",\"11\"],\"refId\":\"cms\",\"refIdKey\":null,\"schoolName\":\"CMS\",\"schoolShortName\":null,\"schoolUrl\":\"http://cms.in\",\"startDate\":1252046885585,\"status\":11}");
		GWT.log(dummyUniversity.toString(), null);
	}

	private University getDummyUniversity() {
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
