package com.example.module_mobile_xml

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.module_mobile_xml.databinding.MathematicFragmentBinding
import normilizeString
import toReversePolishNotation
import java.util.Stack

class MathematicFragment : Fragment() {

    lateinit var binding: MathematicFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MathematicFragmentBinding.inflate(inflater)

        fun makeBlockMathExp(varName: String, expression: String) {

            val layout = activity?.findViewById<LinearLayout>(R.id.codePlace)
            val block = TextView(context)


            with(block) {
                text = "$varName = $expression"

                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                params.setMargins(0, 0, 0, 25)

                layoutParams = params
                setBackgroundResource(R.drawable.math_expression_block)
                setTextSize(resources.getDimension(R.dimen.block_text_size))

                setPadding(15, 15, 15, 15)
            }
            layout?.addView(block)
        }


        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, varNames)
        binding.varMenu.adapter = adapter


        binding.createBlock.setOnClickListener {
            val inputExp = normilizeString(binding.mathExpInput.text.toString())
            val varName = binding.varMenu.selectedItem.toString()

            str += varName + " " + toReversePolishNotation(inputExp) + " = "
            Log.d("app", str)
            makeBlockMathExp(varName, inputExp)
            activity?.getSupportFragmentManager()?.beginTransaction()?.remove(this)?.commit()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MathematicFragment()
    }

}