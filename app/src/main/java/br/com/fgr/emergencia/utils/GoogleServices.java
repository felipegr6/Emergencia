package br.com.fgr.emergencia.utils;

import br.com.fgr.emergencia.models.directions.DirectionResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface GoogleServices {

    @GET("/directions/json")
    public void directions(@Query("origin") String origem, @Query("destination") String destino,
                           Callback<DirectionResponse> cb);

    public void matrix();

}