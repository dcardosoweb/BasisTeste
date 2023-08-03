package com.basis.test.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.basis.test.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupView()
    }

    private fun setupView(){
        binding.btnPesquisar.setOnClickListener {

        }

        binding.btnAdicionar.setOnClickListener {
            val intent = Intent(this@MainActivity, FormActivity::class.java)
            startActivity(intent)
        }
    }
}
