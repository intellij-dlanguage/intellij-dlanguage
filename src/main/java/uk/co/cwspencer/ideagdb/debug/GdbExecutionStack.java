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

package uk.co.cwspencer.ideagdb.debug;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.xdebugger.frame.XExecutionStack;
import com.intellij.xdebugger.frame.XStackFrame;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import org.jetbrains.annotations.Nullable;
import uk.co.cwspencer.gdb.Gdb;
import uk.co.cwspencer.gdb.messages.*;

import java.util.ArrayList;
import java.util.List;

public class GdbExecutionStack extends XExecutionStack {
    private static final Logger m_log =
        Logger.getInstance("#uk.co.cwspencer.ideagdb.debug.GdbExecutionStack");

    // The GDB instance
    private Gdb m_gdb;

    // The thread
    private GdbThread m_thread;

    // The top of the stack
    private GdbExecutionStackFrame m_topFrame;

    /**
     * Constructor.
     *
     * @param gdb    Handle to the GDB instance.
     * @param thread The thread.
     */
    public GdbExecutionStack(Gdb gdb, GdbThread thread) {
        super(thread.formatName(), DLanguageIcons.FILE);

        m_gdb = gdb;
        m_thread = thread;

        // Get the top of the stack
        if (thread.frame != null) {
            m_topFrame = new GdbExecutionStackFrame(gdb, m_thread.id, thread.frame);
        }
    }

    /**
     * Returns the frame at the top of the stack.
     *
     * @return The stack frame.
     */
    @Nullable
    @Override
    public XStackFrame getTopFrame() {
        return m_topFrame;
    }

    /**
     * Gets the stack trace starting at the given index. This passes the request and returns
     * immediately; the data is supplied to container asynchronously.
     *
     * @param firstFrameIndex The first frame to retrieve, where 0 is the top of the stack.
     * @param container       Container into which the stack frames are inserted.
     */
    @Override
    public void computeStackFrames(final int firstFrameIndex, final XStackFrameContainer container) {
        // Just get the whole stack
        String command = "-stack-list-frames";
        m_gdb.sendCommand(command, new Gdb.GdbEventCallback() {
            @Override
            public void onGdbCommandCompleted(GdbEvent event) {
                onGdbStackTraceReady(event, firstFrameIndex, container);
            }
        });
    }

    /**
     * Callback function for when GDB has responded to our stack trace request.
     *
     * @param event           The event.
     * @param firstFrameIndex The first frame from the list to use.
     * @param container       The container passed to computeStackFrames().
     */
    private void onGdbStackTraceReady(GdbEvent event, int firstFrameIndex,
                                      XStackFrameContainer container) {
        if (event instanceof GdbErrorEvent) {
            container.errorOccurred(((GdbErrorEvent) event).message);
            return;
        }
        if (!(event instanceof GdbStackTrace)) {
            container.errorOccurred("Unexpected data received from GDB");
            m_log.warn("Unexpected event " + event + " received from -stack-list-frames request");
            return;
        }

        // Inspect the stack trace
        GdbStackTrace stackTrace = (GdbStackTrace) event;
        if (stackTrace.stack == null || stackTrace.stack.isEmpty()) {
            // No data
            container.addStackFrames(new ArrayList<XStackFrame>(0), true);
        }

        // Build a list of GdbExecutionStaceFrames
        List<GdbExecutionStackFrame> stack = new ArrayList<GdbExecutionStackFrame>();
        for (int i = firstFrameIndex; i < stackTrace.stack.size(); ++i) {
            GdbStackFrame frame = stackTrace.stack.get(i);
            stack.add(new GdbExecutionStackFrame(m_gdb, m_thread.id, frame));
        }

        // Pass the data on
        container.addStackFrames(stack, true);
    }
}
