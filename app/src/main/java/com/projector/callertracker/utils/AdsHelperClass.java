package com.projector.callertracker.utils;

public class AdsHelperClass {

    public static final String MY_PREFERANCE = "my_preferance";
    public static final String CONFIG_DATA = "config_data";
    //ads
    public static final String AD_TYPE_ADMOB = "admob";
    public static final String AD_TYPE_ADX = "adx";

    public static final String BANNER_APP = "banner_ad";
    public static final String SHOW_BANNER = "show_banner";

    public static final String INTERSTITIAL_AD = "interstitial_ad";
    public static final String SHOW_INTERSTITIAL = "show_interstitial";
    private static final String INTERSTITIAL_ADS_COUNT = "interstitial_ads_count";

    public static final String SHOW_NATIVE = "show_native";

    public static final String APP_OPEN_AD = "app_open_ad";
    public static final String SHOW_APP_OPEN = "show_app_open";
    public static final String APP_OPEN_COUNT = "app_open_count";

    public static final String ID = "id";
    public static final String AD_TYPE = "ad_type";
    public static final String IS_AD_ENABLE = "is_ad_enable";
    public static final String EXIT_AD_ENABLE = "exit_ad_enable";
    public static final String ADS_PER_SESSION = "ads_per_session";
    private static final String INTERSTITIAL_ADS_CLICK = "interstitial_ads_click";
    private static final String IS_SPLASH_ON = "is_splash_on";
    private static final String SPLASH_TIME = "splash_time";

    //other
    public static boolean isShowingFullScreenAd = false;
    public static int ads_per_session = 0;
    public static final String FULLSCREEN_IMAGE_DOWNLOAD = "fullscreen_image_download";
    public static final String FULLSCREEN_IMAGE_SHARE = "fullscreen_image_share";
    public static final String FULLSCREEN_SET_WALLPAPER = "fullscreen_set_wallpaper";
    public static final String FULLSCREEN_IMAGE_FAVOTITE = "fullscreen_image_favotite";
    public static final String OPENADS_SHOWED_COUNT = "openads_showed_count";

    public static int getInterstitialAdsCount() {
        return SharedPreferencesClass.getInstance().getInt(INTERSTITIAL_ADS_COUNT, 0);
    }

    public static void setInterstitialAdsCount(int i) {
        SharedPreferencesClass.getInstance().setInt(INTERSTITIAL_ADS_COUNT, i);
    }

    public static int getIsAdEnable() {
        return SharedPreferencesClass.getInstance().getInt(IS_AD_ENABLE, 0);
    }

    public static void setIsAdEnable(int key) {
        SharedPreferencesClass.getInstance().setInt(IS_AD_ENABLE, key);
    }

    public static String getAdType() {
        return SharedPreferencesClass.getInstance().getString(AD_TYPE, "");
    }

    public static void setAdType(String key) {
        SharedPreferencesClass.getInstance().setString(AD_TYPE, key);
    }

    public static int getId() {
        return SharedPreferencesClass.getInstance().getInt(ID, 0);
    }

    public static void setId(int key) {
        SharedPreferencesClass.getInstance().setInt(ID, key);
    }


    public static int getInterstitialAdsClick() {
        return SharedPreferencesClass.getInstance().getInt(INTERSTITIAL_ADS_CLICK, 0);
    }

    public static void setInterstitialAdsClick(int i) {
        SharedPreferencesClass.getInstance().setInt(INTERSTITIAL_ADS_CLICK, i);
    }

    public static int getAdsPerSession() {
        return SharedPreferencesClass.getInstance().getInt(ADS_PER_SESSION, 0);
    }

    public static void setAdsPerSession(int i) {
        SharedPreferencesClass.getInstance().setInt(ADS_PER_SESSION, i);
    }

    public static int getShowAppOpen() {
        return SharedPreferencesClass.getInstance().getInt(SHOW_APP_OPEN, 0);
    }

    public static void setShowAppOpen(int key) {
        SharedPreferencesClass.getInstance().setInt(SHOW_APP_OPEN, key);
    }

    public static int getAppOpenCount() {
        return SharedPreferencesClass.getInstance().getInt(APP_OPEN_COUNT, 0);
    }

    public static void setAppOpenCount(int key) {
        SharedPreferencesClass.getInstance().setInt(APP_OPEN_COUNT, key);
    }

    public static int getShowBanner() {
        return SharedPreferencesClass.getInstance().getInt(SHOW_BANNER, 0);
    }

    public static void setShowBanner(int key) {
        SharedPreferencesClass.getInstance().setInt(SHOW_BANNER, key);
    }

    public static int getShowInterstitial() {
        return SharedPreferencesClass.getInstance().getInt(SHOW_INTERSTITIAL, 0);
    }

    public static void setShowInterstitial(int key) {
        SharedPreferencesClass.getInstance().setInt(SHOW_INTERSTITIAL, key);
    }

    public static int getExitAdEnable() {
        return SharedPreferencesClass.getInstance().getInt(EXIT_AD_ENABLE, 0);
    }

    public static void setExitAdEnable(int key) {
        SharedPreferencesClass.getInstance().setInt(EXIT_AD_ENABLE, key);
    }

    public static int getShowNative() {
        return SharedPreferencesClass.getInstance().getInt(SHOW_NATIVE, 0);
    }

    public static void setShowNative(int key) {
        SharedPreferencesClass.getInstance().setInt(SHOW_NATIVE, key);
    }

    public static String getConfigData() {
        return SharedPreferencesClass.getInstance().getString(CONFIG_DATA, "");
    }

    public static void setConfigData(String key) {
        SharedPreferencesClass.getInstance().setString(CONFIG_DATA, key);
    }

    public static String getBannerAd() {
        return SharedPreferencesClass.getInstance().getString(BANNER_APP, "");
    }

    public static void setBannerAd(String key) {
        SharedPreferencesClass.getInstance().setString(BANNER_APP, key);
    }

    public static String getAppOpenAd() {
        return SharedPreferencesClass.getInstance().getString(APP_OPEN_AD, "");
    }

    public static void setAppOpenAd(String key) {
        SharedPreferencesClass.getInstance().setString(APP_OPEN_AD, key);
    }

    public static String getInterstitialAd() {
        return SharedPreferencesClass.getInstance().getString(INTERSTITIAL_AD, "");
    }

    public static void setInterstitialAd(String key) {
        SharedPreferencesClass.getInstance().setString(INTERSTITIAL_AD, key);
    }


    public static int getIsSplashOn() {
        return SharedPreferencesClass.getInstance().getInt(IS_SPLASH_ON, 0);
    }

    public static void setIsSplashOn(int key) {
        SharedPreferencesClass.getInstance().setInt(IS_SPLASH_ON, key);
    }

    public static int getSplashTime() {
        return SharedPreferencesClass.getInstance().getInt(SPLASH_TIME, 0);
    }

    public static void setSplashTime(int key) {
        SharedPreferencesClass.getInstance().setInt(SPLASH_TIME, key);
    }

    public static int getOpenAdsShowedCount() {
        int type = SharedPreferencesClass.getInstance().getInt(OPENADS_SHOWED_COUNT, 1);
        return type;
    }

    public static void setOpenAdsShowedCount(int str) {
        SharedPreferencesClass.getInstance().setInt(OPENADS_SHOWED_COUNT, str);
    }

}