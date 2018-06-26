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

package uk.co.cwspencer.ideagdb.debug.breakpoints;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.containers.BidirectionalMap;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.breakpoints.XBreakpointHandler;
import com.intellij.xdebugger.breakpoints.XLineBreakpoint;
import org.jetbrains.annotations.NotNull;
import uk.co.cwspencer.gdb.Gdb;
import uk.co.cwspencer.gdb.messages.GdbBreakpoint;
import uk.co.cwspencer.gdb.messages.GdbErrorEvent;
import uk.co.cwspencer.gdb.messages.GdbEvent;
import uk.co.cwspencer.ideagdb.debug.GdbDebugProcess;
import uk.co.cwspencer.ideagdb.debug.utils.SdkUtil;

import java.util.List;

public class GdbBreakpointHandler extends
    XBreakpointHandler<XLineBreakpoint<GdbBreakpointProperties>> {
    private static final Logger m_log =
        Logger.getInstance("#uk.co.cwspencer.ideagdb.debug.breakpoints.GdbBreakpointHandler");
    // The breakpoints that have been set and their GDB breakpoint numbers
    private final BidirectionalMap<Integer, XLineBreakpoint<GdbBreakpointProperties>>
        m_breakpoints = new BidirectionalMap<Integer, XLineBreakpoint<GdbBreakpointProperties>>();
    private final Gdb m_gdb;
    private final GdbDebugProcess m_debugProcess;
    private final boolean m_isWsl;

    public GdbBreakpointHandler(Gdb gdb, GdbDebugProcess debugProcess, boolean isWsl) {
        super(GdbBreakpointType.class);
        m_gdb = gdb;
        m_debugProcess = debugProcess;
        m_isWsl = isWsl;
    }

    /**
     * Registers the given breakpoint with GDB.
     *
     * @param breakpoint The breakpoint.
     */
    @Override
    public void registerBreakpoint(
        @NotNull final XLineBreakpoint<GdbBreakpointProperties> breakpoint) {
        // TODO: I think we can use tracepoints here if the suspend policy isn't to stop the process

        // Check if the breakpoint already exists
        Integer number = findBreakpointNumber(breakpoint);
        if (number != null) {
            // Re-enable the breakpoint
            m_gdb.sendCommand("-break-enable " + number);
        } else {
            // Set the breakpoint
            XSourcePosition sourcePosition = breakpoint.getSourcePosition();
            if (sourcePosition == null) {
                return;
            }

            String filePath = sourcePosition.getFile().getPath();
            if (m_isWsl) {
                filePath = SdkUtil.translateWslWindowsToPosixPath(filePath);
            }

            String command = "-break-insert -f " + filePath + ":" + (sourcePosition.getLine() + 1);
            m_gdb.sendCommand(command, new Gdb.GdbEventCallback() {
                @Override
                public void onGdbCommandCompleted(GdbEvent event) {
                    onGdbBreakpointReady(event, breakpoint);
                }
            });
        }
    }

    /**
     * Unregisters the given breakpoint with GDB.
     *
     * @param breakpoint The breakpoint.
     * @param temporary  Whether we are deleting the breakpoint or temporarily disabling it.
     */
    @Override
    public void unregisterBreakpoint(@NotNull XLineBreakpoint<GdbBreakpointProperties> breakpoint,
                                     boolean temporary) {
        Integer number = findBreakpointNumber(breakpoint);
        if (number == null) {
            m_log.warn("Cannot remove breakpoint; could not find it in breakpoint table");
            return;
        }

        if (!temporary) {
            // Delete the breakpoint
            m_gdb.sendCommand("-break-delete " + number);
            synchronized (m_breakpoints) {
                m_breakpoints.remove(number);
            }
        } else {
            // Disable the breakpoint
            m_gdb.sendCommand("-break-disable " + number);
        }
    }

    /**
     * Finds a breakpoint by its GDB number.
     *
     * @param number The GDB breakpoint number.
     * @return The breakpoint, or null if it could not be found.
     */
    public XLineBreakpoint<GdbBreakpointProperties> findBreakpoint(int number) {
        synchronized (m_breakpoints) {
            return m_breakpoints.get(number);
        }
    }

    /**
     * Finds a breakpoint's GDB number.
     *
     * @param breakpoint The breakpoint to search for.
     * @return The breakpoint number, or null if it could not be found.
     */
    public Integer findBreakpointNumber(XLineBreakpoint<GdbBreakpointProperties> breakpoint) {
        List<Integer> numbers;
        synchronized (m_breakpoints) {
            numbers = m_breakpoints.getKeysByValue(breakpoint);
        }

        if (numbers == null || numbers.isEmpty()) {
            return null;
        } else if (numbers.size() > 1) {
            m_log.warn("Found multiple breakpoint numbers for breakpoint; only returning the " +
                "first");
        }
        return numbers.get(0);
    }

    /**
     * Callback function for when GDB has responded to our breakpoint request.
     *
     * @param event      The event.
     * @param breakpoint The breakpoint we tried to set.
     */
    private void onGdbBreakpointReady(GdbEvent event,
                                      XLineBreakpoint<GdbBreakpointProperties> breakpoint) {
        if (event instanceof GdbErrorEvent) {
            m_debugProcess.getSession().updateBreakpointPresentation(breakpoint,
                AllIcons.Debugger.Db_invalid_breakpoint, ((GdbErrorEvent) event).message);
            return;
        }
        if (!(event instanceof GdbBreakpoint)) {
            m_debugProcess.getSession().updateBreakpointPresentation(breakpoint,
                AllIcons.Debugger.Db_invalid_breakpoint, "Unexpected data received from GDB");
            m_log.warn("Unexpected event " + event + " received from -break-insert request");
            return;
        }

        // Save the breakpoint
        GdbBreakpoint gdbBreakpoint = (GdbBreakpoint) event;
        if (gdbBreakpoint.number == null) {
            m_debugProcess.getSession().updateBreakpointPresentation(breakpoint,
                AllIcons.Debugger.Db_invalid_breakpoint, "No breakpoint number received from GDB");
            m_log.warn("No breakpoint number received from GDB after -break-insert request");
            return;
        }

        synchronized (m_breakpoints) {
            m_breakpoints.put(gdbBreakpoint.number, breakpoint);
        }

        // Mark the breakpoint as set
        // TODO: Don't do this yet if the breakpoint is pending
        m_debugProcess.getSession().updateBreakpointPresentation(breakpoint,
            AllIcons.Debugger.Db_verified_breakpoint, null);
    }
}
