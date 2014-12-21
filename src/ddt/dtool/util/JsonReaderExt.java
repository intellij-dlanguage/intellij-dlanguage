/*******************************************************************************
 * Copyright (c) 2014, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.util;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertFail;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertUnreachable;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;

/** Utility extensions to JsonReader */
public class JsonReaderExt extends JsonReader {
	
	protected final JsonReaderExt jsonReader = this; 
	
	public JsonReaderExt(Reader in) {
		super(in);
	}
	
	public void consumeExpected(JsonToken expectedToken) throws IOException {
		JsonToken tokenType = validateExpectedToken(expectedToken);
		if(tokenType == JsonToken.BEGIN_OBJECT) {
			jsonReader.beginObject();
		} else if(tokenType == JsonToken.END_OBJECT) {
			jsonReader.endObject();
		} else if(tokenType == JsonToken.BEGIN_ARRAY) {
			jsonReader.beginArray();
		} else if(tokenType == JsonToken.END_ARRAY) {
			jsonReader.endArray();
		} else if(tokenType == JsonToken.END_DOCUMENT) {
		} else {
			assertFail();
		}
	}
	
	protected JsonToken validateExpectedToken(JsonToken expectedToken) throws IOException, MalformedJsonException {
		JsonToken tokenType = jsonReader.peek();
		if(tokenType != expectedToken) {
			throw new MalformedJsonException("Expected: " + expectedToken + " Got: " + tokenType);
		}
		return tokenType;
	}
	
	public boolean tryConsume(JsonToken jsonToken) throws IOException {
		if(peek() == jsonToken) {
			consumeExpected(jsonToken);
			return true;
		}
		return false;
	}
	
	public boolean isEOF() throws IOException {
		try {
			if(peek() == JsonToken.END_DOCUMENT) {
				return true;
			}
		} catch (EOFException eof) {
			// This exception is ok. Because of a bug, END_DOCUMENT is sometimes not reported.
			return true;
		}
		
		return false;
	}
	
	/* -----------------  ----------------- */
	
	public void sourceError(String message) throws MalformedJsonException {
		// TODO: add source location to message.
		throw new MalformedJsonException(message);
	}
	
	public void errorUnexpected(JsonToken tokenType) throws MalformedJsonException {
		sourceError("Unexpected token: " + tokenType);
	}
	
	public String consumeStringValue() throws IOException {
		if(jsonReader.peek() != JsonToken.STRING) {
			throw new MalformedJsonException("Expected: " + JsonToken.STRING);
		}
		return jsonReader.nextString();
	}
	
	public ArrayList<String> consumeStringArray(boolean ignoreNulls) throws IOException {
		jsonReader.consumeExpected(JsonToken.BEGIN_ARRAY);
		
		ArrayList<String> strings = new ArrayList<>();
		
		while(jsonReader.hasNext()) {
			JsonToken tokenType = jsonReader.peek();
			
			if(ignoreNulls && tokenType == JsonToken.NULL) {
				jsonReader.nextNull();
				continue;
			}
			
			if(tokenType != JsonToken.STRING) {
				sourceError("Expected String value, instead got: " + tokenType);
			}
			
			String entry = jsonReader.nextString();
			strings.add(entry);
		}
		jsonReader.consumeExpected(JsonToken.END_ARRAY);
		return strings;
	}
	
	public String consumeExpectedPropName() throws IOException {
		JsonToken tokenType = jsonReader.peek();
		
		if(tokenType != JsonToken.NAME) {
			jsonReader.sourceError("Expected property name, instead got: " + tokenType);
		}
		
		// Note: there is a bug in nextName, it throws IllegalStateException insteand of IOE
		// so that's why we check peek ourselves.
		return jsonReader.nextName(); 
	}
	
	public String consumeExpectedName() throws IOException {
		return consumeExpectedPropName();
	}
	
	/* -----------------  ----------------- */
	
	public Object readJsonValue() throws IOException {
		return readJsonValue(this);
	}
	
	public static Object readJsonValue(JsonReaderExt jsonParser) throws IOException {
		switch (jsonParser.peek()) {
		case NULL:
			jsonParser.nextNull(); return null;
		case BOOLEAN:
			return jsonParser.nextBoolean();
		case NUMBER:
			long longValue = jsonParser.nextLong();
			int intValue = (int) longValue;
			if(intValue == longValue) {
				return intValue; // return integer if value can be stored in int
			} else {
				return longValue;
			}
		case STRING:
			return jsonParser.nextString();
		case BEGIN_ARRAY:
			return jsonParser.readJsonArray();
		case BEGIN_OBJECT:
			return jsonParser.readJsonObject();
		case END_ARRAY:
		case END_OBJECT:
		case END_DOCUMENT:
		case NAME:
			jsonParser.sourceError("Invalid JSON token");
			return null;
		}
		
		throw assertUnreachable();
	}
	
	public HashMap<String, Object> readJsonObject() throws IOException {
		return readJsonObject(this);
	}
	
	public static HashMap<String, Object> readJsonObject(JsonReaderExt jsonParser) throws IOException {
		jsonParser.consumeExpected(JsonToken.BEGIN_OBJECT);
		
		HashMap<String, Object> jsonObject = new HashMap<>();
		
		while(jsonParser.tryConsume(JsonToken.END_OBJECT) == false) {
			String propName = jsonParser.consumeExpectedPropName();
			Object propvalue = readJsonValue(jsonParser);
			jsonObject.put(propName, propvalue);
		}
		
		return jsonObject;
	}
	
	public ArrayList<Object> readJsonArray() throws IOException {
		jsonReader.consumeExpected(JsonToken.BEGIN_ARRAY);
		
		ArrayList<Object> array = new ArrayList<>();
		while(jsonReader.hasNext()) {
			array.add(readJsonValue());
		}
		jsonReader.consumeExpected(JsonToken.END_ARRAY);
		return array;
	}
	
}