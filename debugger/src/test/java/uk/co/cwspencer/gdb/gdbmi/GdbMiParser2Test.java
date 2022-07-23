package uk.co.cwspencer.gdb.gdbmi;

import com.intellij.testFramework.UsefulTestCase;
import uk.co.cwspencer.gdb.messages.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Samael Bate (singingbush)
 * created on 11/10/2020
 */
public class GdbMiParser2Test extends UsefulTestCase {

    private GdbMiParser2 parser;
    private static final Charset ASCII = StandardCharsets.US_ASCII;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        parser = new GdbMiParser2(null);
    }

    public void testProcessLineRecords_EmptyString() {
        parser.process("".getBytes());

        final List<GdbMiRecord> records = parser.getRecords();

        assertNotNull(records);
        assertTrue(records.isEmpty());
    }

    public void testProcessLineRecords_BlankString() {
        parser.process("   ".getBytes());

        final List<GdbMiRecord> records = parser.getRecords();

        assertNotNull(records);
        assertTrue(records.isEmpty());
    }

    public void testProcessLineRecords_CarriageReturn() {
        parser.process(ASCII.encode("\n").array());

        final List<GdbMiRecord> records = parser.getRecords();

        assertNotNull(records);
        assertTrue(records.isEmpty());
    }

    public void testProcessConnectedEvent() {
        final String messageStr =
            "^connected,addr=\"0xfe00a300\",func=\"??\",args=[]\r\n" +
                "(gdb)\r\n";

        parser.process(messageStr.getBytes(ASCII));

        final List<GdbMiRecord> records = parser.getRecords();

        assertEquals(1, records.size());

        // Convert the message
        final GdbMiStreamRecord record = (GdbMiStreamRecord) records.get(0);

        assertEquals("^connected,addr=\"0xfe00a300\",func=\"??\",args=[]\n", record.message);
        assertEquals(GdbMiRecord.Type.Target, record.type);
        assertNull(record.userToken);
    }

    public void testProcessErrorEvent() {
        final String messageStr =
            "^error,msg=\"mi_cmd_exec_interrupt: Inferior not executing.\"\r\n" +
                "(gdb)\r\n";

        parser.process(messageStr.getBytes(ASCII));
        final List<GdbMiRecord> records = parser.getRecords();

        // Convert the message
        final GdbMiStreamRecord record = (GdbMiStreamRecord) records.get(0);

        assertEquals("^error,msg=\"mi_cmd_exec_interrupt: Inferior not executing.\"\n", record.message);
        assertEquals(GdbMiRecord.Type.Target, record.type);
        assertNull(record.userToken);
    }

    public void testProcessExitEvent() {
        final String messageStr = "^exit\r\n";

        parser.process(messageStr.getBytes(ASCII));
        final List<GdbMiRecord> records = parser.getRecords();

        // Convert the message
        final GdbMiStreamRecord record = (GdbMiStreamRecord) records.get(0);

        assertEquals("^exit\n", record.message);
        assertEquals(GdbMiRecord.Type.Target, record.type);
        assertNull(record.userToken);

    }

    public void testProcessStoppedEvent() {
        final String messageStr =
            "*stopped," +
                "reason=\"breakpoint-hit\"," +
                "disp=\"keep\"," +
                "bkptno=\"1\"," +
                "thread-id=\"0\"," +
                "stopped-threads=\"all\"," +
                "frame={" +
                "addr=\"0x08048564\"," +
                "func=\"main\"," +
                "args=[{" +
                "name=\"argc\"," +
                "value=\"1\"}," +
                "{name=\"argv\"," +
                "value=\"0xbfc4d4d4\"}]," +
                "file=\"myprog.c\"," +
                "fullname=\"/home/username/myprog.c\"," +
                "line=\"68\"}\r\n" +
                "*stopped,stopped-threads=[\"1\",\"2\"]\r\n" +
                "(gdb)\r\n";

        parser.process(messageStr.getBytes(ASCII));
        final List<GdbMiRecord> records = parser.getRecords();

        // Convert the message
        {
            final GdbMiResultRecord record = (GdbMiResultRecord) records.get(0);
            final Object object = GdbMiMessageConverter.processRecord(record);
            assertNotNull(object);
            assertTrue(object instanceof GdbStoppedEvent);

            final GdbStoppedEvent stoppedEvent = (GdbStoppedEvent) object;

            // todo: finish writing this test
        }

    }

    public void testProcessRunningEvent() {
        final String messageStr =
            "*running,thread-id=\"2\"\r\n" +
                "*running,thread-id=\"all\"\r\n" +
                "(gdb)\r\n";
        parser.process(messageStr.getBytes(ASCII));

        final List<GdbMiRecord> records = parser.getRecords();

        // Convert the message
        {
            final GdbMiResultRecord record = (GdbMiResultRecord) records.get(0);
            final Object object = GdbMiMessageConverter.processRecord(record);
            assertNotNull(object);
            assertTrue(object instanceof GdbRunningEvent);

            final GdbRunningEvent runningEvent = (GdbRunningEvent) object;
            assertFalse(runningEvent.allThreads);
            assertEquals(Integer.valueOf(2), runningEvent.threadId);
        }

        {
            final GdbMiResultRecord record = (GdbMiResultRecord) records.get(1);
            final Object object = GdbMiMessageConverter.processRecord(record);
            assertNotNull(object);
            assertTrue(object instanceof GdbRunningEvent);

            final GdbRunningEvent runningEvent = (GdbRunningEvent) object;
            assertTrue(runningEvent.allThreads);
            assertNull(runningEvent.threadId);
        }
    }

    public void testProcessStackTrace() {
        final String messageStr =
            "^done," +
                "stack=[" +
                "frame={" +
                "level=\"0\"," +
                "addr=\"0x00010734\"," +
                "func=\"callee4\"," +
                "file=\"../../../devo/gdb/testsuite/gdb.mi/basics.c\"," +
                "fullname=\"/home/foo/bar/devo/gdb/testsuite/gdb.mi/basics.c\"," +
                "line=\"8\"}," +
                "frame={" +
                "level=\"1\"," +
                "addr=\"0x0001076c\"," +
                "func=\"callee3\"," +
                "file=\"../../../devo/gdb/testsuite/gdb.mi/basics.c\"," +
                "fullname=\"/home/foo/bar/devo/gdb/testsuite/gdb.mi/basics.c\"," +
                "line=\"17\"}]\r\n" +
                "(gdb)\r\n";

        parser.process(messageStr.getBytes(ASCII));
        final List<GdbMiRecord> records = parser.getRecords();

        // Convert the message
        final GdbMiStreamRecord record = (GdbMiStreamRecord) records.get(0);


        assertEquals(GdbMiRecord.Type.Target, record.type);
        assertNull(record.userToken);
    }

    public void testProcessBreakpoint() {
        final String messageStr =
            "^done," +
                "bkpt={" +
                "number=\"1\"," +
                "type=\"breakpoint\"," +
                "disp=\"keep\"," +
                "enabled=\"y\"," +
                "addr=\"0x000100d0\"," +
                "func=\"main\"," +
                "file=\"hello.c\"," +
                "fullname=\"/home/foo/hello.c\"," +
                "line=\"5\"," +
                "thread-groups=[\"i1\"]," +
                "times=\"0\"}\r\n" +
                "(gdb)\r\n";

        parser.process(messageStr.getBytes(ASCII));
        final List<GdbMiRecord> records = parser.getRecords();

        // Convert the message
        final GdbMiStreamRecord record = (GdbMiStreamRecord) records.get(0);

        assertEquals(GdbMiRecord.Type.Target, record.type);
        assertNull(record.userToken);
    }

    public void testProcessLineRecords_ExampleOutput() {
        parser.process("#0  0x080a72aa in _aaIn (aa={a = 0xf7461410}, keyti=0x8136969) at internal/aaA.d:253".getBytes(ASCII));

        final List<GdbMiRecord> records = parser.getRecords();

        assertNotNull(records);
        assertFalse(records.isEmpty());
        assertEquals(1, records.size());

        final GdbMiStreamRecord gdbMiRecord = (GdbMiStreamRecord)records.get(0);
        assertTrue("Message should have line break appended", gdbMiRecord.message.endsWith("\n"));
        assertEquals(GdbMiRecord.Type.Target, gdbMiRecord.type);
        assertNull(gdbMiRecord.userToken); // todo: should this be the case?
    }

    public void testProcessLineRecords_Tags() {
        final String [] tags = new String [] {
            "        DW_TAG_darray_type              = 0x41,",
            "        DW_TAG_aarray_type              = 0x42,",
            "        DW_TAG_delegate_type            = 0x43,",
            "",
            "        DW_TAG_lo_user                  = 0x4080,",
            "        DW_TAG_hi_user                  = 0xFFFF,"
        };

        for (final String tag : tags) {
            parser.process(tag.getBytes(ASCII));
        }

        final List<GdbMiRecord> records = parser.getRecords();

        assertNotNull(records);
        assertFalse(records.isEmpty());
        assertEquals(5, records.size());

        records.stream()
            .map(r -> (GdbMiStreamRecord)r)
            .forEach(r -> {
                assertTrue("Message should have line break appended", r.message.endsWith("\n"));
                assertEquals(GdbMiRecord.Type.Target, r.type);
                assertNull(r.userToken); // todo: should this be the case?
            });
    }
}
