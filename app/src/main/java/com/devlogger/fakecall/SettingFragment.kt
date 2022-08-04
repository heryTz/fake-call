package com.devlogger.fakecall

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.button.MaterialButton

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {

    private var voiceListView: ListView? = null
    private var voiceAdapter: VoiceAdapter? = null
    private var store: Store? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        val files = activity?.fileList()?.toMutableList() ?: mutableListOf()

        val sharedPref: SharedPreferences = activity?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE) as SharedPreferences

        store = Store(files, sharedPref, getString(R.string.voice_selected_key), requireActivity())

        voiceListView = view.findViewById(R.id.voice_list) as ListView

        voiceAdapter = activity?.let { VoiceAdapter(it, store!!) }
        voiceListView?.adapter = voiceAdapter

        val addButton = view.findViewById(R.id.add) as MaterialButton
        addButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "audio/*"
            }
            getResult.launch(intent)
        }

        return view
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            it.data?.data?.let { uri ->
                val fileStream = activity?.contentResolver?.openInputStream(uri)
                val cursor = activity?.contentResolver?.query(uri, null, null, null, null)
                if (cursor != null) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    cursor.moveToFirst()
                    val filename = cursor.getString(nameIndex)
                    if (store?.canAddNewVoice(filename) == true) {
                        store?.addVoice(filename)
                        voiceAdapter?.notifyDataSetChanged()
                        activity?.openFileOutput(filename, Context.MODE_PRIVATE).use { file ->
                            file?.write(fileStream?.readBytes())
                        }
                    }
                }
            }
        }
    }
}
