package com.example.printorderapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.printorderapp.databinding.FragmentOrderDetailsBinding
import com.example.printorderapp.domaine.model.Order
import com.example.printorderapp.domaine.state.OrderState
import com.example.printorderapp.ui.adapter.ProductAdapter
import com.example.printorderapp.ui.viewmodel.OrderDetailViewModel
import com.example.printorderapp.ui.viewmodel.OrderDetailViewModelFactory
import kotlinx.coroutines.launch

const val ARG_ORDER_ID = "orderId"

class OrderDetailFragment: Fragment() {

    private var _binding: FragmentOrderDetailsBinding? = null
    private lateinit var viewModel: OrderDetailViewModel
    private var adapter: ProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, OrderDetailViewModelFactory(requireContext()))[OrderDetailViewModel::class.java]

        lifecycleScope.launch {
            // Initialize the arguments
            initArgs()

            // Initialize the recycler view for products
            initRecyclerView()

            // Observe the order state
            viewModel.orderStateLD.observe(viewLifecycleOwner) { orderState ->
                handleOrderState(orderState)
            }

            viewModel.getOrder()
        }
    }

    private fun initArgs() {
        val orderId = arguments?.getString(ARG_ORDER_ID)
        viewModel.initArgs(
            orderId = orderId
        )
    }

    private fun initRecyclerView() {
        adapter = ProductAdapter()
        _binding?.rcvProducts?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@OrderDetailFragment.adapter
        }
    }

    /**
     * Handle the state of the order and update the UI accordingly.
     */
    private fun handleOrderState(orderState: OrderState) {
        when (orderState) {
            is OrderState.Loading -> {
                _binding?.apply {
                    progressBar.visibility = View.VISIBLE
                    containerOrderDetails.visibility = View.GONE
                    tvError.visibility = View.GONE
                }
            }

            is OrderState.Success -> {
                _binding?.apply {
                    progressBar.visibility = View.GONE
                    containerOrderDetails.visibility = View.VISIBLE
                    tvError.visibility = View.GONE
                }
                printOrder(orderState.order)
            }

            is OrderState.Error -> {
                _binding?.apply {
                    progressBar.visibility = View.GONE
                    containerOrderDetails.visibility = View.GONE
                    tvError.visibility = View.VISIBLE
                    tvError.text = orderState.message
                }
            }
        }
    }

    /**
     * Print the order details to the screen.
     */
    private fun printOrder(order: Order) {
        _binding?.apply {
            tvCustomerName.text = order.customer?.fullName.orEmpty()
            tvCustomerAddress.text = order.customer?.address.orEmpty()
            adapter?.setProducts(order.products.orEmpty())
            tvTotalPrice.text = order.totalAmount.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}