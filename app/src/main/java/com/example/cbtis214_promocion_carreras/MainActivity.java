package com.example.cbtis214_promocion_carreras;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cbtis214_promocion_carreras.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Handler handler = new Handler();

 //   private static final int[] imageIds = {R.drawable.administracion, R.drawable.contabilidad, R.drawable.construccion, R.drawable.programacion, R.drawable.diseno};


    @SuppressLint({"WrongViewCast", "ResourceType", "MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inflar el layout de la actividad con el ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.navigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.nav_section_1:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.nav_section_2:
                    replaceFragment(new CoursesFragment());
                    break;
                case R.id.nav_section_3:
                    replaceFragment(new NewsFragment());
                    break;
                case R.id.nav_section_4:
                    replaceFragment(new SettingsFragment());
                    break;
                default:
                    break;

            }


            return true;
        });


    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();


    }


    // Esta función muestra un diálogo con controles de video personalizados
    public void showVideoControls() {

        // Inflar el layout custom_controls_video.xml
        View controlsView = getLayoutInflater().inflate(R.layout.custom_controls_video, null);

        // Obtener una referencia a la vista LinearLayout con ID controlLayout
        LinearLayout controlLayout = controlsView.findViewById(R.id.controlLayout);


        controlLayout.setVisibility(View.VISIBLE);


        // Buscar las vistas dentro del layout personalizado
        VideoView videoView = controlsView.findViewById(R.id.surfaceView);
        ImageButton playPauseButton = controlsView.findViewById(R.id.playPauseButton);
        ImageButton fullscreenButton = controlsView.findViewById(R.id.fullscreenButton);
        ImageButton closeButton = controlsView.findViewById(R.id.backButton);
        ProgressBar loadingSpinner = controlsView.findViewById(R.id.loadingSpinner);
        SeekBar seekBar = findViewById(R.id.seekBar);

        if (seekBar != null) {
            seekBar.setProgress((int) ((float) videoView.getCurrentPosition() / videoView.getDuration() * 100));
        }
        // Mostrar el loadingSpinner
        loadingSpinner.setVisibility(View.VISIBLE);

        // Crear un AlertDialog que muestre el layout personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setView(controlsView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Configurar el videoView con el archivo de video que deseas reproducir
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.prom_video_cbtis;
        Uri videoUri = Uri.parse(videoPath);
        videoView.setVideoURI(videoUri);
        videoView.start();

        // Configurar los botones
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    playPauseButton.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    videoView.start();
                    playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
                }
            }
        });

        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Configurar el videoView para que ocupe toda la pantalla
                videoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                // Configurar la actividad para que oculte la barra de navegación
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                // Configurar el botón para que salga de pantalla completa
                fullscreenButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                fullscreenButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Configurar el videoView para que vuelva a su tamaño original
                        videoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                        // Configurar la actividad para que muestre la barra de navegación
                        View decorView = getWindow().getDecorView();
                        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                        // Configurar el botón para que vuelva a pantalla completa
                        fullscreenButton.setImageResource(android.R.drawable.ic_menu_crop);
                        fullscreenButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showVideoControls();
                            }
                        });
                    }
                });
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        // Ocultar el loadingSpinner
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingSpinner.setVisibility(View.GONE);
            }
        }, 3000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }




}