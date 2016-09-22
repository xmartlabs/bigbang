package com.xmartlabs.template.helper.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.NonNull;

import com.annimon.stream.Stream;

import java.util.List;

/**
 * Created by medina on 19/09/2016.
 */
@SuppressWarnings("unused")
public class DrawHelper {
  private static final Path PATH = new Path();

  /**
   * Draws a path in the canvas using the <code>points</code> list, drawing a line between consecutive points
   * @param canvas the canvas to draw the path
   * @param paint the style of the path
   * @param points the points in the path.
   */
  public static void drawPath(@NonNull Canvas canvas, @NonNull Paint paint, @NonNull List<Point> points) {
    if (points.size() < 2) {
      return;
    }
    PATH.reset();

    Point firstPoint = points.get(0);
    PATH.moveTo(firstPoint.x, firstPoint.y);
    Stream.of(points)
        .skip(1)
        .forEach(point -> PATH.lineTo(point.x, point.y));
    PATH.close();
    canvas.drawPath(PATH, paint);
  }
}
