package hu.bme.aut.android.cameralabor

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.bumptech.glide.Glide
import hu.bme.aut.android.cameralabor.databinding.ActivityUploadBinding
import hu.bme.aut.android.cameralabor.network.GalleryInteractor
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream

class UploadActivity : AppCompatActivity() {

    companion object {
        private const val TMP_IMAGE_JPG = "tmp_image.jpg"
        private const val REQUEST_CAMERA_IMAGE = 101
    }

    private lateinit var binding : ActivityUploadBinding
    private var loadedBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCapture.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, REQUEST_CAMERA_IMAGE)
        }

        binding.btnUpload.setOnClickListener {
            loadedBitmap?.let { bitmap ->
                val galleryInteractor = GalleryInteractor()

                val name = binding.etName.text.toString()
                val description = binding.etDescription.text.toString()

                val imagePath = File(cacheDir, TMP_IMAGE_JPG)
                FileOutputStream(imagePath).use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                }
                galleryInteractor.uploadImage(
                    fileUri = Uri.fromFile(imagePath),
                    name = name,
                    description = description,
                    onSuccess = this::uploadSuccess,
                    onError = this::uploadError
                )
            }
        }
    }

    private fun uploadSuccess(responseBody: ResponseBody) {
        Toast.makeText(this, "Successfully uploaded!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun uploadError(e: Throwable) {
        Toast.makeText(this, "Error during uploading photo!", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    loadedBitmap = data!!.extras!!.get("data") as Bitmap
                    Glide.with(this)
                        .load(loadedBitmap!!)
                        .into(binding.ivImage)
                } catch (t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this, "ERROR: " + t.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}