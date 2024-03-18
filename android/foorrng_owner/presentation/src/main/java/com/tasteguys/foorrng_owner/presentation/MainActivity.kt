package com.tasteguys.foorrng_owner.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tasteguys.foorrng_owner.presentation.R.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
    }
}