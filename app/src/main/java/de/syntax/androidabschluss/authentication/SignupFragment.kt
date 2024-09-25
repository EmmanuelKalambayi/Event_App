package de.syntax.androidabschluss.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentSignupBinding
import de.syntax.androidabschluss.FirebaseViewModel

// Diese Klasse repräsentiert das Fragment für die Benutzerregistrierung.
class SignupFragment : Fragment() {
    // ViewModel für Firebase-Operationen
    private val viewModel: FirebaseViewModel by activityViewModels()
    // Binding-Objekt für die Layout-Ressource des Fragments
    private lateinit var binding: FragmentSignupBinding

    // Erstellen der Ansicht des Fragments
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Aufblasen des Layouts für das Fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Konfiguration der Ansicht des Fragments nach deren Erstellung
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Abbrechen der Registrierung und Zurücknavigieren, wenn Abbruch-Button geklickt wird
        binding.signupCancelButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // Ausführen der Registrierung, wenn Registrierungs-Button geklickt wird
        binding.signupSignupButton.setOnClickListener {
            signup()
        }

        // Beobachten des aktuellen Benutzers; Bei vorhandenem Benutzer zur Benutzerselektionsansicht navigieren
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                findNavController().navigate(R.id.userSelectionFragment)
            }
        }
    }

    // Funktion zur Ausführung der Benutzerregistrierung
    private fun signup() {
        // Abrufen der eingegebenen E-Mail-Adresse und des Passworts aus den Eingabefeldern
        val email = binding.signupMail.text.toString()
        val password = binding.signupPassword.text.toString()

        // Überprüfen, ob E-Mail und Passwort nicht leer oder null sind, bevor die Registrierung ausgeführt wird
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            viewModel.signup(email, password)
        }
    }
}
