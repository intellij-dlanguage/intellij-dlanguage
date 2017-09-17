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

    public static Boolean isKnownGdb(String path) {
        try {
            GeneralCommandLine command = new GeneralCommandLine();
            command.setExePath(path);
            command.addParameter("--version");

            ProcessOutput output = new CapturingProcessHandler(
                command.createProcess(),
                Charset.defaultCharset(),
                command.getCommandLineString()).runProcess();

            if (output.getExitCode() != 0) {
                LOG.error("gdb exited with invalid exit code: " + output.getExitCode());
                return false;
            }

            String cmdOutput = output.getStdout();
            return cmdOutput.contains("(GDB) 7.6") || cmdOutput.contains("(GDB) 7.4");
        } catch (Exception e) {
            LOG.error("Exception while executing the process:", e);
            return false;
        }
    }

    public static Boolean isValidGdbPath(String path) {
        try {
            GeneralCommandLine command = new GeneralCommandLine();
            command.setExePath(path);
            command.addParameter("--version");

            ProcessOutput output = new CapturingProcessHandler(
                command.createProcess(),
                Charset.defaultCharset(),
                command.getCommandLineString()).runProcess();

            if (output.getExitCode() != 0) {
                LOG.error("gdb exited with invalid exit code: " + output.getExitCode());
                return false;
            }

            // TODO maybe we should warn the user that his GDB version is not the latest at time of writing (7.6.2)
            return output.getStdout().contains("GDB");
        } catch (Exception e) {
//            LOG.error("Exception while executing the process:", e);
            return false;
        }
    }
}
