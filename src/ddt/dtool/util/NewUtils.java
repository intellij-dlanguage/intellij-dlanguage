package ddt.dtool.util;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ddt.melnorme.utilbox.collections.SimpleChainedIterator;
import ddt.melnorme.utilbox.core.Assert;
import ddt.melnorme.utilbox.misc.IteratorUtil;
import ddt.melnorme.utilbox.misc.Pair;
import ddt.melnorme.utilbox.misc.StreamUtil;

public class NewUtils {
	
	/** Asserts that given object is null or an instance of given klass. Returns casted object. */
	@SuppressWarnings("unchecked")
	public static <T> T assertCast(Object object, Class<T> klass) {
		assertTrue(object == null || klass.isInstance(object));
		return (T) object;
	}
	
	/** Asserts that given object an instance of given klass. Returns casted object. */
	@SuppressWarnings("unchecked")
	public static <T> T assertInstance(Object object, Class<T> klass) {
		assertTrue(klass.isInstance(object));
		return (T) object;
	}
	
	/* ----------------- collection utils ----------------- */
	
	public static <T> T getSingleElementOrNull(Iterable<T> iterable) {
		if(iterable == null) {
			return null;
		}
		Iterator<T> iterator = iterable.iterator();
		if(iterator.hasNext() == false) {
			return null;
		}
		T next = iterator.next();
		assertTrue(iterator.hasNext() == false);
		return next;
	}
	
	/* -----------------  ----------------- */
	
	public static final String[] EMPTY_STRING_ARRAY = new String[0];
	
	/** Shortcut for creating a new {@link ArrayList} */
	public static <T> ArrayList<T> createArrayList(Collection<T> coll) {
		return new ArrayList<T>(coll);
	}
	
	public static <T> Iterator<? extends T> getChainedIterator(
		Iterable<? extends T> iter1, Iterable<? extends T> iter2) 
	{
		if(iter1 == null && iter2 == null)
			return IteratorUtil.emptyIterator();
		if(iter1 == null)
			return iter2.iterator();
		if(iter2 == null)
			return iter1.iterator();
		
		return new SimpleChainedIterator<T>(iter1.iterator(), iter2.iterator());
	}
	
	@SafeVarargs
	public static <T> T[] assertNotContainsNull_(T... arr) {
		for (T elem : arr) {
			Assert.AssertNamespace.assertNotNull(elem);
		}
		return arr;
	}

	public static <T> T lastElement(List<T> list) {
		return list.get(list.size()-1);
	}
	
	public static <T> T[] emptyToNull(T[] array) {
		if(array == null || array.length == 0) {
			return null;
		}
		return array;
	}
	
	public static String emptyToNull(String string) {
		if(string.isEmpty()) {
			return null;
		}
		return string;
	}
	
	/** Returns true if one, and only one of the given objects is null. */
	public static boolean exactlyOneIsNull(Object objA, Object objB) {
		return (objA == null) != (objB == null);
	}
	
	public static int updateIfNull(int currentValue, int newValue) {
		return currentValue == -1 ? newValue : currentValue;
	}
	
	public static String replaceRegexFirstOccurrence(String str, String regex, int regexGroup, String replacement) {
		Matcher matcher = Pattern.compile(regex).matcher(str);
		if(matcher.find()) {
			int matchIx = matcher.start();
			int matchEndIx = matchIx + matcher.group(regexGroup).length();
			return str.substring(0, matchIx) + replacement + str.substring(matchEndIx, str.length()); 
		} else {
			return str;
		}
	}
	
	public static String substringRemoveEnd(String string, int lengthFromEnd) {
		return string.substring(0, string.length() - lengthFromEnd);
	}
	
	public static boolean isValidStringRange(String string, int startIndex, int length) {
		return startIndex >= 0 && length >= 0 && startIndex + length <= string.length();
	}
	
	public static String removeRange(String string, int startIndex, int length) {
		assertTrue(isValidStringRange(string, startIndex, length));
		int endIndex = startIndex + length;
		return string.substring(0, startIndex) + string.substring(endIndex, string.length());
	}
	
	public static String replaceRange(String string, int startIndex, int length, String repl) {
		assertTrue(isValidStringRange(string, startIndex, length));
		int endIndex = startIndex + length;
		return string.substring(0, startIndex) + repl + string.substring(endIndex, string.length());
	}
	
	public static <T, U> void addNew(Map<T, U> map, Map<? extends T, ? extends U> newGlobalExpansions) {
		for (Entry<? extends T, ? extends U> entry : newGlobalExpansions.entrySet()) {
			assertTrue(map.containsKey(entry.getKey()) == false);
			map.put(entry.getKey(), entry.getValue());
		}
	}
	
	public static <T> ArrayList<T> lazyInitArrayList(ArrayList<T> arrayList) {
		if(arrayList == null) {
			return new ArrayList<T>();
		}
		return arrayList;
	}
	
	/**
	 * Compares two strings according to the contract of {@link Comparator#compare(Object, Object)}
	 * Allows null values.
	 */
	public static int compareStrings(String str1, String str2) {
		if(str1 == str2) {
			return 0;
		}
		if(str1 == null && str2 != null) {
			return -1;
		}
		if(str2 == null && str1 != null) {
			return 1;
		}
		return str1.compareTo(str2);
	}
	
	@SafeVarargs
	public static <K, V> HashMap<K, V> newHashMap(Pair<K, V>... entries) {
		return addEntries(new HashMap<K, V>(), entries);
	}
	
	@SafeVarargs
	public static <K, V> HashMap<K, V> addEntries(HashMap<K, V> hashMap, Pair<K, V>... entries) {
		for (Pair<K,V> pair : entries) {
			hashMap.put(pair.getFirst(), pair.getSecond());
		}
		return hashMap;
	}
	
	public static final int EOF = -1;
	
	public static final Charset UTF8 = assertNotNull(Charset.forName("UTF-8"));
	public static final Charset UTF16BE = assertNotNull(Charset.forName("UTF-16BE"));
	public static final Charset UTF16LE = assertNotNull(Charset.forName("UTF-16LE"));
	public static final Charset UTF32BE = assertNotNull(Charset.forName("UTF-32BE"));
	public static final Charset UTF32LE = assertNotNull(Charset.forName("UTF-32LE"));
	
	
	public static Charset detectEncoding(InputStream is, boolean resetIS) throws IOException {
		byte[] bom = new byte[4];
		bom[3] = 0x42; // To disambiguate UTF16LE edge case and UTF32LE
		int read = is.read(bom);
		assertTrue(read == bom.length || is.read() == EOF);
		if(resetIS) {
			is.reset();
		}
		
		if(bom[0] == (byte)0xEF && bom[1] == (byte)0xBB && bom[2] == (byte)0xBF) {
			return UTF8;
		}
		if(bom[0] == (byte)0xFE && bom[1] == (byte)0xFF) {
			return UTF16BE;
		}
		if(bom[0] == (byte)0xFF && bom[1] == (byte)0xFE) {
			if(bom[2] == 0x00 && bom[3] == 0x00) {
				return UTF32LE;
			}
			return UTF16LE;
		}
		if(bom[0] == 0x00 && bom[1] == 0x00 && bom[2] == (byte)0xFE && bom[3] == (byte)0xFF) {
			return UTF32BE;
		}
		
		return null;
	}
	
	public static String readStringFromFile_PreserveBOM(File file, Charset defaultCharset) throws IOException {
		FileInputStream inputStream = new FileInputStream(file);
		return readStringFromStream_preserveBOM(inputStream, defaultCharset);
	}
	
	// XXX: This method could be optimized a bit
	public static String readStringFromStream_preserveBOM(InputStream is, Charset defaultCharset)
			throws IOException {
		byte[] fileBytes = StreamUtil.readAllBytesFromStream(is).toByteArray();
		final Charset encoding = detectEncoding(new ByteArrayInputStream(fileBytes), false);
		String string = new String(fileBytes, encoding == null ? defaultCharset : encoding);
		if(encoding != null) {
			if(encoding == UTF32BE || encoding == UTF32LE) {
				string = "\uFEFF" + string; // Make things consistent across encodings
			}
			assertTrue(string.charAt(0) == 0xFEFF);
		}
		return string;
	}
	
	public static <T> List<T> arrayListFromElement(T element) {
		ArrayList<T> list = new ArrayList<>();
		list.add(element);
		return list;
	}
	
}