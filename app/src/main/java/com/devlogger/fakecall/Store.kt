package com.devlogger.fakecall

import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity

class Store(
    var voices: MutableList<String>,
    var sharedPref: SharedPreferences,
    var selectedVoiceRessource: String,
    var activity: FragmentActivity
) {

    var selectedVoice: String? = null
        get() = sharedPref.getString(selectedVoiceRessource, null)
        set(voice) {
            with(sharedPref.edit()) {
                field = voice
                this?.putString(selectedVoiceRessource, voice)
                this?.apply()
            }
        }

    fun canAddNewVoice(new: String): Boolean {
        if (voices == null) return true
        return voices?.contains(new) != null
    }

    fun addVoice(new: String) {
        voices?.add(0, new)
    }

    fun removeVoice(voice: String) {
        voices.remove(voice)
        activity.deleteFile(voice)
    }

}