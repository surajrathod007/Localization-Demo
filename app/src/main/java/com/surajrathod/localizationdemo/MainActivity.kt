package com.surajrathod.localizationdemo

import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.LocaleList
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.LocaleManagerCompat
import com.surajrathod.localizationdemo.databinding.ActivityMainBinding
import java.lang.Exception
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    //lateinit var localeManager: LocaleManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // val po = savedInstanceState?.getInt("position") ?: 0

        //localeManager = getSystemService(Context.LOCALE_SERVICE) as LocaleManager
        // val currentLocale = localeManager.applicationLocales.
        //Toast.makeText(this, po.toString(), Toast.LENGTH_LONG).show()
        setupSpinner()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnStartActivity.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }
    }

    private fun setupSpinner(position: Int = 0) {
        val supportedLanguages = listOf("Select Language", "en", "fr", "nl", "de", "it", "es")
        val adp =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, supportedLanguages)
        binding.spLanguages.adapter = adp
        binding.spLanguages.setSelection(position)
        binding.spLanguages.onItemSelectedListener = object : OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val v = p1 as TextView
                if (!p1.text.toString().equals(supportedLanguages[0])) {
                    setLocale(v.text.toString())
                    finish()
//                    val i = Intent(this@MainActivity, MainActivity2::class.java)
//                    val b = Bundle()
//                    b.putInt("position",p0?.selectedItemPosition ?: 0)
//                    savedStateRegistry.performSave(b)
                    startActivity(intent)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    }

    fun getCurrentLocale(): Locale {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return getResources().getConfiguration().locale;
        }
    }

    fun setLocale(code: String) {
        try {
            val l = Locale(code)
            val config = resources.configuration
            config.setLocale(l)
            resources.updateConfiguration(config, resources.displayMetrics)
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }
}