package com.squidkingdom.Gavel

import org.jetbrains.exposed.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import java.util.*

val Boolean.int get() = if (this) 1 else 0
val Int.boolean get() = if (this == 1) true else false

sealed class DbMutationResult {
    data class Success(val ret: Any) : DbMutationResult()
    data class Failed(val reason: Throwable) : DbMutationResult()
}

object Teams : Table() {
    val code = text("code").primaryKey()
    val speaker1 = text("speaker1")
    val speaker2 = text("speaker2").nullable()
    val totalWins = integer("total_wins")
    val totalSpeaks = float("total_speaks")
    val isAffLead = integer("is_aff_lead") // This is a boolean, 0 is false and 1 is true
}

fun ResultRow.toTeam() = Team(this[Teams.code], this[Teams.speaker1], this[Teams.speaker2], this[Teams.totalWins], this[Teams.totalSpeaks], this[Teams.isAffLead].boolean)

object DataAccess {
    var db: Optional<Database> = Optional.empty()
    var affNegFlip = true

    fun setupDB() {
        db = Optional.of(Database.connect("jdbc:sqlite:/data/data.db", "org.sqlite.JDBC"))
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction(db.get()) {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(Teams)
        }
    }

    fun createTeam(teamCode: String, speakerOne: String, speakerTwo: String? = null): DbMutationResult = if (!db.isPresent) DbMutationResult.Failed(GavelExeception("DBNotPresent")) else {
        transaction(db.get()) {
            Teams.insert {
                it[code] = teamCode
                it[speaker1] = speakerOne
                it[speaker2] = speakerTwo
                it[totalWins] = 0
                it[totalSpeaks] = 0f
                it[isAffLead] = affNegFlip.int
            }

            affNegFlip = !affNegFlip
        }
        DbMutationResult.Success(2)
    }

    fun getTeam(teamCode: String): DbMutationResult = if (!db.isPresent) DbMutationResult.Failed(GavelExeception("DBNotPresent")) else {
        transaction(db.get()) {
            val teamQuery = Teams.select { Teams.code eq teamCode }.map { it.toTeam() }.toList()

            if (teamQuery.count() != 1) DbMutationResult.Failed(GavelExeception("NoTeamWithThatCode")) else {
                val team = teamQuery.elementAt(1)
                DbMutationResult.Success(team)
            }
        }
    }
}
