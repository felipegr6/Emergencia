package br.com.fgr.emergencia.utils;

import br.com.fgr.emergencia.models.directions.DirectionResponse;
import br.com.fgr.emergencia.models.distancematrix.DistanceMatrixResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface GoogleServices {

    @GET("/directions/json")
    void directions(@Query("origin") String origem, @Query("destination") String destino,
                    @Query("language") String lingua, @Query("sensor") boolean sensor,
                    Callback<DirectionResponse> cb);

    @GET("/distancematrix/json")
    void matrix(@Query("origins") String origem, @Query("destinations") String destinos,
                @Query("mode") String modo, @Query("language") String lingua,
                @Query("sensor") boolean sensor, Callback<DistanceMatrixResponse> cb);

}