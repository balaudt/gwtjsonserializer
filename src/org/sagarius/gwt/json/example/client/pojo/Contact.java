package org.sagarius.gwt.json.example.client.pojo;

import org.sagarius.gwt.json.client.Serializable;

public class Contact implements Serializable {
	private Long refId;
	private Address address;
	private PhoneNumber phoneNumber;

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
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
