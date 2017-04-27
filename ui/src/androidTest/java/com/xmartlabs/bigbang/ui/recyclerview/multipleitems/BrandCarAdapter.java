package com.xmartlabs.bigbang.ui.recyclerview.multipleitems;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.xmartlabs.bigbang.ui.common.recyclerview.BaseRecyclerViewAdapter;
import com.xmartlabs.bigbang.ui.common.recyclerview.SimpleItemRecycleItemType;
import com.xmartlabs.bigbang.ui.common.recyclerview.SingleItemBaseViewHolder;
import com.xmartlabs.bigbang.ui.recyclerview.common.Brand;
import com.xmartlabs.bigbang.ui.recyclerview.common.Car;
import com.xmartlabs.bigbang.ui.test.R;

import java.util.List;

public class BrandCarAdapter extends BaseRecyclerViewAdapter {
  private final SimpleItemRecycleItemType<Car, CarViewHolder> carItemType =
      new SimpleItemRecycleItemType<Car, CarViewHolder>() {
        @NonNull
        @Override
        public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
          return new CarViewHolder(inflateView(parent, R.layout.item_single));
        }
      };

  private final SimpleItemRecycleItemType<Brand, BrandViewHolder> brandItemType =
      new SimpleItemRecycleItemType<Brand, BrandViewHolder>() {
        @NonNull
        @Override
        public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
          return new BrandViewHolder(inflateView(parent, R.layout.item_single));
        }
      };

  @MainThread
  public void setItems(@NonNull List<Brand> brands) {
    Stream.of(brands)
        .forEach(brand -> {
          addItem(brandItemType, brand);
          Stream.ofNullable(brand.getCars())
              .forEach(car -> addItem(carItemType, car));
        });
    notifyDataSetChanged();
  }

  private static final class BrandViewHolder extends SingleItemBaseViewHolder<Brand> {
    private TextView title;

    BrandViewHolder(@NonNull View view) {
      super(view);
      title = (TextView) view.findViewById(android.R.id.title);
    }

    @Override
    public void bindItem(@NonNull Brand item) {
      super.bindItem(item);
      title.setText(item.getName());
    }
  }

  private static final class CarViewHolder extends SingleItemBaseViewHolder<Car> {
    private TextView title;

    CarViewHolder(@NonNull View view) {
      super(view);
      title = (TextView) view.findViewById(android.R.id.title);
    }

    @Override
    public void bindItem(@NonNull Car item) {
      super.bindItem(item);
      title.setText(item.getModel());
    }
  }
}
