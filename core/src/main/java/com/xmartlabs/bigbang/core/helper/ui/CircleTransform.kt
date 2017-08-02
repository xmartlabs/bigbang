/*
 * Copyright 2014 Julian Shen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xmartlabs.bigbang.core.helper.ui

import android.graphics.*

import com.squareup.picasso.Transformation

/**
 * Transforms a [Bitmap] rounding its content.
 * There's an option to add an outer border of any size and color.
 */
class CircleTransform(private val strokeWidth: Float = 0f, private val strokeColor: Int = 0) : Transformation {
  companion object {
    val KEY = "circle"
  }
  
  /**
   * Rounds the given `source` into a circle and crops out the data outside of it.
   *
   * @param source the [Bitmap] instance to be rounded
   * *
   * @return a new [Bitmap] instance, the rounded representation of the `source`
   */
  override fun transform(source: Bitmap): Bitmap {
    val size = Math.min(source.width, source.height)

    val x = (source.width - size) / 2
    val y = (source.height - size) / 2

    val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
    if (squaredBitmap != source) {
      source.recycle()
    }

    val bitmap = Bitmap.createBitmap(size, size, source.config)

    val canvas = Canvas(bitmap)
    val paint = Paint()
    val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    paint.shader = shader
    paint.isAntiAlias = true

    val center = size / 2f
    val radius = center - strokeWidth
    canvas.drawCircle(center, center, radius, paint)

    if (strokeWidth > 0) {
      val strokePaint = Paint()
      strokePaint.color = strokeColor
      strokePaint.style = Paint.Style.STROKE
      strokePaint.isAntiAlias = true
      strokePaint.strokeWidth = strokeWidth
      canvas.drawCircle(center, center, radius, strokePaint)
    }

    squaredBitmap.recycle()
    return bitmap
  }

  override fun key() = KEY
}
