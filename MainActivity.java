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
    private EditText mFirstName, mSurname, mPassword, mMobile, mEmail;
    private Button mRegister, mBtnTestApi, mbtnClear;
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
        mFirstName = (EditText) findViewById(R.id.et_first_name);
        mSurname = (EditText)findViewById(R.id.et_last_name);
        mEmail = (EditText)findViewById(R.id.et_email);
        mMobile = (EditText)findViewById(R.id.et_mobile);
        mPassword = (EditText) findViewById(R.id.et_password);
        mRegister = (Button)findViewById(R.id.btn_register);
        mBtnTestApi = (Button)findViewById(R.id.btn_test);
        mbtnClear = (Button)findViewById(R.id.btn_clear);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        mBtnTestApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testApi();
            }
        });

        mbtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearPrefToken();
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
        String firstName = getInput(mFirstName);
        String lastName = getInput(mSurname);
        String email = getInput(mEmail);
        String mobile = getInput(mMobile);
        String pass = getInput(mPassword);

        api.addUser(firstName, lastName, email, mobile, pass).enqueue(userCreateCallback);
    }

    private void testApi(){
        api.getTest().enqueue(authTestCallback);
    }

    private void clearPrefToken(){
        Utilities.saveToken("", appContext);
    }

    /**
     * Retrofit 2 callback handling new user, if successful a auth token is
     * returned.
     */
    private Callback<ResponseBody> userCreateCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if(response.isSuccessful()){
                Toast.makeText(appContext, "Successful registration", Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jObj = new JSONObject(response.body().string());
                    Utilities.saveToken("token " + jObj.getString("token"), appContext);
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


    private Callback<ResponseBody> authTestCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if(response.isSuccessful()){
                Toast.makeText(appContext, "Successfully hit API!!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(appContext, "Not authorised or error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "User Callback: " + response.code() + " Message:" + response.message());
                Log.d(TAG, "Headers " + call.request().headers());

            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.d(TAG, "CALLBACK FAIL:");
            t.printStackTrace();
        }
    };


}
