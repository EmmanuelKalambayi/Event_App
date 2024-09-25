package de.syntax.androidabschluss.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax.androidabschluss.MainViewModel
import de.syntax.androidabschluss.data.model.PartyInfo
import de.syntax.androidabschluss.databinding.ItemEventBinding
import de.syntax.androidabschluss.ui.authentication.EmpfehlungFragmentDirections

// Adapterklasse für die RecyclerView-Ansicht der Party-Events

class EventAdapter(
    private val dataset: List<PartyInfo>, // Liste der Party-Events
    private val viewModel: MainViewModel // ViewModel, das die Daten bereitstellt
): RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    // View Holder für die einzelnen Elemente im RecyclerView
    class EventViewHolder(val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    // Erzeugt und initialisiert neue View Holder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventViewHolder {
        // Layout aufblasen, um die Ansicht für ein Element im RecyclerView zu erstellen
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    // Gibt die Anzahl der Elemente im Datensatz zurück
    override fun getItemCount(): Int {
        return dataset.size
    }

    // Bindet Daten an die Ansichtelemente in einem bestimmten ViewHolder
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val party = dataset[position] // Aktuelles Party-Event aus dem Dataset
        val imageUri = party.urlImageFull.toUri() // URI für das Party-Bild
        holder.binding.eventLayout.load(imageUri) // Bild laden
        holder.binding.eventStart.text = party.dateStart // Startdatum setzen
        holder.binding.eventEnd.text = party.dateEnd // Enddatum setzen
        holder.binding.eventName.text = party.nameParty // Party-Name setzen

        // Klicklistener für das Event-Layout
        holder.binding.eventLayout.setOnClickListener {
            val navController = holder.binding.eventLayout.findNavController()
            // Navigation zum Detailfragment mit den Party-Details
            navController.navigate(
                EmpfehlungFragmentDirections.actionEmpfehlungFragmentToEmpfehlungDetailFragment(
                    party.id,
                    party.nameParty,
                    party.dateStart,
                    party.dateEnd,
                    party.urlImageFull
                )
            )
        }
    }
}
