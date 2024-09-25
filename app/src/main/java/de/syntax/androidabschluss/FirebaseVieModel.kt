package de.syntax.androidabschluss

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseViewModel(application: Application) : AndroidViewModel(application) {

    // Kommunikationspunkt mit der FirebaseAuth
    private val firebaseAuth = FirebaseAuth.getInstance()

    //currentUser ist null wenn niemand eingeloggt ist
    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser


    // hier wird versucht einen User zu erstellen um diesen anschließend auch gleich einzuloggen
    fun signup(email: String, password: String){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { user ->
            if(user.isSuccessful){
                login(email, password)
            }else {
                Log.e(TAG, "Signup failed: ${user.exception}")
            }

        }
    }

    //hier wird versucht sich mit bestehenden Daten einzuLoggen
    fun login(email: String,password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                _currentUser.value = firebaseAuth.currentUser
            }else {
                Log.e(TAG, "Login failed: ${it.exception}")
            }

        }

    }

    //Hier wird sich ausgeloggt
    fun logut(){
        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser
    }
}