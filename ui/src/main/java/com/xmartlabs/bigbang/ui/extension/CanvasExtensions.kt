package com.xmartlabs.bigbang.ui.extension

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point

/**
 * Draws a path in the canvas using the {@code points} list, drawing a line between consecutive points.
 *
 * @param path the path to draw in
 * @param paint the style of the path
 * @param points the points in the path.
 */
fun Canvas.drawPath(path: Path, paint: Paint, points: List<Point>) {
  if (points.size < 2) {
    return
  }
  path.reset()
  
  val firstPoint = points[0]
  path.moveTo(firstPoint.x.toFloat(), firstPoint.y.toFloat())
  
  points.withIndex()
      .filter { it.index > 0 }
      .forEach { path.lineTo(it.value.x.toFloat(), it.value.y.toFloat()) }
  path.close()
  
  this.drawPath(path, paint)
}
