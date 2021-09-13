package com.android.cryptomanager.home.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.cryptomanager.R
import com.android.cryptomanager.databinding.InvestimentosFragmentBinding
import com.android.cryptomanager.home.data.models.CryptoCard
import com.android.cryptomanager.home.presentation.InvestimentosViewModel
import com.android.cryptomanager.home.view.adapters.CryptoCardListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class InvestimentosFragment : Fragment() {

    private var _binding: InvestimentosFragmentBinding? = null
    private val binding get() = _binding!!
    private val investimentosViewModel by viewModel<InvestimentosViewModel>()

    var bitcoinPrice: Double = 0.0
    var ethereumPrice: Double = 0.0
    var chilizPrice: Double = 0.0

    var bitcoinInvested: Double = 0.0
    var ethereumInvested: Double = 0.0
    var chilizInvested: Double = 0.0

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

        val decimalFormat = DecimalFormat("#,###.###")
        decimalFormat.roundingMode = RoundingMode.CEILING

        investimentosViewModel.bitcoinPrice.observe(viewLifecycleOwner) {
            bitcoinPrice = it
        }

        investimentosViewModel.ethereumPrice.observe(viewLifecycleOwner) {
            ethereumPrice = it
        }

        investimentosViewModel.chilizPrice.observe(viewLifecycleOwner) {
            chilizPrice = it
        }

        investimentosViewModel.bitcoinInvested.observe(viewLifecycleOwner) {
            bitcoinInvested = it
        }

        investimentosViewModel.ethereumInvested.observe(viewLifecycleOwner) {
            ethereumInvested = it
        }

        investimentosViewModel.chilizInvested.observe(viewLifecycleOwner) {
            chilizInvested = it
        }

        investimentosViewModel.totalInvested.observe(viewLifecycleOwner) {
            binding.capitalValue.text = decimalFormat.format(it)
        }

        investimentosViewModel.loading.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.recyclerviewCriptomoedasMenu.adapter = CryptoCardListAdapter(
                    cryptoCards(),
                    object : CryptoCardListAdapter.OnSelectOnClickListener {
                        override fun onSelect(position: Int) {
                            val direction =
                                InvestimentosFragmentDirections.actionInvestimentosFragmentToAddFragment(
                                    cryptoCards()[position])
                            findNavController().navigate(direction)
                        }
                    })
            }
        }

    }

    private fun cryptoCards(): List<CryptoCard> {

        val decimalFormat = DecimalFormat("#,###.###")
        decimalFormat.roundingMode = RoundingMode.CEILING

        return listOf(
            CryptoCard(
                R.drawable.logo_bitcoin,
                getString(R.string.bitcoin),
                decimalFormat.format(bitcoinPrice),
                decimalFormat.format(bitcoinInvested),
            ),
            CryptoCard(
                R.drawable.logo_ethereum,
                getString(R.string.ethereum),
                decimalFormat.format(ethereumPrice),
                decimalFormat.format(ethereumInvested),
            ),
            CryptoCard(
                R.drawable.logo_chiliz,
                getString(R.string.chiliz),
                decimalFormat.format(chilizPrice),
                decimalFormat.format(chilizInvested),
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
