package uk.co.cwspencer.gdb.gdbmi;

import junit.framework.TestCase;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Samael Bate (singingbush)
 * created on 11/10/2020
 */
public class GdbMiParser2Test extends TestCase {

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
