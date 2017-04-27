package com.xmartlabs.bigbang.ui.recyclerview.singleitem;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xmartlabs.bigbang.ui.common.recyclerview.SingleItemBaseRecyclerViewAdapter;
import com.xmartlabs.bigbang.ui.common.recyclerview.SingleItemBaseViewHolder;
import com.xmartlabs.bigbang.ui.recyclerview.common.Car;
import com.xmartlabs.bigbang.ui.test.R;

public class CarAdapter extends SingleItemBaseRecyclerViewAdapter<Car, CarAdapter.CarViewHolder> {
  @NonNull
  @Override
  public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
    return new CarViewHolder(inflateView(parent, R.layout.item_single));
  }

  @Override
  public void getOnBindViewHolder(@NonNull CarViewHolder viewHolder, @NonNull Car item, int position) {
    viewHolder.bindItem(item);
  }

  static final class CarViewHolder extends SingleItemBaseViewHolder<Car> {
    TextView title;

    public CarViewHolder(@NonNull View view) {
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
