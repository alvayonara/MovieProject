package com.alvayonara.movieproject.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alvayonara.movieproject.BuildConfig.BASE_URL_TMDB_POSTER
import com.alvayonara.movieproject.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import de.hdodenhof.circleimageview.CircleImageView
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * View visibility
 */
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

/**
 * Swipe Refresh state
 */
fun SwipeRefreshLayout.hideLoading() {
    this.isRefreshing = false
}

/**
 * Image configuration
 */
fun CircleImageView.cacheImage(path: String?) {
    Glide.with(this.context)
        .load(path)
        .error(R.color.color_grey)
        .into(this)
}

fun ImageView.cacheImage(path: String?) {
    Glide.with(this.context)
        .load(BASE_URL_TMDB_POSTER + path)
        .transition(DrawableTransitionOptions.withCrossFade(200))
        .error(R.color.color_grey)
        .into(this)
}

/**
 * Date Time converter
 */
@SuppressLint("SimpleDateFormat")
fun String?.convertDateTime(
    inputFormat: String = "yyyy-MM-dd",
    outputFormat: String = "dd MMMM yyyy"
): String {
    return try {
        val sdf = SimpleDateFormat(inputFormat)
        val convertDate = sdf.parse(this.orEmpty())
        SimpleDateFormat(outputFormat).format(convertDate!!)
    } catch (e: ParseException) {
        ""
    }
}

/**
 * Intent configuration
 */
inline fun <reified T : AppCompatActivity> Context.moveActivity() {
    Intent(this, T::class.java).also { intent ->
        startActivity(intent)
    }
}

inline fun <reified T : AppCompatActivity> Context.moveActivity(bundle: Bundle) {
    Intent(this, T::class.java).also { intent ->
        intent.putExtras(bundle)
        startActivity(intent)
    }
}