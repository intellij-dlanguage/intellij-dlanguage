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
import uk.co.cwspencer.gdb.messages.annotations.GdbMiEnum;
import uk.co.cwspencer.gdb.messages.annotations.GdbMiField;
import uk.co.cwspencer.gdb.messages.annotations.GdbMiObject;

/**
 * The changes to a GDB variable object since the last update.
 */
@SuppressWarnings("unused")
@GdbMiObject
public class GdbVariableObjectChange {
    /**
     * The name of the variable object.
     */
    @GdbMiField(name = "name", valueType = GdbMiValue.Type.String)
    public String name;
    /**
     * The value of the variable.
     */
    @GdbMiField(name = "value", valueType = GdbMiValue.Type.String)
    public String value;
    /**
     * Whether the variable object is still in scope.
     */
    @GdbMiField(name = "in_scope", valueType = GdbMiValue.Type.String)
    public InScope inScope;
    /**
     * Whether the value's type has changed since the last update
     */
    @GdbMiField(name = "type_changed", valueType = GdbMiValue.Type.String)
    public Boolean typeChanged;
    /**
     * The new type, if typeChanged is true.
     */
    @GdbMiField(name = "new_type", valueType = GdbMiValue.Type.String)
    public String newType;
    /**
     * The new number of children of the object.
     */
    @GdbMiField(name = "new_num_children", valueType = GdbMiValue.Type.String)
    public Integer newNumChildren;
    /**
     * A hint about how to display the value.
     */
    @GdbMiField(name = "displayhint", valueType = GdbMiValue.Type.String)
    public GdbVariableObject.DisplayHint displayHint;
    /**
     * For dynamic objects this specifies whether there appear to be any more children available.
     */
    @GdbMiField(name = "has_more", valueType = GdbMiValue.Type.String)
    public Boolean hasMore;
    /**
     * Whether this is a dynamic variable object.
     */
    @GdbMiField(name = "dynamic", valueType = GdbMiValue.Type.String)
    public Boolean isDynamic;

    /**
     * Possible scope values for the variable object.
     */
    @GdbMiEnum
    public enum InScope {
        /**
         * The variable object is in scope and the value is valid.
         */
        True,
        /**
         * The value is not currently valid, but may come back into scope.
         */
        False,
        /**
         * The variable object is no longer valid because the file being debugged has changed.
         */
        Invalid
    }

    // TODO: What does this field contain?
    //@GdbMiField(name = "new_children", valueType = GdbMiValue.Type.??)
    //public List<??> newChildren;
}
