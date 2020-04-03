package com.squidkingdom.Gavel

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class dataAccessTest {

    @Test
    fun setupDB() {
        DataAccess.setupDB()
        assertTrue(DataAccess.db.isPresent)
    }
}