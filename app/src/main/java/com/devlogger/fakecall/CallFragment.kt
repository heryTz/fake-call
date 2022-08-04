package com.devlogger.fakecall

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ARG_INPUT_VALUE = "inputValue"

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class CallFragment : Fragment() {
    private var inputValue: String? = null
    private var mediaPlayer: MediaPlayer? = null
    var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            inputValue = it.getString(ARG_INPUT_VALUE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_call, container, false)

        val showNumber = view.findViewById(R.id.show_number) as TextView
        showNumber.text = inputValue

        sharedPref = activity?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val currentVoice = sharedPref?.getString(getString(R.string.voice_selected_key), null)

        if (currentVoice != null) {
            val uri = Uri.parse("/data/data/${activity?.packageName}/files/$currentVoice")
            mediaPlayer = MediaPlayer.create(context, uri)
            mediaPlayer?.start()
            mediaPlayer?.isLooping = true
        }

        val callEnd = view.findViewById(R.id.fab_call_end) as FloatingActionButton
        callEnd.setOnClickListener {
            mediaPlayer?.stop()
            activity?.onBackPressed()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
    }
}