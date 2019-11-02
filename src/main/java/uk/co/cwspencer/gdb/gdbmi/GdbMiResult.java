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

import org.jetbrains.annotations.NotNull;

/**
 * Class representing a single result from a GDB/MI result record.
 */
public class GdbMiResult {
    /**
     * Name of the variable.
     */
    public String variable;

    /**
     * Value of the variable.
     */
    public GdbMiValue value;

    /**
     * Constructor.
     *
     * @param variable The name of the variable.
     */
    public GdbMiResult(final String variable) {
        this.variable = variable;
        this.value = new GdbMiValue();
    }

    public GdbMiResult(final String variable, @NotNull final GdbMiValue value) {
        this.variable = variable;
        this.value = value;
    }

    /**
     * Converts the result to a string.
     *
     * @return A string containing the name of the variable and its value.
     */
    @Override
    public String toString() {
        return variable + ": " + value;
    }
}
