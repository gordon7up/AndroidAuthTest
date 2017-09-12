package com.haptix.authtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;
    private Button mBtnLogin, mBtnTestApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


}
