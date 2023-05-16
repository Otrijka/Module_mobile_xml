package com.example.module_mobile_xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.module_mobile_xml.databinding.CreateArrayFragmentBinding

class CreateArrayFragment : Fragment() {

    lateinit var binding: CreateArrayFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateArrayFragmentBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreateArrayFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun makeBlockVar(arrName: String?, arrSize: String?) {
            val layout = activity?.findViewById<LinearLayout>(R.id.codePlace)
            val block = TextView(context)

            with(block) {
                text = "[]: = $arrName($arrSize)"

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

            val scrollView = activity?.findViewById<ScrollView>(R.id.scrollView)
            scrollView!!.post {
                scrollView.scrollTo(0,scrollView.bottom)
            }
        }

        binding.createBlockButton.setOnClickListener {

            val arrName = binding.nameHolder.text.toString()
            val arrSize = binding.dataHolder.text.toString()
            var startRange = binding.rangeFirstInput.text.toString()
            var endRange = binding.rangeSecondInput.text.toString()

            if (arrName.length != 0 && arrSize.length != 0 && startRange.length != 0 && endRange.length != 0 && !arrName.first().isDigit() && arrName !in arrNames && arrName !in varNames) {

                if (startRange.toInt() > endRange.toInt()){
                    val temp = endRange
                    endRange = startRange
                    startRange = temp
                }
                makeBlockVar(arrName, arrSize)
                var temp = ""
                for (i in 0 until arrSize.toInt()){
                    arrNamesWithIndexies.add("${arrName}_${i}")
                    val value = (startRange.toInt()..endRange.toInt()).random()
                    temp += "${arrName}_${i} ${value} = "
                }
                str+=temp
                arrNames.add(arrName)
                lastBlock.add(Pair(lastBlock.size + 1, str))
            }

            activity?.getSupportFragmentManager()?.beginTransaction()?.remove(this)?.commit()
        }
    }
}