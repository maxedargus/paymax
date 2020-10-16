package com.scienceboss.paymax;

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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    private  Boolean sent;

    private CountDownTimer refresher;

    private  String value2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intentx = new Intent(MainActivity.this, Main2Activity.class);

  //      onInstall(MainActivity.this, "Demo","");


        //   not all apps will use the term "coin", so create a argument where the company can place the name of there unit
        //   not all apps will use the term "coin", so create a argument where the company can place the name of there unit
        //   not all apps will use the term "coin", so create a argument where the company can place the name of there unit
        //   not all apps will use the term "coin", so create a argument where the company can place the name of there unit
        //   not all apps will use the term "coin", so create a argument where the company can place the name of there unit

        final EditText box = findViewById(R.id.box);
        final Button button = findViewById(R.id.button);

        String ss = "ussd!!32080638581!!200!!MTN!!08132758276!!demo";
        box.setText(ss);

        //paymax, powered by scienceboss


        optionsDialog(MainActivity.this);

        /*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if the ussd exist on the server already with the same amount in the server.....dialog will show saying, requests already exists....if with different number it will say ussd has already been used....therefore a temporary server will always be available to see ussd that has been used from all users and a offline database that sees ussd that has been used by the user when successful


                //consistency check

           //     String ussd,String imei, String amount, String Network, String phonenumber, String Appname

                makeRequest(MainActivity.this, "ussd","200","MTN");

              //   if(makeRequest(MainActivity.this, box.getText().toString()) == true){
              //      ShowDialog("","Successful",MainActivity.this);
                }
                else{
                    ShowDialog("","Failed",MainActivity.this);
                }

            }
        });

*/









    }


    private void setAppname(Context context, String appname){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("3886PayStackOverflowAppName",appname);
        editor.apply();
    }

    private String getAppName(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String appname = pref.getString("3886PayStackOverflowAppName","");
        return appname;
    }


    private void setImei(Context context, String imei){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("3886PayStackOverflowImei",imei);
        editor.apply();
    }

    private String getImei(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
       String imei = pref.getString("3886PayStackOverflowImei","");
        return imei;
    }


    private void setNumber(Context context,String number){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("3886PayStackOverflowNumber",number);
        editor.apply();
    }

    private String getNumber(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String number = pref.getString("3886PayStackOverflowNumber","");
        return number;
    }


    private void makeRequest(final Context context, final String ussd, final String amount, final String Network){

        if(ussd.isEmpty() || amount.isEmpty() || Network.isEmpty()){
           Toast.makeText(context,"please fill in all required fields",Toast.LENGTH_SHORT).show();
            return;
        }


        final Dialog dialogA = new Dialog(context);
        dialogA.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogA.setContentView(R.layout.confirmnumberdialog);
        dialogA.show();


        final Button applynumber = dialogA.findViewById(R.id.applynumber);
        final Button changenumber = dialogA.findViewById(R.id.changenumber);
        final TextView numberbox = dialogA.findViewById(R.id.numberbox);
        TextView close = dialogA.findViewById(R.id.close);
        numberbox.setText(getNumber(context));



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogA.cancel();
            }
        });


/*
        applynumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest2(context,ussd,"",amount,Network,send);
            }
        });*/



        changenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




///RECEPIENT




                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.getnumberdialog);
                dialog.show();

                final Button applynumber = dialog.findViewById(R.id.applynumber);
                final EditText numberbox = dialog.findViewById(R.id.numberbox);
                final TextView close = dialog.findViewById(R.id.close);

                dialog.show();
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                applynumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(numberbox.getText().toString().isEmpty()){
                            Toast.makeText(context,"a phone number is required",Toast.LENGTH_SHORT).show();return;}

                        if(numberbox.getText().toString().length() < 11){  Toast.makeText(context,"phone number must contain 11 characters",Toast.LENGTH_SHORT).show();return;}

                        numberbox.setEnabled(false);
                        applynumber.setEnabled(false);
                        applynumber.setText("LOADING...");
                        close.setVisibility(View.GONE);

                        final String number = numberbox.getText().toString();

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference myRef = database.getReference("allusers").child("demo").child(getImei(context));
                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                myRef.child("phonenumber").setValue(number);
                                setNumber(context, number);
                                Toast.makeText(context,"successful",Toast.LENGTH_LONG).show();
                                dialogA.show();
                                dialog.cancel();
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        }; myRef.addListenerForSingleValueEvent(eventListener);
                    }
                });









            }
        });


    }


    private  Boolean makeRequest2(final Context context, String ussd, String serial, String amount, String Network,final Button send){
            sent = false;
        // with this boolean, the companies can see if message is sent or not, and give the required action. like saying "request sent"..if true or false


     //   String ss = "ussd!!32080638581!!200!!MTN!!08132758276!!Demo";

        String Appname = getAppName(context);
  //      String phonenumber = getNumber(context);
        String imei = getImei(context);




       final String airtime = ussd + "!!" + imei + "!!" + amount + "!!" + Network  + "!!" + Appname;


        final ArrayList<String> arra = new ArrayList<String>(Arrays.asList(airtime.replaceAll("\\s", "").split("!!")));


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef2 = database.getReference("airtimeRequestsPermanent");





        onInstall(MainActivity.this, "demo","");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                value2 = dataSnapshot.getValue(String.class);
                ArrayList<String> arrayForm = new ArrayList<String>(Arrays.asList(value2.replaceAll("\\s", "").split(",")));

                for(int i = 0; i < arrayForm.size(); i++){

                    String arrconv = arrayForm.get(i);

                    ArrayList<String> arr = new ArrayList<String>(Arrays.asList(arrconv.replaceAll("\\s", "").split("!!")));

                    if(arr.contains(arra.get(0)) &&  arr.get(2).equals(arra.get(2))     && arr.get(1).equals(arra.get(1)) ){
                        //this code has already been used by you
                        ShowDialog("","this code has already been used by you",context);
                        send.setText("send");
                        send.setEnabled(true);
                        return;
                    }

                    if(arr.contains(arra.get(0))   && arr.get(2).equals(arra.get(2))  &&    !arr.get(1).equals(arra.get(1))   ){
                        //this code has already been used by another customer
                        ShowDialog("","this code has been used by another customer",context);
                        send.setText("send");
                        send.setEnabled(true);
                        return;
                    }



                    /////
                    /////
                    /////
                }





                final DatabaseReference myRef = database.getReference("airtimeRequests");
                //  final DatabaseReference myRef2 = database.getReference("airtimeRequestsPermanent");
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value1 = dataSnapshot.getValue(String.class);
                        String newAddition = value1  + airtime + ",";
                        myRef.setValue(newAddition);

                        String newAdditionPPP = value2  + airtime + ",";
                        myRef2.setValue(newAdditionPPP);

                        Toast.makeText(context,"successful",Toast.LENGTH_SHORT).show();
                        send.setText("send");
                        send.setEnabled(true);
                        sent = true;
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }; myRef.addListenerForSingleValueEvent(eventListener);


                /////
                /////
                /////
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        }; myRef2.addListenerForSingleValueEvent(eventListener);








return  sent;
    }


    private void setNumbertoServer(final Context context) {

        //tell users that this will also return phone number incase they want to use it

        //user should be able to get phone number from here, or he should provide the phonenumber he got from another place entirely....and hes app wont progress further till we get the phone number properly

        //if number is not applied it will be set to open again, and the app will not continue

        //  first check if the user has an account in paystac for this particular app/// if not add an account and skip the following step
        //create external method

        ///add permission dialog for imei, compulsory
        ///add permission dialog for imei, compulsory
        ///add permission dialog for imei, compulsory

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("allusers").child("demo").child(getImei(context));
        final DatabaseReference myRefTODAY = database.getReference("allusersTODAY").child("demo").child(getImei(context));
        final DatabaseReference myRefTHISMONTH = database.getReference("allusersTHISMONTH").child("demo").child(getImei(context));

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
          //      myRef.child("codesused").setValue("");
                myRef.child("RECEIVED").setValue("");
                myRef.child("TRANSACTIONS").setValue("");


                myRefTODAY.child("RECEIVED").setValue("");
                myRefTODAY.child("TRANSACTIONS").setValue("");



                myRefTHISMONTH.child("RECEIVED").setValue("");
                myRefTHISMONTH.child("TRANSACTIONS").setValue("");



                Toast.makeText(context,"successful",Toast.LENGTH_LONG).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        }; myRef.addListenerForSingleValueEvent(eventListener);



        /*
        final String imei = getImei(context);


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.getnumberdialog);
        dialog.show();


        final Button applynumber = dialog.findViewById(R.id.applynumber);
        final EditText numberbox = dialog.findViewById(R.id.numberbox);
        TextView close = dialog.findViewById(R.id.close);

        dialog.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"a phone number is required",Toast.LENGTH_SHORT).show();
            }
        });


        applynumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberbox.getText().toString().isEmpty()){
                    Toast.makeText(context,"a phone number is required",Toast.LENGTH_SHORT).show();return;}

                if(numberbox.getText().toString().length() < 11){  Toast.makeText(context,"phone number must contain 11 characters",Toast.LENGTH_SHORT).show();return;}

                numberbox.setEnabled(false);
                applynumber.setEnabled(false);
                applynumber.setText("LOADING");

                final String number = numberbox.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("allusers").child("Demo").child(imei);
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        myRef.child("phonenumber").setValue(number);
                        setNumber(context, number);
                        myRef.child("codesused").setValue("");
                        myRef.child("received").setValue("");
                        myRef.child("transactions").setValue("");
                        Toast.makeText(context,"successful",Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }; myRef.addListenerForSingleValueEvent(eventListener);
            }
        });

return numberbox.getText().toString();   */

    }




    private void onInstall(final Context context, String appPackageName, String SecretCode){

        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermission4();    //return statement not added properly
        }
        String imex = manager.getDeviceId();
        setImei(context, imex);
        setAppname(context,appPackageName + SecretCode);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("allusers").child(appPackageName).child(getImei(context));
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    //create new user
                    setNumbertoServer(context);
                }else{
              //      Toast.makeText(context,"user present",Toast.LENGTH_LONG).show();
           //         String value1 = dataSnapshot.child("codesused").getValue(String.class);
          //          addToused(context, value1);
                //    startActivity(intent);
                    //proceed to start activity
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        }; myRef.addListenerForSingleValueEvent(eventListener);
    }



    private void addToused(Context context,String str){

        //this will also be called when the user first opens the app or clears data, so that the imei will be gotten from scienceboss paystack server, the upload to the server will be done when the coins is sent from paystack
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("3886PayStackOverflowReceiverMaxedUsedCodes", str);
        editor.apply();
    }








    private void intializecodes(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        String rechargeCodes = "4628321459,5320985219,1905646749,9486124659,2365714839,5684165829,4013575869,7802319239,6475321019,6516381579,4923152719,1605376249,4081253629,6436860519,1523415909,6324573939,4387481598,6281350639,1615903419,5941937829,2029179559,1374532489,1973417409,3785793719,5833144579,1936265096,7704681539,6159738469,1906732139,3626514809,3178169209,8974909859,4781035939,8164535959,1257477389,1646094589,7450796749,1403554939,1649309379,3629421579,5102075459,4737973289,6362424697,8198576397,2910890469,1428586759,1327557689,7439225169,3016048309,5020317990,2607395959,3651460719,6949373296,6386131689,9102347439,8501751349,4527352719,6375283059,1748351369,3190918639,7147969379,9136784129,3213464829,1264864196,5683751909,3564025159,1868030429,6014862759,4566259569,4103205759,1924737409,9727975839,1057809539,8967357109,2307658939,8382409379,6905897699,2075801549,4264910789,1923907069,7985607179,6129097049,5137817659,5745065079,1139582059,1726483459,1893602929,8645096949,5421018409,6187654279,6465135459,6318410549,7629527319,1350145897,7160578189,7580632489,3950503879,7453996899,8078363269,5195216899,9372702589,4052150859,1986264959,6150824989,9593212759,4139670289,6741823539,4806176809,5435074889,2506408419,9763543579,3984082279,4871285409,5171036809,1929413219,4365996249,2701845963,2712390929,4021890496,4621079649,9615978989,7967805029,4281231809,3507124739,1967105679,6927341289,7292126149,1324839098,6961079859,7249421819,6073049159,7342105329,4154234279,1014589359,6139701389,5215491839,9912368879,4658087379,9340632019,7130475939,3208517469,4623414039,7859836579,2329031098,1076293059,1794010419,6123087249,7675958309,5391264309,5349810369,6253814309,7348273719,7071481619,5853289797,2632847319,2617808609,5631496939,3683760759,5329769259,3932074089,3195964209,4673254289,9274621389,5326684419,3620953498,3938439319,4139418329,4985413297,7956276189,6180836179,1420573059,5035121149,5206389819,5237474159,2591027109,6305842779,8493713090,8640191529,1956347069,9418418629,3756128909,3064123079,8585095219,5844064949,4723035915,6159743019,6568356729,5718649839,4274578269,5285183609,2064573139,9891805809,8602516479,5048416979,7986077579,8234656849,2189938709,9362830619,8231763039,5036521069,8902458419,7948568659,2151486709,5202727379,5347632910,4172306909,6312454589,4343683179,2197628079,6374601649,1423640829,2890149739,9836216069,2180567439,4663463109,4391052669,4958932591,5606834619,8728926589,7313094249,7846501469,9067685629,8784382269,1875194469,2136986939,3721884929,2103512029,7961647079,2473201059,2145812949,7458601049,9571907109,8156413179,1435054279,1798436759,6547281019,6975918649,4683787989,3146033719,1091947389,3578282109,5258071479,5486720209,8196596199,9897124539,3835389639,9012735369,8676787409,4298757079,4980594579,1539430769,7949105699,4260551759,5689405096,8437852689,6848612019,4596897609,1263519759,8227525069,7043899639,3193739089,5371540359,5244617489,3105250759,8458269409,5367045189,3903493953,5120758839,7320370889,7153730479,4030290819,7526428189,9074189429,3521385729,3693894039,3435187629,8286927459,8191424829,8984641689,5245421049,1094548639,5831316249,8027592519,9152067349,1342858009,7801485759,7806349549,4350103759,9379843039,2486736439,9437818309,3068032959,8431652479,4309478029,2647910189,9653479809,6581081019,6387921169,1696457039,4533975149,3902906079,8734215689,5463850589,3457106389,2653903569,4393273909,4354729249,7276814559,6364057879,3180525939,4120721949,5781854909,1401574169,9715845239,7609037689,2182423709,6148163059,7463910619,6305652519,9670154059,6303848239,6081249429,7258606919,5683652959,5573890059,8614038569,8764594809,3560436999,6843949019,6873062639,9529653839,9305952769,7373681509,3530798279,8753601819,4410930479,1424383549,9305131319,4568174839,4702517539,1715149769,7316524209,7690517459,1989967997,3608918269,8378459129,6454074239,2128218539,4309357189,4018694369,6768623019,7452363129,6847019359,6960431429,4948213809,6502859129,9083517869,2989134939,1468972898,7641726719,1526819369,4567127059,4052540369,3571428349,6023497069,1578642139,8653629789,9021586979,5853182079,2521897809,2182506959,9573713919,5637512739,8496086259,1789533459,5421583699,5130787329,8024250179,9174052349,6258472569,6970515649,3419836279,8981305409,7925402419,8573102359,8619180379,4524652829,1951368079,3892168959,5280573079,4254697849,9530391429,1864085079,5284196979,6850465359,3830838259,4131817829,4378501809,7103209879,7685034919,6075658369,3059750519,9841094079,4124307591,2019362459,1641287319,4043940189,1824873959,3107503219,8091438579,4646013479,1243620196,2543810679,9073153579,2096158719,6739720729,2193792593,1075986739,3291539495,2848506499,3390189579,6476916809,3417080629,2129576879,7601490593,5920561392,2960142489,9082198109,3251570979,7319481059,5364701491,4921348609,9812046379,3793713659,2868616319,9845330519,9465937496,8079484889,6096051259,6219594029,4191517893,9271435890,3797283819,5194754109,8971580689,4024725489,9433198489,4380215489,6319025359,1616403829,5976971609,1724936789,8318250529,3956197679,5064380579,5085432389,9147956829,8932387239,1708373549,2578945197,3842316519,5861080799,9041955579,4349357199,7891605079,6019159349,4152586509,6045180279,5275361509,5113594197,5769802349,5138745209,2371066599,0936419399,6721639539,6146982029,1267043795,3592097159,4305157189,8241971829,4376351769,7105044469,7387582869,1328451509,8749828139,1467787259,4823491089,8714293969,9161260519,1686027109,4391804599,7281655094,9508094849,4621858769,3371052689,4238071259,4382650109,7481897259,1756463749,9293647929,5715098489,5208145609,8672756319,7817948449,1302403589,4153237309,8714732792,6104837289,5490236159,4531290489,2123793569,1734697589,5127948089,2564942159,2267145699,2317863789,6590901699,7250341769,1397083629,6085128749,1043218939,9415469189,5142675359,6251580995,8216057659,4050418269,7140858699,1680637829,6184523109,4038169739,3637490909,2841231209,1054524519,7575897929,5481634289,1247927369,9507233819,1586256389,4645834939,5023184149,1534788309,3438067839,7187173679,4782730619,8209216819,6182506739,9546429789,8058147389,1875642539,7503418229,7867107179,9514563749,4926424089,3830376739,3413583749,6734135609,2940430319,2239806179,2675130798,4522017399,9362537509,2198757209,3073815149,3806963259,8050819659,6130166849,3485431498,1617109490,3280829749,9808679599,3935628076,8671081739,8240372869,2283407129,2790283969,8217325969,6398140639,6952143809,2459043219,1274128109,9081386249,4526201459,3064010579,3086981659,5680164197,4191678459,2432823879,5696497398,4159065959,1032578649,6081391209,1875622529,3030871069,1613824695,2412958779,6516320689,4168296709,7019762349,1832633609,8783219209,2093431759,9317490839,9613390679,7219380790,6435659879,8594174289,8149048297,3621045389,1854989739,5943672939,8378018749,6514908795,9431740949,5358264849,7085925869,6028678939,6310584799,1856957189,6451757469,2548372490,6164127890,9278035019,1894738695,5903651059,6364270529,4215287469,4364735019,2695850579,6830865839,1015142029,6782062599,2130894399,6169037449,9710971949,6951239897,7405939829,7215487898,3754668129,6076396049,2631573929,6968160519,7022432159,6963109369,1892768219,6115741529,5380369369,3696853109,4825067019,4231248739,5687381029,4932180591,4981654934,9186730459,9120597589,2829676909,8983287039,6438032799,3580324939,3197395409,7973658659,3914150819,3018012079,8295106879,7187452389,3425686219,3789820529,2818305949,6198105389,6297104359,9369653669,9138051739,7419648969,1870341749,3856791929,4735631469,3705313289,1465788109,2236306939,2475731819,5710865249,8353854689,3204172189,7250395959,7679921649,4832738799,1395062949,7812462559,3823159489,1892304939,2730451289,8416930719,7454861519,8028176799,9518250149,6951033959,5323985392,4134905189,8191091019,3601956879,5767653419,5201358109,4606915419,9808396019,3812549067,8493435829,1312987269,9783428198,4960435109,1062180579,3452731589,4502522397,1781453429,5451034269,3842187509,6773610369,5671960769,3181570639,8942497469,8381720939,4756243739,1543106029,2148026597,3834017569,8781828929,1033826759,6316813689,4851586999,8230912829,3959261639,3590374639,6071453489,7153462749,7457085029,6014262089,2102072659,4193858809,8185156509,4916878059,8965318749,1361038497,2824960990,9302930739,2641570319,5670492393,5331056709,3861242319,9715258089,3632980169,3608326394,7496147579,2073154219,1249790809,7604414759,4538439429,2683536259,4909345910,6464310949,5936198149,9710139459,6561523709,5870789179,2196054809,1929797492,6531023929,4642891395,2118083459,8656540879,8039342989,2747015709,3471543049,9846768489,7013797797,4186749069,7247170969,1049871989,9625492579,7587535869,4382105849,2173407489,4619760729,3544014289,4751047079,8262914779,3796912329,6432901859,2312684079,6124692459,1568190679,5367812539,7070549591,4637915649,8687051493,8525321249,3216190329,3793805439,7089020949,5956920549,9785964198,9357953189,7059203959,6174175719,9342406929,4810574699,9178032509,4803321179,9646298492,3715240989,8136475879,9340394179,6180465909,3514067982,4380375109,3108625819,7530171949,5236121289,2343965369,1959104729,8137546799,7547129369,2376584919,9047031089,4816769839,8236362709,6715393129,2621470359,1759850199,4985136729,4805974479,2107545979,9680282719,9416870679,1985896419,4947962329,4325231729,8427524689,9089201539,4069170694,9018378409,3505739817,3479289769,7414329489,9755483709,4510928795,4137423549,1854751029,2840384719,2614302897,6724738939,7860810479,3472971098,8170963259,8168407969,6328080489,9621618079,8357532029,8254813869,8428132629,4897956739,5276971079,9357820649,6836135529,4090535349,3613850949,1025761089,9521867296,1879262089,9438183459,4364781389,7183649409,6909016989,2730172859,3164839349,3865071849,3168512539,2199465359,8920487919,8405081839,8178923469,2464741429,8670703849,8697083849,7034983795,9398542149,2017825879,4126778509,8132624790,2586143094,2054176989,8967105749,1359254819,8929602879,2312763089,9143714909,9281235139,2317915179,5701938369,3619609179,2412836759,5726481789,9676301919,8185752309,8058412749,7872792089,9857190319,9801982879,2757329798,4351348269,2650389349,4953718029,9756586319,3803739049,5650138739,1357310419,5361498090,9441785439,8901396819,2064094319,4638550779,3718205259,2068069396,8153024229,9795645693,4103753069,8753420519,9567142609,9403435249,5693985197,2643608391,5632714939,2038064179,9563893519,7681692829,9742471379,9241382079,9501858249,9013647129,4075921089,5049780109,6103736029,3024719239,3073628492,8581308759,3595467579,8982504919,2543083619,4906467969,3614504809,5981087979,8257692089,6654301789,3025394169,3245410699,2068218159,6834584629,2421043289,2067875409,2547150839,8514697398,4216287389,4307256098,7280691329,3761381519,7141480279,6287253059,2897013909,7657310797,5041854890,2058745941,9137890539,7893430839,9138997569,9530585479,1849373389,3706147539,2754167089,1236864509,8324659479,8171087619,2390581249,5621093249,5069679179,1489187489,6262041639,1029897189,7513285679,3715866409,7456463798,3175132879,4674540900,3695715290,1384255269,5695435259,8074319239,4248706869,6047235189,2174131359,7830142759,3213514059,1824936829,4078187929";
        editor.putString("3886PayStackOverflowReceiverMaxedAllCodes",rechargeCodes); editor.apply();
    }


    private String codesMethod(String databaseConvert,ArrayList<String> messageToList, Context context){



        String imei = getImei(context);

        int ime1 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(0)));
        int ime2 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(1)));
        int ime3 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(2)));
        int ime4 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(3)));
        int ime5 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(4)));
        int ime6 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(5)));
        int ime7 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(6)));
        int ime8 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(7)));
        int ime9 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(8)));
        int ime10 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(9)));
        int ime11 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(10)));
        int ime12 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(11)));
        int ime13 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(12)));

        int ime14=0;
        int ime15=0;
        int ime16=0;

        if(messageToList.get(2).length() == 3){

            ime14 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(13)));
        }

        if(messageToList.get(2).length() == 4){
            ime15 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(14)));
        }

        if(messageToList.get(2).length() == 5){
            ime15 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(14)));
            ime16 = Integer.parseInt(String.valueOf((imei + messageToList.get(2)).charAt(15)));
        }

        String index1 = String.valueOf(String.valueOf(databaseConvert.charAt(ime1)));
        String index2 = String.valueOf(String.valueOf(databaseConvert.charAt(ime2)));
        String index3 = String.valueOf(String.valueOf(databaseConvert.charAt(ime3)));
        String index4 = String.valueOf(String.valueOf(databaseConvert.charAt(ime4)));
        String index5 = String.valueOf(String.valueOf(databaseConvert.charAt(ime5)));
        String index6 = String.valueOf(String.valueOf(databaseConvert.charAt(ime6)));
        String index7 = String.valueOf(String.valueOf(databaseConvert.charAt(ime7)));
        String index8 = String.valueOf(String.valueOf(databaseConvert.charAt(ime8)));
        String index9 = String.valueOf(String.valueOf(databaseConvert.charAt(ime9)));
        String index10 = String.valueOf(String.valueOf(databaseConvert.charAt(ime10)));
        String index11 = String.valueOf(String.valueOf(databaseConvert.charAt(ime11)));
        String index12 = String.valueOf(String.valueOf(databaseConvert.charAt(ime12)));
        String index13 = String.valueOf(String.valueOf(databaseConvert.charAt(ime13)));


        String index14 ="";
        String index15 ="";
        String index16 ="";


        if(messageToList.get(2).length() == 3){
            index14 = String.valueOf(String.valueOf(databaseConvert.charAt(ime14)));
        }

        if(messageToList.get(2).length() == 4){
            index15 = String.valueOf(databaseConvert.charAt(ime15));
        }

        if(messageToList.get(2).length() == 5){
            index15 = String.valueOf(databaseConvert.charAt(ime15));
            index16 = String.valueOf(databaseConvert.charAt(ime16));
        }


        String  myStringNew = index1 + index2 + index3 + index4 + index5 + index6 + index7 + index8 + index9 + index10 + index11 + index12 + index13 + index14 + index15 + index16+ ",";

        return  myStringNew;
    }

    private void requestPermission4(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},4);
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


    private int receive(final Context context){


        final int[] amount = {0};

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("allusers").child(getAppName(context)).child(getImei(context)).child("RECEIVED");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value1 = dataSnapshot.getValue(String.class);
               // setmessage(context,"you have received a total of " + value1 + " coins");
                showtransactions(value1);

                ArrayList<String> dataToList = new ArrayList<String>(Arrays.asList(value1.replaceAll("\\s", "").split(",")));

                for(int x = 0; x < dataToList.size(); x++){

                    String collect = dataToList.get(x);

                    ArrayList<String> data = new ArrayList<String>(Arrays.asList(collect.replaceAll("\\s", "").split(",")));

                    if(data.get(3).equals("invalid")){ amount[0] = amount[0] + 0; }

                    if(data.get(3).equals("valid")){  amount[0] = amount[0] + Integer.parseInt(data.get(2));  }

                }
                setmessage(context, "you have received a total of "  + amount[0] +  " coins");
                Toast.makeText(context, "" + amount[0] , Toast.LENGTH_SHORT).show();

                myRef.setValue("");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        }; myRef.addListenerForSingleValueEvent(eventListener);







        return amount[0];
    }


    private void setmessage(Context context,String message){

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



    private void optionsDialog(final Context context){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choice);
        dialog.show();

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
                detailsDialog(context);

            }
        });

        receive.setEnabled(false);

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = pref.edit();


       downloadMessage(context);

       receive.setAlpha(0.5f);

       refresher = new CountDownTimer(100,100) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
             //   downloadMessage(context);
                receive.setEnabled(false);
             //   receive.setBackgroundColor(Color.DKGRAY);
                receive.setAlpha(0.5f);

                if(pref.getString("lookup","").isEmpty() || (pref.getString("lookup","") == "")){
                    receive.setEnabled(false);
              //      receive.setBackgroundColor(Color.DKGRAY);
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

                MyAdapter myAdapter = new MyAdapter(MainActivity.this,  pref.getString("lookup","").split(","),recycler);
                recycler.setAdapter(myAdapter);
                recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                MainLayout.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
             //   receive(context);

                int amount = 0;
                String value1 = pref.getString("lookup","");

                Toast.makeText(context, value1, Toast.LENGTH_LONG).show();
                // setmessage(context,"you have received a total of " + value1 + " coins");
                showtransactions(value1);

                ArrayList<String> dataToList = new ArrayList<String>(Arrays.asList(value1.replaceAll("\\s", "").split(",")));

                for(int x = 0; x < dataToList.size(); x++){

                    String collect = dataToList.get(x);

                    ArrayList<String> data = new ArrayList<String>(Arrays.asList(collect.replaceAll("\\s", "").split("@#!")));

                    if(data.get(3).equals("invalid")){ amount = amount + 0; }

                    if(data.get(3).equals("valid")){  amount = amount + Integer.parseInt(data.get(2));  }
                }
                setmessage(context, "you have received a total of "  + amount +  " coins\n\nclick ok to see receipt");

                editor.putString("lookup","");editor.apply();

                reset(context);

                receive.setEnabled(false);
             //   receive.setBackgroundColor(Color.DKGRAY);
                refresher.cancel();

            }
        });
    }



    private void showtransactions(String trans){

    }


    private  void detailsDialog(final Context context){



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


                makeRequest2(context,ussd,serial, network ,amtt ,send);
            }
        });

    }



    private void downloadMessage(final Context context){


        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = pref.edit();

       //{"25362537392028@#!MTN@#!200@#!invalid,98327187192@#!AIRTEL@#!500@#!invalid"};

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("allusers").child("demo").child(getImei(context)).child("RECEIVED");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value1 = dataSnapshot.getValue(String.class);

                editor.putString("lookup", value1);editor.apply();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        }; myRef.addListenerForSingleValueEvent(eventListener);


    }





    private void errorflash(final View button) {

        Animation animation = new AlphaAnimation(1,0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(3);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setFillAfter(false);
        button.startAnimation(animation);

    }



    private void reset(Context context){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("allusers").child(getAppName(context)).child(getImei(context)).child("received");

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
