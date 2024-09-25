package de.syntax.androidabschluss.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentUserSelectionBinding

class UserSelectionFragment : Fragment() {
    private lateinit var binding: FragmentUserSelectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Aufblasen des Layouts und Zuweisen an die Binding-Variablen
        binding = FragmentUserSelectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Wenn der Benutzer auf den Button für Veranstalter klickt
        binding.button2.setOnClickListener {
            // Navigiere zum Fragment für Veranstalter
            findNavController().navigate(R.id.veranstalterFragment)
        }

        // Wenn der Benutzer auf den Button für Nutzer klickt
        binding.button.setOnClickListener {
            // Navigiere zum Fragment für Nutzer
            findNavController().navigate(R.id.nutzerFragment)
        }

        //Vom User Auswahl zurück zur EmpfehlungFragment
        binding.imageButton.setOnClickListener{
            findNavController().navigate(UserSelectionFragmentDirections.actionUserSelectionFragmentToEmpfehlungFragment())
        }
    }

}
