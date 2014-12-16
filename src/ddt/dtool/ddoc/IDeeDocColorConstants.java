package ddt.dtool.ddoc;


public interface IDeeDocColorConstants {

	/**
	 * Note: This constant is for internal use only. Clients should not use this constant.
	 * The prefix all color constants start with
	 * (value <code>"java_"</code>).
	 */
	String PREFIX= "java_"; //$NON-NLS-1$

	/** The color key for multi-line comments in Java code
	 * (value <code>"java_multi_line_comment"</code>).
	 */
	String JAVA_MULTI_LINE_COMMENT= "java_multi_line_comment"; //$NON-NLS-1$
	
	/** The color key for multi-line comments in Java code
	 * (value <code>"java_multi_line_comment"</code>).
	 */
	String JAVA_MULTI_LINE_PLUS_COMMENT= "java_multi_line_plus_comment"; //$NON-NLS-1$
	
	/** The color key for multi-line comments in Java code
	 * (value <code>"java_multi_line_comment"</code>).
	 */
	String JAVA_MULTI_LINE_PLUS_DOC_COMMENT= "java_multi_line_plus_doc_comment"; //$NON-NLS-1$

	/** The color key for single-line comments in Java code
	 * (value <code>"java_single_line_comment"</code>).
	 */
	String JAVA_SINGLE_LINE_COMMENT= "java_single_line_comment"; //$NON-NLS-1$
	
	/** The color key for single-line comments in Java code
	 * (value <code>"java_single_line_comment"</code>).
	 */
	String JAVA_SINGLE_LINE_DOC_COMMENT= "java_single_line_doc_comment"; //$NON-NLS-1$

	/** The color key for Java keywords in Java code
	 * (value <code>"java_keyword"</code>).
	 */
	String JAVA_KEYWORD= "java_keyword"; //$NON-NLS-1$

	/** The color key for string and character literals in Java code
	 * (value <code>"java_string"</code>).
	 */
	String JAVA_STRING= "java_string"; //$NON-NLS-1$
	
	/** The color key for pragmas in Java code
	 * (value <code>"java_pragma"</code>).
	 */
	String JAVA_PRAGMA= "java_pragma"; //$NON-NLS-1$


	/** The color key for keyword 'return' in Java code
	 * (value <code>"java_keyword_return"</code>).
	 *
	 * @since 3.0
	 */
	String JAVA_KEYWORD_RETURN= "java_keyword_return"; //$NON-NLS-1$
	
	/** The color key for special tokens in Java code
	 * (value <code>"java_special_token"</code>).
	 *
	 * @since 3.0
	 */
	String JAVA_SPECIAL_TOKEN= "java_special_token"; //$NON-NLS-1$

	/** The color key for operators and brackets in Java code
	 * (value <code>"java_operator"</code>).
	 *
	 * @since 3.0
	 */
	String JAVA_OPERATOR= "java_operator"; //$NON-NLS-1$

	/**
	 * The color key for everything in Java code for which no other color is specified
	 * (value <code>"java_default"</code>).
	 */
	String JAVA_DEFAULT= "java_default"; //$NON-NLS-1$


	/**
	 * The color key for task tags in java comments
	 * (value <code>"java_comment_task_tag"</code>).
	 *
	 * @since 2.1
	 */
	String TASK_TAG= "java_comment_task_tag"; //$NON-NLS-1$

	/**
	 * The color key for JavaDoc keywords (<code>@foo</code>) in JavaDoc comments
	 * (value <code>"java_doc_keyword"</code>).
	 */
	String JAVADOC_KEYWORD= "java_doc_keyword"; //$NON-NLS-1$

	/**
	 * The color key for HTML tags (<code>&lt;foo&gt;</code>) in JavaDoc comments
	 * (value <code>"java_doc_tag"</code>).
	 */
	String JAVADOC_TAG= "java_doc_tag"; //$NON-NLS-1$

	/**
	 * The color key for JavaDoc links (<code>{foo}</code>) in JavaDoc comments
	 * (value <code>"java_doc_link"</code>).
	 */
	String JAVADOC_LINK= "java_doc_link"; //$NON-NLS-1$

	/**
	 * The color key for everything in JavaDoc comments for which no other color is specified
	 * (value <code>"java_doc_default"</code>).
	 */
	String JAVADOC_DEFAULT= "java_doc_default"; //$NON-NLS-1$
}

