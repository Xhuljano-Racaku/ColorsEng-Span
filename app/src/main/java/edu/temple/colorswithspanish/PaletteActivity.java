package edu.temple.colorswithspanish;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Locale;

public class PaletteActivity extends AppCompatActivity {
    Spinner spinner;
    ConstraintLayout paletteLayout;
    String[] colors;
    int checkIfSpinnerReady = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);

        Resources res = getResources();
        colors = res.getStringArray(R.array.colors);

        spinner = findViewById(R.id.spinner);
        paletteLayout = findViewById(R.id.layout);
        final ColorAdapter colorAdapter = new ColorAdapter(PaletteActivity.this, colors);


        spinner.setAdapter(colorAdapter);
        String PaletteActivitytitle = res.getString(R.string.PaletteActivity_title);
        getSupportActionBar().setTitle(PaletteActivitytitle);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (++checkIfSpinnerReady > 1) {
                    Intent canvasIntent = new Intent(PaletteActivity.this, CanvasActivity.class);

                    String colorToUse;
                    switch (colors[position]) {
                        case "Azul":
                            colorToUse = "Blue";
                            break;
                        case "Cian":
                            colorToUse = "Cyan";
                            break;
                        case "Gris":
                            colorToUse = "Gray";
                            break;
                        case "Verde":
                            colorToUse = "Green";
                            break;
                        case "Rojo":
                            colorToUse = "Red";
                            break;
                        case "Negro":
                            colorToUse = "Black";
                            break;
                        case "Amarillo":
                            colorToUse = "Yellow";
                            break;
                        default:
                            colorToUse = colors[position];
                            break;
                    }
                    canvasIntent.putExtra("BACKGROUND_COLOR", colorToUse);

                    startActivity(canvasIntent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PaletteActivity.this);
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