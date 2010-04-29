package org.sagarius.gwt.json.example.client.pojo;

import org.sagarius.gwt.json.client.Serializable;

public class University extends School implements Serializable {
	private String forVerification;

	public void setForVerification(String forVerification) {
		this.forVerification = forVerification;
	}

	public String getForVerification() {
		return forVerification;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append(super.toString());
		buffer.append(",");
		buffer.append("forVerification:");
		buffer.append(forVerification + ",");
		buffer.append("}");
		return buffer.toString();
	}

}
