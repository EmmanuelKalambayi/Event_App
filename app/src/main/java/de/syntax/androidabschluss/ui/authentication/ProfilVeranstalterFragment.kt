package de.syntax.androidabschluss.ui.authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.FirebaseViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentProfilVeranstalterBinding

class ProfilVeranstalterFragment : Fragment() {
    private val viewModel: FirebaseViewModel by activityViewModels()
    private lateinit var binding: FragmentProfilVeranstalterBinding
    private lateinit var userMail: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilVeranstalterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("Profiel erfolgreich","${viewModel.currentUser.value}")
        //Navigation zum Statistik Fragment
        binding.statistikVProfile.setOnClickListener(){
            Log.d("Navigation", "Statistik Fragment wurde angeklickt")
            findNavController().navigate(ProfilVeranstalterFragmentDirections.actionProfilFragmentToStatistikFragment())

        }


        //Bestehnder User wird über Login eingelogt
        viewModel.currentUser.observe(viewLifecycleOwner){user ->
           if (user == null){
               findNavController().navigate(R.id.loginFragment)
           }else {
              userMail = user.email.toString()
               binding.nameVeranstalter.text = "Hallo $userMail!"
           }
        }
        // Bestehnder User loggt sich aus!
        binding.profilVLogout.setOnClickListener {
            viewModel.logut()
            // Nach dem Ausloggen zur Empfehlungsfragment navigieren
            findNavController().navigate(R.id.loginFragment)
            Log.e("Auslogen erfolgreich","${viewModel.currentUser.value}")
        }

        //Vom ProfielVeranstalterFragment zu FavouriteFragment
        binding.favouritenVProfil.setOnClickListener {
            findNavController().navigate(ProfilVeranstalterFragmentDirections.actionProfilFragmentToFavouriteFragment())
        }



    }
}

