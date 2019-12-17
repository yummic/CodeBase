package fun.clclcl.yummic.codebase.util.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class IOUnitTest {

    Random random = null;

    @Before
    public void setUp() throws Exception {
        random = new Random();
    }

    @After
    public void tearDown() throws Exception {
        random = null;
    }

    @Test
    public void testByte() {
        long size = Long.MAX_VALUE - random.nextInt(Integer.MAX_VALUE);
        assertEquals(IOUnit.BYTE.toByte(size), size);
        assertEquals(IOUnit.BYTE.toKB(size), size/1024);
        assertEquals(IOUnit.BYTE.toMB(size), size/1024/1024);
        assertEquals(IOUnit.BYTE.toGB(size), size/1024/1024/1024);
        assertEquals(IOUnit.BYTE.toTB(size), size/1024/1024/1024/1024);
        assertEquals(IOUnit.BYTE.toPB(size), size/1024/1024/1024/1024/1024);
    }

    @Test
    public void testKB() {
        long size = Long.MAX_VALUE / 1024 - random.nextInt(Integer.MAX_VALUE);
        assertEquals(IOUnit.KB.toByte(size), size * 1024);
        assertEquals(IOUnit.KB.toKB(size), size);
        assertEquals(IOUnit.KB.toMB(size), size/1024);
        assertEquals(IOUnit.KB.toGB(size), size/1024/1024);
        assertEquals(IOUnit.KB.toTB(size), size/1024/1024/1024);
        assertEquals(IOUnit.KB.toPB(size), size/1024/1024/1024/1024);
    }

    @Test
    public void testMB() {
        long size = (Long.MAX_VALUE - random.nextInt(Integer.MAX_VALUE)) / 1024 / 1024;
        assertEquals(IOUnit.MB.toByte(size), size * 1024 * 1024);
        assertEquals(IOUnit.MB.toKB(size), size * 1024);
        assertEquals(IOUnit.MB.toMB(size), size);
        assertEquals(IOUnit.MB.toGB(size), size/1024);
        assertEquals(IOUnit.MB.toTB(size), size/1024/1024);
        assertEquals(IOUnit.MB.toPB(size), size/1024/1024/1024);
    }

    @Test
    public void testGB() {
        long size = (Long.MAX_VALUE - random.nextInt(Integer.MAX_VALUE)) / 1024 / 1024 / 1024;
        assertEquals(IOUnit.GB.toByte(size), size * 1024 * 1024 * 1024);
        assertEquals(IOUnit.GB.toKB(size), size * 1024 * 1024);
        assertEquals(IOUnit.GB.toMB(size), size * 1024);
        assertEquals(IOUnit.GB.toGB(size), size);
        assertEquals(IOUnit.GB.toTB(size), size/1024);
        assertEquals(IOUnit.GB.toPB(size), size/1024/1024);
    }

    @Test
    public void testTB() {
        long size = (Long.MAX_VALUE - random.nextInt(Integer.MAX_VALUE)) / 1024 / 1024 / 1024 / 1024;
        assertEquals(IOUnit.TB.toByte(size), size * 1024 * 1024 * 1024 * 1024);
        assertEquals(IOUnit.TB.toKB(size), size * 1024 * 1024* 1024);
        assertEquals(IOUnit.TB.toMB(size), size * 1024* 1024);
        assertEquals(IOUnit.TB.toGB(size), size * 1024);
        assertEquals(IOUnit.TB.toTB(size), size);
        assertEquals(IOUnit.TB.toPB(size), size/1024);
    }

    @Test
    public void testPB() {
        long size = (Long.MAX_VALUE - random.nextInt(Integer.MAX_VALUE)) / 1024 / 1024 / 1024 / 1024 / 1024;
        assertEquals(IOUnit.PB.toByte(size), size * 1024 * 1024 * 1024 * 1024* 1024);
        assertEquals(IOUnit.PB.toKB(size), size * 1024 * 1024 * 1024* 1024);
        assertEquals(IOUnit.PB.toMB(size), size * 1024 * 1024* 1024);
        assertEquals(IOUnit.PB.toGB(size), size * 1024* 1024);
        assertEquals(IOUnit.PB.toTB(size), size * 1024);
        assertEquals(IOUnit.PB.toPB(size), size);
    }
}