package com.alexisholyoak.faustinorest;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FaustinoDetailActivity extends AppCompatActivity {

    protected static final String USUARIO="usuario";
    protected static final String CONTRASENA="contrasena";
    protected static final String FREGISTRO="fregistro";
    protected static final String ESTADO="estado";
    private final String baseURL="http://192.168.1.62:8099/unjfsc/";
    EditText inputUsuario,inputContrasena,inputEstado,inputFecha;
    Button btnActualizar,btnEliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faustino_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final String usuario=getIntent().getStringExtra(USUARIO);
        String contrasena=getIntent().getStringExtra(CONTRASENA);
        String fregistro=getIntent().getStringExtra(FREGISTRO);
        int estado=getIntent().getIntExtra(ESTADO,0);

        inputUsuario=(EditText)findViewById(R.id.inputUsuario);
        inputContrasena=(EditText)findViewById(R.id.inputPassword);
        inputEstado=(EditText)findViewById(R.id.inputEstado);
        inputFecha=(EditText)findViewById(R.id.inputFecha);

        btnActualizar=(Button)findViewById(R.id.btnActualizar);
        btnEliminar=(Button)findViewById(R.id.btnEliminar);

        Retrofit retrofit=new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final FaustinoService faustinoService=retrofit.create(FaustinoService.class);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> deleteRequest=faustinoService.deleteById(usuario);
                deleteRequest.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent intent=new Intent(getApplication(),MainActivity.class);
                        intent.putExtra("deleteresponse",response.toString());
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        inputFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Faustino faustinoUpdated=new Faustino();
                faustinoUpdated.setFRegistro(inputFecha.getText().toString());
                faustinoUpdated.setEstado(Integer.parseInt(inputEstado.getText().toString()));
                faustinoUpdated.setContrasena(inputContrasena.getText().toString());
                Call<ResponseBody> updateRequest=faustinoService.updateById(usuario,faustinoUpdated);
                updateRequest.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent intent=new Intent(getApplication(),MainActivity.class);
                        intent.putExtra("updateresponse",response.toString());
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        inputUsuario.setText(usuario);
        inputFecha.setText(fregistro);
        inputContrasena.setText(contrasena);
        inputEstado.setText(String.valueOf(estado));
    }
    /**DATE PICKER ACTIONS**/
    private void showDatePicker(){
        DatePickerFragment date=new DatePickerFragment();
        /*
         * Set up Current Date in Dialog
         */
        Calendar calendar=Calendar.getInstance();
        Bundle args=new Bundle();
        args.putInt("year",calendar.get(Calendar.YEAR));
        args.putInt("month",calendar.get(Calendar.MONTH));
        args.putInt("day",calendar.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /*
         * Ser CallBack to capture selected Data
         */
        date.setCallBack(ondate);
        date.show(getSupportFragmentManager(),"Date picker");
    }
    DatePickerDialog.OnDateSetListener ondate=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            inputFecha.setText(String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth+1));
        }
    };
}
