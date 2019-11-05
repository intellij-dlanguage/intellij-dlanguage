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
    private final Gdb m_gdb;

    // The evaluation context
    private final int m_thread;
    private final int m_frame;

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
