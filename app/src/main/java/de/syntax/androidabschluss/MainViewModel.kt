package de.syntax.androidabschluss

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.syntax.androidabschluss.data.local.getDatabase
import de.syntax.androidabschluss.data.model.Favourite
import de.syntax.androidabschluss.data.remote.EventSyntaxApi
import de.syntax.androidabschluss.data.remote.Repository
import kotlinx.coroutines.launch

// Diese Klasse repräsentiert das ViewModel für die Hauptaktivität der Anwendung.

class MainViewModel(application: Application) :AndroidViewModel(application) {
    val TAG = "MainViewModel"

    // Instanziierung der Datenbank und des Repositorys
    private val database = getDatabase(application)
    private val repository = Repository(EventSyntaxApi, database)

    // LiveData für die Liste der Partys und die empfohlene Party
    val partys = repository.partyList
    val favourite = repository.empfehlungParty

    // LiveData für Statusänderungen des Favoriten
    private val _favouriteStatusChanges = MutableLiveData<Boolean>()
    val favouriteStatusChanges: LiveData<Boolean>
        get() = _favouriteStatusChanges

    // LiveData für alle Favoriten
    val allFavParty: LiveData<List<Favourite>>

    // Initialisierung des ViewModels
    init {
        loadPartys() // Partys laden
        allFavParty = repository.allFavParty // Alle Favoriten laden
    }

    // Funktion zum Laden der Partys vom Server
    fun loadPartys() {
        try {
            viewModelScope.launch {
                repository.getPartys()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Fehler beim Laden der Partys: $e")
        }
    }

    // Funktion zum Einfügen eines Favoriten
    fun insertFavourite(favourite: Favourite) {
        viewModelScope.launch {
            repository.insertFavourite(favourite)
        }
    }

    // Funktion zum Löschen eines Favoriten
    fun deletefavourite(favourite: Favourite) {
        viewModelScope.launch {
            repository.deleteFavourite(favourite)
        }
    }

    // Funktion zum Überprüfen, ob eine Party als Favorit markiert ist
    suspend fun isFav(id: Int): Boolean {
        return repository.isFav(id)
    }

    // Funktion zum Überprüfen und Aktualisieren des Favoritenstatus
    private fun checkAndUpdateFavouriteIcon(favouriteId: Int) {
        viewModelScope.launch {
            val isFav = isFav(favouriteId)
            _favouriteStatusChanges.postValue(isFav)
        }
    }

    // Funktion zum Umschalten des Favoritenstatus
   /* fun toggleFavouriteStatus(favourite: Favourite) {
        viewModelScope.launch {
            val isFav = isFav(favourite.id)
            if (isFav) {
                deletefavourite(favourite)
            } else {
                insertFavourite(favourite)
            }
        }
        checkAndUpdateFavouriteIcon(favourite.id)
    }*/

    // Funktion zum Suchen von Partys
    fun searchPartys(name: String) {
        viewModelScope.launch {
            try {
                repository.searchPartys(name) // Suchfunktion im Repository aufrufen
            } catch (e: Exception) {
                Log.e(TAG, "Fehler beim Suchen der Partys: $e")
            }
        }
    }
}
