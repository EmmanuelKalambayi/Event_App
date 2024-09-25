package de.syntax.androidabschluss.ui.authentication

// Import der Adapter-Klasse für die Favoritenliste
import FavouriteAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.MainViewModel
import de.syntax.androidabschluss.databinding.FragmentFavouriteBinding

// Fragment zur Anzeige der favorisierten Events
class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    // ViewModel für die Datenbindung und -verarbeitung
    private val viewModel: MainViewModel by activityViewModels()

    // Erstellen der Ansicht des Fragments
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Aufblasen des Layouts für das Fragment mit Data Binding
        binding = FragmentFavouriteBinding.inflate(layoutInflater)
        return binding.root
    }

    // Konfigurieren der Ansicht des Fragments nach deren Erstellung
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigieren zurück zum vorherigen Fragment beim Klicken auf den Zurück-Button
        binding.backBottomFavourite.setOnClickListener {
            findNavController().navigateUp()
        }

        // Beobachten der Liste aller favorisierten Events im ViewModel und Aktualisieren der Ansicht
        viewModel.allFavParty.observe(viewLifecycleOwner) {
            // Setzen des Adapters für die RecyclerView, um die Liste der favorisierten Events anzuzeigen
            binding.rvFavouriteEvent.adapter = FavouriteAdapter(it,viewModel,requireContext())
        }
    }
}
