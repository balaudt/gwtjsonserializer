package org.sagarius.gwt.json.example.client.pojo;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.sagarius.gwt.json.client.Serializable;

@PersistenceCapable
@EmbeddedOnly
public class PhoneNumber implements Serializable {
	@Persistent
	private String number;
	@Persistent
	private String ext;
	@Persistent
	private String type;
	@Persistent
	private String listedStatus;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getListedStatus() {
		return listedStatus;
	}

	public void setListedStatus(String listedStatus) {
		this.listedStatus = listedStatus;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("number:");
		buffer.append(number + ",");
		buffer.append("ext:");
		buffer.append(ext + ",");
		buffer.append("type:");
		buffer.append(type + ",");
		buffer.append("listedStatus:");
		buffer.append(listedStatus + ",");
		buffer.append("}");
		return buffer.toString();
	}

}
