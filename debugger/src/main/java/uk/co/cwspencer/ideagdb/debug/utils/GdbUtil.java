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
package uk.co.cwspencer.ideagdb.debug.utils;

import com.google.common.collect.ImmutableMap;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.diagnostic.Logger;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by Florin Patan <florinpatan@gmail.com>
 * <p>
 * 1/14/14
 */
public class GdbUtil {
    private static final Logger LOG = Logger.getInstance(GdbUtil.class);

    // TODO properly add only types that work here
    private static final Map<String, Boolean> editingSupport = ImmutableMap.<String, Boolean>builder()
        .put("string", false)
        .build();

    public static String getGoObjectType(String originalType) {
        if (originalType.contains("struct string")) {
            return originalType.replace("struct ", "");
        }

        return originalType;
    }

    public static Boolean supportsEditing(String varType) {
        String goType = getGoObjectType(varType);

        if (!editingSupport.containsKey(goType)) {
            return true;
        }

        return editingSupport.get(goType);
    }

//    public static Boolean isKnownGdb(String path) {
//        try {
//            final GeneralCommandLine command = new GeneralCommandLine()
//                .withExePath(path)
//                .withParameters("--version");
//
//            final ProcessOutput output = new CapturingProcessHandler(
//                command.createProcess(),
//                Charset.defaultCharset(),
//                command.getCommandLineString()).runProcess();
//
//            if (output.getExitCode() != 0) {
//                LOG.error("gdb exited with invalid exit code: " + output.getExitCode());
//                return false;
//            }
//
//            String cmdOutput = output.getStdout();
//            return cmdOutput.contains("(GDB) 7.6") || cmdOutput.contains("(GDB) 7.4");
//        } catch (Exception e) {
//            LOG.error("Exception while executing the process:", e);
//            return false;
//        }
//    }
//
//    public static Boolean isValidGdbPath(String path) {
//        try {
//            final GeneralCommandLine command = new GeneralCommandLine()
//                .withExePath(path)
//                .withParameters("--version");
//
//            final ProcessOutput output = new CapturingProcessHandler(
//                command.createProcess(),
//                Charset.defaultCharset(),
//                command.getCommandLineString()).runProcess();
//
//            if (output.getExitCode() != 0) {
//                LOG.error("gdb exited with invalid exit code: " + output.getExitCode());
//                return false;
//            }
//
//            // TODO maybe we should warn the user that his GDB version is not the latest at time of writing (7.6.2)
//            return output.getStdout().contains("GDB");
//        } catch (Exception e) {
////            LOG.error("Exception while executing the process:", e);
//            return false;
//        }
//    }
}
