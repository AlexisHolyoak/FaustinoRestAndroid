package com.alexisholyoak.faustinorest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvFaustino;
    private final String baseURL="http://192.168.1.62:8099/unjfsc/";
    List<Faustino> faustinoList=new ArrayList<>();
    FaustinoAdapter adapter;

    Button btnRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit=new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        rvFaustino=(RecyclerView)findViewById(R.id.rvFaustino);
        rvFaustino.setLayoutManager(new LinearLayoutManager(this));
        btnRegistro=(Button)findViewById(R.id.btnNuevo);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getApplication(),FaustinoRegisterActivity.class);
                startActivity(intent);
            }
        });
        final FaustinoService faustinoService=retrofit.create(FaustinoService.class);
        if(this.getIntent().getExtras()!=null){
            if(this.getIntent().getStringExtra("deleteresponse")!=null){
                Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
            }
            if(this.getIntent().getStringExtra("updateresponse")!=null){
                Toast.makeText(this, "Registro actualizado", Toast.LENGTH_SHORT).show();
            }
            if(this.getIntent().getStringExtra("postresponse")!=null){
                Toast.makeText(this, "Registro creado", Toast.LENGTH_SHORT).show();
            }
        }
        Call<List<Faustino>> lista = faustinoService.getAll();
        lista.enqueue(new Callback<List<Faustino>>() {
            @Override
            public void onResponse(Call<List<Faustino>> call, Response<List<Faustino>> response) {
                if(response.isSuccessful()){
                    faustinoList=response.body();
                    adapter=new FaustinoAdapter(getApplication(),faustinoList);
                    rvFaustino.setAdapter(adapter);
                    adapter.setOnEntryClickListener(new FaustinoAdapter.OnEntryClickListener() {
                        @Override
                        public void onEntryClick(View view, int position) {
                            Intent intent=new Intent();
                            intent.putExtra("usuario",faustinoList.get(position).getIdUsuario());
                            intent.putExtra("contrasena",faustinoList.get(position).getContrasena());
                            intent.putExtra("fregistro",faustinoList.get(position).getFRegistro());
                            intent.putExtra("estado",faustinoList.get(position).getEstado());
                            intent.setClass(getApplication(),FaustinoDetailActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Faustino>> call, Throwable t) {

            }
        });

    }
}
