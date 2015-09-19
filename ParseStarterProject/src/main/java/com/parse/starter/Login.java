package com.parse.starter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class Login extends AppCompatActivity {
    public EditText et_login_username, et_login_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_login_username = (EditText)findViewById(R.id.et_login_username);
        et_login_password = (EditText)findViewById(R.id.et_login_password);
        final Button btn_login = (Button) findViewById(R.id.btn_login_activity);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validationError = false;
                StringBuilder validationErrorMessage = new StringBuilder("Please ");
                if (et_login_username.length() == 0) {
                    validationError = true;
                    validationErrorMessage.append("enter a valid username");
                }
                if (et_login_password.length() == 0) {
                    if(validationError){
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter a valid password");
                }
                if (validationError) {
                    Toast.makeText(Login.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                // setting up progres dialog

                final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                progressDialog.setTitle("Logging in..");
                progressDialog.setMessage("Please wait...logging in...");
                progressDialog.show();

                ParseUser user = new ParseUser();
                user.logInInBackground(et_login_username.getText().toString(), et_login_password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        progressDialog.dismiss();
                        if (e != null){
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, DispatchActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });

            }
        });

        Button btn_signup_from_login = (Button) findViewById(R.id.btn_signup_login_activity);
        btn_signup_from_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
