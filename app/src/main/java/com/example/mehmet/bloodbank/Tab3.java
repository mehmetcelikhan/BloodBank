package com.example.mehmet.bloodbank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class Tab3 extends Fragment {

Button editUpdate;
EditText name,surname,phonenumber,email,password;
Spinner city;
    DatabaseReference dbtaken;
    FirebaseDatabase db;
    Person instance;
    String Email;
    String[] cities;
    int pos;
    String SelectedCity;
    Person editedPerson;
    Person person;
    int cityPos;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       db=FirebaseDatabase.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final View view=inflater.inflate(R.layout.fragment_tab3, container, false);
       name=view.findViewById(R.id.etName);
        surname=view.findViewById(R.id.etSurname);
        phonenumber=view.findViewById(R.id.etPhoneNumber);
        email=view.findViewById(R.id.etMail);
       password=view.findViewById(R.id.etPassword);
        cities = new String[]{"Select Your City", "Adana", "Adıyaman", "Afyon", "Ağrı", "Aksaray", "Amasya", "Ankara", "Antalya", "Ardahan", "Artvin", "Aydın", "Bartın", "Batman", "Balıkesir", "Bayburt", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Düzce", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Iğdır", "Isparta", "İçel", "İstanbul", "İzmir", "Karabük", "Karaman", "Kars", "Kastamonu", "Kayseri", "Kırıkkale", "Kırklareli", "Kırşehir", "Kilis", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Osmaniye", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Şırnak", "Uşak", "Van", "Yalova", "Yozgat", "Zonguldak"};
        ArrayAdapter<String> citiesadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, cities);
     city=(Spinner)view.findViewById(R.id.spinCity);
     city.setAdapter(citiesadapter);
     editUpdate=view.findViewById(R.id.btnEdit);
     email.setEnabled(false);
     editUpdate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String[] BolunmusElemanlar = Email.split("@");
             DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(BolunmusElemanlar[0].toString());
             Map<String, Object> updates = new HashMap<String,Object>();


//etc



                     editedPerson=instance;
                     editedPerson.name=name.getText().toString();
                     editedPerson.surName=surname.getText().toString();
                     editedPerson.phoneNumber=phonenumber.getText().toString();
                     editedPerson.e_mail=email.getText().toString();
                     editedPerson.passWord=password.getText().toString();
                     editedPerson.city=cities[cityPos].toString();
             updates.put("name", editedPerson.name);
             updates.put("surName", editedPerson.surName);
             updates.put("phoneNumber", editedPerson.phoneNumber);
             updates.put("e_mail", editedPerson.e_mail);
             updates.put("passWord", editedPerson.passWord);
              updates.put("city",editedPerson.city);
             ref.updateChildren(updates);

             Toast.makeText(view.getContext(), "Edited Completed", Toast.LENGTH_SHORT).show();



         }
     });
     city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             cityPos=position;
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
     });
      try {

          person=Person.GetInstance();
         Email= person.e_mail;
      }
        catch (Exception e)
        {
            Toast.makeText(this.getActivity(), "Lab3", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

     /*   Intent intent = ((Activity) getContext()).getIntent();
        Email= intent.getExtras().getString("veri");*/
    dataTaken();

        return view;
    }
   private void dataTaken()
    {

         dbtaken=db.getReference("Users");

        dbtaken.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                for (DataSnapshot coming:dataSnapshot.getChildren()) {
       if(Email.equals(coming.getValue(Person.class).getE_mail())){
                    instance=coming.getValue(Person.class);
                      SelectedCity=instance.city;

                    }
                }

                name.setText(instance.name);
                surname.setText(instance.surName);
                password.setText(instance.passWord);
                email.setText(instance.e_mail);
                phonenumber.setText(instance.phoneNumber);
                for (int i = 0; i < cities.length; i++) {
                   if( cities[i].equals(SelectedCity))
                       pos=i;
                }
                city.setSelection(pos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });



    }

}
