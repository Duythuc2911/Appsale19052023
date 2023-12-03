package com.example.appsale19052023.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.appsale19052023.R
import com.example.appsale19052023.data.api.AppResource
import com.example.appsale19052023.data.model.Cart
import com.example.appsale19052023.presentation.view.adapter.ProductAdapter
import com.example.appsale19052023.presentation.viewmodel.ProductViewModel
import com.example.appsale19052023.util.ToastUtils

class ProductActivity : AppCompatActivity() {
    private lateinit var btnDetail: LinearLayout
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var layoutLoading: LinearLayout
    private var cartItemArea: FrameLayout? = null
    private var textBadge: TextView? = null
    private var toolBar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        

        productViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductViewModel(this@ProductActivity) as T
            }
        })[ProductViewModel::class.java]
        initView()
        observerData()
        event()
    }

    private fun event() {
        productViewModel.executeGetListProducts()
        productViewModel.executeGetCart()

        productAdapter.setOnItemClickFood(object : ProductAdapter.OnItemClickProduct{
            override fun onClick(type: Int, position: Int) {
               if(type == ProductAdapter.TYPE_ADD){
                   addProduct(position)
               }else if(type == ProductAdapter.TYPE_DETAIL){
                   gotoDetail(position)
               }
            }
        })
    }

    private fun gotoDetail(position: Int) {
        val product = productAdapter.getListProducts().getOrNull(index = position)

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("KEY_DATA", product!!)
        startActivity(intent)
    }

    private fun addProduct(position: Int) {
        val idProduct = productAdapter.getListProducts().getOrNull(index = position)?.id ?: ""
        if (idProduct.isNotEmpty()) {
            productViewModel.executeAddToCart(idProduct)
        }
    }

    private fun observerData() {
        productViewModel.getLoading().observe(this) {
            layoutLoading.isGone = !it
        }

        productViewModel.getListProducts().observe(this) {
            when (it) {
                is AppResource.Success -> productAdapter.updateListProduct(it.data)
                is AppResource.Error -> ToastUtils.showToast(this, it.error)
            }
        }

        productViewModel.getCart().observe(this) {
            when (it) {
                is AppResource.Success -> updateBadge(it.data ?: Cart())
                is AppResource.Error -> ToastUtils.showToast(this, it.error)
            }
        }
    }

    private fun initView() {

        productRecyclerView = findViewById(R.id.recycler_view_product)
        layoutLoading = findViewById(R.id.layout_loading)
        toolBar = findViewById(R.id.toolbar_home)
        productAdapter = ProductAdapter(context = this)
        productRecyclerView.adapter = productAdapter
        productRecyclerView.setHasFixedSize(true)
        setSupportActionBar(toolBar)
        supportActionBar?.title = "Product"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product, menu)
        val rootView = menu?.findItem(R.id.item_menu_cart)?.actionView
        cartItemArea = rootView?.findViewById<FrameLayout>(R.id.frame_layout_cart_area)
        textBadge = rootView?.findViewById<TextView>(R.id.text_cart_badge)

        cartItemArea?.setOnClickListener{
            ToastUtils.showToast(this@ProductActivity, "Click cart")
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_menu_history_order -> {
                ToastUtils.showToast(this@ProductActivity, "Click history card")
            }
        }

        return true
    }


    private fun updateBadge(cart: Cart) {
        val totalProduct = cart.listProduct.size
        if (totalProduct == 0) {
            textBadge?.isGone = true
        } else {
            textBadge?.isVisible = true
            textBadge?.text = cart.listProduct
                .map { it.quantity }
                .reduce { acc, quantity -> acc + quantity }
                .toString()
        }
    }
}