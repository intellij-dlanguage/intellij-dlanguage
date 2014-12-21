/*******************************************************************************
 * Copyright (c) 2013, 2013 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.dub;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;

import ddt.melnorme.utilbox.misc.ArrayUtil;

import com.google.gson.stream.JsonToken;

import ddt.dtool.dub.DubBundle.DubBundleException;
import ddt.dtool.util.JsonReaderExt;

public class DubDescribeParser extends CommonDubParser {
	
	public static final String ERROR_PACKAGES_IS_EMPTY = "'packages' entry is empty or missing.";
	
	public static DubBundleDescription parseDescription(BundlePath bundlePath, String describeSource) {
		assertNotNull(bundlePath);
		return new DubDescribeParser().parseInput(bundlePath, describeSource);
	}
	
	protected String bundleName;
	protected ArrayList<DubBundle> bundles;
	
	public DubDescribeParser() {
	}
	
	public DubBundleDescription parseInput(BundlePath bundlePath, String describeSource) {
		try {
			parseFromSource(describeSource);
		} catch (DubBundleException e) {
			dubError = e;
		}
		
		if(bundles == null || bundles.size() == 0) {
			
			if(bundleName == null) {
				bundleName = "<error_undefined>";
			}
			putError(ERROR_PACKAGES_IS_EMPTY);
			return new DubBundleDescription(new DubBundle(bundlePath, bundleName, dubError));
			
		} else {
			DubBundle mainBundle = bundles.remove(0);
			DubBundle[] bundleDeps = ArrayUtil.createFrom(bundles, DubBundle.class);
			return new DubBundleDescription(mainBundle, bundleDeps);
		}
	}

	@Override
	protected void readData(JsonReaderExt jsonParser) throws IOException {
		
		jsonParser.consumeExpected(JsonToken.BEGIN_OBJECT);
		
		while(jsonParser.hasNext()) {
			JsonToken tokenType = jsonParser.peek();
			
			if(tokenType == JsonToken.NAME) {
				String propertyName = jsonParser.nextName();
				
				if(propertyName.equals("mainPackage")) {
					bundleName = jsonParser.consumeStringValue();
				} else if(propertyName.equals("packages")) {
					bundles = readDescribedBundles(jsonParser);
				} else {
					jsonParser.skipValue();
				}
			} else {
				jsonParser.errorUnexpected(tokenType);
			}
		}
		
		jsonParser.consumeExpected(JsonToken.END_OBJECT);
	}
	
	protected static ArrayList<DubBundle> readDescribedBundles(JsonReaderExt jsonParser) throws IOException {
		jsonParser.consumeExpected(JsonToken.BEGIN_ARRAY);
		
		ArrayList<DubBundle> bundles = new ArrayList<>();
		
		while(jsonParser.hasNext()) {
			JsonToken tokenType = jsonParser.peek();
			
			if(tokenType == JsonToken.BEGIN_OBJECT) {
				DubBundle bundle = new DubManifestParser().readBundle(jsonParser).createBundle(null, false);
				bundles.add(bundle);
			} else {
				jsonParser.errorUnexpected(tokenType);
			}
		}
		
		jsonParser.consumeExpected(JsonToken.END_ARRAY);
		return bundles;
	}
	
}