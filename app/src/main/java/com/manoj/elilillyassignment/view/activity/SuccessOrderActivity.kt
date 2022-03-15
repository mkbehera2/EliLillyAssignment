package com.manoj.elilillyassignment.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.manoj.elilillyassignment.databinding.ActivitySuccessScreenBinding

class SuccessOrderActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySuccessScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDismiss.setOnClickListener {
            finish()
        }
    }

}