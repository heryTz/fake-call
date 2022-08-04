package com.devlogger.fakecall

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class VoiceAdapter(private val context: Activity, private val store: Store): ArrayAdapter<String>(context, R.layout.voice_item, store.voices as List<String>) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.voice_item, null, true)
        val rowItem = store.voices[position]

        val voiceTitle = rowView.findViewById(R.id.voice_title) as TextView
        val voiceSelect = rowView.findViewById(R.id.voice_select) as CheckBox
        val voiceDelete = rowView.findViewById(R.id.voice_delete) as ImageView

        voiceTitle.text = rowItem
        voiceSelect.isChecked = when(store.selectedVoice) {
            null -> false
            else -> rowItem == store.selectedVoice
        }

        voiceSelect.setOnCheckedChangeListener { button, checked ->
            store.selectedVoice = rowItem
            button.isChecked = checked
            notifyDataSetChanged()
        }

        voiceDelete.setOnClickListener {
            store.removeVoice(rowItem)
            notifyDataSetChanged()
        }

        return rowView
    }
}