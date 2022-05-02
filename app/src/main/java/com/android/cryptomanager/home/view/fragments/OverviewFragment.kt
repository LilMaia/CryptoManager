package com.android.cryptomanager.home.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.billingclient.api.*
import com.android.cryptomanager.databinding.OverviewFragmentBinding
import com.android.cryptomanager.home.presentation.OverviewViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.RoundingMode
import java.text.DecimalFormat


class OverviewFragment : Fragment() {

    private var _binding: OverviewFragmentBinding? = null
    private val binding get() = _binding!!

    private val overviewViewModel by viewModel<OverviewViewModel>()
//    private var billingClient: BillingClient? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = OverviewFragmentBinding.inflate(layoutInflater)
//        billingClient =
//            BillingClient.newBuilder(requireContext()).enablePendingPurchases().setListener(this)
//                .build()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val decimalFormat = DecimalFormat("#,###.###")
        decimalFormat.roundingMode = RoundingMode.CEILING
    }
//
//    fun consume(view: View?) {
//        if (billingClient!!.isReady) {
//            initiatePurchase()
//        } else {
//            billingClient = BillingClient.newBuilder(requireContext()).enablePendingPurchases()
//                .setListener(this).build()
//            billingClient!!.startConnection(object : BillingClientStateListener {
//                override fun onBillingSetupFinished(billingResult: BillingResult) {
//                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
//                        initiatePurchase()
//                    } else {
//                        Toast.makeText(
//                            requireContext(),
//                            "Error " + billingResult.debugMessage,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//
//                override fun onBillingServiceDisconnected() {}
//            })
//        }
//    }
//
//    private fun initiatePurchase() {
//        val skuList: MutableList<String> = ArrayList()
//        skuList.add(PRODUCT_ID.toString())
//        val params = SkuDetailsParams.newBuilder()
//        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)
//        billingClient!!.querySkuDetailsAsync(
//            params.build()
//        )
//        { billingResult, skuDetailsList ->
//            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
//                if (skuDetailsList != null && skuDetailsList.size > 0) {
//                    val flowParams = BillingFlowParams.newBuilder()
//                        .setSkuDetails(skuDetailsList[0])
//                        .build()
//                    billingClient!!.launchBillingFlow(requireActivity(), flowParams)
//                } else {
//                    Toast.makeText(requireContext(), "Purchase Item not Found", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            } else {
//                Toast.makeText(
//                    requireContext(),
//                    " Error " + billingResult.debugMessage, Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }
//
//    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
//        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
//            handlePurchases(purchases)
//        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
//            val queryAlreadyPurchasesResult =
//                billingClient!!.queryPurchases(BillingClient.SkuType.INAPP)
//            val alreadyPurchases = queryAlreadyPurchasesResult.purchasesList
//            alreadyPurchases?.let { handlePurchases(it) }
//        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
//            Toast.makeText(requireContext(), "Purchase Canceled", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(
//                requireContext(),
//                "Error " + billingResult.debugMessage,
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//
//    fun handlePurchases(purchases: List<Purchase>) {
//        for (purchase in purchases) {
//            if (PRODUCT_ID == purchase.sku && purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
//                if (!verifyValidSignature(purchase.originalJson, purchase.signature)) {
//                    Toast.makeText(
//                        requireContext(),
//                        "Error : Invalid Purchase",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return
//                }
//                if (!purchase.isAcknowledged) {
//                    val consumeParams = ConsumeParams.newBuilder()
//                        .setPurchaseToken(purchase.purchaseToken)
//                        .build()
//                    billingClient!!.consumeAsync(consumeParams, consumeListener)
//                }
//            } else if (PRODUCT_ID == purchase.sku && purchase.purchaseState == Purchase.PurchaseState.PENDING) {
//                Toast.makeText(
//                    applicationContext,
//                    "Purchase is Pending. Please complete Transaction", Toast.LENGTH_SHORT
//                ).show()
//            } else if (PRODUCT_ID == purchase.sku && purchase.purchaseState == Purchase.PurchaseState.UNSPECIFIED_STATE) {
//                Toast.makeText(applicationContext, "Purchase Status Unknown", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }
//
//    var consumeListener = ConsumeResponseListener { billingResult, purchaseToken ->
//        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
//            val consumeCountValue = purchaseCountValueFromPref + 1
//            savePurchaseCountValueToPref(consumeCountValue)
//            Toast.makeText(applicationContext, "Item Consumed", Toast.LENGTH_SHORT).show()
//            consumeCount!!.text = "Item Consumed $purchaseCountValueFromPref Time(s)"
//        }
//    }
//
//    companion object {
//        const val PRODUCT_ID = 20
//    }
}
