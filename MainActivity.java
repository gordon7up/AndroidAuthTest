package com.haptix.authtest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.haptix.authtest.api.Api;
import com.haptix.authtest.api.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;
    private Button mBtnLogin, mBtnTestApi;
    public static Context appContext;

    public static final String TAG = MainActivity.class.getSimpleName();

    ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appContext = getApplicationContext();

        api = Api.getInstance().getApiService();//Create an API instance.

        //Setup UI widgets...
        setupUi();
    }

    /**
     * Register the simple UI elements
     */
    private void setupUi(){
        mUsername = (EditText) findViewById(R.id.et_username);
        mPassword = (EditText) findViewById(R.id.et_password);
        mBtnLogin = (Button)findViewById(R.id.btn_login);
        mBtnTestApi = (Button)findViewById(R.id.btn_test);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    /**
     * simple helper to retrieve user input from edit text field.
     * @param et
     * @return
     */
    private String getInput(EditText et){
        String userInput = et.getText().toString().trim();

        return userInput;
    }

    private void login(){
        String user = getInput(mUsername);
        String pass = getInput(mPassword);

        api.addUser(user, "tester", "tester7@testy.com", "0878923234", pass).enqueue(userCreateCallback);
        //login to api via retrofit.
    }

    /**
     * Retrofit 2 callback handling the getMarkets request.
     */
    private Callback<ResponseBody> userCreateCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if(response.isSuccessful()){
                Toast.makeText(appContext, "Successful registration", Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jObj = new JSONObject(response.body().string());
                    Utilities.saveToken(jObj.getString("token"), appContext);
                    Toast.makeText(appContext, jObj.getString("token"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else{
                Log.d(TAG, "User Callback: " + response.code() + " Message:" + response.message());
                Log.d(TAG, "CALL: " + call.request().url());
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.d(TAG, "CALLBACK FAIL:");
            t.printStackTrace();
        }
    };


}
