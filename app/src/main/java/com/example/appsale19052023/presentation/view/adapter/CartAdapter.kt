package com.example.appsale19052023.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsale19052023.R
import com.example.appsale19052023.common.AppConstant
import com.example.appsale19052023.data.model.Product
import com.example.appsale19052023.util.StringUtils

class CartAdapter(
    private var listProducts: MutableList<Product> = mutableListOf(),
    private var context: Context? = null,
) : RecyclerView.Adapter<CartAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_cart_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(context, listProducts[position])
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var textViewName = view.findViewById<TextView>(R.id.textViewName)
        private var textViewAddress = view.findViewById<TextView>(R.id.textViewAddress)
        private var textViewPrice = view.findViewById<TextView>(R.id.textViewPrice)
        private var imageView = view.findViewById<ImageView>(R.id.imageView)

        fun bind(context: Context?, product: Product) {
            val context = context ?: return
            textViewName.text = product.name
            textViewAddress.text = product.address
            textViewPrice.text = String.format(
                "Giá: %s VND",
                StringUtils.formatCurrency(product.price.toInt())
            )
            Glide.with(context)
                .load(AppConstant.BASE_URL + product.img)
                .placeholder(R.drawable.ic_logo)
                .into(imageView)
        }
    }
}