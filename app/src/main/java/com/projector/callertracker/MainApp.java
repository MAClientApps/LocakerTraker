package com.projector.callertracker;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.projector.callertracker.nativenotifier.EventState;
import com.projector.callertracker.nativenotifier.EventNotifierApp;
import com.projector.callertracker.nativenotifier.NotifierFactoryApp;
import com.projector.callertracker.utils.AdsHelperClass;
import com.projector.callertracker.utils.AppOpenAdsListners;
import com.projector.callertracker.utils.UserHelper;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainApp extends Application {

    public static final String ADMOB_TAG = "Ads:";
    public static Context context;
    public static AppOpenAd appOpenAd;
    public static Dialog loadAdsDialog;
    private static MainApp mInstance;
    //NativeAd
    public List<NativeAd> mNativeAdsGHome = new ArrayList<>();
    public ArrayList<String> mAdsId = new ArrayList<>();

    //InterstitialAd
    public InterstitialAd mInterstitialAd;
    public AdManagerInterstitialAd mAdManagerInterstitialAd;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MainApp.context = context;
    }

    public static synchronized MainApp getInstance() {
        return mInstance;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        setContext(getApplicationContext());

        List<String> testDeviceIds = Arrays.asList("");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

        MobileAds.initialize(this, initializationStatus -> {
        });

        mInstance = this;

    }

    public boolean isNetworkConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        setContext(base);
    }

    // Load Banner Ads
    public void loadBanner(RelativeLayout adContainerView, Activity activity) {

//        adContainerView.setVisibility(View.GONE);

        if (!isNetworkConnected(activity)) {
            return;
        }
        if (AdsHelperClass.getIsAdEnable() != 1) {
            return;
        }

        if (AdsHelperClass.getAdType().equalsIgnoreCase(AdsHelperClass.AD_TYPE_ADMOB)) {
            loadAdMobBanner(adContainerView, activity);
        } else if (AdsHelperClass.getAdType().equalsIgnoreCase(AdsHelperClass.AD_TYPE_ADX)) {
            loadAdxBanner(adContainerView, activity);
        }
    }

    private void loadAdMobBanner(RelativeLayout adContainerView, Activity activity) {
        if (AdsHelperClass.getShowBanner() == 1) {
            String adUnitId;
            if (BuildConfig.DEBUG)
                adUnitId = getString(R.string.admob_banner_ads_id);
            else {
                adUnitId = AdsHelperClass.getBannerAd();
            }

            UserHelper.PrintLog(ADMOB_TAG, "BannerAd ID ===> " + adUnitId);

            if (TextUtils.isEmpty(adUnitId)) {
                return;
            }

            //BannerAd
            AdView admobManagerAdView = new AdView(activity);
            admobManagerAdView.setAdUnitId(adUnitId);
            adContainerView.addView(admobManagerAdView);
            AdRequest adRequest = new AdRequest.Builder().build();
            AdSize adSize = getAdSize(activity);
            admobManagerAdView.setAdSize(adSize);
            admobManagerAdView.loadAd(adRequest);
            admobManagerAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    adContainerView.setVisibility(View.VISIBLE);
                    UserHelper.PrintLog(ADMOB_TAG, "BannerAd ===> onAdLoaded");
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    adContainerView.setVisibility(View.GONE);
                    UserHelper.PrintLog(ADMOB_TAG, "BannerAd ===> onAdFailedToLoad " + loadAdError.getMessage());
                }
            });
        }
    }

    private void loadAdxBanner(RelativeLayout adContainerView, Activity activity) {
        if (AdsHelperClass.getShowBanner() == 1) {
            String adUnitId;
            if (BuildConfig.DEBUG) {
                adUnitId = getString(R.string.adx_banner_ads_id);
            } else {
                adUnitId = AdsHelperClass.getBannerAd();
            }

            UserHelper.PrintLog(ADMOB_TAG, "BannerAd ID ===> " + adUnitId);

            if (TextUtils.isEmpty(adUnitId)) {
                return;
            }

            AdManagerAdView adXManagerAdView = new AdManagerAdView(activity);
            adXManagerAdView.setAdUnitId(adUnitId);
            adContainerView.addView(adXManagerAdView);
            AdRequest adRequest = new AdRequest.Builder().build();
            AdSize adSize = getAdSize(activity);
            adXManagerAdView.setAdSize(adSize);
            adXManagerAdView.loadAd(adRequest);
            adXManagerAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    adContainerView.setVisibility(View.VISIBLE);
                    UserHelper.PrintLog(ADMOB_TAG, "BannerAd ===> onAdLoaded");
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    UserHelper.PrintLog(ADMOB_TAG, "BannerAd ===> onAdFailedToLoad " + loadAdError.getMessage());
                }
            });
        }
    }

    private AdSize getAdSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }


    //Native

    public List<NativeAd> getGNativeHome() {
        return mNativeAdsGHome;
    }


    public void setmAdsId(String id) {
        mAdsId.add(id);
    }

    public int getmAdsIdSize() {
        return mAdsId.size();
    }

    public void setmAdsIdClear() {
        mAdsId.clear();
    }

    public void loadNativeOptional(int adxCount, Activity activity) {

        if (!isNetworkConnected(activity)) {
            return;
        }
        if (AdsHelperClass.getIsAdEnable() != 1) {
            return;
        }

        if (AdsHelperClass.getShowNative() == 1) {
            if (adxCount == 0) {
                mNativeAdsGHome = new ArrayList<>();
                UserHelper.PrintLog(ADMOB_TAG, "NativeAds ID ==> 0");
            }
            AdLoader.Builder builder;

            String adUnitId;
            if (BuildConfig.DEBUG) {
                if (AdsHelperClass.getAdType().equalsIgnoreCase(AdsHelperClass.AD_TYPE_ADMOB)) {
                    adUnitId = getString(R.string.admob_native_ads_id);
                } else if (AdsHelperClass.getAdType().equalsIgnoreCase(AdsHelperClass.AD_TYPE_ADX)) {
                    adUnitId = getString(R.string.adx_native_ads_id);
                } else {
                    return;
                }

            } else {
                adUnitId = mAdsId.get(adxCount);
            }
            UserHelper.PrintLog(ADMOB_TAG, "NativeAds ID ==> " + adUnitId);

            if (TextUtils.isEmpty(adUnitId)) {
                return;
            }

            builder = new AdLoader.Builder(this, adUnitId);

            builder.forNativeAd(nativeAd -> {
                mNativeAdsGHome.add(nativeAd);

                int nextConunt = adxCount + 1;
                if (nextConunt < mAdsId.size()) {
                    loadNativeOptional(nextConunt, activity);
                }
                if (nextConunt == mAdsId.size()) {
                    UserHelper.PrintLog(ADMOB_TAG, "NativeAds ID => " + "last ==> " + adxCount);
                    EventNotifierApp notifier = NotifierFactoryApp.getInstance().getNotifier(NotifierFactoryApp.EVENT_NOTIFIER_AD_STATUS);
                    notifier.eventNotify(EventState.EVENT_AD_LOADED_NATIVE, null);
                }
            }).withAdListener(new AdListener() {

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    UserHelper.PrintLog(ADMOB_TAG, "NativeAds ID ==> " + "Success to onAdLoaded");
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    UserHelper.PrintLog(ADMOB_TAG, "NativeAds ID => " + loadAdError.getMessage());
                }
            });

            VideoOptions videoOptions = new VideoOptions.Builder()
                    .setStartMuted(true)
                    .build();

            NativeAdOptions adOptions = new NativeAdOptions.Builder()
                    .setVideoOptions(videoOptions)
                    .build();

            builder.withNativeAdOptions(adOptions);
            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                    UserHelper.PrintLog(ADMOB_TAG, "onAdFailedToLoad ==> " + adError.getMessage());
                    if (mNativeAdsGHome.size() == 0) {
                        UserHelper.PrintLog(ADMOB_TAG, "onAdFailedToLoad mNativeAdsGHome.size() ==>  0");
                    } else {
                        UserHelper.PrintLog(ADMOB_TAG, "onAdFailedToLoad mNativeAdsGHome.size() ==>  Event");
                    }

                }
            }).build();

            if (AdsHelperClass.getAdType().equalsIgnoreCase(AdsHelperClass.AD_TYPE_ADMOB)) {
                adLoader.loadAd(new AdRequest.Builder().build());
            } else if (AdsHelperClass.getAdType().equalsIgnoreCase(AdsHelperClass.AD_TYPE_ADX)) {
                adLoader.loadAd(new AdManagerAdRequest.Builder().build());
            }

        }
    }


    public void loadNativeAdBig(RelativeLayout fl_adplaceholder, Context activity, int adNo) {
        try {
            if (MainApp.getInstance().getGNativeHome() != null && MainApp.getInstance().getGNativeHome().size() > 0
                    && MainApp.getInstance().getGNativeHome().get(adNo) != null) {
                NativeAd nativeAd = MainApp.getInstance().getGNativeHome().get(adNo);

                if (nativeAd != null) {
                    NativeAdView adView = (NativeAdView) LayoutInflater.from(activity).inflate(R.layout.ad_unifiled_regular, null);
                    populateUnifiedNativeRegularAdView(nativeAd, adView);
                    fl_adplaceholder.setVisibility(View.VISIBLE);
                    fl_adplaceholder.removeAllViews();
                    fl_adplaceholder.addView(adView);
                    UserHelper.PrintLog(ADMOB_TAG, "NativeAds ID ==> " + "Success to show");
                } else {
                    fl_adplaceholder.setVisibility(View.GONE);
                    UserHelper.PrintLog(ADMOB_TAG, "NativeAds ID ==> " + adNo + " null 111");
                }
            } else {
                fl_adplaceholder.setVisibility(View.GONE);
                UserHelper.PrintLog(ADMOB_TAG, "NativeAds ID ==> " + adNo + " null 222");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fl_adplaceholder.setVisibility(View.GONE);
            UserHelper.PrintLog(ADMOB_TAG, "NativeAds ID ==> " + "Fail " + e.getMessage());

        }
    }

    public void loadNativeAdSmall(FrameLayout fl_adplaceholder, Context activity, int adNo) {
        try {
            if (MainApp.getInstance().getGNativeHome() != null && MainApp.getInstance().getGNativeHome().size() > 0
                    && MainApp.getInstance().getGNativeHome().get(adNo) != null) {
                NativeAd nativeAd = MainApp.getInstance().getGNativeHome().get(adNo);

                if (nativeAd != null) {
                    NativeAdView adView = (NativeAdView) LayoutInflater.from(activity).inflate(R.layout.ad_unifiled_small, null);
                    populateUnifiedNativeSmallAdView(nativeAd, adView);
                    fl_adplaceholder.setVisibility(View.VISIBLE);
                    fl_adplaceholder.removeAllViews();
                    fl_adplaceholder.addView(adView);
                    UserHelper.PrintLog(ADMOB_TAG, "NativeAds ID ==> " + "Success to show");
                } else {
                    fl_adplaceholder.setVisibility(View.GONE);
                    UserHelper.PrintLog(ADMOB_TAG, "NativeAds ID ==> " + adNo + " null 111");
                }
            } else {
                fl_adplaceholder.setVisibility(View.GONE);
                UserHelper.PrintLog(ADMOB_TAG, "NativeAds ID ==> " + adNo + " null 222");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fl_adplaceholder.setVisibility(View.GONE);
            UserHelper.PrintLog(ADMOB_TAG, "NativeAds ID ==> " + "Fail " + e.getMessage());

        }
    }


    public void populateUnifiedNativeRegularAdView(NativeAd nativeAd, NativeAdView adView) {
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);

        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        try {
            ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (nativeAd.getBody() == null) {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }
        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) Objects.requireNonNull(adView.getStarRatingView()))
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            Objects.requireNonNull(adView.getAdvertiserView()).setVisibility(View.INVISIBLE);
        } else {
            ((TextView) Objects.requireNonNull(adView.getAdvertiserView())).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.getStoreView().setVisibility(View.GONE);
        adView.getPriceView().setVisibility(View.GONE);

        adView.setNativeAd(nativeAd);

        VideoController vc = Objects.requireNonNull(nativeAd.getMediaContent()).getVideoController();

        if (vc.hasVideoContent()) {
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        }

    }

    public void populateUnifiedNativeSmallAdView(NativeAd nativeAd, NativeAdView adView) {
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        try {
            ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (nativeAd.getBody() == null) {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }


        if (nativeAd.getStore() == null) {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) Objects.requireNonNull(adView.getStarRatingView()))
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            Objects.requireNonNull(adView.getAdvertiserView()).setVisibility(View.INVISIBLE);
        } else {
            ((TextView) Objects.requireNonNull(adView.getAdvertiserView())).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.getStoreView().setVisibility(View.GONE);
        adView.getPriceView().setVisibility(View.GONE);
        adView.setNativeAd(nativeAd);
    }


    // Load InterstitialAds
    public void loadInterstitialAd(Activity activity, Intent intent, boolean isFinish) {

        if (!isNetworkConnected(activity)) {
            doNext(activity, intent, isFinish);
            return;
        }
        if (AdsHelperClass.getIsAdEnable() != 1) {
            doNext(activity, intent, isFinish);
            return;
        }
        if (AdsHelperClass.ads_per_session == AdsHelperClass.getAdsPerSession()) {
            doNext(activity, intent, isFinish);
            return;
        }

        if (AdsHelperClass.getAdType().equalsIgnoreCase(AdsHelperClass.AD_TYPE_ADMOB)) {
            loadAdmobInterstitialAd(activity, intent, isFinish);
        } else if (AdsHelperClass.getAdType().equalsIgnoreCase(AdsHelperClass.AD_TYPE_ADX)) {
            loadAdxInterstitialAd(activity, intent, isFinish);
        } else {
            doNext(activity, intent, isFinish);
        }


    }

    private void loadAdmobInterstitialAd(Activity activity, Intent intent, boolean isFinish) {

        if (AdsHelperClass.getShowInterstitial() == 1) {

            String adUnitId;
            if (BuildConfig.DEBUG)
                adUnitId = getString(R.string.admob_interstitial_ads_id);
            else {
                adUnitId = AdsHelperClass.getInterstitialAd();
            }
            UserHelper.PrintLog(ADMOB_TAG, "Admob InterstitialAd ===> " + adUnitId);
            if (TextUtils.isEmpty(adUnitId)) {
                return;
            }
            loadAdsDialog = new Dialog(activity);
            loadAdsDialog.setContentView(R.layout.layout_loading);
            loadAdsDialog.setCanceledOnTouchOutside(false);
            loadAdsDialog.setCancelable(false);
            loadAdsDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            loadAdsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loadAdsDialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            loadAdsDialog.show();

            ((TextView) loadAdsDialog.findViewById(R.id.title)).setText("Loading Ads...");
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(this, adUnitId, adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            mInterstitialAd = interstitialAd;
                            UserHelper.PrintLog(ADMOB_TAG, "InterstitialAd ===> onAdLoaded");
                            if (loadAdsDialog != null && loadAdsDialog.isShowing()) {
                                loadAdsDialog.dismiss();
                            }
                            displayInterstitialAds(activity, intent, isFinish);
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            UserHelper.PrintLog(ADMOB_TAG, "InterstitialAd ===> " + loadAdError.getMessage());
                            mInterstitialAd = null;
                            if (loadAdsDialog != null && loadAdsDialog.isShowing()) {
                                loadAdsDialog.dismiss();
                            }
                            doNext(activity, intent, isFinish);
                        }
                    });
        } else {
            doNext(activity, intent, isFinish);
        }
    }

    private void loadAdxInterstitialAd(Activity activity, Intent intent, boolean isFinish) {
        if (AdsHelperClass.getShowInterstitial() == 1) {

            String adUnitId;
            if (BuildConfig.DEBUG)
                adUnitId = getString(R.string.adx_interstitial_ads_id);
            else {
                adUnitId = AdsHelperClass.getInterstitialAd();
            }
            UserHelper.PrintLog(ADMOB_TAG, "Adx InterstitialAd ===> " + adUnitId);
            if (TextUtils.isEmpty(adUnitId)) {
                return;
            }

            loadAdsDialog = new Dialog(activity);
            loadAdsDialog.setContentView(R.layout.layout_loading);
            loadAdsDialog.setCanceledOnTouchOutside(false);
            loadAdsDialog.setCancelable(false);
            loadAdsDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            loadAdsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loadAdsDialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            loadAdsDialog.show();
            ((TextView) loadAdsDialog.findViewById(R.id.title)).setText("Loading Ads...");

            AdManagerAdRequest adManagerAdRequest = new AdManagerAdRequest.Builder().build();
            AdManagerInterstitialAd.load(this, adUnitId, adManagerAdRequest,
                    new AdManagerInterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                            mAdManagerInterstitialAd = interstitialAd;
                            UserHelper.PrintLog(ADMOB_TAG, "Adx InterstitialAd ===>" + "onAdLoaded");
                            if (loadAdsDialog != null && loadAdsDialog.isShowing()) {
                                loadAdsDialog.dismiss();
                            }
                            displayInterstitialAds(activity, intent, isFinish);
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            UserHelper.PrintLog(ADMOB_TAG, "Adx InterstitialAd ===> " + loadAdError.getMessage());
                            mAdManagerInterstitialAd = null;
                        }
                    });
        } else {
            doNext(activity, intent, isFinish);
        }
    }


    // Display InterstitialAds

    /*onClick*/

    public void displayInterstitialAds(Activity activity, Intent intent, boolean isFinished) {
        if (AdsHelperClass.getIsAdEnable() == 1 && AdsHelperClass.getShowInterstitial() == 1) {
            if (AdsHelperClass.getAdType().equalsIgnoreCase(AdsHelperClass.AD_TYPE_ADMOB)) {
                displayAdMobInterstitialAd(activity, intent, isFinished);
            } else if (AdsHelperClass.getAdType().equalsIgnoreCase(AdsHelperClass.AD_TYPE_ADX)) {
                displayAdxInterstitialAd(activity, intent, isFinished);
            } else {
                doNext(activity, intent, isFinished);
            }
        } else {
            doNext(activity, intent, isFinished);
        }
    }

    private void displayAdMobInterstitialAd(Activity activity, Intent intent, boolean isFinished) {
        int count = AdsHelperClass.getInterstitialAdsCount();
        if (count % AdsHelperClass.getInterstitialAdsClick() == 0
                && AdsHelperClass.ads_per_session != AdsHelperClass.getAdsPerSession()) {
            if (mInterstitialAd != null) {
                UserHelper.PrintLog(ADMOB_TAG, "Admob InterstitialAd ===> " + "Showed");
                mInterstitialAd.show(activity);
                AdsHelperClass.isShowingFullScreenAd = true;
                AdsHelperClass.ads_per_session++;
            } else {
                loadInterstitialAd(activity, intent, isFinished);
                AdsHelperClass.isShowingFullScreenAd = false;
                return;
            }
        } else {
            doNext(activity, intent, isFinished);
        }
        AdsHelperClass.setInterstitialAdsCount(count + 1);

        if (mInterstitialAd != null) {
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when fullscreen content is dismissed.
                    UserHelper.PrintLog(ADMOB_TAG, "Admob InterstitialAd ===> The ad was dismissed.");
                    doNext(activity, intent, isFinished);
                    AdsHelperClass.isShowingFullScreenAd = false;
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    UserHelper.PrintLog(ADMOB_TAG, "Admob InterstitialAd ===> The ad failed to show.");
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    mInterstitialAd = null;
                    UserHelper.PrintLog(ADMOB_TAG, "Admob InterstitialAd ===> The ad was shown.");
                }
            });
        }
    }

    private void displayAdxInterstitialAd(Activity activity, Intent intent, boolean isFinished) {
        int count = AdsHelperClass.getInterstitialAdsCount();
        if (count % AdsHelperClass.getInterstitialAdsClick() == 0
                && AdsHelperClass.ads_per_session != AdsHelperClass.getAdsPerSession()) {
            if (mAdManagerInterstitialAd != null) {
                UserHelper.PrintLog(ADMOB_TAG, "Adx InterstitialAd ===> " + "Showed");
                mAdManagerInterstitialAd.show(activity);
                AdsHelperClass.isShowingFullScreenAd = true;
                AdsHelperClass.ads_per_session++;
            } else {
                loadInterstitialAd(activity, intent, isFinished);
                AdsHelperClass.isShowingFullScreenAd = false;
                return;
            }
        } else {
            doNext(activity, intent, isFinished);
        }
        AdsHelperClass.setInterstitialAdsCount(count + 1);

        if (mAdManagerInterstitialAd != null) {
            mAdManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when fullscreen content is dismissed.
                    UserHelper.PrintLog(ADMOB_TAG, "Adx InterstitialAd ===> The ad was dismissed.");
                    doNext(activity, intent, isFinished);
                    AdsHelperClass.isShowingFullScreenAd = false;
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    UserHelper.PrintLog(ADMOB_TAG, "Adx InterstitialAd ===> The ad failed to show.");
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    mAdManagerInterstitialAd = null;
                    UserHelper.PrintLog(ADMOB_TAG, "Adx InterstitialAd ===> The ad was shown.");
                }
            });
        }
    }



    private void doNext(Activity activity, Intent intent, boolean isFinished) {
        if (intent != null) {
            activity.startActivity(intent);
        }
        if (isFinished) {
            activity.finish();
        }
    }



    // app open Ads
    public void loadOpenAppAdsOnSplash(Activity activity, AppOpenAdsListners appCallBackOpenAppAds) {

        if (AdsHelperClass.getIsAdEnable() != 1) {
            return;
        }

        if (AdsHelperClass.getShowAppOpen() != 1) {
            return;
        }

        if (!isNetworkConnected(activity)) {
            return;
        }

        if (BuildConfig.DEBUG) {
            UserHelper.PrintLog("Ads: ", "Load Open App class");
            String adUnitId = "";

            if (AdsHelperClass.getAdType().equalsIgnoreCase(AdsHelperClass.AD_TYPE_ADMOB)) {
                adUnitId = activity.getString(R.string.app_open_ads_id);
            } else if (AdsHelperClass.getAdType().equalsIgnoreCase(AdsHelperClass.AD_TYPE_ADX)) {
                adUnitId = activity.getString(R.string.adx_app_open_ads_id);
            } else {
                return;
            }

            AppOpenAd.load(this, adUnitId, new AdRequest.Builder().build(), AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AppOpenAd ad) {
                    appOpenAd = ad;
                    appCallBackOpenAppAds.onAdLoad(true);
                    UserHelper.PrintLog("Ads ", "OpenApp loadOpenAppAdsOnSplash:  onAdLoaded");
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    appOpenAd = null;
                    appCallBackOpenAppAds.onAdLoad(false);
                    UserHelper.PrintLog("Ads ", "OpenApp loadOpenAppAdsOnSplash:  onAdFailedToLoad");
                }
            });
        } else {
            String adUnitId = AdsHelperClass.getAppOpenAd();
            if (adUnitId == null) {
                return;
            }
            if (adUnitId.isEmpty()) {
                return;
            }
            UserHelper.PrintLog("Ads: ", "Load Open App class");
            AppOpenAd.load(this, adUnitId, new AdRequest.Builder().build(), AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AppOpenAd ad) {
                    appOpenAd = ad;
                    appCallBackOpenAppAds.onAdLoad(true);
                    UserHelper.PrintLog("Ads ", "OpenApp loadOpenAppAdsOnSplash:  onAdLoaded");
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    appOpenAd = null;
                    appCallBackOpenAppAds.onAdLoad(false);
                    UserHelper.PrintLog("Ads ", "OpenApp loadOpenAppAdsOnSplash:  onAdFailedToLoad");
                }
            });
        }
    }
}
