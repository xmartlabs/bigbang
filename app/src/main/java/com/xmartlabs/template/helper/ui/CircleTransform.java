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

package com.xmartlabs.template.helper.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Transforms a {@link Bitmap} rounding its content.
 *
 * There's an option to add an outer border of any size and color.
 */
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("unused")
public class CircleTransform implements Transformation {
  private static final String KEY = "circle";

  /** The border width. 0 will mean no border is rendered. **/
  private float strokeWidth;
  /** The fill color of the border, if any. **/
  private int strokeColor;

  /**
   * Rounds the given {@code source} into a circle and crops out the data outside of it.
   *
   * @param source the {@link Bitmap} instance to be rounded
   * @return a new {@link Bitmap} instance, the rounded representation of the {@code source}
   */
  @Override
  public Bitmap transform(Bitmap source) {
    int size = Math.min(source.getWidth(), source.getHeight());

    int x = (source.getWidth() - size) / 2;
    int y = (source.getHeight() - size) / 2;

    Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
    if (squaredBitmap != source) {
      source.recycle();
    }

    Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint();
    BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
    paint.setShader(shader);
    paint.setAntiAlias(true);

    float center = size / 2f;
    float radius = center - strokeWidth;
    canvas.drawCircle(center, center, radius, paint);

    if (strokeWidth > 0) {
      Paint strokePaint = new Paint();
      strokePaint.setColor(strokeColor);
      strokePaint.setStyle(Paint.Style.STROKE);
      strokePaint.setAntiAlias(true);
      strokePaint.setStrokeWidth(strokeWidth);
      canvas.drawCircle(center, center, radius, strokePaint);
    }

    squaredBitmap.recycle();
    return bitmap;
  }

  @Override
  public String key() {
    return KEY;
  }
}
