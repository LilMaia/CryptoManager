package com.android.cryptomanager.home.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import com.android.billingclient.api.*
import com.android.cryptomanager.databinding.InvestimentosFragmentBinding
import java.io.IOException

class InvestimentosFragment : Fragment(), PurchasesUpdatedListener {

    private var _binding: InvestimentosFragmentBinding? = null
    private val binding get() = _binding!!

    //    private val investimentosViewModel by viewModel<InvestimentosViewModel>()
    private var billingClient: BillingClient? = null

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
        billingClient = BillingClient.newBuilder(requireContext())
            .enablePendingPurchases().setListener(this).build()
        billingClient!!.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    val queryPurchase = billingClient!!.queryPurchases(BillingClient.SkuType.SUBS)
                    val queryPurchases = queryPurchase.purchasesList
                    if (queryPurchases != null && queryPurchases.size > 0) {
                        handlePurchases(queryPurchases)
                    } else {
                        saveSubscribeValueToPref(false)
                    }
                }
            }

            override fun onBillingServiceDisconnected() {
                Toast.makeText(requireContext(), "Service Disconnected", Toast.LENGTH_SHORT).show()
            }
        })

        if (subscribeValueFromPref) {
            binding.subscribe.visibility = View.GONE
            binding.premiumContent.visibility = View.VISIBLE
            binding.subscriptionStatus.text = "Subscription Status : Subscribed"
        } else {
            binding.premiumContent.visibility = View.GONE
            binding.subscribe.visibility = View.VISIBLE
            binding.subscriptionStatus.text = "Subscription Status : Not Subscribed"
        }

        binding.subscribe.setOnClickListener {
            subscribe(view)
        }
    }

    private val preferenceObject: SharedPreferences
        get() {
            return requireContext().getSharedPreferences(PREF_FILE, 0)
        }

    private val preferenceEditObject: SharedPreferences.Editor
        get() {
            val pref = requireContext().getSharedPreferences(PREF_FILE, 0)
            return pref.edit()
        }

    private val subscribeValueFromPref: Boolean
        get() = preferenceObject.getBoolean(SUBSCRIBE_KEY, false)

    private fun saveSubscribeValueToPref(value: Boolean) {
        preferenceEditObject.putBoolean(SUBSCRIBE_KEY, value).commit()
    }

    fun subscribe(view: View?) {
        if (billingClient!!.isReady) {
            initiatePurchase()
        } else {
            billingClient =
                BillingClient.newBuilder(requireContext()).enablePendingPurchases()
                    .setListener(this).build()
            billingClient!!.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        initiatePurchase()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error " + billingResult.debugMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onBillingServiceDisconnected() {
                    Toast.makeText(requireContext(), "Service Disconnected ", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

    private fun initiatePurchase() {
        val skuList: MutableList<String> = ArrayList()
        skuList.add(ITEM_SKU_SUBSCRIBE)
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)
        val billingResult =
            billingClient!!.isFeatureSupported(BillingClient.FeatureType.SUBSCRIPTIONS)
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            billingClient!!.querySkuDetailsAsync(
                params.build()
            ) { billingResult, skuDetailsList ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    if (skuDetailsList != null && skuDetailsList.size > 0) {
                        val flowParams = BillingFlowParams.newBuilder()
                            .setSkuDetails(skuDetailsList[0])
                            .build()
                        billingClient!!.launchBillingFlow(requireActivity(), flowParams)
                    } else {
                        Toast.makeText(requireContext(), "Item not Found", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        " Error " + billingResult.debugMessage, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Sorry Subscription not Supported. Please Update Play Store", Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            handlePurchases(purchases)
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            val queryAlreadyPurchasesResult =
                billingClient!!.queryPurchases(BillingClient.SkuType.SUBS)
            val alreadyPurchases = queryAlreadyPurchasesResult.purchasesList
            alreadyPurchases?.let { handlePurchases(it) }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(requireContext(), "Purchase Canceled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                requireContext(),
                "Error " + billingResult.debugMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun handlePurchases(purchases: List<Purchase>) {
        for (purchase in purchases) {
            //if item is purchased
            if (ITEM_SKU_SUBSCRIBE == purchase.skus[0] && purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                if (!verifyValidSignature(purchase.originalJson, purchase.signature)) {
                    // Invalid purchase
                    // show error to user
                    Toast.makeText(requireContext(), "Error : invalid Purchase", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                // else purchase is valid
                //if item is purchased and not acknowledged
                if (!purchase.isAcknowledged) {
                    val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                        .build()
                    billingClient!!.acknowledgePurchase(acknowledgePurchaseParams, ackPurchase)
                } else {
                    // Grant entitlement to the user on item purchase
                    // restart activity
                    if (!subscribeValueFromPref) {
                        saveSubscribeValueToPref(true)
                        Toast.makeText(requireContext(), "Item Purchased", Toast.LENGTH_SHORT)
                            .show()
                        recreate(requireActivity())
                    }
                }
            } else if (ITEM_SKU_SUBSCRIBE == purchase.skus[0] && purchase.purchaseState == Purchase.PurchaseState.PENDING) {
                Toast.makeText(
                    requireContext(),
                    "Purchase is Pending. Please complete Transaction", Toast.LENGTH_SHORT
                ).show()
            } else if (ITEM_SKU_SUBSCRIBE == purchase.skus[0] && purchase.purchaseState == Purchase.PurchaseState.UNSPECIFIED_STATE) {
                saveSubscribeValueToPref(false)
                binding.premiumContent.visibility = View.GONE
                binding.subscribe.visibility = View.VISIBLE
                binding.subscriptionStatus.text = "Subscription Status : Not Subscribed"
                Toast.makeText(requireContext(), "Purchase Status Unknown", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    var ackPurchase = AcknowledgePurchaseResponseListener { billingResult ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            //if purchase is acknowledged
            // Grant entitlement to the user. and restart activity
            saveSubscribeValueToPref(true)
            recreate(requireActivity())
        }
    }

    /**
     * Verifies that the purchase was signed correctly for this developer's public key.
     * Note: It's strongly recommended to perform such check on your backend since hackers can
     * replace this method with "constant true" if they decompile/rebuild your app.
     */
    private fun verifyValidSignature(signedData: String, signature: String): Boolean {
        return try {
            // To get key go to Developer Console > Select your app > Development Tools > Services & APIs.
            val base64Key = "Enter Your Key Here"
            Security.verifyPurchase(base64Key, signedData, signature)
        } catch (e: IOException) {
            false
        }
    }

    companion object {
        const val ITEM_SKU_SUBSCRIBE = "sub_example"
        const val PREF_FILE = "1231"
        const val SUBSCRIBE_KEY = "231dqad213"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
