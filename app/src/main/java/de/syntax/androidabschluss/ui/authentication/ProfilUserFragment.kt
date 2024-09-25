package de.syntax.androidabschluss.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.FirebaseViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentProfilUserBinding


class ProfilUserFragment : Fragment() {
    private val viewModel: FirebaseViewModel by activityViewModels()
    private lateinit var binding: FragmentProfilUserBinding
    private lateinit var userMail: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentUser.observe(viewLifecycleOwner){user ->
            if (user == null){
                findNavController().navigate(R.id.loginFragment)
            }else {
                userMail = user.email.toString()
                binding.nameUser.text = "Hallo $userMail!"
            }
        }

        binding.profilULogout.setOnClickListener {
            viewModel.logut()
            // Nach dem Ausloggen zur Empfehlungsfragment navigieren
            findNavController().navigate(R.id.loginFragment)
        }

        //Vom ProfielUserFragment zu FavouriteFragment
        binding.favouritenUProfil.setOnClickListener {
            findNavController().navigate(ProfilUserFragmentDirections.actionProfilUserFragmentToFavouriteFragment())
        }
    }
}