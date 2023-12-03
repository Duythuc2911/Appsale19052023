package com.example.appsale19052023.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsale19052023.R
import com.example.appsale19052023.common.AppConstant
import com.example.appsale19052023.data.model.Product
import com.example.appsale19052023.util.StringUtils

class ProductAdapter(
    private var listProducts: MutableList<Product> = mutableListOf(),
    private var context: Context? = null,
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    companion object{
        const val TYPE_ADD: Int = 1
        const val TYPE_DETAIL: Int = 2
    }
    private var onItemClickProduct: OnItemClickProduct? = null

    fun updateListProduct(data: List<Product>?) {
            if (listProducts.size > 0) {
                listProducts.clear()
            }
            listProducts.addAll(data!!)
            notifyDataSetChanged()
    }

    fun getListProducts(): List<Product> {
        return listProducts
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(context, listProducts[position])
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var buttonDetails = view.findViewById<LinearLayout>(R.id.button_detail)
        private var buttonAdd = view.findViewById<LinearLayout>(R.id.button_add)
        private var textViewName = view.findViewById<TextView>(R.id.textViewName)
        private var textViewAddress = view.findViewById<TextView>(R.id.textViewAddress)
        private var textViewPrice = view.findViewById<TextView>(R.id.textViewPrice)
        private var imageView = view.findViewById<ImageView>(R.id.imageView)
        init {
            buttonAdd.setOnClickListener {
                if (onItemClickProduct != null) {
                    onItemClickProduct?.onClick(TYPE_ADD, adapterPosition)
                }
            }

            buttonDetails.setOnClickListener {
                if (onItemClickProduct != null) {
                    onItemClickProduct?.onClick(TYPE_DETAIL, adapterPosition)
                }
            }
        }

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

    fun setOnItemClickFood(onItemClickProduct: OnItemClickProduct?) {
        this.onItemClickProduct = onItemClickProduct
    }

    interface OnItemClickProduct {
        fun onClick(type: Int, position: Int)
    }
}