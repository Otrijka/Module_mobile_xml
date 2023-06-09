package com.example.module_mobile_xml

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.module_mobile_xml.databinding.StartMenuActivityBinding

class StartMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = StartMenuActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(this, MainActivity::class.java)

        binding.startButton.setOnClickListener {
            varNames.clear()
            arrNames.clear()
            arrNamesWithIndexies.clear()
            variablesMap.clear()
            str = ""
            lastBlock.clear()
            outPutList.clear()
            startActivity(intent)
            overridePendingTransition(
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_slide_out_top
            )
        }
    }
}