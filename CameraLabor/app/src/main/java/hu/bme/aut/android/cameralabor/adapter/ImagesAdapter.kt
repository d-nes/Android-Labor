package hu.bme.aut.android.cameralabor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.android.cameralabor.databinding.VhImageBinding
import hu.bme.aut.android.cameralabor.model.Image
import hu.bme.aut.android.cameralabor.network.GalleryAPI

class ImagesAdapter(
    private val context: Context,
    private val images: MutableList<Image>)
    : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    init {
        this.images.reverse()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesAdapter.ViewHolder {
        return ViewHolder(VhImageBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        Glide.with(context).load(GalleryAPI.IMAGE_PREFIX_URL + image.url).into(holder.imageView)
    }

    override fun getItemCount() = images.size

    class ViewHolder(binding: VhImageBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.ivImage
    }
}