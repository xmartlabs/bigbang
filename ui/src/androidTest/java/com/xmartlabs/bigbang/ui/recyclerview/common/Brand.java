package com.xmartlabs.bigbang.ui.recyclerview.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Brand {
  String name;
  List<Car> cars = new ArrayList<>();
}
