package com.example.module_mobile_xml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.module_mobile_xml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Листенеры на элементы drawer
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.varInteger -> {
                    openFragment(CreateVariableFragment.newInstance(), R.id.blockSettingsFragment)
                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.printVar -> {
                    openFragment(PrintVariableFragment.newInstance(), R.id.blockSettingsFragment)
                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.mathExpression ->{
                    openFragment(MathematicFragment.newInstance(),R.id.blockSettingsFragment)
                    binding.drawer.closeDrawer(GravityCompat.END)
                }
            }
            true
        }

        //Листенеры на элементы bottomNav
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.blocks_button -> {
                    binding.drawer.openDrawer(GravityCompat.END)
                }
                R.id.compile_button ->{
                    compile()
                }
            }
            true
        }

    }

    private fun openFragment(fragment: Fragment, idHolder: Int) {
        supportFragmentManager.beginTransaction().replace(idHolder, fragment).commit()
    }
}
