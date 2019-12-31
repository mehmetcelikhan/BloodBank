package com.example.mehmet.bloodbank;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class Tab2 extends Fragment  {
 /*   // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab2() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
*/  FirebaseDatabase db;
    String[] str;
    Spinner bloodGroup;
    String bGroup;
    ListView lvBloodCount;
    ArrayList<String> Count;
    int pos=0;
    int Aplus,Aminus,Bplus,Bminus,Oplus,Ominus,ABplus,ABminus;
    ArrayAdapter<String> ArrayABloodCount;
    Button sendReq;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=FirebaseDatabase.getInstance();
        ActivityCompat.requestPermissions(this.getActivity(),new String[]{Manifest.permission.SEND_SMS},1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

   View view= inflater.inflate(R.layout.fragment_tab2, container, false);
        str = new String[]{"You can Request Donators with SMS", "A+", "A-", "B+", "B-", "0+", "0-", "AB+", "AB-"};
        ArrayAdapter<String> BloodGroupadapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, str);
        bloodGroup=view.findViewById(R.id.spinSelectBlood);
        bloodGroup.setAdapter(BloodGroupadapter);
        lvBloodCount=view.findViewById(R.id.lvbloodCount);
        Count=new ArrayList<String>();
        sendReq=view.findViewById(R.id.btnSendRequest);
        sendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });
        dataTaken();
bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        pos=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
   return view;
    }
    private void dataTaken()
    {
        ArrayABloodCount = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, Count);

        DatabaseReference dbtaken=db.getReference("Users");

        dbtaken.addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange( DataSnapshot dataSnapshot) {
                                                       for (DataSnapshot coming:dataSnapshot.getChildren()) {
                                                           bGroup=coming.getValue(Person.class).getBloodGroup();
                                                               if (bGroup.equals("A+")) {
                                                                   Aplus++;
                                                               }
                                                           else if (bGroup.equals("A-")) {
                                                               Aminus++;
                                                           } else if (bGroup.equals("B+")) {
                                                               Bplus++;
                                                           } else if (bGroup.equals("B-")) {
                                                               Bminus++;
                                                           } else if (bGroup.equals("0+")) {
                                                               Oplus++;
                                                           } else if (bGroup.equals("0-")) {
                                                               Ominus++;
                                                           } else if (bGroup.equals("AB+")) {
                                                               ABplus++;
                                                           } else if (bGroup.equals("AB-")) {
                                                               ABminus++;
                                                           }


                                                       }

                                                       Count.add("A+  :"+String.valueOf(Aplus) );
                                                       Count.add("A-  :"+String.valueOf(Aminus) );
                                                       Count.add("B+  :"+String.valueOf(Bplus) );
                                                       Count.add("B-  :"+String.valueOf(Bminus) );
                                                       Count.add("0+  :"+String.valueOf(Oplus) );
                                                       Count.add("0-  :"+String.valueOf(Ominus) );
                                                       Count.add("AB+ :"+String.valueOf(ABplus) );
                                                       Count.add("AB- :"+String.valueOf(ABminus) );

                                                       lvBloodCount.setAdapter(ArrayABloodCount);
                                                   }

                                                   @Override
                                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                                   }


                                               });



    }
    private  void sendSms(){
    ArrayABloodCount = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, Count);

    DatabaseReference dbtaken=db.getReference("Users");

        dbtaken.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange( DataSnapshot dataSnapshot) {
            for (DataSnapshot coming:dataSnapshot.getChildren()) {
                bGroup=coming.getValue(Person.class).getBloodGroup();
                if (str[pos].equals("A+")&&str[pos].equals(bGroup)) {
                 sendSMS(coming.getValue(Person.class).getPhoneNumber(),"Could you donation blood group:"+bGroup);
                }
                else if (str[pos].equals("A-")&&str[pos].equals(bGroup)) {
                    sendSMS(coming.getValue(Person.class).getPhoneNumber(),"Could you donation blood group:"+bGroup);
                } else if (str[pos].equals("B+")&&str[pos].equals(bGroup)) {
                    sendSMS(coming.getValue(Person.class).getPhoneNumber(),"Could you donation blood group:"+bGroup);
                } else if (str[pos].equals("B-")&&str[pos].equals(bGroup)) {
                    sendSMS(coming.getValue(Person.class).getPhoneNumber(),"Could you donation blood group:"+bGroup);
                } else if (str[pos].equals("0+")&&str[pos].equals(bGroup)) {
                    sendSMS(coming.getValue(Person.class).getPhoneNumber(),"Could you donation blood group:"+bGroup);
                } else if (str[pos].equals("0-")&&str[pos].equals(bGroup)) {
                    sendSMS(coming.getValue(Person.class).getPhoneNumber(),"Could you donation blood group:"+bGroup);
                } else if (str[pos].equals("AB+")&&str[pos].equals(bGroup)) {
                    sendSMS(coming.getValue(Person.class).getPhoneNumber(),"Could you donation blood group:"+bGroup);
                } else if (str[pos].equals("AB-")&&str[pos].equals(bGroup)) {
                    sendSMS(coming.getValue(Person.class).getPhoneNumber(),"Could you donation blood group:"+bGroup);
                }

                                                             /*  Person instance;
                                                               instance=coming.getValue(Person.class);
                                                               String Information=instance.toString();
                                                               Users.add(Information);*/





            }




        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }


    });



}
    public  void sendSMS(String phoneNumber, String message)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(this.getActivity(), "Sms SENT", Toast.LENGTH_SHORT).show();
    }
  /*  // TODO: Rename method, update argument and hook method into UI event
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
