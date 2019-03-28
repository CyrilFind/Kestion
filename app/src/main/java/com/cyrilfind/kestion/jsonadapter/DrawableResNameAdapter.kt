package com.cyrilfind.kestion.jsonadapter

import android.content.Context
import android.graphics.drawable.Drawable
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class DrawableResName

class DrawableResNameAdapter(val context: Context) {
    @ToJson
    fun toJson(@DrawableResName drawable: Drawable?): String? {
        error("not implemented")
    }

    @FromJson
    @DrawableResName
    fun fromJson(drawableResName: String?): Drawable? {
        val resId = context.resources.getIdentifier(drawableResName, "drawable", context.packageName)
        if (resId == 0) error("drawableResName not found: ${drawableResName ?: "none"}")
        return context.getDrawable(resId)
    }
}