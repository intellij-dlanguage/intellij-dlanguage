package io.github.intellij.dlanguage.codeinsight.dcd;

import io.github.intellij.dlanguage.codeinsight.dcd.completions.Completion;
import junit.framework.TestCase;

import java.util.List;

/**
 * @author Samael Bate (singingbush)
 * created on 17/09/2020
 */
public class DCDCompletionClientTest extends TestCase {

    private DCDCompletionClient dcdClient;

    public void setUp() throws Exception {
        super.setUp();

        this.dcdClient = new DCDCompletionClient();
    }

    public void testProcessDcdOutput_EmptyString() {
        final List<Completion> completions = dcdClient.processDcdOutput("");
        assertNotNull(completions);
        assertEquals(0, completions.size());
    }

    public void testProcessDcdOutput_Example1() {
        final String output = "identifiers\n" +
            "parts\tv\n" +
            "name\tv\n" +
            "location\tv\n" +
            "qualifier\tv\n" +
            "kind\tv\n" +
            "type\tv\n" +
            "resolvedType\tv\n" +
            "calltip\tv\n" +
            "getPartByName\tf";

        final List<Completion> completions = dcdClient.processDcdOutput(output);
        assertEquals(9, completions.size());
    }

    public void testProcessDcdOutput_Example2() {
        final String output = "identifiers\n" +
            "alignof\tk\n" +
            "dup\tk\n" +
            "idup\tk\n" +
            "init\tk\n" +
            "length\tk\n" +
            "mangleof\tk\n" +
            "ptr\tk\n" +
            "sizeof\tk\n" +
            "stringof\tk";

        final List<Completion> completions = dcdClient.processDcdOutput(output);
        assertEquals(9, completions.size());
    }
}
