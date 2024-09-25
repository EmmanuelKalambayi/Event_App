package de.syntax.androidabschluss.data.model

// Datenklasse, die Informationen über ein Party-Event enthält
data class PartyInfo(
    val id: Int,
    val dateEnd: String,
    val dateStart: String,
    val isoCountry: String,
    val nameCountry: String,
    val nameParty: String,
    val nameTown: String,
    val nameType: String,
    val nameStatus: String,
    val urlImageFull: String,
    val urlImageMedium: String,
   val nameOrganizer: String,


)