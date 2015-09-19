package com.parse.starter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Signup extends AppCompatActivity {
    EditText et_username;
    EditText et_password;
    Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // setting up sign up forms
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_signup = (Button) findViewById(R.id.btn_signup_1);
        btn_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean validationError = false;
                StringBuilder validationErrorMessage = new StringBuilder("Please ");
                if (et_username.length() == 0) {
                    validationError = true;
                    validationErrorMessage.append("enter a valid username");
                }
                if (et_password.length() == 0) {
                    if(validationError){
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter a valid password");
                }
                if (validationError) {
                    Toast.makeText(Signup.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                // setting up dialog box to start
                final ProgressDialog progressDialog = new ProgressDialog(Signup.this);
                progressDialog.setTitle("Please wait...");
                progressDialog.setMessage("Signing up...please wait...");
                progressDialog.show();

                // setting up user to sign up

                ParseUser parseUser = new ParseUser();
                parseUser.setUsername(et_username.getText().toString());
                parseUser.setPassword(et_password.getText().toString());

                // calling the sign up process of parse
                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        progressDialog.dismiss();
                        if (e != null){
                            Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }else{
                            Intent intent = new Intent(Signup.this, Login.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                    }
                });
            }
        });
        Button btn_login = (Button)findViewById(R.id.btn_signup_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this, Login.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
