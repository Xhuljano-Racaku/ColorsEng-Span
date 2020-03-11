package edu.temple.colorswithspanish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class CanvasActivity extends AppCompatActivity {
    ConstraintLayout canvasLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        canvasLayout = findViewById(R.id.canvasLayout);
        Resources res = getResources();
        String CanvasActivitytitle = res.getString(R.string.CanvasActivity_title);
        getSupportActionBar().setTitle(CanvasActivitytitle);


        Intent bgColorInt = getIntent();
        // Get the BACKGROUND_COLOR passed from PaletteActivity
        String bgColor = (String) bgColorInt.getSerializableExtra("BACKGROUND_COLOR");
        // Set background color for CanvasActivity layout
        canvasLayout.setBackgroundColor(Color.parseColor(bgColor));


        Button changeLang = findViewById(R.id.changeMyLang);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show AlertDialog to display list of languages
                showChangeLanguageDialog();
            }
        });
    }

    private void showChangeLanguageDialog() {
        //array of languages
        final String[] listItems = {"English", "Spanish"};

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CanvasActivity.this);
        mBuilder.setTitle("Choose Language...");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    setLocale("en");
                    recreate();
                }

                if (i == 1) {
                    setLocale("es");
                    recreate();

                }

                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        //show alert dialog
        mDialog.show();

    }

    private void setLocale(String lang){

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        //save data to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    public void loadLocale(){
        SharedPreferences pref = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        String language = pref.getString("My_Lang","");
        setLocale(language);
    }
}