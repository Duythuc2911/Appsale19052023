package com.example.appsale19052023.presentation.view.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appsale19052023.R
import com.example.appsale19052023.data.model.Product

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chi_tiet)

        initViews()
    }

    private fun initViews() {
        val ivProduct = findViewById<ImageView>(R.id.imgchitiet)
        val tvName = findViewById<TextView>(R.id.tensp)
        val tvPrice = findViewById<TextView>(R.id.giasp)
        val tvInfo = findViewById<TextView>(R.id.tvthongtinsanpham)

        val product  = intent.getSerializableExtra("KEY_DATA") as Product
        Glide.with(this).load("https://serverappsale.onrender.com/${product.img}").into(ivProduct)
        tvName.text = product.name
        tvPrice.text = "${product.price}"
        tvInfo.text = product.name
    }
}