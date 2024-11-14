package com.woodev.noticerti.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class X500ParserTest {

    @Test
    public void testGetValue() {
        // given
        String dn = "CN=Test,OU=Test,O=Test,L=Test,ST=Test,C=Test";
        String key = "CN";

        // when
        String value = X500Parser.getValue(dn, key);

        // then
        assertEquals("Test", value);
    }

    @Test
    public void testParse() {
        // given
        String dn = "CN=Test,OU=Test,O=Test,L=Test,ST=Test,C=Test";

        // when
        Map<String, Object> map = X500Parser.parse(dn);

        // then
        assertEquals("Test", map.get("CN"));
        assertEquals("Test", map.get("OU"));
        assertEquals("Test", map.get("O"));
        assertEquals("Test", map.get("L"));
        assertEquals("Test", map.get("ST"));
        assertEquals("Test", map.get("C"));
    }

}