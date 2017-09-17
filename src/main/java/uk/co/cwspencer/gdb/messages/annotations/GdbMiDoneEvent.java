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

package uk.co.cwspencer.gdb.messages.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation applied to classes which represent 'done' events in GDB/MI messages.
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface GdbMiDoneEvent {
    /**
     * The request command that causes GDB to send this type of response.
     */
    String command();

    /**
     * If set this indicates that the object should be populated with the data from the result with
     * the given name. This avoids the need to have a 'done event' class that only contains one
     * field which points to the real data. The result must be a tuple or a list of results.
     */
    String transpose() default "";
}
