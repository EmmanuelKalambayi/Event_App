package de.syntax.androidabschluss.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.databinding.FragmentStastistikBinding


class StatistikFragment : Fragment() {

    private lateinit var binding: FragmentStastistikBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStastistikBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBottomStatisstik.setOnClickListener {
            findNavController().navigate(StatistikFragmentDirections.actionStatistikFragmentToProfilFragment())
        }
    }

}