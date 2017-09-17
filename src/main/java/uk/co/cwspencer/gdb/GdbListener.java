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

package uk.co.cwspencer.gdb;

import uk.co.cwspencer.gdb.gdbmi.GdbMiResultRecord;
import uk.co.cwspencer.gdb.gdbmi.GdbMiStreamRecord;
import uk.co.cwspencer.gdb.messages.GdbEvent;

/**
 * Interface that users of the Gdb class must implement to receive events.
 */
public interface GdbListener {
    /**
     * Called when a GDB error occurs.
     *
     * @param ex The exception
     */
    void onGdbError(Throwable ex);

    /**
     * Called when GDB has started.
     */
    void onGdbStarted();

    /**
     * Called whenever a command is sent to GDB.
     *
     * @param command The command that was sent.
     * @param token   The token the command was sent with.
     */
    void onGdbCommandSent(String command, long token);

    /**
     * Called when an event is received from GDB.
     *
     * @param event The event.
     */
    void onGdbEventReceived(GdbEvent event);

    /**
     * Called when a stream record is received.
     * This should only be used for logging or advanced behaviour. Prefer to use
     * onGdbEventReceived() instead.
     *
     * @param record The record.
     */
    void onStreamRecordReceived(GdbMiStreamRecord record);

    /**
     * Called when a result record is received.
     * This should only be used for logging or advanced behaviour. Prefer to use
     * onGdbEventReceived() instead.
     *
     * @param record The record.
     */
    void onResultRecordReceived(GdbMiResultRecord record);
}
