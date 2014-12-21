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
package ddt.dtool.ddoc;

import ddt.descent.core.ddoc.HTMLPrinterUtils;

// TODO: escape HTML content in string parans 
public class HTMLWriterUtil {
	
	public static String href(String url) {
		return href(url, url);
	}
	
	public static String href(String url, String text) {
		return "<a href=\""+url+"\" >" + text + "</span>";
	}
	
	public static String span(String cssClass, String style, String spanContents) {
		return "<span style=\""+style+"\" class=\"" + cssClass + "\" >" + spanContents + "</span>";
	}
	
	public static String convertoToHTML(String string) {
		string = HTMLPrinterUtils.convertToHTMLContent(string);
		string = string.replace("\n", "<br/>");
		return string;
	}
	
}