package de.syntax.androidabschluss.ui.authentication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import de.syntax.androidabschluss.MainViewModel
import de.syntax.androidabschluss.adapter.EventAdapter
import de.syntax.androidabschluss.databinding.FragmentEmpfehlungBinding


class EmpfehlungFragment : Fragment() {
    private lateinit var binding: FragmentEmpfehlungBinding
    private val viewModel: MainViewModel by activityViewModels()  // Wird aufgerufen, um die Layout-Datei für das Fragment aufzublasen
    private lateinit var userMail: String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Aufblasen des Layouts und Zuweisen an die Binding-Variablen
        binding = FragmentEmpfehlungBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Verlinkung zu google Maps
        binding.imgHeaderMap.setOnClickListener {
            // Erstelle einen Google Maps Intent mit den Koordinaten deines gewünschten Ziels
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:50.9375,6.9603?q=Köln")
            )
            // Starte die Google Maps App
            startActivity(intent)
        }

        // Beobachten der Liste von Partys im ViewModel und Aktualisieren der RecyclerView
        viewModel.partys.observe(viewLifecycleOwner){partyList ->
            binding.rvEvent.adapter = EventAdapter(partyList, viewModel)
            //Toast.makeText(requireContext(),"Event gespeichert: ${favourite.nameF}", Toast.LENGTH_SHORT).show()

            }

        //Festlegen, dass die Größe der RecylerView konstant bleibt(verbessert die Leistung)
        binding.rvEvent.setHasFixedSize(true)

            //
            /*viewModel.favouriteStatusChanges.observe(viewLifecycleOwner){isFavourite ->
                val icon = if (isFavourite) R.drawable.folgenblue else R.drawable.folgen
                binding.imageView
            }*/





        }



    }










