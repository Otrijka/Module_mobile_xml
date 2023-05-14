package com.example.module_mobile_xml

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.module_mobile_xml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fun makeCustomBlock(operatorName: String, backgroundID: Int, textSize: Int) {

            val parentLayout = findViewById<LinearLayout>(R.id.codePlace)
            val block = TextView(this)

            with(block) {
                text = operatorName

                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                params.setMargins(0, 0, 0, 25)

                layoutParams = params
                setBackgroundResource(backgroundID)
                setTextSize(resources.getDimension(textSize))
                setPadding(15, 15, 15, 15)
            }

            parentLayout?.addView(block)
        }
        //Листенеры на элементы drawer
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.varInteger -> {
                    openFragment(CreateVariableFragment.newInstance(), R.id.blockSettingsFragment)
                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.arrayInteger -> {
                    openFragment(ArrayFragment.newInstance(), R.id.blockSettingsFragment)
                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.printVar -> {
                    openFragment(PrintVariableFragment.newInstance(), R.id.blockSettingsFragment)
                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.printArray ->{
                    openFragment(PrintArrayFragment.newInstance(), R.id.blockSettingsFragment)
                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.mathExpression -> {
                    openFragment(MathematicFragment.newInstance(), R.id.blockSettingsFragment)
                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.mathExpressionArray ->{
                    openFragment(MathematicArrayFragment.newInstance(), R.id.blockSettingsFragment)
                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.ifOperator -> {
                    openFragment(IfOperatorFragment.newInstance(), R.id.blockSettingsFragment)
                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.whileOperator -> {
                    openFragment(WhileFragment.newInstance(), R.id.blockSettingsFragment)
                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.endWhile -> {
                    makeCustomBlock(
                        "endWhile",
                        R.drawable.while_block,
                        R.dimen.little_block_text_size
                    )
                    str += "] endWhile "
                    lastBlock.add(Pair(lastBlock.size + 1, str))

                    val scrollView = binding.scrollView
                    scrollView.post {
                        scrollView.scrollTo(0, scrollView.bottom)
                    }

                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.thenOperator -> {
                    makeCustomBlock("then", R.drawable.then_block, R.dimen.little_block_text_size)
                    str += "[ "
                    lastBlock.add(Pair(lastBlock.size + 1, str))

                    val scrollView = binding.scrollView
                    scrollView.post {
                        scrollView.scrollTo(0, scrollView.bottom)
                    }

                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.elseOperator -> {
                    makeCustomBlock("else", R.drawable.else_block, R.dimen.little_block_text_size)
                    str += "] [ "
                    lastBlock.add(Pair(lastBlock.size + 1, str))

                    val scrollView = binding.scrollView
                    scrollView.post {
                        scrollView.scrollTo(0, scrollView.bottom)
                    }

                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.endIf -> {
                    str += "] endIf "
                    makeCustomBlock("endIf", R.drawable.endif_block, R.dimen.little_block_text_size)
                    lastBlock.add(Pair(lastBlock.size + 1, str))

                    val scrollView = binding.scrollView
                    scrollView.post {
                        scrollView.scrollTo(0, scrollView.bottom)
                    }

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
                R.id.compile_button -> {
                    variablesMap.clear()
                    try {
                        compile()
                    } catch (e: java.lang.Exception) {

                    }
                    val intent = Intent(this@MainActivity, CompilerActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(
                        androidx.appcompat.R.anim.abc_slide_in_bottom,
                        androidx.appcompat.R.anim.abc_slide_out_top
                    )
                }
                R.id.delete_last_button -> {
                    if (lastBlock.size != 0) {
                        binding.codePlace.removeViewAt(lastBlock[lastBlock.size - 1].first - 1)
                        if (lastBlock.size - 2 >= 0) {
                            str = lastBlock[lastBlock.size - 2].second
                        } else {
                            str = ""
                        }
                        lastBlock.removeLast()
                    }
                }
            }
            true
        }

    }

    private fun openFragment(fragment: Fragment, idHolder: Int) {
        supportFragmentManager.beginTransaction().replace(idHolder, fragment).commit()
    }
}
