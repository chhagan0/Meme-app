package com.example.meme

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currentImage :String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()

    }
        private fun  loadmeme(){
            var progressBar = findViewById<ProgressBar>(R.id.progressBar)
            progressBar.visibility= View.VISIBLE
            val queue = Volley.newRequestQueue(this)
            val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url,null,
                { response ->
                    currentImage = response.getString("url")
                    val imagememe= findViewById<ImageView>(R.id.imagememe)
                    Glide.with(this).load(currentImage).listener(object : RequestListener<Drawable> {

                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }




                    } ) .into(imagememe)

                },
                {

                })

// Add the request to the RequestQueue.
            queue.add(jsonObjectRequest)

    }

    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type= "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Sending...$currentImage")
        val chooser= Intent.createChooser(intent,"Share This Meme Using...")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadmeme()
    }

}