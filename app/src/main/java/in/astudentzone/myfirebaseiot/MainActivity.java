package in.astudentzone.myfirebaseiot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private Button on, off ,on1, off1;
    private TextView mic;
    private TextToSpeech tts;


    DatabaseReference myRef_LedStatus;
    DatabaseReference myRef_LedStatus1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        UIElements();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef_LedStatus = database.getReference("LED Status");  //for led Red
        myRef_LedStatus1 = database.getReference("LED Status1");  //for led Blue

        //switching on and off for Red Led
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOnRedLed();
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOffRedLed();
            }
        });

        //switching on and off for Blue Led
        on1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOnBlueLed();

            }
        });
        off1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOffBlueLed();

            }
        });


        //when mic icon is clicked
        voiceRecognition();
    }




    private void voiceRecognition() {
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                startActivityForResult(intent,200);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200 && resultCode==RESULT_OK){
            ArrayList<String> arrayList=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String voice=arrayList.get(0).toLowerCase();
            if (  (voice.contains("on")) &&  (voice.contains("red"))  ){
                speakRedLedOn();
                turnOnRedLed();
            }else if ( (voice.contains("off")) &&  (voice.contains("red"))  ){
                speakRedLedOff();
                turnOffRedLed();
            }else if ( (voice.contains("on")) &&  (voice.contains("blue"))  ){
                speakBlueLedOn();
                turnOnBlueLed();
            }else if ( (voice.contains("off")) &&  (voice.contains("blue"))  ){
                speakBlueLedOff();
                turnOffBlueLed();
            }else{
                Toast.makeText(this, "Didn't get you !", Toast.LENGTH_LONG).show();

            }
        }
    }






    //tts engine
    private void speakRedLedOn() {
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i==TextToSpeech.SUCCESS){
                    tts.setLanguage(Locale.US);
                    tts.setSpeechRate(0.8f);
                    tts.speak("Turning red light On",TextToSpeech.QUEUE_ADD,null);
                }
            }
        });
    }
    private void speakRedLedOff() {
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i==TextToSpeech.SUCCESS){
                    tts.setLanguage(Locale.US);
                    tts.setSpeechRate(0.8f);
                    tts.speak("Turning red light Off",TextToSpeech.QUEUE_ADD,null);
                }
            }
        });
    }
    private void speakBlueLedOn() {
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i==TextToSpeech.SUCCESS){
                    tts.setLanguage(Locale.US);
                    tts.setSpeechRate(0.8f);
                    tts.speak("Turning blue light On",TextToSpeech.QUEUE_ADD,null);
                }
            }
        });
    }
    private void speakBlueLedOff() {
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i==TextToSpeech.SUCCESS){
                    tts.setLanguage(Locale.US);
                    tts.setSpeechRate(0.8f);
                    tts.speak("Turning blue light Off",TextToSpeech.QUEUE_ADD,null);
                }
            }
        });
    }




    private void turnOnRedLed() {
        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                myRef_LedStatus.setValue("1");
            }
        };
        handler.postDelayed(runnable,2000);

    }
    private void turnOffRedLed() {
        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                myRef_LedStatus.setValue("0");
            }
        };
        handler.postDelayed(runnable,2000);
    }
    private void turnOnBlueLed() {
        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                myRef_LedStatus1.setValue("1");
            }
        };
        handler.postDelayed(runnable,2000);
    }
    private void turnOffBlueLed() {
        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                myRef_LedStatus1.setValue("0");
            }
        };
        handler.postDelayed(runnable,2000);
    }




    private void UIElements() {
        on=findViewById(R.id.btn_on);
        off=findViewById(R.id.btn_off);
        on1=findViewById(R.id.btn_ledOn1);
        off1=findViewById(R.id.btn_ledOff1);
        mic=findViewById(R.id.tv_vRecog);
    }
}