package id.muhammadfaisal.mcommerce.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.muhammadfaisal.mcommerce.adapter.InventoryAdapter
import id.muhammadfaisal.mcommerce.databinding.ActivityHomeBinding
import id.muhammadfaisal.mcommerce.helper.GeneralHelper
import id.muhammadfaisal.mcommerce.viewmodel.InventoryViewModel

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val TAG = HomeActivity::class.simpleName

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: InventoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityHomeBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        this.setup()
    }

    private fun setup() {
        this.viewModel = ViewModelProvider(this)[InventoryViewModel::class.java]
        this.viewModel.loading.observe(this) {
            if (it) {
                this.binding.progressBar.show()
            } else {
                this.binding.progressBar.hide()
            }
        }

        this.viewModel.inventories.observe(this) {
            if (it.isNotEmpty()) {
                this.binding.recyclerView.layoutManager = LinearLayoutManager(this)
                this.binding.recyclerView.adapter = InventoryAdapter(it)
                this.binding.recyclerView.addItemDecoration(
                    DividerItemDecoration(
                        this,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }

        this.viewModel.search.observe(this) {
            this.viewModel.search(it)
        }

        this.binding.inputQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.search(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        this.binding.fabAdd.setOnClickListener {
            GeneralHelper.move(this, AddProductActivity::class.java)
        }

        this.binding.chipHistory.setOnClickListener {
            GeneralHelper.move(this, HistoryActivity::class.java)
        }

        this.binding.chipCart.setOnClickListener {
            GeneralHelper.move(this, CartActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume() called")
        this.viewModel.getInventories()
    }
}