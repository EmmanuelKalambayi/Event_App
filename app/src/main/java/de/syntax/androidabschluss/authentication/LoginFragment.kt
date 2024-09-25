package de.syntax.androidabschluss.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentLoginBinding
import de.syntax.androidabschluss.FirebaseViewModel


class LoginFragment : Fragment() {

    private val viewModel: FirebaseViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        /*binding.loginSignupButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProfilUserFragment())
        }*/

        /*binding.loginSignupButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProfilFragment())
        }*/


        //Emmail und Passwort abgeglichen
        binding.loginButton.setOnClickListener {
            val email = binding.loginEmailEdit.text.toString()
            val password = binding.loginPasswordEdit.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)


            }
        }


        //Vom Login zu Sign up Button
        binding.loginSignupButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }
        //Navigation zum profilUserFragment wenn user nicht null ist
        viewModel.currentUser.observe(viewLifecycleOwner){user ->
            if (user != null){
                findNavController().navigate(R.id.profilUserFragment)
            }
        }
    }
}