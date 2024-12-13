package com.billy.simpleunitconvert.feature.common

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.billy.simpleunitconvert.core.data.utils.logError
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


@Composable
fun BannerAdView(adUnitId: String) {
    val context = LocalContext.current
    if (Utils.isEnableAds && context.isNetworkAvailable()) {
        val adView = remember {
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                setAdUnitId(adUnitId)
            }
        }

        DisposableEffect(adView) {
            onDispose {
                adView.destroy() // Ensure the AdView is properly cleaned up
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            AndroidView(modifier = Modifier.fillMaxWidth(), factory = { adView }, update = { view ->
                if (view.adListener == null) {
                    view.loadAd(AdRequest.Builder().build())
                }
            })
        }
    }
}


class InterstitialAdHelper(val context: Context, private val adUnitId: String) {
    private var interstitialAd: InterstitialAd? = null
    private var isAdLoaded = false

    fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: InterstitialAd) {
                Log.e("AppOpenAdManager", "Ad is loaded, ad: $ad")
                interstitialAd = ad
                isAdLoaded = true
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                isAdLoaded = false
                logError("InterstitialAdHelper: Failed to load ad: ${adError.message}")
            }
        })
    }

    fun showAd(onAdClosed: () -> Unit) {
        if (isAdLoaded && interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null // Clear the ad reference
                    isAdLoaded = false
                    onAdClosed()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    logError("InterstitialAdHelper: Failed to show ad: ${adError.message}")
                    interstitialAd = null
                    isAdLoaded = false
                }
            }
            interstitialAd?.show(context as Activity)
        } else {
            onAdClosed()
        }
    }
}

class AppOpenAdManager(val context: Context) {

    private var appOpenAd: AppOpenAd? = null
    var isShowingAd = false

    // Load App Open Ad
    fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        AppOpenAd.load(context,
            Utils.ADSUNITID.APP_OPEN,
            adRequest,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    Log.e("AppOpenAdManager", "Ad is loaded, ad: $ad")
                    appOpenAd = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    logError(" AppOpenAdManager: Failed to load ad: ${error.message}")
                }

            })
    }

    // Show App Open Ad
    fun showAdIfAvailable(context: Context, onAdDismissed: () -> Unit) {
        appOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                isShowingAd = true
                appOpenAd = null
                onAdDismissed()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                isShowingAd = true
                appOpenAd = null
                logError("onAdFailedToShowFullScreenContent failed to show ad: ${error.message}")
                onAdDismissed()
            }

            override fun onAdShowedFullScreenContent() {
                Log.e("AppOpenAdManager", "Ad is showing")
            }
        }
        appOpenAd?.show(context as Activity)
        if (appOpenAd == null) {
            isShowingAd = true
            onAdDismissed()
        }
    }
}


/*
Test Ad Units
Banner: ca-app-pub-3940256099942544/6300978111
Interstitial: ca-app-pub-3940256099942544/1033173712
to test
Banner Ad: ca-app-pub-3940256099942544/6300978111
Interstitial Ad: ca-app-pub-3940256099942544/1033173712
Rewarded Video Ad: ca-app-pub-3940256099942544/5224354917
* */


