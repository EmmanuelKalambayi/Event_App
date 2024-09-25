package de.syntax.androidabschluss.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.syntax.androidabschluss.data.local.FavouriteDatabase
import de.syntax.androidabschluss.data.model.Favourite
import de.syntax.androidabschluss.data.model.PartyInfo

//  Repository-Klasse, die als Zwischenschicht zwischen Datenquellen und ViewModel dient.

class Repository(private val api: EventSyntaxApi, private val database: FavouriteDatabase) {

    private val TAG = "Repository"

    // LiveData, die eine Liste aller Favoriten aus der lokalen Datenbank enthält
    val allFavParty: LiveData<List<Favourite>> = database.favouriteDatabaseDao.getAll()

    // MutableLiveData für die Liste der Party-Informationen, die vom API abgerufen wird
    private val _partyList = MutableLiveData<List<PartyInfo>>()
    val partyList: LiveData<List<PartyInfo>>
        get() = _partyList

    // MutableLiveData für die empfohlene Party
    private val _empfehlungParty = MutableLiveData<Favourite>()
    val empfehlungParty: LiveData<Favourite>
        get() = _empfehlungParty

    // MutableLiveData für die Suchergebnisse nach Partys
    private val _searchPartys = MutableLiveData<List<PartyInfo>>()
    val searchPartys: LiveData<List<PartyInfo>>
        get() = _searchPartys

    // Funktion zum Abrufen der Party-Informationen vom API
    suspend fun getPartys() {
        try {
            val results = api.retrofitService.getPartylist().partylist
            _partyList.postValue(results)
            Log.i(TAG, "apiTest ${results}")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Data from Repository $e")
        }
    }

    // Funktion zum Einfügen eines Favoriten in die lokale Datenbank
    suspend fun insertFavourite(favourite: Favourite) {
        try {
            database.favouriteDatabaseDao.insertFavourite(favourite)
        } catch (e: Exception) {
            Log.e(TAG, "Error inserting Favourite from Database e: ¢e")
        }
    }

    // Funktion zum Löschen eines Favoriten aus der lokalen Datenbank
    suspend fun deleteFavourite(favourite: Favourite) {
        try {
            database.favouriteDatabaseDao.deleteFavourite(favourite)
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting Favourite from Database e: $e")
        }
    }

    // Funktion zur Überprüfung, ob eine Party als Favorit markiert ist
    suspend fun isFav(id: Int): Boolean = database.favouriteDatabaseDao.getFavouriteByeId(id) != null

    // Funktion zur Suche nach Partys anhand des Namens
    fun searchPartys(name: String) {
        try {
            val allParties = _partyList.value ?: return
            val results = mutableListOf<PartyInfo>()

            for (party in allParties) {
                if (party.nameParty.contains(name, ignoreCase = true)) {
                    results.add(party)
                }
            }

            _partyList.postValue(results)
        } catch (e: Exception) {
            Log.e(TAG, "Error searching Parties from Repository: $e")
        }
    }
}
