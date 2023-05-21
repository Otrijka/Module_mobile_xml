package com.example.module_mobile_xml

import android.content.ClipData.Item
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import checkNames
import com.example.module_mobile_xml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fun makeCustomBlock(
            operatorName: String,
            backgroundID: Int,
            textSize: Int,
            marginBottom: Int,
            marginTop: Int
        ) {

            val parentLayout = findViewById<LinearLayout>(R.id.codePlace)
            val block = TextView(this)

            with(block) {
                text = operatorName

                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                params.setMargins(0, marginTop, 0, marginBottom)

                layoutParams = params
                setBackgroundResource(backgroundID)
                setTextSize(resources.getDimension(textSize))
                setPadding(15, 15, 15, 15)
            }

            parentLayout?.addView(block)

            val scrollView = binding.scrollView
            scrollView.post {
                scrollView.scrollTo(0,scrollView.bottom)
            }
            block.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fall_block))
        }

        //Листенеры на элементы drawer
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.varInteger -> {
                    openFragment(CreateVariableFragment.newInstance())
                }
                R.id.arrayInteger -> {
                    openFragment(CreateArrayFragment.newInstance())
                }
                R.id.printVar -> {
                    openFragment(PrintVariableFragment.newInstance())
                }
                R.id.printArr ->{
                    openFragment(PrintArrayFragment.newInstance())
                }

                R.id.mathExpressionVariable -> {
                    openFragment(MathematicFragment.newInstance())
                }
                R.id.mathExpressionArray -> {
                    openFragment(MathematicArrayFragment.newInstance())
                }
                R.id.ifOperator -> {
                    openFragment(IfOperatorFragment.newInstance())
                }
                R.id.whileOperator -> {
                    openFragment(WhileFragment.newInstance())
                }
                R.id.forOperator ->{
                    openFragment(ForFragment.newInstance())
                }
                R.id.endWhile -> {
                    makeCustomBlock(
                        "endWhile",
                        R.drawable.while_block,
                        R.dimen.little_block_text_size,
                        25,
                        25
                    )
                    str += "] endWhile "
                    lastBlock.add(Pair(lastBlock.size + 1, str))

                    val scrollView = binding.scrollView
                    scrollView.post {
                        scrollView.scrollTo(0, scrollView.bottom)
                    }

                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.endFor ->{
                    makeCustomBlock(
                        "endFor",
                        R.drawable.for_block,
                        R.dimen.little_block_text_size,
                        25,
                        25
                    )
                    str += "] endFor "
                    lastBlock.add(Pair(lastBlock.size + 1, str))

                    val scrollView = binding.scrollView
                    scrollView.post {
                        scrollView.scrollTo(0, scrollView.bottom)
                    }

                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.thenOperator -> {
                    makeCustomBlock(
                        "then",
                        R.drawable.then_block,
                        R.dimen.little_block_text_size,
                        25,
                        0
                    )
                    str += "[ "
                    lastBlock.add(Pair(lastBlock.size + 1, str))

                    val scrollView = binding.scrollView
                    scrollView.post {
                        scrollView.scrollTo(0, scrollView.bottom)
                    }

                    binding.drawer.closeDrawer(GravityCompat.END)
                }
                R.id.elseOperator -> {
                    makeCustomBlock(
                        "else",
                        R.drawable.else_block,
                        R.dimen.little_block_text_size,
                        25,
                        0
                    )
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
                    makeCustomBlock(
                        "endIf",
                        R.drawable.endif_block,
                        R.dimen.little_block_text_size,
                        25,
                        25,
                    )
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
                R.id.blocks_menu_button -> {
                    binding.drawer.openDrawer(GravityCompat.END)
                }
                R.id.compile_button -> {
                    variablesMap.clear()
                    compile()
                    val intent = Intent(this@MainActivity, CompilerActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(
                        androidx.appcompat.R.anim.abc_popup_enter,
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
                        checkNames()
                        lastBlock.removeLast()
                    }
                }
            }
            true
        }

    }

    //Открытие фрагмента с закрытием drawer`a
    private fun openFragment(fragment: Fragment, idHolder: Int = R.id.blockSettingsFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
        transaction.replace(idHolder, fragment)
        transaction.commit()
        binding.drawer.closeDrawer(GravityCompat.END)
    }
}
