/*
 * Files adapted from the go-lang-idea-plugin project at,
 *  https://github.com/go-lang-plugin-org/go-lang-idea-plugin
 * Original copyright from the golang idea plugin:
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
