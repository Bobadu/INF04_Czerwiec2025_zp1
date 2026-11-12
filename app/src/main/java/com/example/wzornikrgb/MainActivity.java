package com.example.wzornikrgb;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar suwakR;
    private SeekBar suwakG;
    private SeekBar suwakB;
    private TextView wartoscR;
    private TextView wartoscG;
    private TextView wartoscB;
    private View duzyProstokat;
    private TextView malyProstokat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this, SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT));
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        suwakR = findViewById(R.id.suwakR);
        suwakG = findViewById(R.id.suwakG);
        suwakB = findViewById(R.id.suwakB);
        wartoscR = findViewById(R.id.wartoscR);
        wartoscG = findViewById(R.id.wartoscG);
        wartoscB = findViewById(R.id.wartoscB);
        duzyProstokat = findViewById(R.id.duzyProstokat);
        malyProstokat = findViewById(R.id.malyProstokat);
        Button przyciskPobierz = findViewById(R.id.przyciskPobierz);

        suwakR.setOnSeekBarChangeListener(this);
        suwakG.setOnSeekBarChangeListener(this);
        suwakB.setOnSeekBarChangeListener(this);
        przyciskPobierz.setOnClickListener(v -> pobierzKolor());

        odswiezPodglad();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        odswiezPodglad();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private int aktualnyKolor() {
        return Color.rgb(suwakR.getProgress(), suwakG.getProgress(), suwakB.getProgress());
    }

    private void odswiezPodglad() {
        wartoscR.setText(String.valueOf(suwakR.getProgress()));
        wartoscG.setText(String.valueOf(suwakG.getProgress()));
        wartoscB.setText(String.valueOf(suwakB.getProgress()));
        ustawKolorTla(duzyProstokat, aktualnyKolor());
    }

    private void pobierzKolor() {
        int kolor = aktualnyKolor();
        ustawKolorTla(malyProstokat, kolor);
        malyProstokat.setText(getString(R.string.wartosc_rgb,
                suwakR.getProgress(), suwakG.getProgress(), suwakB.getProgress()));
        malyProstokat.setTextColor(czyCiemny(kolor) ? Color.WHITE : Color.parseColor("#3B3B3B"));
    }

    private void ustawKolorTla(View widok, int kolor) {
        GradientDrawable tlo = (GradientDrawable) widok.getBackground().mutate();
        tlo.setColor(kolor);
    }

    private boolean czyCiemny(int kolor) {
        return 0.299 * Color.red(kolor) + 0.587 * Color.green(kolor) + 0.114 * Color.blue(kolor) < 128;
    }
}
