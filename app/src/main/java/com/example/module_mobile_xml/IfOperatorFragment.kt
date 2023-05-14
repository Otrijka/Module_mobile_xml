package com.example.module_mobile_xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import checkInMapKeys
import checkOnArrayReverse
import com.example.module_mobile_xml.databinding.IfOperatorFragmentBinding

class IfOperatorFragment : Fragment() {

    lateinit var binding: IfOperatorFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IfOperatorFragmentBinding.inflate(inflater)

        val conditions = arrayListOf("==", "!=", ">", "<", ">=", "<=")
        val conditionAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, conditions)

        binding.conditionSpinner.adapter = conditionAdapter

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = IfOperatorFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun makeBlockVar(varName1: String?, condition: String?, varName2: String?) {

            val parentLayout = activity?.findViewById<LinearLayout>(R.id.codePlace)
            val block = TextView(context)

            with(block) {
                text = "if ($varName1 $condition $varName2)"

                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                params.setMargins(0, 0, 0, 25)

                layoutParams = params
                setBackgroundResource(R.drawable.if_block)
                setTextSize(resources.getDimension(R.dimen.block_text_size))
                setPadding(15, 15, 15, 15)
            }

            parentLayout?.addView(block)

            val scrollView = activity?.findViewById<ScrollView>(R.id.scrollView)
            scrollView!!.post {
                scrollView.scrollTo(0, scrollView.bottom)
            }
        }


        binding.createBlockButton.setOnClickListener {
            val var1 = binding.var1Input.text.toString()
            val var2 = binding.var2Input.text.toString()
            val condition = binding.conditionSpinner.selectedItem.toString()

            var newVar1 = var1.replace("[", " ").replace("]", "").trim()
            var newVar2 = var2.replace("[", " ").replace("]", "").trim()
            newVar1 = checkInMapKeys(newVar1)
            newVar2 = checkInMapKeys(newVar2)

            val temp = "$newVar1 $newVar2 $condition "
            str += temp
            makeBlockVar(var1, condition, var2)
            lastBlock.add(Pair(lastBlock.size + 1, str))
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }


}
