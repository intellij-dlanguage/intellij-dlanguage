/*
 * (These files where modified from: https://bitbucket.org/spencercw/ideagdb/src
 * Original Copyright:
 * Copyright (c) 2013 Chris Spencer <spencercw@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.)
 */

package uk.co.cwspencer.gdb.gdbmi;

/**
 * Utility functions relating to the use of GDB/MI.
 */
public class GdbMiUtil {
    // Mapping from hexadecimal digits to ASCII characters
    private static final char[] m_hexAscii =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Formats a string into the format required by GDB/MI.
     *
     * @param string The string to format.
     * @return The formatted string.
     */
    public static String formatGdbString(String string, Boolean autoQuote) {
        StringBuilder sb = new StringBuilder();
        if (autoQuote) {
            sb.append("\"");
        }
        for (int i = 0; i < string.length(); ++i) {
            char ch = string.charAt(i);

            // Restrict the use of escape characters to those that are likely to be safe
            if (ch == '\n') {
                sb.append("\\n");
            } else if (ch == '\r') {
                sb.append("\\r");
            } else if (ch == '"') {
                sb.append("\\\"");
            } else if (ch == '\\') {
                sb.append("\\\\");
            } else if (ch <= 0x1f || ch >= 0x7f) {
                sb.append("\\x");
                sb.append(m_hexAscii[ch >> 4]);
                sb.append(m_hexAscii[ch & 0x0f]);
            } else {
                sb.append(ch);
            }
        }
        if (autoQuote) {
            sb.append("\"");
        }
        return sb.toString();
    }

    public static String formatGdbString(String string) {
        return formatGdbString(string, true);
    }
}
