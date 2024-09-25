package de.syntax.androidabschluss.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "party_fav")
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val urlImageFull: String,
    var nameParty: String,
    val dateStart: String,
    val dateEnd: String,



    )
