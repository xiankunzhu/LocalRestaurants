package com.localrestaurants;

import com.localrestaurants.model.NearbyPlaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Xiankun Zhu
 */

public interface PlacesInterface {

    @GET("nearbysearch/json")
    Call<NearbyPlaces> getPlaces(
            @Query("location") String location,
            @Query("radius") int radius,
            @Query("type") String type,
            @Query("key") String key);

    @GET("nearbysearch/json")
    Call<NearbyPlaces> getPlacesWithQuery(
            @Query("query") String type,
            @Query("key") String key);

}
