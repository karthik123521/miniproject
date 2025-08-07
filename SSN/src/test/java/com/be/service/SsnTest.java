package com.be.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssn.service.SsnServiceImpl;


class SsnTest {

	//@Autowired
    private SsnServiceImpl beService;

    @BeforeEach
    void setUp() {
        beService = new SsnServiceImpl();
    }

    @ParameterizedTest
   // @ValueSource(longs = {412345678L, 112345678L})
    @CsvFileSource(resources = "/states_phone.csv" , numLinesToSkip = 1) // commas separated value
    void testNewJersey(String s,Long ssn) {
    	String giveState = beService.giveState(ssn);
        assertEquals(s, giveState);
    }

    @ParameterizedTest
    @ValueSource(longs = {512345678L, 112345678L})
    void testOhio(Long s) {
    	String giveState = beService.giveState(s);
        assertNotEquals("California", giveState);
        assertNotEquals("New Jersey", giveState);
    }

    @Test
    void testCalifornia() {
        assertNotEquals("California", beService.giveState(812345678L));
        assertEquals("California", beService.giveState(612345678L));
    }

    @Test
    void testTexas() {
        assertEquals("Texas", beService.giveState(712345678L));
        assertEquals("Texas", beService.giveState(812345678L));
    }

    @Test
    void testBoston() {
        assertEquals("Boston", beService.giveState(912345678L));
    }
}

