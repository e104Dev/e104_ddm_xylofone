package br.com.trajanocamargo.e104_ddm_xylofone

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseIntArray
import android.view.View

class MainActivity : AppCompatActivity() {

    var soundMap: SparseIntArray = SparseIntArray()
    lateinit var audioManager: AudioManager
    var soundPool: SoundPool? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        soundPool = SoundPool.Builder()
            .setMaxStreams(1).setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            ).build()

        configMapSoundResource()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool?.release()
        soundPool = null
    }

    fun playSound(view: View) {
        //TODO: Exibir identificador do botão que disparou o evento
        Log.d("BUTTON: ", view.id.toString())

        val idStream: Int = soundMap.get(view.id)

        //TODO: executar o som do Xylophone
        if (idStream > 0) {
            val streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()
            val streamMaxVolume =
                audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat()
            val volume = streamVolume / streamMaxVolume

            soundPool?.play(idStream, volume, volume, 1, 0, 1.0F)
        }

    }

    fun configMapSoundResource() {
        soundPool?.load(applicationContext, R.raw.note1, 1)?.let { soundMap.put(R.id.button1, it) }
        soundPool?.let { soundMap.put(R.id.button2, it.load(applicationContext, R.raw.note2, 1)) }
        soundPool?.let { soundMap.put(R.id.button3, it.load(applicationContext, R.raw.note3, 1)) }
        soundPool?.let { soundMap.put(R.id.button4, it.load(applicationContext, R.raw.note4, 1)) }
        soundPool?.let { soundMap.put(R.id.button5, it.load(applicationContext, R.raw.note5, 1)) }
        soundPool?.let { soundMap.put(R.id.button6, it.load(applicationContext, R.raw.note6, 1)) }
        soundPool?.let { soundMap.put(R.id.button7, it.load(applicationContext, R.raw.note7, 1)) }
    }
}