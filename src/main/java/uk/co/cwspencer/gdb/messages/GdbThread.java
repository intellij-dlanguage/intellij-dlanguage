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
