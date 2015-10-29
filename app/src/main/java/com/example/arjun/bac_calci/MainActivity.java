package com.example.arjun.bac_calci;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String gender_value,weigh_val;
    RadioGroup rg;
    String rvalue;
    double r, A, W, num, deno, finalv, ounce, aoh_value;
    int flag;
    double sum= 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button save_btn = (Button) findViewById(R.id.Save_button);
        final Switch switchvalue = (Switch) findViewById(R.id.Gender_switch);
        final EditText weight_value = (EditText) findViewById(R.id.Weight_value);
        final TextView status =(TextView) findViewById(R.id.status_value);

        final TextView gen_val = (TextView) findViewById(R.id.textViewGender);
        final ProgressBar progress = (ProgressBar)findViewById(R.id.progressBar);
        final Button add_drink = (Button)findViewById(R.id.add_drink_button);
        final TextView bacvalue = (TextView) findViewById(R.id.bac_value);
        final SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        final TextView tv = (TextView) findViewById(R.id.alcohol_value);
        final ArrayList<Double> bac_val = new ArrayList<>();
        status.setText("You're safe");
        status.setTextColor(Color.WHITE);
        status.setBackgroundColor(Color.GREEN);

        switchvalue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d("demo", "Male");
                    flag=1;
                    gen_val.setText("M");
                } else {
                    Log.d("demo", "Female");
                    flag=0;
                    gen_val.setText("F");
                }
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weigh_val = weight_value.getText().toString();

                if(weigh_val == null || weigh_val.isEmpty()) {
                   // status.setText("Please enter Weight");
                }
                if(weigh_val != null) {
                    Log.d("demo", weigh_val);
                }

                if(flag == 1){
                    gender_value="Male";

                }
                else if(flag == 0){
                    gender_value="Female";
                    gen_val.setText("F");
                }
                Log.d("demo",gender_value);
            }
        });

        rg = (RadioGroup) findViewById(R.id.radio_group);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton r = (RadioButton) findViewById(checkedId);
                rvalue = r.getText().toString();
                Log.d("demo",rvalue);
                if(rvalue.equals(null) || rvalue.isEmpty()) {
                    ounce = 1.0;
                    Log.d("demo","ounce-1");
                }
                else if(rvalue.equals("5 oz")) {
                    ounce = 5.0;
                    Log.d("demo","ounce-5");
                }
                else if(rvalue.equals("12 oz")){
                    ounce = 12.0;
                    Log.d("demo","ounce-12");
                }
                Log.d("demo",rvalue);
                Log.d("demo",(String.format("%.2f",ounce)));
            }
        });

        final int start = 5;
        final int[] sump = {0};
        //anonymous class
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               // sump[0] = sump[0] +start;
                //seekBar.setProgress(sump[0]);
                seekBar.setKeyProgressIncrement(1);
                seekBar.setMax(5);
                tv.setText(progress*5 + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //final ProgressBar pb =(ProgressBar) findViewById(R.id.progressBar);

        add_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weigh_val == null || weigh_val.isEmpty()) {
                    Log.d("demo", "weight is null");
//                    status.setError("Enter weight in lbs");
//                    status.setText("Enter weight in lbs");
                    weight_value.setError("Enter weight on lbs");
                } else {
                    Log.d("demo", "weight is not null");
                    Log.d("demo", weigh_val);

                    if (gender_value == "Male") {
                        r = 0.73;
                    } else {
                        r = 0.66;
                    }

                    aoh_value = Integer.parseInt(tv.getText().toString());
                    aoh_value = aoh_value / 100;
                    A = ounce * aoh_value;
                    bac_val.add(A);

                    W = Integer.parseInt(weigh_val);
                    Log.d("demo", tv.getText().toString());

                    for(int i=0; i < bac_val.size(); i++) {
                        sum = sum + bac_val.get(i);
                    }
                    System.out.println(sum);

                    num = sum * 5.14;
                    deno = W * r;
                    finalv = num / deno;

                    Log.d("demo", (String.format("%.3f", finalv)));
                    bacvalue.setText((String.format("%.3f", finalv)));

                    if (finalv > 0.0) {
                        int val = (int) (finalv * 100);
                        System.out.println(val);
                        progress.setProgress(val);
                    }
                    if (finalv >= 0.25) {
                        progress.setProgress(25);
                    }


                    if (finalv <= 0.08) {
                        status.setText("You're safe");
                        status.setTextColor(Color.WHITE);
                        status.setBackgroundColor(Color.GREEN);
                    } else if (finalv > 0.08 && finalv < 0.20) {
                        status.setText("Be careful..");
                        status.setTextColor(Color.WHITE);
                        status.setBackgroundColor(Color.YELLOW);
                    } else if (finalv > 0.20 && finalv < 0.25) {
                        status.setText("Over the limit!");
                        status.setTextColor(Color.WHITE);
                        status.setBackgroundColor(Color.RED);
                    } else if (finalv >= 0.25) {
                        int val;
                        val = (int) finalv;
                        //pb.setMax(val);
                        status.setText("Over the limit!");

                        save_btn.setEnabled(false);
                        add_drink.setEnabled(false);

                        Toast.makeText(getApplicationContext(), "No more drinks for you",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
//        new Thread(new Runnable() {
//            public void run() {
//                while (progressStatus[0] < 100) {
//                    progressStatus[0] += 1;
//                    // Update the progress bar and display the
//                    //current value in the text view
//                    handler.post(new Runnable() {
//                        public void run() {
//                            progress.setProgress(progressStatus[0]);
//
//                        }
//                    });
//                    try {
//                        // Sleep for 200 milliseconds.
//                        //Just to display the progress slowly
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();

        Button reset = (Button) findViewById(R.id.reset_button);
        final RadioButton rb = (RadioButton) findViewById(R.id.radio_1oz);
        final TextView bac_value = (TextView) findViewById(R.id.bac_value);


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo","reset clicked");
                save_btn.setEnabled(true);
                add_drink.setEnabled(true);
                sb.setProgress(5);
                rb.setChecked(true);
                weight_value.setText("");
                switchvalue.setChecked(false);
                status.setText("You're safe");
                status.setTextColor(Color.WHITE);
                status.setBackgroundColor(Color.GREEN);

                finalv = 0.00;
                bac_value.setText((String.format("%.2f", finalv)));
                weigh_val = "";
                gender_value = "";
                Log.d("demo",weigh_val);
                Log.d("demo",gender_value);
            }
        });
    }
}
