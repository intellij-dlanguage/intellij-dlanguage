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
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.evaluation.XDebuggerEvaluator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.co.cwspencer.gdb.Gdb;
import uk.co.cwspencer.gdb.messages.GdbErrorEvent;
import uk.co.cwspencer.gdb.messages.GdbEvent;
import uk.co.cwspencer.gdb.messages.GdbVariableObject;
import uk.co.cwspencer.gdb.messages.GdbVariableObjects;

/**
 * Expression evaluator for GDB.
 */
public class GdbEvaluator extends XDebuggerEvaluator {
    private static final Logger m_log =
        Logger.getInstance("#uk.co.cwspencer.ideagdb.debug.GdbEvaluator");

    // The GDB instance
    private Gdb m_gdb;

    // The evaluation context
    private int m_thread;
    private int m_frame;

    /**
     * Constructor.
     *
     * @param gdb    Handle to the GDB instance.
     * @param thread The thread to evaluate expressions in.
     * @param frame  The frame to evaluate expressions in.
     */
    public GdbEvaluator(Gdb gdb, int thread, int frame) {
        m_gdb = gdb;
        m_thread = thread;
        m_frame = frame;
    }

    /**
     * Evaluates the given expression.
     *
     * @param expression         The expression to evaluate.
     * @param callback           The callback function.
     * @param expressionPosition ??
     */
    @Override
    public void evaluate(@NotNull String expression, @NotNull final XEvaluationCallback callback,
                         @Nullable XSourcePosition expressionPosition) {
        m_gdb.evaluateExpression(m_thread, m_frame, expression, new Gdb.GdbEventCallback() {
            @Override
            public void onGdbCommandCompleted(GdbEvent event) {
                onGdbExpressionReady(event, callback);
            }
        });
    }

//    /**
//     * Evaluates the given expression.
//     *
//     * @param expression         The expression to evaluate.
//     * @param callback           The callback function.
//     * @param expressionPosition ??
//     * @param mode               Evaluation mode for the expression.
//     */
//    @Override
//    public void evaluate(@NotNull String expression, @NotNull XEvaluationCallback callback,
//                         @Nullable XSourcePosition expressionPosition, @Nullable EvaluationMode mode) {
//        if (mode != null && mode != EvaluationMode.EXPRESSION) {
//            throw new IllegalArgumentException("Unsupported expression evaluation mode");
//        }
//
//        evaluate(expression, callback, expressionPosition);
//    }

    /**
     * Indicates whether we can evaluate code fragments.
     *
     * @return Whether we can evaluate code fragments.
     */
    @Override
    public boolean isCodeFragmentEvaluationSupported() {
        // TODO: Add support for this if possible
        return false;
    }

    /**
     * Callback function for when GDB has responded to our expression evaluation request.
     *
     * @param event    The event.
     * @param callback The callback passed to evaluate().
     */
    private void onGdbExpressionReady(GdbEvent event, XEvaluationCallback callback) {
        if (event instanceof GdbErrorEvent) {
            callback.errorOccurred(((GdbErrorEvent) event).message);
            return;
        }
        if (!(event instanceof GdbVariableObjects)) {
            callback.errorOccurred("Unexpected data received from GDB");
            m_log.warn("Unexpected event " + event + " received from expression request");
            return;
        }

        GdbVariableObjects variableObjects = (GdbVariableObjects) event;
        if (variableObjects.objects.isEmpty()) {
            callback.errorOccurred("Failed to evaluate expression");
            return;
        }

        GdbVariableObject variableObject = variableObjects.objects.get(0);
        if (variableObject.value == null) {
            callback.errorOccurred("Failed to evaluate expression");
            return;
        }

        callback.evaluated(new GdbValue(m_gdb, variableObject));
    }
}
