package org.sagarius.gwt.json.client;

import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONValue;

public interface Serializer {
	String serialize(Object pojo);

	JSONValue serializeToJson(Object pojo);

	Object deSerialize(JSONValue jsonValue) throws JSONException;

	Object deSerialize(String jsonString) throws JSONException;

}
