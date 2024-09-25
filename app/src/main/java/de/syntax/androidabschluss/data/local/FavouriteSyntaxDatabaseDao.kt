package de.syntax.androidabschluss.data.local


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.syntax.androidabschluss.data.model.Favourite

// DAO Schnittstelle für die Interaktion mit der Datenbanktabelle "party_fav"

@Dao
interface FavouriteDatabaseDao {
    // Methode zum Einfügen eines Favoriten in die Datenbank
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: Favourite)

    // Methode zum Löschen eines Favoriten aus der Datenbank
    @Delete
    suspend fun deleteFavourite(favourite: Favourite)

    // Abfrage, um alle Favoriten aus der Datenbank abzurufen
    @Query("SELECT * FROM party_fav")
    fun getAll(): LiveData<List<Favourite>> // Verwendung von LiveData für eine automatische Aktualisierung der UI bei Änderungen in der Datenbank

    // Abfrage, um einen Favoriten anhand seiner ID abzurufen
    @Query("SELECT * FROM party_fav Where id = :id")
    suspend fun getFavouriteByeId(id: Int): Favourite?
}
