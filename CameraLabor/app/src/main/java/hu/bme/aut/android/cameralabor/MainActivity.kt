package hu.bme.aut.android.cameralabor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import hu.bme.aut.android.cameralabor.adapter.ImagesAdapter
import hu.bme.aut.android.cameralabor.databinding.ActivityMainBinding
import hu.bme.aut.android.cameralabor.model.Image
import hu.bme.aut.android.cameralabor.network.GalleryInteractor

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var adapter: ImagesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvImages.layoutManager = GridLayoutManager(this, 2)
        binding.srlImages.setOnRefreshListener { loadImages() }
    }

    override fun onResume() {
        super.onResume()
        loadImages()
    }

    private fun loadImages() {
        val galleryInteractor = GalleryInteractor()
        galleryInteractor.getImages(onSuccess = this::showImages, onError = this::showError)
    }

    private fun showImages(images: List<Image>) {
        adapter = ImagesAdapter(applicationContext, images.toMutableList())
        binding.rvImages.adapter = adapter
        binding.srlImages.isRefreshing = false
    }

    private fun showError(e: Throwable) {
        e.printStackTrace()
        binding.srlImages.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.upload -> {
                val intent = Intent(this, UploadActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}