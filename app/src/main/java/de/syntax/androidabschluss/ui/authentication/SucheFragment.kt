package de.syntax.androidabschluss.ui.authentication

import SucheAdapter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.MainViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentSucheBinding

class SucheFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SucheAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        adapter = SucheAdapter(emptyList())
        return inflater.inflate(R.layout.fragment_suche, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val binding = FragmentSucheBinding.bind(view)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_event_suche)
        recyclerView.adapter = adapter


        // Beobachtung der Partys im ViewModel
        viewModel.partys.observe(viewLifecycleOwner) { partyList ->
            // Aktualisierung des Adapters mit den gefundenen Partys
            adapter.updateData(partyList)
        }

        //Änderungen werden im Eingabefeld überwacht um dan in einer Suchfunktion im ViewModel aufzurufen
        binding.suchEingabe.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // aktuelle Text aus dem Eingabefeld extrahiert und mit `trim()` bereinigt, um Leerzeichen zu entfernen
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword = s.toString().trim()
                viewModel.searchPartys(keyword)//Nachdem der Eingabetext extrahiert wurde, wird `searchPartys(keyword)
                // ` im ViewModel aufgerufen und mit dem bereinigten Suchbegriff als Parameter übergeben.
            }

            override fun afterTextChanged(s: Editable?) {
                // wenn Suchtext gelöscht wird, alle Partys wieder anzeigen
                if (s.isNullOrEmpty()) {
                    viewModel.loadPartys()
                }
            }
        })
        // Dies ist ein test
        //Vom SuchFragment zurück zum Ausgangspunkt
        binding.backBottomSuche.setOnClickListener {
            findNavController().navigateUp()
        }




    }
    }

