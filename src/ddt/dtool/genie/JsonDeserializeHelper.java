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
package ddt.dtool.genie;

import java.nio.file.Path;
import java.util.Map;

import ddt.melnorme.utilbox.misc.MiscUtil;


/**
 * A helper to parse/serialize/analyze JSON into proper domain data
 */
public abstract class JsonDeserializeHelper<EXC extends Exception> {
	
	public JsonDeserializeHelper() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T validateType(Object value, String propName, Class<T> klass, boolean allowNull) 
			throws EXC {
		
		if(value == null) {
			if(allowNull) {
				return null;
			}
			throw validationError("Expected non-null value" + " for property: " + propName);
		}
		
		if(!klass.isInstance(value)) {
			throw validationError("Expected value of type " + klass.getSimpleName() + 
				" for property: " + propName + ", instead got: " + value.getClass().getSimpleName());
		}
		return (T) value;
	}
	
	protected abstract EXC validationError(String message);
	
	protected <T> T getValue(Map<String, Object> map, String propName, Class<T> klass, boolean allowNull)
			throws EXC {
		Object value = map.get(propName);
		return validateType(value, propName, klass, allowNull);
	}
	
	protected String getString(Map<String, Object> map, String propName) throws EXC {
		return getValue(map, propName, String.class, false);
	}
	protected String getStringOrNull(Map<String, Object> map, String propName) throws EXC {
		return getValue(map, propName, String.class, true);
	}
	
	protected int getInt(Map<String, Object> map, String propName) throws EXC {
		return getValue(map, propName, Integer.class, false);
	}
	protected Integer getIntegerOrNull(Map<String, Object> map, String propName) throws EXC {
		return getValue(map, propName, Integer.class, true);
	}
	
	protected boolean getBoolean(Map<String, Object> map, String propName) throws EXC {
		return getValue(map, propName, Boolean.class, false);
	}
	
	protected Path getPath(Map<String, Object> map, String propName) throws EXC {
		return getPath(map, propName, false);
	}
	protected Path getPathOrNull(Map<String, Object> map, String propName) throws EXC {
		return getPath(map, propName, true);
	}
	
	protected Path getPath(Map<String, Object> map, String propName, boolean allowNull) throws EXC {
		String pathString = getValue(map, propName, String.class, allowNull);
		Path path = pathString == null ? null : MiscUtil.createPathOrNull(pathString);
		if(path == null) {
			if(allowNull) 
				return null;
			throw validationError("Invalid path: " + pathString);
		}
		return path;
	}
	
}