package com.example.mehmet.bloodbank;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class Login extends AppCompatActivity {
FirebaseDatabase dbx;
FirebaseAuth mFirebaseAuth;
FirebaseAuth.AuthStateListener mAuthStateListener;
    Boolean State;
    FirebaseDatabase db;
    private static final int RC_SIGN_IN = 123;
    public String email;
    String password;
    EditText etEmail,etPassword;
    Button btnSignin;
    private static FragmentManager fragmentManager;
    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAuthStateListener!=null)
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==RC_SIGN_IN){
            if(resultCode==RESULT_OK )
            {

            }
            }
            else if(resultCode==RESULT_CANCELED)
                finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fragmentManager = getSupportFragmentManager();//Get Fragment Manager
        etEmail=(EditText) findViewById(R.id.Email);
        etPassword=(EditText)findViewById(R.id.Password);
        dbx=FirebaseDatabase.getInstance();
        db=FirebaseDatabase.getInstance();
        mFirebaseAuth=FirebaseAuth.getInstance();
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null)
                    Toast.makeText(Login.this, "Giris Yaptin", Toast.LENGTH_SHORT).show();
                else
                    {

                }

            }
        };

    }

    public void btnSign(View view) {
        DatabaseReference dbtaken=db.getReference("Users");
email=etEmail.getText().toString();
password=etPassword.getText().toString();
if(email.isEmpty()||password.isEmpty()||email.isEmpty()&&password.isEmpty())
{
  etEmail.setHint("This area not be EMPTY!!!");
  etPassword.setHint("This area not be EMPTY!!!");
}
  Person person=Person.GetInstance();
  person.e_mail=email.toString();
  person.passWord=password.toString();

 State=false;
        dbtaken.addListenerForSingleValueEvent(new ValueEventListener() {
                                          @Override
                                          public void onDataChange( DataSnapshot dataSnapshot) {
                                              for (DataSnapshot coming:dataSnapshot.getChildren()) {
                                                  if(email.equals(coming.getValue(Person.class).getE_mail())&&password.equals(coming.getValue(Person.class).getPassWord()))
                                                  {
                                                     Intent i=new Intent(Login.this,Main2Activity.class);
                                                      startActivity(i);
                                                    State=true;
                                                  }

                                              }
                                              AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                              builder.setTitle("Login Failed!");
                                              builder.setMessage("Email Or Password Wrong!");
                                              builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                  public void onClick(DialogInterface dialog, int id) {


                                                  }
                                              });

                                          if(State==false){
                                              builder.show();}
                                          }

                                          @Override
                                          public void onCancelled(@NonNull DatabaseError databaseError) {

                                          }


                                      }
        );
    }

    public void btnRegis(View view) {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }


}
