package com.example.pokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.pokemon.models.Pokemon;
import com.example.pokemon.models.PokemonRespuesta;
import com.example.pokemon.pokeapi.PokeapiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= findViewById(R.id.recyclerView);
        listaPokemonAdapter= new ListaPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        retrofit= new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerDatos();
    }
    private void obtenerDatos(){
        PokeapiService service= retrofit.create((PokeapiService.class));
        Call<PokemonRespuesta> pokemonRespuestaCall = service.obtenerListaPokemon();

        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                 if(response.isSuccessful()){
                     PokemonRespuesta pokemonRespuesta = response.body();
                     ArrayList<Pokemon> listaPokemon = pokemonRespuesta.getResults();

                     listaPokemonAdapter.adicionarListaPokemon(listaPokemon);
                     /*for(int i=0; i< listaPokemon.size(); i++){
                         Pokemon p = listaPokemon.get(i);
                         Log.i(TAG, "Pokemon: "+ p.getName());
                     }*/
                 }else{
                     Log.e(TAG, "onResponse: " +response.errorBody());

                 }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                Log.e(TAG, "OnFailure: " +t.getMessage());

            }
        });
    }
}