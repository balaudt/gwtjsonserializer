package org.sagarius.gwt.json.client;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.json.client.JSONBoolean;

public class SerializerHelper {
	private static Set<Object> objectsVisited = new HashSet<Object>();
	private static Object starter;
	private static boolean isStarted = false;

	public static boolean add(Object e) {
		return objectsVisited.add(e);
	}

	public static boolean addAll(Collection<? extends Object> c) {
		return objectsVisited.addAll(c);
	}

	public static void clear() {
		objectsVisited.clear();
	}

	public static boolean contains(Object o) {
		return objectsVisited.contains(o);
	}

	public static boolean containsAll(Collection<?> c) {
		return objectsVisited.containsAll(c);
	}

	public static Iterator<Object> iterator() {
		return objectsVisited.iterator();
	}

	public static boolean remove(Object o) {
		return objectsVisited.remove(o);
	}

	public static boolean removeAll(Collection<?> c) {
		return objectsVisited.removeAll(c);
	}

	public static int size() {
		return objectsVisited.size();
	}

	public static Object[] toArray() {
		return objectsVisited.toArray();
	}

	public static <T> T[] toArray(T[] a) {
		return objectsVisited.toArray(a);
	}

	public static JSONValue getString(String string) {
		if (string == null) {
			return JSONNull.getInstance();
		}
		return new JSONString(string);
	}

	public static JSONValue getBoolean(Boolean boolValue) {
		if (boolValue == null) {
			return JSONNull.getInstance();
		}
		return JSONBoolean.getInstance(boolValue);
	}

	public static JSONValue getNumber(Number number) {
		if (number == null) {
			return JSONNull.getInstance();
		}
		return new JSONNumber(number.doubleValue());
	}

	public static JSONValue getChar(Character character) {
		if (character == null) {
			return JSONNull.getInstance();
		}
		return new JSONString(new String(new char[] { character }));
	}

	public static JSONValue getDate(Date date) {
		if (date == null) {
			return JSONNull.getInstance();
		}
		return new JSONNumber(date.getTime());
	}

	public static void setStarter(Object starter) {
		SerializerHelper.starter = starter;
	}

	public static Object getStarter() {
		return starter;
	}

	public static void setStarted(boolean isStarted) {
		SerializerHelper.isStarted = isStarted;
	}

	public static boolean isStarted() {
		return isStarted;
	}
}
