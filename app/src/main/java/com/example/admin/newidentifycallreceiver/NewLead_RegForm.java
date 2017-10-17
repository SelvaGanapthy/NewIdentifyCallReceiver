package com.example.admin.newidentifycallreceiver;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 10/17/2017.
 */

public class NewLead_RegForm  extends AppCompatActivity implements View.OnClickListener {

        RelativeLayout include1, include2, include3, voice_found;
        TextView tv_no_voice_found;
        EditText new_lead_Name, new_lead_phoneno, new_lead_Email, new_lead_Projects;
        String phoneValid = "[987]";
        Pattern pattern_Email = Pattern.compile("^.+@.+\\..+$");
        ImageView newLead_feedbackReg_buttonNxt, newLead_conversDetail_bNxt, emoji_happy, emoji_very_happy, emoji_sad;
        MediaPlayer mp;
        static String adpath;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_new_lead__reg_form);
            mp = new MediaPlayer();
            Intent intent = getIntent();
            adpath = intent.getStringExtra("audiopath");
            initialize();
            newLead_feedbackReg_buttonNxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newLead_Validate();
                }
            });
            newLead_conversDetail_bNxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    include1.setVisibility(View.GONE);
                    include2.setVisibility(View.GONE);
                    include3.setVisibility(View.VISIBLE);

                }
            });
        }

        private void initialize() {
            voice_found = (RelativeLayout) findViewById(R.id.audio_record_play);
            tv_no_voice_found = (TextView) findViewById(R.id.no_voice_found);
            new_lead_Name = (EditText) findViewById(R.id.new_lead_Name);
            new_lead_phoneno = (EditText) findViewById(R.id.new_lead_phoneno);
            new_lead_Email = (EditText) findViewById(R.id.new_lead_Email);
            new_lead_Projects = (EditText) findViewById(R.id.new_lead_Projects);
            emoji_happy = (ImageView) findViewById(R.id.emojihappy);
            emoji_sad = (ImageView) findViewById(R.id.emojisad);
            emoji_very_happy = (ImageView) findViewById(R.id.emojiveryhappy);
            newLead_feedbackReg_buttonNxt = (ImageView) findViewById(R.id.newLead_feedbackReg_buttonNxt);
            newLead_conversDetail_bNxt = (ImageView) findViewById(R.id.newLead_conversDetail_bNxt);
            include1 = (RelativeLayout) findViewById(R.id.include1);
            include2 = (RelativeLayout) findViewById(R.id.include2);
            include3 = (RelativeLayout) findViewById(R.id.include3);
            emoji_happy.setImageResource(R.drawable.hover_emoji_happy);
            emoji_happy.setOnClickListener(this);
            emoji_very_happy.setOnClickListener(this);
            emoji_sad.setOnClickListener(this);
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
                if (adpath.equals("null")) {
                    tv_no_voice_found.setVisibility(View.VISIBLE);
                    voice_found.setVisibility(View.GONE);
                    Snackbar.make(findViewById(android.R.id.content), "No Voice Record Found", Snackbar.LENGTH_SHORT).show();
                } else
                    try {

                        mp.setDataSource(adpath);
                        mp.prepare();
                        mp.start();
                    } catch (Exception e) {
                        e.printStackTrace();
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
            }

        }
}
