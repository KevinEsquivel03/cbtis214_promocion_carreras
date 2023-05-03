package com.example.cbtis214_promocion_carreras;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
    private final Handler handler = new Handler();
    private int mCurrentPosition = 0;




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
        View controlsView = getLayoutInflater().inflate(R.layout.custom_controls_video, null);
        LinearLayout controlLayout = controlsView.findViewById(R.id.controlLayout);
        controlLayout.setVisibility(View.VISIBLE);

        VideoView videoView = controlsView.findViewById(R.id.surfaceView);
        ImageButton playPauseButton = controlsView.findViewById(R.id.playPauseButton);
        ImageButton fullscreenButton = controlsView.findViewById(R.id.fullscreenButton);
        ImageButton closeButton = controlsView.findViewById(R.id.backButton);
        ProgressBar loadingSpinner = controlsView.findViewById(R.id.loadingSpinner);
        SeekBar seekBar = findViewById(R.id.seekBar);
        playPauseButton.setImageResource(android.R.drawable.ic_media_pause);



        loadingSpinner.setVisibility(View.VISIBLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setView(controlsView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        setupVideoView(videoView);
        setupPlayPauseButton(playPauseButton, videoView);
        setupFullscreenButton(fullscreenButton, videoView);
        setupCloseButton(closeButton, alertDialog);
        hideLoadingSpinner(loadingSpinner);
    }

    private void setupVideoView(VideoView videoView) {
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.prom_video_cbtis;
        Uri videoUri = Uri.parse(videoPath);
        videoView.setVideoURI(videoUri);
        videoView.start();
    }

    private void setupPlayPauseButton(ImageButton playPauseButton, VideoView videoView) {
        playPauseButton.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
                playPauseButton.setImageResource(android.R.drawable.ic_media_play);
            } else {
                videoView.start();
                playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
            }
        });
    }

    private void setupFullscreenButton(ImageButton fullscreenButton, VideoView videoView) {
        fullscreenButton.setOnClickListener(v -> {
            setupFullscreen(videoView, fullscreenButton);
        });
    }

    private void setupFullscreen(VideoView videoView, ImageButton fullscreenButton) {
        videoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION );
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        fullscreenButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        fullscreenButton.setOnClickListener(v -> {
            exitFullscreen(videoView, fullscreenButton);
        });
    }

    private void exitFullscreen(VideoView videoView, ImageButton fullscreenButton) {
        videoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        fullscreenButton.setImageResource(android.R.drawable.ic_menu_crop);
        fullscreenButton.setOnClickListener(v -> {
            setupFullscreen(videoView, fullscreenButton);
        });
    }

    private void setupCloseButton(ImageButton closeButton, AlertDialog alertDialog) {
        closeButton.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    private void hideLoadingSpinner(ProgressBar loadingSpinner) {
        handler.postDelayed(() -> {
            loadingSpinner.setVisibility(View.GONE);
        }, 3000);
    }

    public void setupSeekBarListener(SeekBar seekBar, VideoView videoView){

        int DELAY_DURATION = 1000;

        if (seekBar != null) {
            seekBar.setMax(videoView.getDuration());
            //seekBar.setProgress((int) ((float) videoView.getCurrentPosition() / videoView.getDuration() * 100));

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    seekBar.setProgress((int) ((float)videoView.getCurrentPosition()));
                    handler.postDelayed(this, DELAY_DURATION);
                }
            }, 0);



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            onProgressChanged(seekBar, seekBar.)

            }
        }
    }

    private void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
        // Si el cambio en el valor de la seekBar provino del usuario, actualizamos la posición actual
        if (fromUser) {
            mCurrentPosition = progress;
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        // No hacemos nada al empezar a mover la seekBar
    }

    public void onStopTrackingTouch(SeekBar seekBar, VideoView videoView) {
        // Cuando se deja de mover la seekBar, actualizamos la posición actual y saltamos a esa posición en el video
        mCurrentPosition = seekBar.getProgress();
        videoView.seekTo(mCurrentPosition);
    }

    public void updateSeekBarPosition(VideoView videoView, SeekBar seekBar){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (videoView.isPlaying()) {
                    mCurrentPosition = videoView.getCurrentPosition();
                    seekBar.setProgress(mCurrentPosition);
                }
                handler.postDelayed(this, 1000); // actualizamos la seekBar cada segundo
            }
        }, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }




}