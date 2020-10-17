package com.scienceboss.mylib;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.scienceboss.mylib.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LibraryContent {





    private CountDownTimer refresher;



    public void setAppname(Context context, String appname){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("3886PayStackOverflowAppName",appname);
        editor.apply();
    }

    public String getAppName(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String appname = pref.getString("3886PayStackOverflowAppName","");
        return appname;
    }


    public void setImei(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        if(pref.getString("timecheck","firsttime").equals("firsttime")){
            editor.putString("timecheck","secondtime");editor.apply();

            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                //return statement not added properly
            }
            String imex = manager.getDeviceId();

            editor.putString("3886PayStackOverflowImei",imex);
            editor.apply(); }


    }

    public String getImei(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String imei = pref.getString("3886PayStackOverflowImei","");
        return imei;
    }


    public void setNumber(Context context,String number){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("3886PayStackOverflowNumber",number);
        editor.apply();
    }

    public String getNumber(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String number = pref.getString("3886PayStackOverflowNumber","");
        return number;
    }











    public  Boolean makeRequest2(final Context context, String ussd, String serial, String amount, String Network, final Button send, final String appname, final String code){

        final Boolean[] sent = new Boolean[1];
        final String[] value2 = new String[1];
        sent[0] = false;

        setAppname(context,appname +"AQAWA" +code);
        String Appname = getAppName(context);
        String imei = getImei(context);

        final String airtime = ussd + "!!" + imei + "!!" + amount + "!!" + Network  + "!!" + Appname;
        final ArrayList<String> arra = new ArrayList<String>(Arrays.asList(airtime.replaceAll("\\s", "").split("!!")));
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef2 = database.getReference("airtimeRequestsPermanent");





        final DatabaseReference myRef = database.getReference("allusers").child(appname + "AQAWA" + code).child("USERBASEFOLDER").child(getImei(context));
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    //create new user
                    setNumbertoServer(context, appname + "AQAWA" + code);
                }else{
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        }; myRef.addListenerForSingleValueEvent(eventListener);
        ///////////////



        ValueEventListener eventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value2[0] = dataSnapshot.getValue(String.class);
                ArrayList<String> arrayForm = new ArrayList<String>(Arrays.asList(value2[0].replaceAll("\\s", "").split(",")));
                for(int i = 0; i < arrayForm.size(); i++){
                    String arrconv = arrayForm.get(i);
                    ArrayList<String> arr = new ArrayList<String>(Arrays.asList(arrconv.replaceAll("\\s", "").split("!!")));
                    if(arr.contains(arra.get(0)) &&  arr.get(2).equals(arra.get(2))     && arr.get(1).equals(arra.get(1)) ){
                        ShowDialog("","this code has already been used by you",context);
                        send.setText("send");
                        send.setEnabled(true);
                        return;
                    }

                    if(arr.contains(arra.get(0))   && arr.get(2).equals(arra.get(2))  &&    !arr.get(1).equals(arra.get(1))   ){
                        ShowDialog("","this code has been used by another customer",context);
                        send.setText("send");
                        send.setEnabled(true);
                        return;
                    }
                }

                final DatabaseReference myRef = database.getReference("airtimeRequests");
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value1 = dataSnapshot.getValue(String.class);
                        String newAddition = value1  + airtime + ",";
                        myRef.setValue(newAddition);
                        String newAdditionPPP = value2[0] + airtime + ",";
                        myRef2.setValue(newAdditionPPP);
                        Toast.makeText(context,"successful",Toast.LENGTH_SHORT).show();
                        send.setText("send");
                        send.setEnabled(true);
                        sent[0] = true;
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }; myRef.addListenerForSingleValueEvent(eventListener);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        }; myRef2.addListenerForSingleValueEvent(eventListener2);

        return sent[0];
    }














    public void setNumbertoServer(final Context context, String app) {

        ///add permission dialog for imei, compulsory
        ///add permission dialog for imei, compulsory
        ///add permission dialog for imei, compulsory

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("allusers").child(app).child("USERBASEFOLDER").child(getImei(context));
        final DatabaseReference myRefTODAY = database.getReference("allusersTODAY").child(app).child("USERBASEFOLDER").child(getImei(context));
        final DatabaseReference myRefTHISMONTH = database.getReference("allusersTHISMONTH").child(app).child("USERBASEFOLDER").child(getImei(context));

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                myRef.child("TRANSACTIONS").setValue("");

                myRefTODAY.child("TRANSACTIONS").setValue("");

                myRefTHISMONTH.child("TRANSACTIONS").setValue("");
                //      Toast.makeText(context,"successful",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        }; myRef.addListenerForSingleValueEvent(eventListener);
    }







    public void ShowDialog(String heading, String text, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(heading);
        builder.setMessage(text);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }











    public void setmessage(Context context,String message){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.receivedialog);
        dialog.show();
        final Button messageBox = dialog.findViewById(R.id.message);
        final TextView ok= dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        messageBox.setText(message);
    }



    public void options(final Context context, final String appname, final String code, final CountDownTimer timer){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choice);
        dialog.show();

        setImei(context);

        final Button send = dialog.findViewById(R.id.sendAirtime);
        final Button ID = dialog.findViewById(R.id.id);

        ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmessage(context, getImei(context));
            }
        });



        final Button receive = dialog.findViewById(R.id.receiveAirtime);
        TextView close = dialog.findViewById(R.id.close);
        final RecyclerView recycler = dialog.findViewById(R.id.recycler);
        final RelativeLayout MainLayout = dialog.findViewById(R.id.layoutMain);
        recycler.setVisibility(View.GONE);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                detailsDialog(context,appname,code);
            }
        });



        //  receive.setEnabled(false);
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = pref.edit();

        downloadMessage(context, appname + "AQAWA" + code);
        receive.setAlpha(0.5f);

        refresher = new CountDownTimer(100,100) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {

                //    receive.setEnabled(false);
                receive.setAlpha(0.5f);

                if(pref.getString("lookup","").isEmpty() || (pref.getString("lookup","") == "")){
                    //    receive.setEnabled(false);
                    receive.setAlpha(0.5f);
                    refresher.start();
                }
                if(!pref.getString("lookup","").isEmpty() || (pref.getString("lookup","") != "")){
                    receive.setEnabled(true);
                    receive.setBackgroundColor(Color.GREEN);
                    receive.setTextColor(Color.WHITE);
                    receive.setAlpha(1f);
                    refresher.cancel();
                }
            }
        };
        refresher.start();

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
                /*
                MyAdapter myAdapter = new MyAdapter(context,  pref.getString("lookup","").split("!!!"),recycler);
                recycler.setAdapter(myAdapter);
                recycler.setLayoutManager(new LinearLayoutManager(context));
                MainLayout.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                int amount = 0;
                String value1 = pref.getString("lookup","");

                Toast.makeText(context, value1, Toast.LENGTH_LONG).show();

                ArrayList<String> dataToList = new ArrayList<String>(Arrays.asList(value1.replaceAll("\\s", "").split("!!!")));
                for(int x = 0; x < dataToList.size(); x++){
                    String collect = dataToList.get(x);
                    ArrayList<String> data = new ArrayList<String>(Arrays.asList(collect.replaceAll("\\s", "").split(",")));
                    if(data.get(3).equals("invalid")){ amount = amount + 0; }
                    if(data.get(3).equals("valid")){  amount = amount + Integer.parseInt(data.get(0));  }
                }
                setmessage(context, "you have received a total of "  + amount +  " coins\n\nclick ok to see receipt");
                editor.putString("lookup","");editor.apply();
                reset(context);
                receive.setEnabled(false);
                refresher.cancel(); */
            }
        });
    }

    public int receiver(Context context){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choice);
        dialog.show();


        final RecyclerView recycler = dialog.findViewById(R.id.recycler);
        final RelativeLayout MainLayout = dialog.findViewById(R.id.layoutMain);
        recycler.setVisibility(View.GONE);
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = pref.edit();

        MyAdapter myAdapter = new MyAdapter(context,  pref.getString("lookup","").split("!!!"),recycler);
        recycler.setAdapter(myAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(context));
        MainLayout.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
        int amount = 0;

        String value1 = pref.getString("lookup","");

        Toast.makeText(context, value1, Toast.LENGTH_LONG).show();

        ArrayList<String> dataToList = new ArrayList<String>(Arrays.asList(value1.replaceAll("\\s", "").split("!!!")));
        for(int x = 0; x < dataToList.size(); x++){
            String collect = dataToList.get(x);
            ArrayList<String> data = new ArrayList<String>(Arrays.asList(collect.replaceAll("\\s", "").split(",")));
            if(data.get(3).equals("invalid")){ amount = amount + 0; }
            if(data.get(3).equals("valid")){  amount = amount + Integer.parseInt(data.get(0));  }
        }
        setmessage(context, "you have received a total of "  + amount +  " coins\n\nclick ok to see receipt");
        editor.putString("lookup","");editor.apply();
        reset(context);
        //  receive.setEnabled(false);
        refresher.cancel();

        return amount;
    }




    public  void detailsDialog(final Context context, final String appname, final String code){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.details);
        final EditText ussdcode = dialog.findViewById(R.id.ussdcode);
        final EditText serialcode = dialog.findViewById(R.id.serialcode);
        final TextView close = dialog.findViewById(R.id.close);
        final Spinner amt = dialog.findViewById(R.id.amountt);
        final Spinner netwk = dialog.findViewById(R.id.network);
        final Button send = dialog.findViewById(R.id.send);
        final Button help = dialog.findViewById(R.id.help);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogA = new Dialog(context);
                dialogA.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogA.setContentView(R.layout.card);
                dialogA.show();
                dialog.cancel();
                TextView ok = dialogA.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogA.cancel();
                        dialog.show();
                    }
                });
            }
        });


        final String[] amounts = {"Amount","100", "200", "500", "1000"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, amounts);
        amt.setAdapter(adapter);
        final String[] networks = {"Network","MTN", "AIRTEL"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context, R.layout.spinner_item,networks);
        netwk.setAdapter(adapter2);
        dialog.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send.setText("sending...");
                String ussd = ussdcode.getText().toString();
                String serial = serialcode.getText().toString();
                String network = netwk.getSelectedItem().toString();
                String amtt = amt.getSelectedItem().toString();

                if(netwk.getSelectedItem().toString().equals("Network")){
                    Toast.makeText(context,"select a network",Toast.LENGTH_SHORT).show(); errorflash(netwk);
                    return;
                }
                if(amt.getSelectedItem().toString().equals("Amount")){
                    Toast.makeText(context,"select an amount",Toast.LENGTH_SHORT).show(); errorflash(amt);
                    return;
                }
                if(ussd.length() < 8){ Toast.makeText(context,"the recharge code cannot be less than 8 characters",Toast.LENGTH_SHORT).show(); errorflash(ussdcode); return;}
                if(ussd.isEmpty()){ Toast.makeText(context,"fill in the recharge code of the recharge card",Toast.LENGTH_SHORT).show(); errorflash(ussdcode);return; }
                if(serial.length() < 4){ Toast.makeText(context,"serial number needs to be 4 digits",Toast.LENGTH_SHORT).show(); errorflash(serialcode); return;}
                if(serial.isEmpty()){ Toast.makeText(context,"fill in the serial number of the card",Toast.LENGTH_SHORT).show(); errorflash(serialcode);return; }

                makeRequest2(context,ussd,serial, network ,amtt ,send, appname, code);
            }
        });

    }



    public void downloadMessage(final Context context,String app){
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = pref.edit();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("allusers").child(app).child("USERBASEFOLDER").child(getImei(context)).child("TRANSACTIONS");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value1 = dataSnapshot.getValue(String.class);
                editor.putString("lookup", value1);editor.apply(); }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        }; myRef.addListenerForSingleValueEvent(eventListener);
    }





    public void errorflash(final View button) {
        Animation animation = new AlphaAnimation(1,0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(3);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setFillAfter(false);
        button.startAnimation(animation);
    }


    public void reset(Context context){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("allusers").child(getAppName(context)).child("USERBASEFOLDER").child(getImei(context)).child("TRANSACTIONS");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                myRef.setValue("");

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        }; myRef.addListenerForSingleValueEvent(eventListener);
    }










}
