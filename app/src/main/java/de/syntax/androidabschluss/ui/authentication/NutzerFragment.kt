package de.syntax.androidabschluss.ui.authentication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.databinding.FragmentNutzerBinding

// Dieses Fragment ist der erste Bildschirm, den der Benutzer nach der Anmeldung sieht.
class NutzerFragment : Fragment() {
    private lateinit var binding: FragmentNutzerBinding

    // Erstellen der Ansicht des Fragments
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Aufblasen des Layouts für das Fragment mit Data Binding
        binding = FragmentNutzerBinding.inflate(inflater, container, false)

        // Navigieren zu einem anderen Fragment nach einer Verzögerung von 3 Sekunden
        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(NutzerFragmentDirections.actionNutzerFragmentToProfilUserFragment())
        }, 3000)

        return binding.root
    }
}
