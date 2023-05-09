package com.example.module_mobile_xml

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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
                text = "VAR: $varName = $varData"

                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                params.setMargins(0, 0, 0, 25)

                layoutParams = params
                setBackgroundResource(R.drawable.variable_block)
                setTextSize(resources.getDimension(R.dimen.block_text_size))
                setPadding(15, 15, 15, 15)
            }

            layout?.addView(block)
        }

        binding.createBlockButton.setOnClickListener {

            val varName = binding.nameHolder.text.toString()
            val varData = binding.dataHolder.text.toString()

            if (varName.length != 0 && varData.length != 0 && !varName.first().isDigit()) {
                makeBlockVar(varName, varData)
                val temp = "$varName $varData = "
                str+=temp
                if (varName !in varNames){
                    varNames.add(varName)
                }
                //variablesMap.put(varName,varData.toLong())

                lastBlock.add(Pair(lastBlock.size + 1, str))
            }

            activity?.getSupportFragmentManager()?.beginTransaction()?.remove(this)?.commit()
        }
    }
}