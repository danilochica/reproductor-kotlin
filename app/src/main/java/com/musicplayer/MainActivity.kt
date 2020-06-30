package com.musicplayer

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playMusic()
    }

    private fun playMusic() {
        var mediaPlayer = MediaPlayer.create(this, R.raw.funk)
        var audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        var maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

        btnPlay.setOnClickListener { mediaPlayer.start() }
        btnPause.setOnClickListener { mediaPlayer.pause() }

        seekVolumen.setMax(maxVolume)
        seekVolumen.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar): Unit {}

            override fun onStopTrackingTouch(seekBar: SeekBar): Unit {}
        })

        musicSeekBar.max = mediaPlayer.duration
        musicSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                Log.i("Pista", "Valor: $progress ")

                if(fromUser)
                    mediaPlayer.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar): Unit {}

            override fun onStopTrackingTouch(seekBar: SeekBar): Unit {}
        })

        Timer().scheduleAtFixedRate(0,1000){
            musicSeekBar.progress = mediaPlayer.currentPosition
        }

    }


}