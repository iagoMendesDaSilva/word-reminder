package com.lighthouse.wordreminder.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BillingManager(
    private val context: Context,
    private val onPurchaseSuccess: (productId: String) -> Unit = {}
) {

    companion object {
        const val PRODUCT_COFFEE = "support_coffee"
        const val PRODUCT_SNACK = "support_snack"
        const val PRODUCT_LUNCH = "support_lunch"

        val ALL_PRODUCTS = listOf(
            PRODUCT_COFFEE,
            PRODUCT_SNACK,
            PRODUCT_LUNCH
        )
    }

    private val _products = MutableStateFlow<Map<String, ProductDetails>>(emptyMap())
    val products: StateFlow<Map<String, ProductDetails>> = _products.asStateFlow()

    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                    consumePurchase(purchase)
                }
            }
        }
    }

    private val pendingPurchasesParams = PendingPurchasesParams.newBuilder()
        .enableOneTimeProducts()
        .build()

    private val billingClient: BillingClient = BillingClient.newBuilder(context)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases(pendingPurchasesParams)
        .build()

    init {
        startConnection()
    }

    private fun startConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                Log.d("BillingManager", "onBillingSetupFinished: code=${billingResult.responseCode}, msg=${billingResult.debugMessage}")
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    queryProducts()
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.w("BillingManager", "onBillingServiceDisconnected")
            }
        })
    }

    fun queryProducts() {
        if (!billingClient.isReady) {
            Log.d("BillingManager", "queryProducts: billingClient not ready, reconnecting...")
            startConnection()
            return
        }

        val productList = ALL_PRODUCTS.map { productId ->
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(productId)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        }

        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, queryProductDetailsResult ->
            val count = queryProductDetailsResult.productDetailsList.size
            Log.d("BillingManager", "queryProductDetails: code=${billingResult.responseCode}, msg=${billingResult.debugMessage}, count=$count")
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val productMap = queryProductDetailsResult.productDetailsList.associateBy { it.productId }
                _products.value = productMap
            }
        }
    }

    fun launchBillingFlow(
        activity: Activity,
        productId: String,
        onError: () -> Unit = {}
    ) {
        val productDetails = _products.value[productId]
        Log.d("BillingManager", "launchBillingFlow: productId=$productId, hasDetails=${productDetails != null}, isReady=${billingClient.isReady}")
        if (productDetails == null || !billingClient.isReady) {
            onError()
            return
        }

        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        val billingResult = billingClient.launchBillingFlow(activity, billingFlowParams)
        if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
            onError()
        }
    }

    private fun consumePurchase(purchase: Purchase) {
        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        billingClient.consumeAsync(consumeParams) { result, _ ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                val purchasedId = purchase.products.firstOrNull() ?: ""
                onPurchaseSuccess(purchasedId)
            }
        }
    }

    fun destroy() {
        if (billingClient.isReady) {
            billingClient.endConnection()
        }
    }
}
