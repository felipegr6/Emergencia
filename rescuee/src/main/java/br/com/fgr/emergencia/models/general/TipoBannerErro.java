package br.com.fgr.emergencia.models.general;

public enum TipoBannerErro {

    BANNER_LOADED("bannerLoaded"), BANNER_FAILED("bannerFailed"), BANNER_CLICKED("bannerClicked"),
    BANNER_EXPANDED("bannerExpanded"), BANNER_COLLAPSED("bannerCollapsed");

    private String tipoBannerErro;

    TipoBannerErro(String tipoBannerErro) {
        this.tipoBannerErro = tipoBannerErro;
    }

    public String getTipoBannerErro() {
        return tipoBannerErro;
    }

}