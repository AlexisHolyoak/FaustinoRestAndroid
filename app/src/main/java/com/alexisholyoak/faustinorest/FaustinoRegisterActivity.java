package com.alexisholyoak.faustinorest;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FaustinoRegisterActivity extends AppCompatActivity {
    EditText inputUsuario,inputContrasena,inputFecha,inputEstado;
    Button btnRegistrar;
    private final String baseURL="http://192.168.1.62:8099/unjfsc/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faustino_register);
        inputContrasena=(EditText)findViewById(R.id.inputPasswordR);
        inputEstado=(EditText)findViewById(R.id.inputEstadoR);
        inputFecha=(EditText)findViewById(R.id.inputFechaR);
        inputUsuario=(EditText)findViewById(R.id.inputUsuarioR);
        btnRegistrar=(Button)findViewById(R.id.btnRegistrar);

        Retrofit retrofit=new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final FaustinoService faustinoService=retrofit.create(FaustinoService.class);
        inputFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Faustino faustinoCreated=new Faustino();
                faustinoCreated.setIdUsuario(inputUsuario.getText().toString());
                faustinoCreated.setFRegistro(inputFecha.getText().toString());
                faustinoCreated.setEstado(Integer.parseInt(inputEstado.getText().toString()));
                faustinoCreated.setContrasena(inputContrasena.getText().toString());
                Call<ResponseBody> request = faustinoService.postNew(faustinoCreated);
                request.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent intent=new Intent(getApplication(),MainActivity.class);
                        intent.putExtra("postresponse",response.toString());
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
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
