package com.example.module_mobile_xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.module_mobile_xml.databinding.PrintVariableFragmentBinding

class PrintVariableFragment : Fragment() {

    lateinit var binding: PrintVariableFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PrintVariableFragmentBinding.inflate(inflater)

        fun makeBlockPrintVar(varName: String?) {
            val layout = activity?.findViewById<LinearLayout>(R.id.codePlace)
            val block = TextView(context)

            with(block) {
                text = "Print($varName)"

                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                params.setMargins(10, 0, 0, 25)

                layoutParams = params
                setBackgroundResource(R.drawable.print_variable_block)
                setTextSize(resources.getDimension(R.dimen.block_text_size))

                setPadding(15, 15, 15, 15)
            }

            layout?.addView(block)
        }

        val varListForPrint = binding.varList

        var varNames = variablesMap.keys.toList()

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, varNames)
        varListForPrint.adapter = adapter

        varListForPrint.setOnItemClickListener { parent, view, position, id ->
            val selectedItemName = parent.getItemAtPosition(position) as String
            activity?.getSupportFragmentManager()?.beginTransaction()?.remove(this)?.commit()
            makeBlockPrintVar(selectedItemName)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = PrintVariableFragment()
    }
}