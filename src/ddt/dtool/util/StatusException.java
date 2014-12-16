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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

/**
 * A generic status exception.
 * Has an associated message, and optionally an associated exception cause.
 */
public class StatusException extends Exception {
	
	private static final long serialVersionUID = -7324639626503261646L;
	
	public StatusException(String message, Throwable cause) {
		super(assertNotNull(message), cause);
	}
	
}