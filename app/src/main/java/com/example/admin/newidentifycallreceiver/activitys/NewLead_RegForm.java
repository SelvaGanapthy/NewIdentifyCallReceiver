package com.example.admin.newidentifycallreceiver.activitys;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.newidentifycallreceiver.R;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 10/17/2017.
 */

public class NewLead_RegForm extends AppCompatActivity implements View.OnClickListener {
    private Calendar cal;
    private int day, month, year;
    static final int DATE_PICKER_ID = 1111;
    Thread updateSeekbar;
    SeekBar seekBar;
    RelativeLayout include1, include2, include3, include4, voice_found;
    TextView tv_no_voice_found;
    EditText new_lead_Name, new_lead_phoneno, new_lead_Email, new_lead_Projects, date;
    String phoneValid = "[987]";
    Pattern pattern_Email = Pattern.compile("^.+@.+\\..+$");
    ImageView newLead_feedbackReg_buttonNxt, newLead_conversDetail_bNxt, emoji_happy, emoji_very_happy, emoji_sad;
    static MediaPlayer mp;
    static String adpath;
    static boolean volume = true;
    ImageButton playnpause_bt, volumeonnoff_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lead__reg_form);
        initialize();
        Intent intent = getIntent();
        adpath = intent.getStringExtra("audiopath");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });

        updateSeekbar = new Thread() {
            @Override
            public void run() {
                int totalduration = mp.getDuration(), currentduration = 0;
                while (currentduration < totalduration) {
                    try {
                        sleep(500);
                        currentduration = mp.getCurrentPosition();
                        seekBar.setProgress(currentduration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

    }

    private void initialize() {

        tv_no_voice_found = (TextView) findViewById(R.id.no_voice_found);
        new_lead_Name = (EditText) findViewById(R.id.new_lead_Name);
        new_lead_phoneno = (EditText) findViewById(R.id.new_lead_phoneno);
        new_lead_Email = (EditText) findViewById(R.id.new_lead_Email);
        date = (EditText) findViewById(R.id.date);
        new_lead_Projects = (EditText) findViewById(R.id.new_lead_Projects);
        emoji_happy = (ImageView) findViewById(R.id.emojihappy);
        emoji_sad = (ImageView) findViewById(R.id.emojisad);
        emoji_very_happy = (ImageView) findViewById(R.id.emojiveryhappy);
        newLead_feedbackReg_buttonNxt = (ImageView) findViewById(R.id.newLead_feedbackReg_buttonNxt);
        newLead_conversDetail_bNxt = (ImageView) findViewById(R.id.newLead_conversDetail_bNxt);
        include1 = (RelativeLayout) findViewById(R.id.include1);
        include2 = (RelativeLayout) findViewById(R.id.include2);
        include3 = (RelativeLayout) findViewById(R.id.include3);
        include4 = (RelativeLayout) findViewById(R.id.include4);
        voice_found = (RelativeLayout) findViewById(R.id.audio_record_play);
        playnpause_bt = (ImageButton) findViewById(R.id.playnpause_bt);
        volumeonnoff_bt = (ImageButton) findViewById(R.id.volumeonnoff_bt);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        emoji_happy.setImageResource(R.drawable.hover_emoji_happy);
        emoji_happy.setOnClickListener(this);
        emoji_very_happy.setOnClickListener(this);
        emoji_sad.setOnClickListener(this);
        playnpause_bt.setOnClickListener(this);
        volumeonnoff_bt.setOnClickListener(this);
        newLead_conversDetail_bNxt.setOnClickListener(this);
        newLead_feedbackReg_buttonNxt.setOnClickListener(this);
        mp = new MediaPlayer();

    }

    private void newLead_Validate() {
        String phoneno = new_lead_phoneno.getText().toString();
        Matcher matcher_Email = pattern_Email.matcher(new_lead_Email.getText().toString());
        boolean f1, f2, f3, f4;
        f1 = f2 = f3 = f4 = true;
        if (new_lead_Name.length() == 0 || new_lead_Name.length() >= 15) {
            new_lead_Name.setError("Enter the name less than 15 char");
            f1 = false;
        }

        if (new_lead_phoneno.length() == 0 || new_lead_phoneno.length() != 10 || (!String.valueOf(phoneno.charAt(0)).matches(phoneValid))) {
            new_lead_phoneno.setError(" Enter the valid 10 digit mob.no");
            f2 = false;
        }
        if (new_lead_Email.length() == 0 || (!matcher_Email.matches())) {
            new_lead_Email.setError(" Enter the valid Email-id");
            f3 = false;
        }
        if (new_lead_Projects.length() == 0) {
            new_lead_Projects.setError("Enter the Projects");
            f4 = false;
        }
        if (f1 && f2 && f3 && f4) {
            include1.setVisibility(View.GONE);
            include2.setVisibility(View.VISIBLE);
            include3.setVisibility(View.GONE);
            include4.setVisibility(View.GONE);
            if (adpath.equals("null")) {
                tv_no_voice_found.setVisibility(View.VISIBLE);
                voice_found.setVisibility(View.GONE);
                //Snackbar.make(findViewById(android.R.id.content), "No Voice Record Found", Snackbar.LENGTH_SHORT).show();
            } else {
                try {

                    mp.setDataSource(adpath);
                    mp.prepare();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                mp.start();
                seekBar.setMax(mp.getDuration());
                updateSeekbar.start();

            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emojisad:
                emoji_sad.setImageResource(R.drawable.hover_emoji_sad);
                emoji_happy.setImageResource(R.drawable.emoji_happy);
                emoji_very_happy.setImageResource(R.drawable.emoji_veryhappy);
                break;
            case R.id.emojihappy:
                emoji_happy.setImageResource(R.drawable.hover_emoji_happy);
                emoji_sad.setImageResource(R.drawable.emoji_sad);
                emoji_very_happy.setImageResource(R.drawable.emoji_veryhappy);
                break;
            case R.id.emojiveryhappy:
                emoji_very_happy.setImageResource(R.drawable.hover_emoji_veryhappy);
                emoji_sad.setImageResource(R.drawable.emoji_sad);
                emoji_happy.setImageResource(R.drawable.emoji_happy);
                break;
            case R.id.playnpause_bt:
                if (mp.isPlaying()) {
                    mp.pause();
                    playnpause_bt.setImageResource(R.drawable.playbuttonimg);
                } else {
                    mp.start();
                    playnpause_bt.setImageResource(R.drawable.pausebuttonimg);
                }
                break;
            case R.id.volumeonnoff_bt:
                if (volume) {
                    mp.setVolume(0, 0);
                    volumeonnoff_bt.setImageResource(R.drawable.volume_off);
                    volume = false;
                } else {
                    mp.setVolume(1, 1);
                    volumeonnoff_bt.setImageResource(R.drawable.volume_on);
                    volume = true;

                }
                break;
            case R.id.newLead_conversDetail_bNxt:
                include1.setVisibility(View.GONE);
                include2.setVisibility(View.GONE);
                include3.setVisibility(View.VISIBLE);
                include4.setVisibility(View.GONE);
                cal = Calendar.getInstance();
                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);
                showDialog(DATE_PICKER_ID);
                break;
            case R.id.newLead_feedbackReg_buttonNxt:
                newLead_Validate();
                break;
        }

    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // the callback received when the user "sets" the Date in the
        // DatePickerDialog
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            date.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
           // Toast.makeText(getApplicationContext(), "get", Toast.LENGTH_SHORT).show();
            include1.setVisibility(View.GONE);
            include2.setVisibility(View.GONE);
            include3.setVisibility(View.GONE);
            include4.setVisibility(View.VISIBLE);
        }

    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                // create a new DatePickerDialog with values you want to show
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePickerListener, year, month, day);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 0); // Add 0 days to Calendar
                Date newDate = calendar.getTime();
                datePickerDialog.getDatePicker().setMinDate(newDate.getTime() - (newDate.getTime() % (24 * 60 * 60 * 1000)));
                datePickerDialog.setTitle("\t\tNEXT FOLLOW UP DETAILS");
                return datePickerDialog;
        }
        return null;
    }
}
