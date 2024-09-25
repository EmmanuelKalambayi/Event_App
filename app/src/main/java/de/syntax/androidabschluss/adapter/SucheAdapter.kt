import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax.androidabschluss.data.model.PartyInfo
import de.syntax.androidabschluss.databinding.ItemEventSucheBinding
import de.syntax.androidabschluss.ui.authentication.SucheFragmentDirections

// Adapterklasse für die RecyclerView-Ansicht der Suchergebnisse
class SucheAdapter(
    private var dataset: List<PartyInfo> // Liste der Suchergebnisse
): RecyclerView.Adapter<SucheAdapter.SucheViewHolder>() {

    // View Holder für die einzelnen Elemente im RecyclerView
    inner class SucheViewHolder(val binding: ItemEventSucheBinding): RecyclerView.ViewHolder(binding.root)

    // Erzeugt und initialisiert neue View Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SucheViewHolder {
        // Layout aufblasen, um die Ansicht für ein Element im RecyclerView zu erstellen
        val binding = ItemEventSucheBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SucheViewHolder(binding)
    }

    // Gibt die Anzahl der Elemente im Datensatz zurück
    override fun getItemCount(): Int {
        return dataset.size
    }

    // Bindet Daten an die Ansichtelemente in einem bestimmten ViewHolder
    override fun onBindViewHolder(holder: SucheViewHolder, position: Int) {
        val party = dataset[position] // Aktuelle Party aus dem Dataset

        // Daten in die Ansichtelemente einfügen
        holder.binding.apply {
            eventStartSuche.text = party.dateStart // Startdatum setzen
            eventEndeSuche.text = party.dateEnd // Enddatum setzen
            eventNameSuche.text = party.nameParty // Party-Name setzen
            eventLayoutSuche.load(party.urlImageFull) // Bild laden
        }

        // Klicklistener für das Suchergebnis-Event-Layout
        holder.binding.eventLayoutSuche.setOnClickListener {
            val navController = holder.binding.eventLayoutSuche.findNavController()
            // Navigation zum Detailfragment mit den Party-Details
            navController.navigate(
                SucheFragmentDirections.actionSucheFragmentToEmpfehlungDetailFragment(
                    party.id,
                    party.nameParty,
                    party.dateStart,
                    party.dateEnd,
                    party.urlImageFull
                )
            )
        }
    }

    // Funktion zum Aktualisieren des Datensatzes, wenn neue Daten verfügbar sind
    fun updateData(newDataset: List<PartyInfo>) {
        dataset = newDataset // Neue Daten setzen
        notifyDataSetChanged() // RecyclerView aktualisieren
    }
}
