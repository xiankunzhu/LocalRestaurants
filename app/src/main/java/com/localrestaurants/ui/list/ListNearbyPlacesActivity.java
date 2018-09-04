package com.localrestaurants.ui.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.localrestaurants.R;
import com.localrestaurants.model.RestClient;
import com.localrestaurants.model.NearbyPlaces;
import com.localrestaurants.model.Result;
import com.localrestaurants.ui.search.ListNearbyAdapter;
import com.localrestaurants.util.ui.MaterialProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNearbyPlacesActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private ListNearbyAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MaterialProgressBar progressBar;

    private String lnglat = "";
    private String type;
    private int radius;
    private String apiKey;
    private String queryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nearby_places);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // values
            queryString = extras.getString("query");
            lnglat = extras.getString("lnglat");
        }

        type = "restaurant";
        radius = 500;

        // Get API KEY
        apiKey = getApplicationContext().getResources().getString(R.string.GEO_API_KEY);

        progressBar = (MaterialProgressBar) findViewById(R.id.material_progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.places_recyclerview);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        displayPlaces();

    }


    public void displayPlaces(){

        showLoading();
        Call<NearbyPlaces> call;

        // If latitude and longtitude is found, use the Coordinates. If not, use a direct query string

        if(!lnglat.equals("")) {

            call = RestClient.getInstance().getPlaces(
                    lnglat,
                    radius,
                    type,
                    apiKey
            );

        } else {

            call = RestClient.getInstance().getPlacesWithQuery(
                    queryString,
                    apiKey
            );

        }

        call.enqueue(new Callback<NearbyPlaces>() {
            @Override
            public void onResponse(Call<NearbyPlaces> call, Response<NearbyPlaces> response) {
                if (response.isSuccessful()) {
                    NearbyPlaces nearbyPlaces = response.body();
                    ArrayList<Result> nearbyPlacesList = new ArrayList<Result>(nearbyPlaces.getResults());

                    adapter = new ListNearbyAdapter(getApplicationContext(), nearbyPlacesList);
                    recyclerView.setAdapter(adapter);
                    hideLoading();

                } else {
                    // perform an action
                    hideLoading();
                }
            }

            @Override
            public void onFailure(Call<NearbyPlaces> call, Throwable t) {
                Log.d(TAG, t.getLocalizedMessage());
            }
        });

    }

    public void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading(){
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
