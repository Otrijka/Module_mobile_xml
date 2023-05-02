package com.example.module_mobile_xml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.module_mobile_xml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Листенеры на элементы менюшки
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.varInteger -> {
                    binding.drawer.closeDrawer(GravityCompat.END)
                    openFragment(CreateVariableFragment.newInstance(), R.id.placeHolder)
                }
            }
            true
        }

    }

    private fun openFragment(fragment: Fragment, idHolder: Int) {
        supportFragmentManager.beginTransaction().replace(idHolder, fragment).commit()
    }
}
