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
 * Class representing information about an execution thread from GDB.
 */
@SuppressWarnings("unused")
@GdbMiObject
public class GdbThread {
    /**
     * Flag indicating whether this is the current thread.
     */
    @GdbMiField(name = "current", valueType = GdbMiValue.Type.String)
    public Boolean current;
    /**
     * The GDB identifier.
     */
    @GdbMiField(name = "id", valueType = GdbMiValue.Type.String)
    public Integer id;
    /**
     * The target identifier.
     */
    @GdbMiField(name = "target-id", valueType = GdbMiValue.Type.String)
    public String targetId;
    /**
     * Extra information about the thread in a target-specific format
     */
    @GdbMiField(name = "details", valueType = GdbMiValue.Type.String)
    public String details;
    /**
     * The name of the thread, if available.
     */
    @GdbMiField(name = "name", valueType = GdbMiValue.Type.String)
    public String name;
    /**
     * The stack frame currently executing in the thread.
     */
    @GdbMiField(name = "frame", valueType = GdbMiValue.Type.Tuple)
    public GdbStackFrame frame;
    /**
     * The thread's state.
     */
    @GdbMiField(name = "state", valueType = GdbMiValue.Type.String)
    public State state;
    /**
     * The core on which the thread is running, if known.
     */
    @GdbMiField(name = "core", valueType = GdbMiValue.Type.String)
    public String core;

    // TODO: Should this be an integer?

    /**
     * Formats the thread into a string suitable to be prevented to the user.
     *
     * @return The formatted thread name.
     */
    public String formatName() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(id);
        sb.append("]");

        if (name != null) {
            sb.append(" ");
            sb.append(name);
        } else if (targetId != null) {
            sb.append(" ");
            sb.append(targetId);
        }

        if (frame != null && frame.function != null) {
            sb.append(" :: ");
            sb.append(frame.function);
            sb.append("()");
        }

        return sb.toString();
    }

    /**
     * Possible states for the thread.
     */
    @GdbMiEnum
    public enum State {
        Stopped,
        Running
    }
}
