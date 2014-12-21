package ddt.dtool.engine.modules;

import static ddt.melnorme.utilbox.core.CoreUtil.array;

import java.nio.file.Path;

import ddt.melnorme.lang.tooling.context.ModuleFullName;
import ddt.melnorme.utilbox.misc.MiscUtil;
import ddt.melnorme.utilbox.misc.StringUtil;
import ddt.dtool.parser.DeeLexingUtil;

/**
 * Naming rules code for compilation units and packages.
 * 
 */
public class ModuleNamingRules {
	
	private static final String DEE_FILE_EXTENSION = ".d";
	private static final String DEE_HEADERFILE_EXTENSION = ".di";
	
	public static final String[] VALID_EXTENSIONS = array(DEE_FILE_EXTENSION, DEE_HEADERFILE_EXTENSION);
	
	
	public static String getDefaultModuleNameFromFileName(String fileName) {
		return StringUtil.substringUntilMatch(fileName, ".");
	}
	
	protected static boolean isValidDFileExtension(String fileExt) {
		return DEE_FILE_EXTENSION.equals(fileExt) || DEE_HEADERFILE_EXTENSION.equals(fileExt);
	}
	
	/* ----------------- ----------------- */
	
	public static ModuleFullName getValidModuleNameOrNull(Path filePath) {
		int count = filePath.getNameCount();
		if(count == 0) {
			return null;
		}
		
		String fileName = filePath.getFileName().toString();
		String moduleBaseName = getModuleNameIfValidFileName(fileName, true);
		if(moduleBaseName == null) {
			return null;
		}
		
		if(moduleBaseName.equals("package")) {
			count--;
			if(count == 0) {
				return null;
			}
			moduleBaseName = filePath.getName(count-1).toString();
		}
		if(!DeeLexingUtil.isValidDIdentifier(moduleBaseName)) {
			return null;
		}
		
		String[] segments = new String[count];
		segments[count - 1] = moduleBaseName;
		
		for (int i = 0; i < count - 1; i++) {
			segments[i] = filePath.getName(i).toString();
			if(!isValidPackageNameSegment(segments[i])) {
				return null;
			}
		}
		
		return new ModuleFullName(segments);
	}
	
	public static boolean isValidCompilationUnitName(String fileName) {
		return getModuleNameIfValidFileName(fileName) != null;
	}
	
	protected static String getModuleNameIfValidFileName(String fileName) {
		return getModuleNameIfValidFileName(fileName, false);
	}
	
	protected static String getModuleNameIfValidFileName(String fileName, boolean allowPackageName) {
		String fileExtension = StringUtil.substringFromMatch(".", fileName);
		if(!isValidDFileExtension(fileExtension)){
			return null;
		}
		String moduleName = StringUtil.substringUntilMatch(fileName, ".");
		if(DeeLexingUtil.isValidDIdentifier(moduleName)) {
			return moduleName;
		}
		if(allowPackageName && moduleName.equals("package")) {
			return moduleName;
		}
		return null;
	}
	
	public static boolean isValidPackageNameSegment(String partname) {
		return DeeLexingUtil.isValidDIdentifier(partname);
	}
	
	/* ----------------- ----------------- */
	
	public static boolean isValidPackagesPath(String packagesPathStr) {
		if(packagesPathStr.equals(""))
			return true;
		
		String[] segments = packagesPathStr.split("/");
		for (String segment : segments) {
			if(!isValidPackageNameSegment(segment))
				return false;
		}
		return true;
	}
	
	
	public static String getModuleFQNameFromFilePath(String packagePath, String fileName) {
		Path path = MiscUtil.createPathOrNull(packagePath + "/" + fileName);
		if(path == null) {
			return null;
		}
		
		ModuleFullName moduleValidName = getValidModuleNameOrNull(path);
		return moduleValidName == null ? null : moduleValidName.getFullNameAsString();
	}
	
}