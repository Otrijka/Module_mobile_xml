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
import com.example.module_mobile_xml.databinding.IfOperatorFragmentBinding
import com.example.module_mobile_xml.databinding.WhileFragmentBinding

class WhileFragment : Fragment() {

    lateinit var binding: WhileFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WhileFragmentBinding.inflate(inflater)

        val conditions = arrayListOf("<", ">", ">=", "<=", "==")
        val conditionAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, conditions)
        val varAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, varNames)
        binding.varSpinner.adapter = varAdapter
        binding.conditionSpinner.adapter = conditionAdapter

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = WhileFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun makeBlockVar(varName1: String?, condition: String?, varName2: String?) {

            val parentLayout = activity?.findViewById<LinearLayout>(R.id.codePlace)
            val block = TextView(context)

            with(block) {
                text = "while ($varName1 $condition $varName2)"

                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                params.setMargins(0, 0, 0, 50)

                layoutParams = params
                setBackgroundResource(R.drawable.while_block)
                setTextSize(resources.getDimension(R.dimen.block_text_size))
                setPadding(15, 15, 15, 15)
            }

            parentLayout?.addView(block)

            val scrollView = activity?.findViewById<ScrollView>(R.id.scrollView)
            scrollView!!.post {
                scrollView.scrollTo(0,scrollView.bottom)
            }
        }


        binding.createBlockButton.setOnClickListener {
            val var1 = binding.varSpinner.selectedItem.toString()
            val var2 = binding.var2Input.text.toString().trim()
            val condition = binding.conditionSpinner.selectedItem.toString()
            if ((var1 in varNames && var2 in varNames) || (var1.isDigitsOnly() && var2 in varNames) || (var1 in varNames && var2.isDigitsOnly()) || (var1.isDigitsOnly() && var2.isDigitsOnly()) && (!var1.equals("") && !var2.equals(""))) {
                val temp = "{$var1 $var2 $condition} [ "
                makeBlockVar(var1, condition, var2)
                str+=temp
                lastBlock.add(Pair(lastBlock.size + 1,str))
                activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
            } else {
                activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
            }
        }


    }
}