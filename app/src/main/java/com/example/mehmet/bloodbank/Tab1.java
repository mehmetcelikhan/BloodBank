package com.example.mehmet.bloodbank;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.PeriodicSync;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Tab1 extends Fragment {
  /*  // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     *//*
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
*/String[] str;
Spinner bloodGroup;
ListView lvSearch;
String bGroup;
int pos=0;
ArrayList<String> Users;
    View view;
    ArrayAdapter<String> lvBloodGroupadapter;
    FirebaseDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=FirebaseDatabase.getInstance();

        }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_tab1, container, false);
                str = new String[]{"Select Blood Group", "A+", "A-", "B+", "B-", "0+", "0-", "AB+", "AB-"};
        ArrayAdapter<String> BloodGroupadapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, str);
        bloodGroup=view.findViewById(R.id.spinnerBlood);
        lvSearch=view.findViewById(R.id.lvSearchDonor);


Users=new ArrayList<String>();
        BloodGroupadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        bloodGroup.setAdapter(BloodGroupadapter);

        bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                if(position!=0) {
                    dataTake();
                    lvBloodGroupadapter.clear();
                    Users.clear();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private void dataTake()
    {lvBloodGroupadapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, Users);
        DatabaseReference dbtaken=db.getReference("Users");
        bGroup=str[pos].toString();
        dbtaken.addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange( DataSnapshot dataSnapshot) {
                                                       for (DataSnapshot coming:dataSnapshot.getChildren()) {
                                                           if(bGroup.equals(coming.getValue(Person.class).getBloodGroup()))
                                                           {
                                                               Person instance;
                                                               instance=coming.getValue(Person.class);
                                                               String Information=instance.toString();
                                                               Users.add(Information);

                                                           }



                                                       }



                                                      lvSearch.setAdapter(lvBloodGroupadapter);


                                                   }

                                                   @Override
                                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                                   }


                                               }

        );
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String SeciliItem = lvSearch.getItemAtPosition(i).toString();
                String[] BolunmusElemanlar = SeciliItem.split(" ");
                String phoneNumber=BolunmusElemanlar[2];
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
            }
        });

        lvBloodGroupadapter.clear();
        Users.clear();
    }
    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
