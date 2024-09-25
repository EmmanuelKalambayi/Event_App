package de.syntax.androidabschluss.ui.authentication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import de.syntax.androidabschluss.MainViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.model.Favourite
import de.syntax.androidabschluss.databinding.FragmentEmpfehlungDetailBinding


class EmpfehlungDetailFragment : Fragment() {
    private lateinit var binding: FragmentEmpfehlungDetailBinding
    private val viewModel: MainViewModel by activityViewModels()

    private var teilnehmerCount = 0 // Zähler für die Anzahl der Teilnehmer

    private var eventId = 0
    private var name = ""
    private var start = ""
    private var ende = ""
    private var image = ""
    private var land = ""
    private var stadt = ""






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
           eventId = it.getInt("eventId")
            name = it.getString("eventName").toString()
            start = it.getString("eventStart").toString()
            ende = it.getString("eventEnde").toString()
            image = it.getString("eventImage").toString()
            land = it.getString("eventCountry").toString() //Maps
          stadt = it.getString("eventCity").toString() //Maps

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Aufrufen der Methode im ViewModel, um die Events für den bestimmten event abzurufen
        viewModel.loadPartys()
        //Aufblasen des Layouts und Zuweisen an die Binding-Variablen
        binding = FragmentEmpfehlungDetailBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Binding über Navigation Argumente wurden über geben



        //Binding über ID
// Beobachten von Änderungen an der Liste der Partys im ViewModel und Aktualisieren der Ansicht entsprechend der aktuellen Party
        viewModel.partys.observe(viewLifecycleOwner) { party ->
            // Finden der Party mit der entsprechenden eventId
            val partys = party.find { it.id == eventId }
            // Wenn die Party gefunden wurde
            partys?.let {
                // Aktualisieren der Ansicht mit den Details der Party
                binding.eventart.text = partys.nameType
                binding.status.text = partys.nameStatus
                binding.organisatoren.text = partys.nameOrganizer
                binding.land.text = partys.nameCountry
                binding.stadt.text = partys.nameTown
                binding.eventName.text = partys.nameParty
                binding.eventStart.text = partys.dateStart
                binding.eventEnd.text = partys.dateEnd
                // Setzen eines Bildes für den Standort
                binding.imageStandort.setImageResource(R.drawable.pin_drop)
                // Laden des Bildes für die Party und Anzeigen in der Ansicht
                // (Hier wird angenommen, dass 'image' eine URI des Bildes ist)
                binding.eventLayout.load(image.toUri())

                // Verlinkung zu Google Maps mit dem Ausgangspunkt als das Land und die Stadt
                binding.imageStandort.setOnClickListener {
                    // Erstelle einen Google Maps Intent mit den Koordinaten des gewünschten Ortes (hier Köln)
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("geo:50.9375,6.9603?q=${partys.nameTown}, ${partys.nameCountry}")
                    )
                    startActivity(intent)
                }
                binding.imageView74.setOnClickListener{
                    viewModel.insertFavourite(Favourite(partys.id,partys.urlImageFull,partys.nameParty,partys.dateStart,partys.dateEnd))
                    Toast.makeText(requireContext(),"Das Event wurde gespeichert!",Toast.LENGTH_LONG).show()
                }

            }

        }




        //Implementation des Favourite Buttom
        /*binding.imageView74.setOnClickListener {
            viewModel.partys.value?.let { fav ->
                val favEvent = fav.find { it.id == eventId }
                val partyF = favEvent?.let { it1 ->
                    Favourite(eventId,
                        it1.urlImageFull, favEvent.nameParty, favEvent.dateStart, favEvent.dateEnd,)
                }
                if (partyF != null) {
                    viewModel.toggleFavouriteStatus(partyF)
                }
            }
        }*/

        // Beobachten von Änderungen des Favoritenstatus im ViewModel und Reagieren auf Änderungen in der Ansicht
       /* viewModel.favouriteStatusChanges.observe(viewLifecycleOwner) { isFavourite ->
            // Bestimmen des entsprechenden Icons basierend auf dem Favoritenstatus
            val icon = if (isFavourite) R.drawable.folgenblue else R.drawable.folgen
            // Setzen des Icons in das ImageView der Ansicht
            binding.imageView74.setImageResource(icon)
        }*/




        //EmpfehlungDetailFragment zurück ins Empfehlung Fragment
        binding.backBottom.setOnClickListener{
            findNavController().navigateUp() //(EmpfehlungDetailFragmentDirections.actionEmpfehlungDetailFragmentToEmpfehlungFragment())
        }


        // ShareBottom implizierter intent im Fragment
        binding.shareBottom.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT,"Ich habe hier ein super tolles Event endeckt!!!")
            intent.type = "text/plain"
            val shareIntent = Intent.createChooser(intent,null)
            startActivity(shareIntent)
        }



        val teilnahmeButton = binding.teilnahme
        val teilnehmerCountButton = binding.teilnehmerCountButton

        // Klicken Listener für den Teilnahme-Button
        teilnahmeButton.setOnClickListener {
            // Zähler erhöhen
            teilnehmerCount++
            // Text des Buttons aktualisieren
            teilnehmerCountButton.text = "Teilnehmer: $teilnehmerCount"
            // Hintergrundfarbe des Buttons ändern
            teilnahmeButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.teilnahme_button_color))
        }








    }


}