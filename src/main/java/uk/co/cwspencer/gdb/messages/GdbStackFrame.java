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

package uk.co.cwspencer.gdb.messages;

import uk.co.cwspencer.gdb.gdbmi.GdbMiValue;
import uk.co.cwspencer.gdb.messages.annotations.GdbMiField;
import uk.co.cwspencer.gdb.messages.annotations.GdbMiObject;

import java.util.Map;

/**
 * Class representing information about a stack frame from GDB.
 */
@SuppressWarnings("unused")
@GdbMiObject
public class GdbStackFrame {
    /**
     * The position of the frame within the stack, where zero is the top of the stack.
     */
    @GdbMiField(name = "level", valueType = GdbMiValue.Type.String)
    public Integer level;

    /**
     * The execution address.
     */
    @GdbMiField(name = "addr", valueType = GdbMiValue.Type.String,
        valueProcessor = "uk.co.cwspencer.gdb.messages.GdbMiMessageConverterUtils.hexStringToLong")
    public Long address;

    /**
     * The name of the function.
     */
    @GdbMiField(name = "func", valueType = GdbMiValue.Type.String, valueProcessor =
        "uk.co.cwspencer.gdb.messages.GdbMiMessageConverterUtils.passThroughIfNotQQ")
    public String function;

    /**
     * The arguments to the function.
     */
    @GdbMiField(name = "args", valueType = GdbMiValue.Type.List)
    public Map<String, String> arguments;

    /**
     * The relative path to the file being executed.
     */
    @GdbMiField(name = "file", valueType = GdbMiValue.Type.String)
    public String fileRelative;

    /**
     * The absolute path to the file being executed.
     */
    @GdbMiField(name = "fullname", valueType = GdbMiValue.Type.String)
    public String fileAbsolute;

    /**
     * The line number being executed.
     */
    @GdbMiField(name = "line", valueType = GdbMiValue.Type.String)
    public Integer line;

    /**
     * The module where the function is defined.
     */
    @GdbMiField(name = "from", valueType = GdbMiValue.Type.String)
    public String module;
}
