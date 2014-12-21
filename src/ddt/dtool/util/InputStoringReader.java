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

import java.io.CharArrayWriter;
import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;

/**
 * A {@link Reader} that stores all the input it reads from the stream into an appendable.
 */
public class InputStoringReader<T extends Appendable> extends FilterReader {
	
	public static InputStoringReader<StringWriter> createDefault(Reader in) {
		return new InputStoringReader<StringWriter>(in, false, new StringWriter());
	}
	
	protected final boolean allowMarkReset;
	protected final CharArrayWriter storedInput2 = new CharArrayWriter();
	protected final T storedInput;
	
	public InputStoringReader(Reader in, boolean allowMarkReset, T storedInput) {
		super(in);
		this.allowMarkReset = allowMarkReset;
		this.storedInput = storedInput;
	}
	
	@Override
	public int read() throws IOException {
		int read = in.read();
		if(read != -1) {
			storedInput.append((char) read);
		}
		return read;
	}
	
	@Override
	public int read(char cbuf[], int off, int len) throws IOException {
		int readLen = in.read(cbuf, off, len);
		if(readLen != -1) {
			storedInput.append(new String(cbuf, off, readLen));
		}
		return readLen;
	}
	
	@Override
	public long skip(long n) throws IOException {
		long readCount = 0;
		while(readCount < n) {
			int ch = read();
			if(ch == -1)
				break;
			readCount++;
		}
		return readCount;
	}
	
	@Override
	public boolean ready() throws IOException {
		return in.ready();
	}
	
	@Override
	public void close() throws IOException {
		in.close();
	}
	
	public T getStoredInput() {
		return storedInput;
	}
	
	/* ----------------- mark/reset ----------------- */
	
	@Override
	public boolean markSupported() {
		if(!allowMarkReset) {
			return false;
		}
		return in.markSupported();
	}
	
	@Override
	public void mark(int readAheadLimit) throws IOException {
		if(!allowMarkReset) {
			throw new IOException("mark() not supported");
		}
		in.mark(readAheadLimit);
	}
	
	@Override
	public void reset() throws IOException {
		if(!allowMarkReset) {
			throw new IOException("reset() not supported");
		}
		in.reset();
	}
	
}