package com.example.deathsatyr.myapplication;


import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

//import com.androidadvance.androidsurvey.SurveyActivity;

import com.google.android.gms.common.api.Response;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class MainActivity extends AppCompatActivity {

    private static final int SURVEY_REQUEST = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Nothing fancy here. Plain old simple buttons....

        Intent i_survey = new Intent(MainActivity.this, SurveyActivity.class);
        //you have to pass as an extra the json string.
        i_survey.putExtra("json_survey", loadSurveyJson("example_survey_1.json"));
        startActivityForResult(i_survey, SURVEY_REQUEST);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SURVEY_REQUEST) {
            if (resultCode == RESULT_OK) {
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();
                String answers_json = data.getExtras().getString("answers");
                String[] arrAnswers = answers_json.split("\\\"");
                mDatabase.child(arrAnswers[1]).child(arrAnswers[3]).push().setValue(arrAnswers[3]);
                mDatabase.child(arrAnswers[5]).child(arrAnswers[7]).push().setValue(arrAnswers[7]);
                mDatabase.child(arrAnswers[9]).child(arrAnswers[11]).push().setValue(arrAnswers[11]);
                mDatabase.child(arrAnswers[13]).child(arrAnswers[15]).push().setValue(arrAnswers[15]);
                mDatabase.child(arrAnswers[17]).child(arrAnswers[19]).push().setValue(arrAnswers[19]);
                mDatabase.child(arrAnswers[21]).child(arrAnswers[23]).push().setValue(arrAnswers[23]);
                mDatabase.child(arrAnswers[25]).child(arrAnswers[27]).push().setValue(arrAnswers[27]);
                mDatabase.child(arrAnswers[29]).child(arrAnswers[31]).push().setValue(arrAnswers[31]);
                mDatabase.child(arrAnswers[33]).child(arrAnswers[35]).push().setValue(arrAnswers[35]);
                mDatabase.child(arrAnswers[37]).child(arrAnswers[39]).push().setValue(arrAnswers[39]);
                mDatabase.child(arrAnswers[41]).child(arrAnswers[43]).push().setValue(arrAnswers[43]);
                mDatabase.child(arrAnswers[45]).child(arrAnswers[47]).push().setValue(arrAnswers[47]);

                mDatabase.child(arrAnswers[49]).child(arrAnswers[51]).push().setValue(arrAnswers[51]);
                mDatabase.child(arrAnswers[53]).child(arrAnswers[55]).push().setValue(arrAnswers[55]);
                mDatabase.child(arrAnswers[57]).child(arrAnswers[59]).push().setValue(arrAnswers[59]);

                mDatabase.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.getChildrenCount() == 15) {
                                                            setContentView(R.layout.activity_pi);
                                                            int w=1;
                                                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                                                List<SliceValue> pieData = new ArrayList<>();
                                                                int i=0;
                                                                for (DataSnapshot data1 : data.getChildren()){
                                                                    if (i==0) {
                                                                        pieData.add(new SliceValue(data1.getChildrenCount(), Color.BLUE).setLabel(data1.getKey()));
                                                                    }else if(i==1){
                                                                        pieData.add(new SliceValue(data1.getChildrenCount(), Color.GRAY).setLabel(data1.getKey()));
                                                                    }
                                                                    else if(i==2){
                                                                        pieData.add(new SliceValue(data1.getChildrenCount(), Color.RED).setLabel(data1.getKey()));
                                                                    }
                                                                    else if(i==3){
                                                                        pieData.add(new SliceValue(data1.getChildrenCount(), Color.MAGENTA).setLabel(data1.getKey()));
                                                                    }
                                                                    else if(i==4){
                                                                        pieData.add(new SliceValue(data1.getChildrenCount(), Color.BLACK).setLabel(data1.getKey()));
                                                                    }
                                                                    i++;
                                                                }
                                                                int id = getResources().getIdentifier("chart"+Integer.toString(w),"id",getPackageName());
                                                                PieChartView pieChartView = findViewById(id);

                                                                PieChartData pieChartData = new PieChartData(pieData);
                                                                pieChartData.setHasLabels(true);
                                                                pieChartData.setHasCenterCircle(true).setCenterText1(data.getKey()).setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
                                                                pieChartView.setPieChartData(pieChartData);
                                                                w++;

                                                            }



                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                        // Getting Post failed, log a message
                                                        Log.w("error", "loadPost:onCancelled", databaseError.toException());
                                                        // ...
                                                    }
                                                });
            }
        }
    }


    //json stored in the assets folder. but you can get it from wherever you like.
    private String loadSurveyJson(String filename) {
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


}