package br.com.fgr.emergencia.models.directions;

public class TempoEstimado {

    private String value;
    private String texto;
    private String timezone;

    public TempoEstimado(String value, String texto, String timezone) {
        this.value = value;
        this.texto = texto;
        this.timezone = timezone;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}