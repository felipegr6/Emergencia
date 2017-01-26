package br.com.fgr.emergencia.utils;

import br.com.fgr.emergencia.models.directions.DirectionResponse;
import br.com.fgr.emergencia.models.distancematrix.DistanceMatrixResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleServices {

    @GET("directions/json") Call<DirectionResponse> directions(@Query("origin") String origem,
        @Query("destination") String destino, @Query("language") String lingua,
        @Query("sensor") boolean sensor, @Query("mode") String modo);

    @GET("distancematrix/json") Call<DistanceMatrixResponse> matrix(@Query("origins") String origem,
        @Query("destinations") String destinos, @Query("mode") String modo,
        @Query("language") String lingua, @Query("sensor") boolean sensor);
}
