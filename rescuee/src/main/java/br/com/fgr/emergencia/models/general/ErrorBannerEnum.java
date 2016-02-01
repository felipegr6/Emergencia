package br.com.fgr.emergencia.models.general;

public enum ErrorBannerEnum {

    BANNER_LOADED("bannerLoaded"), BANNER_FAILED("bannerFailed"), BANNER_CLICKED("bannerClicked"),
    BANNER_EXPANDED("bannerExpanded"), BANNER_COLLAPSED("bannerCollapsed");

    private String errorBannerType;

    ErrorBannerEnum(String errorBannerType) {
        this.errorBannerType = errorBannerType;
    }

    public String getErrorBannerType() {
        return errorBannerType;
    }

}