package com.example.module_mobile_xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.module_mobile_xml.databinding.ArrayFragmentBinding

class ArrayFragment : Fragment() {

    lateinit var binding: ArrayFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ArrayFragmentBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArrayFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun makeBlockVar(arrayName: String?, capacity: String?) {
            val layout = activity?.findViewById<LinearLayout>(R.id.codePlace)
            val block = TextView(context)

            with(block) {
                text = "ARRAY: $arrayName($capacity)"

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
                scrollView.scrollTo(0, scrollView.bottom)
            }
        }

        binding.createBlockButton.setOnClickListener {

            val arrayName = binding.nameHolder.text.toString()
            val capacity = binding.dataHolder.text.toString()

            if (arrayName.length != 0 && capacity.length != 0 && !arrayName.first().isDigit() && arrayName !in str) {
                makeBlockVar(arrayName, capacity)
                for (i in 0 until capacity.toInt()) {
                    val temp = "$arrayName$i 0 = "
                    str += temp
                }
                if (arrayName !in arrayNames){
                    arrayNames.add(arrayName)
                }
                lastBlock.add(Pair(lastBlock.size + 1, str))
            }

            activity?.getSupportFragmentManager()?.beginTransaction()?.remove(this)?.commit()
        }
    }
}