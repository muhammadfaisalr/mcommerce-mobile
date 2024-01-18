package id.muhammadfaisal.mcommerce.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.muhammadfaisal.mcommerce.databinding.ActivityAddProductBinding
import id.muhammadfaisal.mcommerce.viewmodel.InventoryViewModel

@AndroidEntryPoint
class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding

    private val viewModel: InventoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityAddProductBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        this.setup()
    }

    private fun setup() {
        this.binding.viewModel = this.viewModel
        this.viewModel.loading.observe(this) {
            if (it) {
                this.binding.progressIndicator.show()
            } else {
                this.binding.progressIndicator.hide()
            }
        }

        this.viewModel.isUploadSuccess.observe(this) {
            if (it) {
                finish()
            }
        }
    }
}