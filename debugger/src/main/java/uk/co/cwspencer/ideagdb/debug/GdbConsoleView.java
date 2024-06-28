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

import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.filters.TextConsoleBuilderImpl;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.jetbrains.annotations.NotNull;
import uk.co.cwspencer.gdb.Gdb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Console tab for GDB input and output.
 *
 * This class should probably implement ConsoleView and potentially ObservableConsoleView
 */
public class GdbConsoleView extends JPanel {

    private static final Logger m_log = Logger.getInstance(GdbConsoleView.class);

    private JPanel m_contentPanel;
    private JTextField m_prompt;
    private JPanel m_consoleContainer;

    private final Gdb m_gdb;
    //private final Project project;

    // The actual console
    private ConsoleView m_console;

    // The last command that was sent
    private String m_lastCommand;

    /*
    * The GDB Console is displayed in a tab alongside the GDB Raw Output console.
    */
    public GdbConsoleView(@NotNull final Gdb gdb, @NotNull final Project project) {
        this.m_gdb = gdb;

        // Shouldn't have to instantiate these as they should be bound to the form
        if (m_contentPanel == null)
            m_contentPanel = new JPanel(new GridLayoutManager(1, 1));

        if (m_prompt == null)
            m_prompt = new JTextField();

        if (m_consoleContainer == null)
            m_consoleContainer = new JPanel(new BorderLayout());

        // The builder is used with the equivalent of calling new ConsoleViewImpl(project, true)
        final TextConsoleBuilder builder = TextConsoleBuilderFactory.getInstance()
            .createBuilder(project, GlobalSearchScope.allScope(project)); // todo: find out if allScope or projectScope should be used
        ((TextConsoleBuilderImpl)builder).setUsePredefinedMessageFilter(true);
        builder.setViewer(true);

        m_console = builder.getConsole(); // was previously: new ConsoleViewImpl(project, true);

        m_consoleContainer.add(m_console.getComponent(), BorderLayout.CENTER);
        m_prompt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String command = event.getActionCommand();
                if (command.isEmpty() && m_lastCommand != null) {
                    // Resend the last command
                    m_gdb.sendCommand(m_lastCommand);
                } else if (!command.isEmpty()) {
                    // Send the command to GDB
                    m_lastCommand = command;
                    m_prompt.setText("");
                    m_gdb.sendCommand(command);
                }
            }
        });
    }

    public ConsoleView getConsole() {
        return m_console;
    }

    public JComponent getComponent() {
        return m_contentPanel;
    }

    public JComponent getPreferredFocusableComponent() {
        return m_prompt;
    }
}
