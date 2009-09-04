package org.sagarius.gwt.json.example.client.pojo;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.sagarius.gwt.json.client.Serializable;

@PersistenceCapable
@EmbeddedOnly
public class Address implements Serializable {
	@Persistent
	private String line1;
	@Persistent
	private String line2;
	@Persistent
	private String city;
	@Persistent
	private String state;
	@Persistent
	private String country;
	@Persistent
	private String zipCode;

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("line1:");
		buffer.append(line1 + ",");
		buffer.append("line2:");
		buffer.append(line2 + ",");
		buffer.append("city:");
		buffer.append(city + ",");
		buffer.append("state:");
		buffer.append(state + ",");
		buffer.append("country:");
		buffer.append(country + ",");
		buffer.append("zipCode:");
		buffer.append(zipCode + ",");
		buffer.append("}");
		return buffer.toString();
	}

}
