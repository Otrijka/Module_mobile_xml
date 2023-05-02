package com.example.module_mobile_xml

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.module_mobile_xml.databinding.CreateVariableFragmentBinding

class CreateVariableFragment : Fragment() {

    lateinit var binding: CreateVariableFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateVariableFragmentBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreateVariableFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun makeBlockVar(varName: String?, varData: String?) {
            val layout = activity?.findViewById<LinearLayout>(R.id.codePlace)
            val block = TextView(context)

            with(block) {
                text = "$varName = $varData"

                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                params.setMargins(10, 10, 10, 10)

                layoutParams = params
                setTextColor(Color.BLUE)
                setBackgroundResource(R.drawable.button_start_border)
                setTextSize(22f)
                setPadding(25, 20, 25, 20)
            }

            layout?.addView(block)
        }

        binding.createVariableButton.setOnClickListener {

            val varName = binding.nameHolder.text.toString()
            val varData = binding.dataHolder.text.toString()

            if (varName.length != 0 && varData.length != 0) {
                makeBlockVar(varName, varData)
            }
            activity?.getSupportFragmentManager()?.beginTransaction()?.remove(this)?.commit()
        }
    }
}