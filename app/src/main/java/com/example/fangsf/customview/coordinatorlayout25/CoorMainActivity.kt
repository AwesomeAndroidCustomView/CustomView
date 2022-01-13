package com.example.fangsf.customview.coordinatorlayout25

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.fangsf.customview.R

class CoorMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coor_main)


    }

    fun scrollView(view: View) {
        startActivity(Intent(this, CoordinatorScrollActivity::class.java))
    }
}