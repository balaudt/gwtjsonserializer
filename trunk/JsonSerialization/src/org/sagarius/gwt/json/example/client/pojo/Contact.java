package org.sagarius.gwt.json.example.client.pojo;

import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.sagarius.gwt.json.client.Serializable;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Contact implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key refId;
	@Persistent
	@Embedded
	private Address address;
	@Persistent
	@Embedded
	private PhoneNumber phoneNumber;

	public Key getRefId() {
		return refId;
	}

	public void setRefId(Key refId) {
		this.refId = refId;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("refId:");
		buffer.append(refId + ",");
		buffer.append("address:");
		buffer.append(address + ",");
		buffer.append("phoneNumber:");
		buffer.append(phoneNumber + ",");
		buffer.append("}");
		return buffer.toString();
	}

}
