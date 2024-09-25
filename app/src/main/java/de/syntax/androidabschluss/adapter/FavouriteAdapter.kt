import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax.androidabschluss.MainViewModel
import de.syntax.androidabschluss.data.model.Favourite
import de.syntax.androidabschluss.databinding.ItemFavouriteEventBinding
import de.syntax.androidabschluss.ui.authentication.FavouriteFragmentDirections

// Adapterklasse für die RecyclerView-Ansicht der Favoriten-Events
class FavouriteAdapter(
    private val dataset: List<Favourite>, // Liste der Favoriten-Events
    private val viewModel: MainViewModel,
    private val context: Context
): RecyclerView.Adapter<FavouriteAdapter.EventViewHolder>() {

    // View Holder für die einzelnen Elemente im RecyclerView
    inner class EventViewHolder(val binding: ItemFavouriteEventBinding) : RecyclerView.ViewHolder(binding.root)

    // Erzeugt und initialisiert neue View Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        // Layout aufblasen, um die Ansicht für ein Element im RecyclerView zu erstellen
        val binding = ItemFavouriteEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    // Gibt die Anzahl der Elemente im Datensatz zurück
    override fun getItemCount(): Int {
        return dataset.size
    }

    // Bindet Daten an die Ansichtelemente in einem bestimmten ViewHolder
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val favourites = dataset[position] // Aktuelles Favoriten-Event aus dem Dataset
        val imageUri = favourites.urlImageFull.toUri() // URI für das Favoriten-Bild
        holder.binding.eventLayoutFavourite.load(imageUri) // Bild laden
        holder.binding.eventNameFavourite.text = favourites.nameParty // Party-Name setzen
        holder.binding.eventStartFavourite.text = favourites.dateStart // Startdatum setzen
        holder.binding.eventEndFavourite.text = favourites.dateEnd // Enddatum setzen

        // Klicklistener für das Favoriten-Event-Layout
        holder.binding.eventLayoutFavourite.setOnClickListener {
            val navController = holder.binding.eventLayoutFavourite.findNavController()
            // Navigation zum Detailfragment mit den Favoriten-Party-Details
            navController.navigate(
                FavouriteFragmentDirections.actionFavouriteFragmentToEmpfehlungDetailFragment(
                    favourites.id,
                    favourites.nameParty,
                    favourites.dateStart,
                    favourites.dateEnd,
                    favourites.urlImageFull
                )
            )
        }

        holder.itemView.setOnLongClickListener{
            Toast.makeText(context,"Das Event wurde entfernt!", Toast.LENGTH_LONG).show()
            viewModel.deletefavourite(favourites)
            true
        }
    }
}
