package com.example.module_mobile_xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import com.example.module_mobile_xml.databinding.ForFragmentBinding
import normilizeString
import toReversePolishNotation

class ForFragment : Fragment() {

    lateinit var binding: ForFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ForFragmentBinding.inflate(inflater)


        val conditionAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            arrayListOf("<", ">", ">=", "<=", "==")
        )
        binding.conditionSpinner.adapter = conditionAdapter

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ForFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun makeBlock(iterName: String?, condition: String?, valueStart : String ,valueTo: String?, step : String) {

            val parentLayout = activity?.findViewById<LinearLayout>(R.id.codePlace)
            val block = TextView(context)

            with(block) {
                text = "for (${iterName} = ${valueStart}; $iterName $condition $valueTo; $iterName += $step)"

                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                params.setMargins(0, 0, 0, 50)

                layoutParams = params
                setBackgroundResource(R.drawable.for_block)
                setTextSize(resources.getDimension(R.dimen.block_text_size))
                setPadding(15, 15, 15, 15)
            }

            parentLayout?.addView(block)

            val scrollView = activity?.findViewById<ScrollView>(R.id.scrollView)
            scrollView!!.post {
                scrollView.scrollTo(0, scrollView.bottom)
            }
            block.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fall_block))
        }

        binding.createBlockButton.setOnClickListener {
            val closeSettingBlock = activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
            try {
                val iteratorName = binding.iteratorNameInput.text.toString()
                val valueStart = binding.startValueInput.text.toString()
                var valueTo = binding.conditionValueInput.text.toString()
                val step = binding.stepInput.text.toString()
                val condition = binding.conditionSpinner.selectedItem.toString()

                if (iteratorName.length != 0 && valueStart.length != 0 && valueTo.length != 0 && step.length != 0){

                    makeBlock(iteratorName,condition,valueStart,valueTo,step)

                    if (iteratorName !in varNames && valueStart.isDigitsOnly()) {
                        varNames.add(iteratorName)
                        variablesMap.put(iteratorName, valueStart.toLong()-1)
                    }else{
                        varNames.add(iteratorName)
                        variablesMap.put(iteratorName, -1)
                    }

                    str += "$iteratorName ${toReversePolishNotation(normilizeString("$valueStart-1"))} = "

                    if (valueTo in varNames){
                        valueTo = (variablesMap[valueTo]!! - 1).toString()
                    }else{
                        valueTo = (valueTo.toLong() - 1).toString()
                    }


                    val temp = "{$iteratorName $valueTo $condition} [$iteratorName $iteratorName $step + = "
                    str += temp
                    lastBlock.add(Pair(lastBlock.size + 1, str))
                    closeSettingBlock
                }else{
                    closeSettingBlock
                }

            }catch (e:java.lang.Exception){
                closeSettingBlock
            }
        }

    }
}