package com.example.module_mobile_xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import checkNameInArrayNames
import checkNameInVarNames
import com.example.module_mobile_xml.databinding.PrintArrayFragmentBinding

class PrintArrayFragment : Fragment() {

    lateinit var binding: PrintArrayFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PrintArrayFragmentBinding.inflate(inflater)

        fun makeBlockPrintVar(arrayName: String?) {
            val layout = activity?.findViewById<LinearLayout>(R.id.codePlace)
            val block = TextView(context)

            with(block) {
                text = "Print($arrayName)"

                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                params.setMargins(0, 0, 0, 25)

                layoutParams = params
                setBackgroundResource(R.drawable.print_variable_block)
                setTextSize(resources.getDimension(R.dimen.block_text_size))

                setPadding(15, 15, 15, 15)
            }

            layout?.addView(block)

            val scrollView = activity?.findViewById<ScrollView>(R.id.scrollView)
            scrollView!!.post {
                scrollView.scrollTo(0,scrollView.bottom)
            }
        }

        val varListForPrint = binding.arrayList
        checkNameInArrayNames()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrayNames)
        varListForPrint.adapter = adapter

        varListForPrint.setOnItemClickListener { parent, view, position, id ->
            val selectedItemName = parent.getItemAtPosition(position) as String
            activity?.getSupportFragmentManager()?.beginTransaction()?.remove(this)?.commit()
            makeBlockPrintVar(selectedItemName)
            val temp = "$selectedItemName printArray "
            str += temp
            lastBlock.add(Pair(lastBlock.size + 1,str))
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = PrintArrayFragment()
    }
}