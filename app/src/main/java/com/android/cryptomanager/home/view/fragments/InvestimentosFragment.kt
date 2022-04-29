package com.android.cryptomanager.home.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.cryptomanager.R
import com.android.cryptomanager.databinding.InvestimentosFragmentBinding
import com.android.cryptomanager.home.data.models.CryptoCard
import com.android.cryptomanager.home.presentation.InvestimentosViewModel
import com.android.cryptomanager.home.presentation.auxiliar.Screenshot
import com.android.cryptomanager.home.view.adapters.CryptoCardListAdapter
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class InvestimentosFragment : Fragment() {

    private var _binding: InvestimentosFragmentBinding? = null
    private val binding get() = _binding!!
    private val investimentosViewModel by viewModel<InvestimentosViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = InvestimentosFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.screenshot.setOnClickListener {
            Screenshot.share(requireContext(), binding.root)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
