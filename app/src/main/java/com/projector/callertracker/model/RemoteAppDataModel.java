package com.projector.callertracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RemoteAppDataModel {

    @SerializedName("nativeids")
    @Expose
    private ArrayList<String> nativeids;

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("bannerid")
    @Expose
    private String bannerid;
    @SerializedName("banner")
    @Expose
    private Integer banner;
    @SerializedName("native")
    @Expose
    private Integer _native;
    @SerializedName("interstitialid")
    @Expose
    private String interstitialid;
    @SerializedName("interstitial")
    @Expose
    private Integer interstitial;
    @SerializedName("openadid")
    @Expose
    private String openadid;
    @SerializedName("openad")
    @Expose
    private Integer openad;


    @SerializedName("adtype")
    @Expose
    private String adtype;
    @SerializedName("is_ad_enable")
    @Expose
    private Integer isAdEnable;

    @SerializedName("ads_per_click")
    @Expose
    private Integer ads_per_click;
    @SerializedName("exit_ad_enable")
    @Expose
    private Integer exit_ad_enable;
    @SerializedName("ads_per_session")
    @Expose
    private Integer ads_per_session;
    @SerializedName("app_open_count")
    @Expose
    private Integer app_open_count;
    @SerializedName("is_splash_on")
    @Expose
    private Integer is_splash_on;
    @SerializedName("splash_time")
    @Expose
    private Integer splash_time;


    public Integer get_native() {
        return _native;
    }

    public void set_native(Integer _native) {
        this._native = _native;
    }

    public Integer getExit_ad_enable() {
        return exit_ad_enable;
    }

    public void setExit_ad_enable(Integer exit_ad_enable) {
        this.exit_ad_enable = exit_ad_enable;
    }

    public Integer getAds_per_session() {
        return ads_per_session;
    }

    public void setAds_per_session(Integer ads_per_session) {
        this.ads_per_session = ads_per_session;
    }

    public Integer getAds_per_click() {
        return ads_per_click;
    }

    public void setAds_per_click(Integer ads_per_click) {
        this.ads_per_click = ads_per_click;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBannerid() {
        return bannerid;
    }

    public void setBannerid(String bannerid) {
        this.bannerid = bannerid;
    }

    public Integer getBanner() {
        return banner;
    }

    public void setBanner(Integer banner) {
        this.banner = banner;
    }

    public Integer getNative() {
        return _native;
    }

    public void setNative(Integer _native) {
        this._native = _native;
    }

    public String getInterstitialid() {
        return interstitialid;
    }

    public void setInterstitialid(String interstitialid) {
        this.interstitialid = interstitialid;
    }

    public Integer getInterstitial() {
        return interstitial;
    }

    public void setInterstitial(Integer interstitial) {
        this.interstitial = interstitial;
    }

    public String getOpenadid() {
        return openadid;
    }

    public void setOpenadid(String openadid) {
        this.openadid = openadid;
    }

    public Integer getOpenad() {
        return openad;
    }

    public void setOpenad(Integer openad) {
        this.openad = openad;
    }


    public String getAdtype() {
        return adtype;
    }

    public void setAdtype(String adtype) {
        this.adtype = adtype;
    }

    public Integer getIsAdEnable() {
        return isAdEnable;
    }

    public void setIsAdEnable(Integer isAdEnable) {
        this.isAdEnable = isAdEnable;
    }


    public Integer getApp_open_count() {
        return app_open_count;
    }

    public void setApp_open_count(Integer app_open_count) {
        this.app_open_count = app_open_count;
    }

    public Integer getIs_splash_on() {
        return is_splash_on;
    }

    public void setIs_splash_on(Integer is_splash_on) {
        this.is_splash_on = is_splash_on;
    }

    public Integer getSplash_time() {
        return splash_time;
    }

    public void setSplash_time(Integer splash_time) {
        this.splash_time = splash_time;
    }

    public ArrayList<String> getNativeid() {
        return nativeids;
    }

    public void setNativeid(ArrayList<String> nativeids) {
        this.nativeids = nativeids;
    }
}
