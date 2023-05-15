package com.example.module_mobile_xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import checkInMapKeys
import com.example.module_mobile_xml.databinding.MathematicArrayFragmentBinding
import normilizeString
import toReversePolishNotation

class MathematicArrayFragment : Fragment() {

    lateinit var binding: MathematicArrayFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MathematicArrayFragmentBinding.inflate(inflater)

        fun makeBlockMathExp(arrName: String, index: String, expression: String) {

            val layout = activity?.findViewById<LinearLayout>(R.id.codePlace)
            val block = TextView(context)


            with(block) {
                text = "$arrName[$index] = $expression"

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

            val scrollView = activity?.findViewById<ScrollView>(R.id.scrollView)
            scrollView!!.post {
                scrollView.scrollTo(0, scrollView.bottom)
            }
        }

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrayNames)
        binding.varMenu.adapter = adapter

        binding.createBlockButton.setOnClickListener {
            try {
                val arrName = binding.varMenu.selectedItem.toString()
                var inputExp = binding.mathExpInput.text.toString()

                val index = binding.indexInput.text.toString()

                if (inputExp.length != 0) {

                    var newInputExp = inputExp.replace("[", " ").replace("]", " ")
                    newInputExp = checkInMapKeys(newInputExp)
                    newInputExp = normilizeString(newInputExp)
                    inputExp = normilizeString(inputExp)
                    val temp = "$index $arrName ${toReversePolishNotation(newInputExp)} = "
                    str += temp
                    makeBlockMathExp(arrName, index, inputExp)
                    lastBlock.add(Pair(lastBlock.size + 1, str))
                    activity?.getSupportFragmentManager()?.beginTransaction()?.remove(this)
                        ?.commit()
                } else {

                    activity?.getSupportFragmentManager()?.beginTransaction()?.remove(this)
                        ?.commit()
                }
            } catch (e: java.lang.Exception) {
                activity?.getSupportFragmentManager()?.beginTransaction()?.remove(this)?.commit()
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MathematicArrayFragment()
    }

}