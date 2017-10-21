package com.example.admin.newidentifycallreceiver.broadcastReceivers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.newidentifycallreceiver.R;
import com.example.admin.newidentifycallreceiver.activitys.NewLead_RegForm;

import java.util.Date;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 10/17/2017.
 */

public class CallReceiver  extends PhoneCallReceiver {
    static MediaRecorder mr;
    Context context;
    Random random;
    static String audiopaths = "null";
    static String voice_recording = "false";
    String randomaudio_Names = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    static AlertDialog a, b, c, d;
    int[] img_id = {R.drawable.gokuls, R.drawable.emoji_happy, R.drawable.newlead_ac_nameimg};
    String[] names = {"Gokul", "Iffan", "Karthick"};
    String[] no = {"+91701091596", "+91824808323", "+919629578743"};


    @Override
    protected void onIncomingCallStarted(final Context ctx, final String number, Date start) {
        boolean flag = false;
        context = ctx;
        AlertDialog.Builder alert = new AlertDialog.Builder(ctx.getApplicationContext());
        a = alert.create();
        b = alert.create();
        audiopaths = "null";
        for (int i = 0; i < no.length; i++)
            if (no[i].equals(number)) {
                View view = LayoutInflater.from(ctx.getApplicationContext()).inflate(R.layout.availablelead_calling_alertdialog, null);
                TextView caller_name = (TextView) view.findViewById(R.id.availablecaller_calling_name);
                TextView caller_phno = (TextView) view.findViewById(R.id.availablecaller_calling_phno);
                CircleImageView iv_dp = (CircleImageView) view.findViewById(R.id.newcaller_calling_defaultimg);
                ImageButton caller_cancel = (ImageButton) view.findViewById(R.id.id_alerdialog_cancel_img);
                caller_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        a.cancel();

                    }
                });
                iv_dp.setImageResource(img_id[i]);
                caller_name.setText(" " + names[i]);
                caller_phno.setText(" " + no[i]);
                a.setView(view);
                a.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                a.show();
                flag = true;
            }


        if (flag == false) {
            View view = LayoutInflater.from(ctx.getApplicationContext()).inflate(R.layout.newlead_calling_alertdialog, null);
            TextView caller_phno = (TextView) view.findViewById(R.id.newcaller_calling_phno);
            ImageButton caller_cancel = (ImageButton) view.findViewById(R.id.newlead_cancel_img);
            caller_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    b.cancel();
                }
            });
            caller_phno.setText(" " + number);
            b.setView(view);
            b.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            b.show();
        }
    }

    @Override
    protected void onIncomingCallEnded(final Context ctx, final String number, Date start, Date end) {
        boolean flag = false;
        b.cancel();
        a.cancel();
        AlertDialog.Builder alert = new AlertDialog.Builder(ctx.getApplicationContext());
        c = alert.create();
        d = alert.create();


        for (int i = 0; i < no.length; i++)
            if (no[i].equals(number)) {
                View view = LayoutInflater.from(ctx.getApplicationContext()).inflate(R.layout.availablelead_called_alertdialog, null);
                TextView availablelead_called_name = (TextView) view.findViewById(R.id.availablelead_called_name);
                Button availlead_called_followbt = (Button) view.findViewById(R.id.availlead_called_followbt);
                Button availlead_called_blockbt = (Button) view.findViewById(R.id.availlead_called_blockbt);
                Button availlead_called_proposalbt = (Button) view.findViewById(R.id.availlead_called_proposalbt);
                Button availlead_called_finalsationbt = (Button) view.findViewById(R.id.availlead_called_finalsationbt);
                Button availlead_called_prebookbt = (Button) view.findViewById(R.id.availlead_called_prebookbt);
                Button availlead_called_sitevisitbt = (Button) view.findViewById(R.id.availlead_called_sitevisitbt);
                CircleImageView iv_dp = (CircleImageView) view.findViewById(R.id.newcaller_calling_defaultimg);
                ImageButton cancel_img = (ImageButton) view.findViewById(R.id.cancel_img_called);
                cancel_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.cancel();
                        d.dismiss();
                    }
                });
                iv_dp.setImageResource(img_id[i]);
                availablelead_called_name.setText("" + names[i]);
                d.setView(view);
                d.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                d.show();
                flag = true;
            }

        if (voice_recording.equals("true")) {
            voice_recording = "false";
            mr.stop();
            // Toast.makeText(ctx.getApplicationContext(), "stop", Toast.LENGTH_SHORT).show();
        }

        if (flag == false) {
            View view = LayoutInflater.from(ctx.getApplicationContext()).inflate(R.layout.newlead_called_alertdislog, null);
            Button newlead_called_but = (Button) view.findViewById(R.id.newlead_called_but);
            Button newlead_called_discardbut = (Button) view.findViewById(R.id.newlead_called_discardbut);
            TextView newcaller_called_phno = (TextView) view.findViewById(R.id.newcaller_called_phno);
            newcaller_called_phno.setText("" + number);

            newlead_called_but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ctx.startActivity(new Intent(ctx.getApplicationContext(), NewLead_RegForm.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("audiopath", audiopaths));
                    c.cancel();
                    c.dismiss();
                }
            });

            newlead_called_discardbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    c.dismiss();
                    c.cancel();
                }
            });
            c.setView(view);
            c.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            c.show();

        }

    }

    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start, Date end) {
        b.cancel();
        a.cancel();
        //Toast.makeText(ctx, "Answered", Toast.LENGTH_SHORT).show();
        mr = new MediaRecorder();
        random = new Random();
        audiopaths = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + CreateRandomAudioFileName(5) + "AudioRecording.3gp";
        MediaRecorderReady();
        try {
            mr.prepare();
            mr.start();
            voice_recording = "true";
       //     Toast.makeText(ctx, "Voice Recording stated", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MediaRecorderReady() {
        mr.setAudioSource(MediaRecorder.AudioSource.MIC);
        mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mr.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mr.setOutputFile(audiopaths);
    }

    public String CreateRandomAudioFileName(int value) {
        StringBuilder sb = new StringBuilder(value);
        for (int i = 0; i < value; i++)
            sb.append(randomaudio_Names.charAt(random.nextInt(randomaudio_Names.length())));
        return sb.toString();
    }
}
