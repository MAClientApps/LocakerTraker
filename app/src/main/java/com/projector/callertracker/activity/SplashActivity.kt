package com.projector.callertracker.activity


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projector.callertracker.AppOpenAds
import com.projector.callertracker.BuildConfig
import com.projector.callertracker.MainApp
import com.projector.callertracker.R
import com.projector.callertracker.model.RemoteAppDataModel
import com.projector.callertracker.utils.AdsHelperClass
import com.projector.callertracker.utils.SharedPreferencesClass
import com.projector.callertracker.utils.UserHelper

class SplashActivity : BaseActivity() {

    private var mFirebaseRemoteConfig: FirebaseRemoteConfig? = null
    private var isAppOpenAdLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        hideSystemBars()

        AdsHelperClass.setOpenAdsShowedCount(0)
        SharedPreferencesClass.getInstance().setBoolean("isSplash", true)

        val ivBack = findViewById<ImageView>(R.id.ivBack)
        val bitmapLocal =
            decodeSampledBitmapFromResource(resources, R.drawable.ic_splash_bg, 500, 500)
        ivBack.setImageBitmap(bitmapLocal)
        val ivLogo = findViewById<ImageView>(R.id.ivLogo)
        val bitmapLogo =
            decodeSampledBitmapFromResource(resources, R.drawable.ic_logo_128, 500, 500)
        ivLogo.setImageBitmap(bitmapLogo)

        toHome()


        if (MainApp.getInstance()!!.isNetworkConnected(this)) {
            remoteConfig()

        } else {


        }
    }

    fun remoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600).build()
        mFirebaseRemoteConfig!!.setConfigSettingsAsync(configSettings)
        mFirebaseRemoteConfig!!.fetchAndActivate()
            .addOnCompleteListener(this) { task: Task<Boolean?> ->
                if (task.isSuccessful) {
                    val response = mFirebaseRemoteConfig!!.getString("caller_id")
                    if (!response.isEmpty()) {
                        Log.e("RemoteData: ", response)
//                        doNext(response)
                    } else {
                        toHome()
                    }
                } else {
                    toHome()
                }
            }.addOnFailureListener { e: Exception ->
                Log.e("Splash: ", "RemoteData: " + e.message)
                toHome()
            }
    }


    fun parseAppUserListModel(jsonObject: String?): RemoteAppDataModel? {
        try {
            val gson = Gson()
            val token: TypeToken<RemoteAppDataModel> = object : TypeToken<RemoteAppDataModel>() {}
            return gson.fromJson(jsonObject, token.type)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }


    private fun setAppData(appData: RemoteAppDataModel) {
        try {
            AdsHelperClass.setBannerAd(java.lang.String.valueOf(appData.bannerid))
            AdsHelperClass.setShowBanner(appData.banner)
            AdsHelperClass.setInterstitialAd(java.lang.String.valueOf(appData.interstitialid))
            AdsHelperClass.setShowInterstitial(appData.interstitial)
            AdsHelperClass.setInterstitialAdsClick(appData.ads_per_click)
            AdsHelperClass.setShowNative(appData.native)
            AdsHelperClass.setAppOpenAd(java.lang.String.valueOf(appData.openadid))
            AdsHelperClass.setShowAppOpen(appData.openad)
            AdsHelperClass.setAppOpenCount(appData.app_open_count)
            AdsHelperClass.setId(appData.id)
            AdsHelperClass.setAdType(java.lang.String.valueOf(appData.adtype))
            AdsHelperClass.setIsAdEnable(appData.isAdEnable)
            AdsHelperClass.setExitAdEnable(appData.exit_ad_enable)
            AdsHelperClass.setAdsPerSession(appData.ads_per_session)
            AdsHelperClass.setIsSplashOn(appData.is_splash_on)
            AdsHelperClass.setSplashTime(appData.splash_time)


        } catch (e: java.lang.Exception) {
            UserHelper.PrintLog("Exception", e.message)
        }
    }

    private fun loadAppOpenAd() {
        if (!AdsHelperClass.isShowingFullScreenAd
            && MainApp.appOpenAd != null && MainApp.getInstance().isNetworkConnected(this)
            && AdsHelperClass.getIsSplashOn() == 1
        ) {
            val callback: FullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    MainApp.appOpenAd = null
                    UserHelper.PrintLog(MainApp.ADMOB_TAG, "Open AD ==> The ad was dismissed.")
                    toHome()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    UserHelper.PrintLog(MainApp.ADMOB_TAG, "Open AD ==> The ad failed to show.")
                }

                override fun onAdShowedFullScreenContent() {
                    UserHelper.PrintLog(MainApp.ADMOB_TAG, "Open AD ==> The ad was shown.")
                }
            }
            MainApp.appOpenAd.fullScreenContentCallback = callback
            MainApp.appOpenAd.show(this)
        }
    }


    private fun toHome() {
        startActivity(Intent(this@SplashActivity, StartAppActivity::class.java))
        overridePendingTransition(R.anim.enter, R.anim.exit)
        finish()
    }
}