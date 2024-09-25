package de.syntax.androidabschluss.ui.authentication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.databinding.FragmentVeranstalterBinding

// Fragment für den Veranstalter
class VeranstalterFragment : Fragment() {

    private lateinit var binding: FragmentVeranstalterBinding

    // Erstellen der Ansicht des Fragments
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Aufblasen des Layouts für das Fragment mit Data Binding
        binding = FragmentVeranstalterBinding.inflate(inflater, container, false)

        // Verzögertes Navigieren zu einem anderen Fragment nach einer Wartezeit von 3 Sekunden
        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(VeranstalterFragmentDirections.actionVeranstalterFragmentToProfilFragment())
        }, 3000)

        // Rückgabe der Wurzelansicht des Fragments
        return  binding.root
    }
}
