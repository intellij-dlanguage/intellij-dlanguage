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

import uk.co.cwspencer.gdb.gdbmi.GdbMiRecord;
import uk.co.cwspencer.gdb.gdbmi.GdbMiValue;
import uk.co.cwspencer.gdb.messages.annotations.GdbMiEvent;
import uk.co.cwspencer.gdb.messages.annotations.GdbMiField;

import java.util.Map;

/**
 * Event fired when GDB connects to a remote target.
 */
@SuppressWarnings("unused")
@GdbMiEvent(recordType = GdbMiRecord.Type.Immediate, className = "connected")
public class GdbConnectedEvent extends GdbEvent {
    /**
     * The execution address.
     */
    @SuppressWarnings("unused")
    @GdbMiField(name = "addr", valueType = GdbMiValue.Type.String,
        valueProcessor = "uk.co.cwspencer.gdb.messages.GdbMiMessageConverterUtils.hexStringToLong")
    public Long address;

    /**
     * The name of the function.
     */
    @SuppressWarnings("unused")
    @GdbMiField(name = "func", valueType = GdbMiValue.Type.String, valueProcessor =
        "uk.co.cwspencer.gdb.messages.GdbMiMessageConverterUtils.passThroughIfNotQQ")
    public String function;

    /**
     * The arguments to the function.
     */
    @SuppressWarnings("unused")
    @GdbMiField(name = "args", valueType = GdbMiValue.Type.List)
    public Map<String, String> arguments;
}
