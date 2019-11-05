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
 * Class representing a token read from a GDB/MI stream.
 */
public class GdbMiToken {
    /**
     * The type of token.
     */
    public Type type;
    /**
     * The token value, if any.
     */
    public String value = null;

    /**
     * Constructor; sets the values.
     *
     * @param type  The type of the token.
     * @param value The value of the token.
     */
    public GdbMiToken(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Constructor; sets the type. The value is set to null.
     *
     * @param type The type of token.
     */
    public GdbMiToken(Type type) {
        this.type = type;
    }

    /**
     * Converts the token to a string.
     *
     * @return A string containing the type and, if set, the value.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        if (value != null) {
            sb.append(": ");
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * Possible token types.
     */
    public enum Type {
        UserToken,                  // String of digits
        ResultRecordPrefix,         // "^"
        ExecAsyncOutputPrefix,      // "*"
        StatusAsyncOutputPrefix,    // "+"
        NotifyAsyncOutputPrefix,    // "="
        ConsoleStreamOutputPrefix,  // "~"
        TargetStreamOutputPrefix,   // "@"
        LogStreamOutputPrefix,      // "&"
        Identifier,                 // result-class or async-class
        Equals,                     // "="
        ResultSeparator,            // ","
        StringPrefix,               // """
        StringFragment,             // Part of a string
        StringEscapePrefix,         // "\"
        StringEscapeApostrophe,     // "'"
        StringEscapeQuote,          // """
        StringEscapeQuestion,       // "?"
        StringEscapeBackslash,      // "\"
        StringEscapeAlarm,          // "a"
        StringEscapeBackspace,      // "b"
        StringEscapeFormFeed,       // "f"
        StringEscapeNewLine,        // "n"
        StringEscapeCarriageReturn, // "r"
        StringEscapeHorizontalTab,  // "t"
        StringEscapeVerticalTab,    // "v"
        StringEscapeHexPrefix,      // "x"
        StringEscapeHexValue,       // 1-* hexadecimal digits
        StringEscapeOctValue,       // 1-3 octal digits
        StringSuffix,               // """
        TuplePrefix,                // "{"
        TupleSuffix,                // "}"
        ListPrefix,                 // "["
        ListSuffix,                 // "]"
        NewLine,                    // "\r" or "\r\n"
        GdbSuffix                   // "(gdb)"
    }
}
