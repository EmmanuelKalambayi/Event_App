package de.syntax.androidabschluss.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.syntax.androidabschluss.data.model.Favourite

// definiert die Room-Datenbank, die die Favourite-Entitäten enthält.

@Database(entities = [Favourite::class], version = 1)
abstract class FavouriteDatabase : RoomDatabase() {
    abstract val favouriteDatabaseDao: FavouriteDatabaseDao // DAO-Schnittstelle für Datenbankoperationen
}

// Globale Datenbankinstanz
private lateinit var INSTANCE: FavouriteDatabase

// Funktion zum Abrufen der Datenbankinstanz
fun getDatabase(context: Context): FavouriteDatabase {
    synchronized(FavouriteDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext, // Anwendungs-Kontext
                FavouriteDatabase::class.java, // Referenz auf die Datenbankklasse
                "favourite_database" // Name der Datenbankdatei
            ).build() // Erzeugen der Datenbankinstanz
        }
    }
    return INSTANCE
}
