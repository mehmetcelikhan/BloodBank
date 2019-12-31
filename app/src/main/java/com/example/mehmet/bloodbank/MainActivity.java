package com.example.mehmet.bloodbank;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText EtAd, EtSoyad, EtTelefon, EtMail, EtPass;
    Button btnAdd, btnS, btnD, btnL;
    ListView lstVe;
    int SeciliDeger = -1;
    Spinner spinnerBloodGroup, spinnerCities;
    ProgressBar loading;
    String[] str;
    String[] cities;
    Person newPerson;
FirebaseDatabase db;
    int i ;
    int j ;
    FirebaseDatabase dbx;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    int RC_SIGN_IN = 1;

    int z=1;
    private void Listele() {
        VeriTabani vt = new VeriTabani(MainActivity.this);
        List<String> list = vt.VerileriListele();
        ArrayAdapter<String> adaptor = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
        lstVe.setAdapter(adaptor);
    }

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
                Intent intent=new Intent(this,Login.class);
                startActivity(intent);
            }
        }
        else if(resultCode==RESULT_CANCELED)
            finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EtAd = findViewById(R.id.etName);
        EtSoyad = findViewById(R.id.EtSirname);
        EtTelefon = findViewById(R.id.EtPhone);
        EtMail = findViewById(R.id.etMail);
        EtPass = findViewById(R.id.etPassword);

        btnAdd = findViewById(R.id.btnKayit);
        //btnS = findViewById(R.id.BtnSil);

        loading = findViewById(R.id.progressBar);
        db=FirebaseDatabase.getInstance();
        spinnerBloodGroup = findViewById(R.id.spnBloodGroup);
        spinnerCities = findViewById(R.id.spinnercity);

loading.setVisibility(View.INVISIBLE);
        str = new String[]{"Select Blood Group", "A+", "A-", "B+", "B-", "0+", "0-", "AB+", "AB-"};
        cities = new String[]{"Select Your City", "Adana", "Adıyaman", "Afyon", "Ağrı", "Aksaray", "Amasya", "Ankara", "Antalya", "Ardahan", "Artvin", "Aydın", "Bartın", "Batman", "Balıkesir", "Bayburt", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Düzce", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Iğdır", "Isparta", "İçel", "İstanbul", "İzmir", "Karabük", "Karaman", "Kars", "Kastamonu", "Kayseri", "Kırıkkale", "Kırklareli", "Kırşehir", "Kilis", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Osmaniye", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Şırnak", "Uşak", "Van", "Yalova", "Yozgat", "Zonguldak"};
        ArrayAdapter<String> BloodGroupadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, str);
        ArrayAdapter<String> citiesadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities);
        spinnerBloodGroup.setAdapter(BloodGroupadapter);
        spinnerCities.setAdapter(citiesadapter);
        dbx=FirebaseDatabase.getInstance();
        mFirebaseAuth=FirebaseAuth.getInstance();

        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null)
                    Toast.makeText(MainActivity.this, "Giris Yaptin", Toast.LENGTH_SHORT).show();
                else
                {

                }
            }
        };
        spinnerBloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                i = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                j = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                newPerson = Person.GetInstance();
                newPerson.name = EtAd.getText().toString();
                newPerson.surName = EtSoyad.getText().toString();
                newPerson.phoneNumber = EtTelefon.getText().toString();
                newPerson.e_mail = EtMail.getText().toString();
                newPerson.passWord = EtPass.getText().toString();


                newPerson.bloodGroup = str[i].toString();
                newPerson.city = cities[j].toString();
                if (newPerson.name.isEmpty() || newPerson.surName.isEmpty() || newPerson.phoneNumber.isEmpty()||i==0||j==0 || newPerson.e_mail.isEmpty() || newPerson.passWord .isEmpty() && newPerson.e_mail.isEmpty()) {
                    EtAd.setHint("This area not be EMPTY!!!"+"Name");
                    EtSoyad.setHint("This area not be EMPTY!!!"+"Sure Name");
                    EtTelefon.setHint("This area not be EMPTY!!!"+"Phone Number");
                    EtPass.setHint("This area not be EMPTY!!!(Length Must be at least 6)"+"Password");
                    EtMail.setHint("This area not be EMPTY!!!"+"E Mail");

                        Toast.makeText(MainActivity.this, "Please Select Blood Group", Toast.LENGTH_LONG).show();

                        Toast.makeText(MainActivity.this, "Please Select City", Toast.LENGTH_SHORT).show();


                }
              else{
                DatabaseReference dbtaken = db.getReference("Users");

                dbtaken.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot coming : dataSnapshot.getChildren()) {
                            if (EtMail.getText().toString().equals(coming.getValue(Person.class).getE_mail())) {
                                z = 0;
                            } else {

                            }

                        }
                        try {
                            if (z == 0) {

                                Toast.makeText(MainActivity.this, "This Email Already Taken!", Toast.LENGTH_SHORT).show();
                                z = 1;
                            } else {
                                userAdd(newPerson.name, newPerson.surName, newPerson.phoneNumber, newPerson.e_mail, newPerson.passWord, newPerson.bloodGroup, newPerson.city);

                                Toast.makeText(MainActivity.this, "Registration Completed!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MainActivity.this, Login.class);
                                loading.setVisibility(View.VISIBLE);
                                startActivity(i);

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });

            }


            }
        });



    }
    private  void yapilacakEkle(String yapilacak)
    {
DatabaseReference dbRef=db.getReference("yapilacaklar");
String key=dbRef.push().getKey();
DatabaseReference dbRefYeni=db.getReference("yapilacaklar/"+key);
        dbRefYeni.setValue(yapilacak);
    }
private  void yapilacaklarGor()
{
DatabaseReference okuma=db.getReference() ;
okuma.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        EtMail.setText("");
        Iterable<DataSnapshot> keys=dataSnapshot.getChildren();
        for (DataSnapshot key: keys)
        {

           EtMail.append(key.getValue().toString()+"\n");
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
}
private  void userAdd(String Ad, String SoyAd, String TelefonNo, String Email, String Pass, String Bgroup,String City)
{
    DatabaseReference dbRef=db.getReference("Users");
    String key=dbRef.push().getKey();
    String[] BolunmusElemanlar = Email.split("@");
    DatabaseReference dbRefKeyli=db.getReference("Users/"+BolunmusElemanlar[0]);
    dbRefKeyli.setValue(newPerson);
}

}
