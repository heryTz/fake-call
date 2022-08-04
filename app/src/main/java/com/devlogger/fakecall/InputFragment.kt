package com.devlogger.fakecall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class InputFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        val setting = view.findViewById(R.id.setting) as ImageView
        val inputNumber = view.findViewById(R.id.input_number) as TextView
        val backspace = view.findViewById(R.id.backspace) as ImageView
        val fabCall = view.findViewById(R.id.fab_call) as FloatingActionButton
        val button0 = view.findViewById(R.id.button0) as Button
        val button1 = view.findViewById(R.id.button1) as Button
        val button2 = view.findViewById(R.id.button2) as Button
        val button3 = view.findViewById(R.id.button3) as Button
        val button4 = view.findViewById(R.id.button4) as Button
        val button5 = view.findViewById(R.id.button5) as Button
        val button6 = view.findViewById(R.id.button6) as Button
        val button7 = view.findViewById(R.id.button7) as Button
        val button8 = view.findViewById(R.id.button8) as Button
        val button9 = view.findViewById(R.id.button9) as Button
        val buttonStar = view.findViewById(R.id.buttonStar) as Button
        val buttonSharp = view.findViewById(R.id.buttonSharp) as Button

        arrayOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonSharp, buttonStar).forEach {  button ->
            button.setOnClickListener {
                val b = it as Button
                inputNumber.text = "${inputNumber.text}${b.text}"
            }
        }

        backspace.setOnClickListener {
            inputNumber.text = inputNumber.text.dropLast(1)
        }

        fabCall.setOnClickListener {
            val bundle = bundleOf("inputValue" to inputNumber.text)
            view.findNavController().navigate(R.id.callFragment, bundle)
        }

        setting.setOnClickListener {
            view.findNavController().navigate(R.id.settingFragment)
        }

        return view
    }
}