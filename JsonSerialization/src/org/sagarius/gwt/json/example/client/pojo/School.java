package org.sagarius.gwt.json.example.client.pojo;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.sagarius.gwt.json.client.Serializable;

@PersistenceCapable
public class School implements Serializable {
	@PrimaryKey
	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	protected String refIdKey;
	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.pk-name", value = "true")
	protected String refId;
	@Persistent
	protected String schoolName;
	@Persistent
	protected String schoolShortName;
	@Persistent
	protected String schoolUrl;
	@Persistent
	protected int status;
	protected List<String> gradeLevels;
	@Persistent
	protected List<Contact> contactInfo;
	@Persistent
	protected Date startDate;

	public String getRefIdKey() {
		return refIdKey;
	}

	public void setRefIdKey(String refIdKey) {
		this.refIdKey = refIdKey;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolShortName() {
		return schoolShortName;
	}

	public void setSchoolShortName(String schoolShortName) {
		this.schoolShortName = schoolShortName;
	}

	public String getSchoolUrl() {
		return schoolUrl;
	}

	public void setSchoolUrl(String schoolUrl) {
		this.schoolUrl = schoolUrl;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public School() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((refIdKey == null) ? 0 : refIdKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		School other = (School) obj;
		if (refIdKey == null) {
			if (other.refIdKey != null)
				return false;
		} else if (!refIdKey.equals(other.refIdKey))
			return false;
		return true;
	}

	public void setContactInfo(List<Contact> contactInfo) {
		this.contactInfo = contactInfo;
	}

	public List<Contact> getContactInfo() {
		return contactInfo;
	}

	public void setGradeLevels(List<String> gradeLevels) {
		this.gradeLevels = gradeLevels;
	}

	public List<String> getGradeLevels() {
		return gradeLevels;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("refIdKey:");
		buffer.append(refIdKey + ",");
		buffer.append("refId:");
		buffer.append(refId + ",");
		buffer.append("schoolName:");
		buffer.append(schoolName + ",");
		buffer.append("schoolShortName:");
		buffer.append(schoolShortName + ",");
		buffer.append("schoolUrl:");
		buffer.append(schoolUrl + ",");
		buffer.append("status:");
		buffer.append(status + ",");
		buffer.append("gradeLevels:");
		buffer.append("[");
		if (gradeLevels != null) {
			for (String i : gradeLevels) {
				buffer.append(i + ",");
			}
		}
		buffer.append("],");
		buffer.append("contactInfo:");
		buffer.append("[");
		if (contactInfo != null) {
			for (Contact i : contactInfo) {
				buffer.append(i + ",");
			}
		}
		buffer.append("],");
		buffer.append("startDate:");
		buffer.append(startDate + ",");
		buffer.append("}");
		return buffer.toString();
	}

}
