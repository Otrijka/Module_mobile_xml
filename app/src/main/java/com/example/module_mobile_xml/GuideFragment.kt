package com.example.module_mobile_xml

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.module_mobile_xml.databinding.CreateVariableFragmentBinding
import com.example.module_mobile_xml.databinding.GuideFragmentBinding

class GuideFragment(textId: Int) : Fragment() {
    val textId = textId
    lateinit var binding: GuideFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GuideFragmentBinding.inflate(inflater)
        binding.textView.text = resources.getText(textId)

        binding.createBlockButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(text: Int) = GuideFragment(text)
    }

}