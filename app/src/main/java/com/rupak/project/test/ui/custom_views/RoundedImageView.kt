package com.rupak.project.test.ui.custom_views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created By Rupak Adhikari
 */
class RoundedImageView : ImageView {

    private val borderColor = Color.parseColor("#000000")
    private val borderWidth = 15.0f

    constructor(context: Context):super(context){}

    constructor(context: Context,attrs: AttributeSet):super(context,attrs){}

    constructor(context: Context,attrs: AttributeSet,defStyle: Int):super(context,attrs,defStyle) {}

    override fun onDraw(canvas: Canvas) {
        drawRound(canvas)
        drawBorder(canvas)
    }

    private fun drawBorder(canvas: Canvas) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val radius = width/2f

        paint.color = borderColor
        paint.style =  Paint.Style.STROKE
        paint.strokeWidth = borderWidth
        canvas.drawCircle(radius,radius,radius - borderWidth/2f,paint)

    }

    private fun drawRound(canvas: Canvas) {
        if(drawable == null) return
        if(height==0||width==0) return
        var bitMapp: Bitmap = (drawable as BitmapDrawable).bitmap
        val bitmap = bitMapp.copy(Bitmap.Config.ARGB_8888,true)
        val scaledBitmap: Bitmap
        val ratio: Float = bitmap.width.toFloat()/bitmap.height.toFloat()
        val height = Math.round(width/ratio)
        scaledBitmap = Bitmap.createScaledBitmap(bitmap,width,height,false)
        val shader:Shader
        shader = BitmapShader(scaledBitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP)
        val rect = RectF()
        rect.set(0f,0f,width.toFloat(),height.toFloat())
        val imagePaint = Paint()
        imagePaint.isAntiAlias = true
        imagePaint.shader = shader
        canvas.drawRoundRect(rect,width.toFloat(),height.toFloat(),imagePaint)
    }


}