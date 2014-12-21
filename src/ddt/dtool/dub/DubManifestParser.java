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
import static ddt.melnorme.utilbox.misc.MiscUtil.createPath;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import ddt.melnorme.utilbox.misc.ArrayUtil;
import ddt.melnorme.utilbox.misc.MiscUtil;
import ddt.melnorme.utilbox.misc.PathUtil.InvalidPathExceptionX;

import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;

import ddt.dtool.dub.DubBundle.BundleFile;
import ddt.dtool.dub.DubBundle.DubBundleException;
import ddt.dtool.dub.DubBundle.DubDependecyRef;
import ddt.dtool.util.JsonReaderExt;

/**
 * Parse a Dub bundle in a filesystem location into an in-memory description of the bundle.
 */
public class DubManifestParser extends CommonDubParser {
	
	public static final String ERROR_BUNDLE_NAME_UNDEFINED = "Bundle name not defined.";
	
	public static DubBundle parseDubBundleFromLocation(BundlePath bundlePath) {
		assertNotNull(bundlePath);
		return new DubManifestParser().parseDubBundle(bundlePath);
	}
	
	protected String source;
	
	protected String bundleName = null;
	protected String pathString = null;
	
	protected String version = null;
	protected String[] sourceFolders = null;
	protected List<BundleFile> bundleFiles = null;
	protected DubDependecyRef[] dependencies = null;
	protected String targetName = null;
	protected String targetPath = null;
	
	protected DubManifestParser() {
	}
	
	protected DubBundle parseDubBundle(BundlePath bundlePath) {
		assertNotNull(bundlePath);
		try {
			parseFromLocation(bundlePath);
		} catch (DubBundleException e) {
			dubError = e;
		}
		return createBundle(bundlePath, true);
	}
	
	protected void parseFromLocation(BundlePath bundlePath) throws DubBundleException {
		File jsonLocation = bundlePath.getManifestFilePath().toFile();
		
		try {
			source = readStringFromFile(jsonLocation);
		} catch (IOException e) {
			throw new DubBundleException(e);
		}
		parseFromSource(source);
	}
	
	public DubBundle createBundle(BundlePath bundlePath, boolean searchImplicitSourceFolders) {
		if(bundleName == null) {
			bundleName = "<undefined>";
			
			putError(ERROR_BUNDLE_NAME_UNDEFINED);
		}
		
		if(bundlePath == null) {
			if(pathString == null) {
				putError("Missing path entry.");
			} else {
				bundlePath = BundlePath.create(pathString);
				if(bundlePath == null) {
					putError("Invalid path: " + pathString);
				}
			}
		}
		Path[] effectiveSourceFolders;
		if(sourceFolders != null) {
			effectiveSourceFolders = createPaths(sourceFolders);
		} else if(searchImplicitSourceFolders && bundlePath != null) {
			effectiveSourceFolders = searchImplicitSrcFolders(bundlePath.getPath());
		} else {
			effectiveSourceFolders = null;
		}
		
		return new DubBundle(bundlePath, bundleName, dubError, version, 
			sourceFolders, effectiveSourceFolders, 
			bundleFiles,
			dependencies, targetName, targetPath);
	}
	
	protected Path[] createPaths(String[] paths) {
		if(paths == null) 
			return null;
		
		ArrayList<Path> pathArray = new ArrayList<>();
		for (String pathString : paths) {
			try {
				pathArray.add(createPath(pathString));
			} catch (InvalidPathExceptionX e) {
				putError("Invalid source/import path: " + pathString);
			}
		}
		
		return ArrayUtil.createFrom(pathArray, Path.class);
	}
	
	protected Path[] searchImplicitSrcFolders(Path location) {
		if(location == null) {
			return new Path[0];
		}
		File locationDir = location.toFile();
		if(!locationDir.isDirectory()) {
			putError("location is not a directory");
			return new Path[0];
		}
		
		final ArrayList<Path> implicitFolders = new ArrayList<>();
		locationDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(dir.isDirectory() && (name.equals("src") || name.equals("source"))) {
					implicitFolders.add(MiscUtil.createValidPath(name));
				}
				return false;
			}
		});
		
		return ArrayUtil.createFrom(implicitFolders, Path.class);
	}
	
	@Override
	protected void readData(JsonReaderExt jsonParser) throws IOException {
		readBundle(jsonParser);
	}
	
	protected DubManifestParser readBundle(JsonReaderExt jsonReader) throws IOException {
		jsonReader.consumeExpected(JsonToken.BEGIN_OBJECT);
		
		while(jsonReader.hasNext()) {
			JsonToken tokenType = jsonReader.peek();
			
			if(tokenType == JsonToken.NAME) {
				String propertyName = jsonReader.nextName();
				
				if(propertyName.equals("name")) {
					bundleName = jsonReader.consumeStringValue();
				} else if(propertyName.equals("version")) {
					version = jsonReader.consumeStringValue();
				} else if(propertyName.equals("path")) {
					pathString = jsonReader.consumeStringValue();
				} else if(propertyName.equals("importPaths")) {
					sourceFolders = parseSourcePaths(jsonReader);
				} else if(propertyName.equals("dependencies")) {
					dependencies = parseDependencies(jsonReader);
				} else if(propertyName.equals("files")) {
					bundleFiles = parseFiles(jsonReader);
				} else if(propertyName.equals("targetName")) {
					targetName = jsonReader.consumeStringValue();
				} else if(propertyName.equals("targetPath")) {
					targetPath = jsonReader.consumeStringValue();
				} else {
					jsonReader.skipValue();
				}
			} else {
				jsonReader.errorUnexpected(tokenType);
			}
		}
		
		jsonReader.consumeExpected(JsonToken.END_OBJECT);
		return this;
	}
	
	protected String[] parseSourcePaths(JsonReaderExt jsonReader) throws IOException {
		ArrayList<String> stringArray = jsonReader.consumeStringArray(true);
		return ArrayUtil.createFrom(stringArray, String.class);
	}
	
	
	protected ArrayList<BundleFile> parseFiles(JsonReaderExt jsonReader) throws IOException {
		jsonReader.consumeExpected(JsonToken.BEGIN_ARRAY);
		
		ArrayList<BundleFile> bundleFiles = new ArrayList<>();
		
		while(jsonReader.hasNext()) {
			BundleFile bundleFile = parseFile(jsonReader);
			bundleFiles.add(bundleFile);
		}
		
		jsonReader.consumeExpected(JsonToken.END_ARRAY);
		return bundleFiles;
	}
	
	protected BundleFile parseFile(JsonReaderExt jsonReader) throws IOException {
		jsonReader.consumeExpected(JsonToken.BEGIN_OBJECT);
		String path = null;
		boolean importOnly = false;
		
		while(jsonReader.hasNext()) {
			String propName = jsonReader.consumeExpectedPropName();
			
			switch(propName) {
			case "path":
				path = jsonReader.consumeStringValue();
				break;
			case "type":
				//TODO
				
			default:
				jsonReader.skipValue();
			}
		}
		jsonReader.consumeExpected(JsonToken.END_OBJECT);
		if(path == null) {
			path = "<missing_path>";
			putError("missing path property for files entry.");
		}
		return new DubBundle.BundleFile(path, importOnly);
	}
	
	protected DubDependecyRef[] parseDependencies(JsonReaderExt jsonParser) throws IOException {
		return new BundleDependenciesSegmentParser(jsonParser).parse();
	}
	
	protected class BundleDependenciesSegmentParser {
		
		protected JsonReaderExt jsonReader;
		
		public BundleDependenciesSegmentParser(JsonReaderExt jsonParser) {
			this.jsonReader = jsonParser;
		}
		
		public DubDependecyRef[] parse() throws IOException {
			if(jsonReader.peek() == JsonToken.BEGIN_OBJECT) 
				return parseRawDeps();
			
			return parseResolvedDeps();
		}
		
		public DubDependecyRef[] parseRawDeps() throws IOException, MalformedJsonException {
			jsonReader.consumeExpected(JsonToken.BEGIN_OBJECT);
			
			ArrayList<DubDependecyRef> deps = new ArrayList<>();
			
			while(jsonReader.hasNext()) {
				String depName = jsonReader.consumeExpectedPropName();
				jsonReader.skipValue(); // Ignore value for now, TODO
				
				deps.add(new DubDependecyRef(depName, null));
			}
			jsonReader.consumeExpected(JsonToken.END_OBJECT);
			return ArrayUtil.createFrom(deps, DubDependecyRef.class);
		}
		
		public DubDependecyRef[] parseResolvedDeps() throws IOException, MalformedJsonException {
			jsonReader.consumeExpected(JsonToken.BEGIN_ARRAY);
			
			ArrayList<DubDependecyRef> deps = new ArrayList<>();
			
			while(jsonReader.hasNext()) {
				String depName = jsonReader.nextString();
				deps.add(new DubDependecyRef(depName, null));
			}
			jsonReader.consumeExpected(JsonToken.END_ARRAY);
			return ArrayUtil.createFrom(deps, DubDependecyRef.class);
		}
	}
	
}