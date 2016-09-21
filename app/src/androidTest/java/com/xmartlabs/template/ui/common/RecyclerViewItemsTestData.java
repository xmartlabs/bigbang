package com.xmartlabs.template.ui.common;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * Created by medina on 21/09/2016.
 */
@Builder
@Data
public class RecyclerViewItemsTestData {
  List<Pair<Integer, Integer>> content;
  @Nullable
  String serviceUrl;
  @Nullable
  String jsonResponsePath;
  @Nullable
  Intent intent;
}
