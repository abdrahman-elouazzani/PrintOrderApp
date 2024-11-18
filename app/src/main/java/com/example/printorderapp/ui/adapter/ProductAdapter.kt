package com.example.printorderapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.printorderapp.databinding.AdapterProductItemBinding
import com.example.printorderapp.domaine.model.Product

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var products: List<Product> = emptyList()

    inner class ProductViewHolder(val binding: AdapterProductItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = AdapterProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.apply {
            val product = products[position]
            tvProductQt.text = "${product.quantity} x "
            tvProductName.text = product.name.orEmpty()
            tvProductPrice.text = product.price.toString()
        }
    }

    fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

}