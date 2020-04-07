package com.squidkingdom.Gavel

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class dataAccessTest {

    @Test
    fun setupDB() {
        DataAccess.setupDB()
        assertTrue(DataAccess.db.isPresent)

        DataAccess.createTeam("t1", "s1", "s2")
        val dbRes = DataAccess.getTeam("t1")
        assertTrue(dbRes is DbMutationResult.Success)

        assertTrue(when (dbRes) {
            is DbMutationResult.Success -> true
            is DbMutationResult.Failed -> false
            else -> throw GavelExeception("There's an issue here")
        })

        assertTrue(when (dbRes) {
            is DbMutationResult.Success -> when (dbRes.ret) {
                is Team -> {
                    assertEquals(dbRes.ret.code, "t1")
                    assertEquals(dbRes.ret.person1, "s1")
                    assertEquals(dbRes.ret.person2, "s2")
                    assertEquals(dbRes.ret.totalWins, 0)
                    assertEquals(dbRes.ret.totalSpeaks, 0)
                    assertEquals(dbRes.ret.totalWins, 0)
                    assertTrue(dbRes.ret.isAffLead)
                    true
                }
                else -> throw GavelExeception("There's an issue here")
            }
            else -> throw GavelExeception("There's an issue here")
        })
    }
}