package com.example.palette

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.transition.ChangeImageTransform
import android.transition.Fade
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import com.google.android.material.appbar.MaterialToolbar


class ImagePalette : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.palette_activity)

        window.enterTransition = Fade()
        window.exitTransition  = Slide()
        window.sharedElementEnterTransition = TransitionSet()
            .addTransition(ChangeImageTransform())
            .setDuration(1000)

        val toolbar2 = findViewById<MaterialToolbar>(R.id.toolbarPalette)
        setSupportActionBar(toolbar2)

        val id = intent.getIntExtra("image_resource", 0)

        val imageView = findViewById<ImageView>(R.id.imageView2)
        imageView.setImageResource(id)

        val textView1 = findViewById<TextView>(R.id.textView1)
        val textView2 = findViewById<TextView>(R.id.textView2)
        val textView3 = findViewById<TextView>(R.id.textView3)
        val textView4 = findViewById<TextView>(R.id.textView4)

        val bitmap : Bitmap = (imageView.drawable as BitmapDrawable).bitmap

        Palette.from(bitmap).generate { palette ->
            palette?.let {
                val vibrantColor = it.getVibrantColor(0)
                toolbar2.setBackgroundColor(vibrantColor)

                window.statusBarColor = it.getDarkVibrantColor(0)
                textView1.setBackgroundColor(it.getDarkVibrantColor(0))
                textView2.setBackgroundColor(it.getLightVibrantColor(0))
                textView3.setBackgroundColor(it.getMutedColor(0))
                textView4.setBackgroundColor(it.getDarkMutedColor(0))
            }
        }

        imageView.setOnClickListener {
            finishAfterTransition()
        }
    }
}