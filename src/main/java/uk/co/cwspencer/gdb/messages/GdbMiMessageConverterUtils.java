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

/**
 * Utility functions for use with the message converter.
 */
@SuppressWarnings("unused")
public class GdbMiMessageConverterUtils {
    /**
     * Converts a hexadecimal string to a long.
     */
    public static Long hexStringToLong(GdbMiValue value) {
        Long longValue = null;
        if (value.type == GdbMiValue.Type.String && value.string.substring(0, 2).equals("0x")) {
            longValue = Long.parseLong(value.string.substring(2), 16);
        }
        return longValue;
    }

    /**
     * Returns true if value is equal to "all".
     */
    public static Boolean valueIsAll(GdbMiValue value) {
        return value.type == GdbMiValue.Type.String && value.string.equals("all");
    }

    /**
     * Returns null if value is equal to "all", or otherwise requests normal processing for the
     * value.
     */
    public static Object passThroughIfNotAll(GdbMiValue value) {
        if (valueIsAll(value)) {
            return null;
        }
        return GdbMiMessageConverter.ValueProcessorPassThrough;
    }

    /**
     * Returns null if value is equal to "??", or otherwise requests normal processing for the
     * value.
     */
    public static Object passThroughIfNotQQ(GdbMiValue value) {
        if (value.type == GdbMiValue.Type.String && value.string.equals("??")) {
            return null;
        }
        return GdbMiMessageConverter.ValueProcessorPassThrough;
    }

}
