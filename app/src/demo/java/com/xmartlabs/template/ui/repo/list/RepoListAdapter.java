package com.xmartlabs.template.ui.repo.list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.R;
import com.xmartlabs.template.helper.CircleTransform;
import com.xmartlabs.template.model.Repo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by remer on 10/12/15.
 */
public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoItemViewHolder> {
  @Inject
  Picasso picasso;

  private final List<Repo> items;
  private DemoAdapterOnItemClickListener listener;

  public interface DemoAdapterOnItemClickListener {
    void onItemClick(@NonNull Repo repo, @NonNull RepoItemViewHolder repoView);
  }

  public RepoListAdapter() {
    items = new ArrayList<>();
    BaseProjectApplication.getContext().inject(this);
  }

  @NonNull
  @SuppressWarnings("unused")
  public List<Repo> getItems() {
    return items;
  }

  public void setItems(@NonNull List<Repo> items) {
    this.items.clear();
    this.items.addAll(items);
    notifyDataSetChanged();
  }

  @NonNull
  public Repo getItem(int position) {
    return items.get(position);
  }

  @Override
  public long getItemId(int position) {
    return items.get(position).hashCode();
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  @Override
  public RepoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo_repo, parent, false);
    return new RepoItemViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RepoItemViewHolder holder, int position) {
    Repo repo = getItem(position);
    holder.nameTextView.setText(repo.getName());
    holder.authorTextView.setText(repo.getOwner().getLogin());
    holder.repo = repo;
    holder.listener = listener;

    picasso.load(repo.getOwner().getAvatar_url())
        .placeholder(R.drawable.ic_action_action_polymer)
        .transform(new CircleTransform())
        .into(holder.imageView);
  }

  public void setListener(@Nullable DemoAdapterOnItemClickListener listener) {
    this.listener = listener;
  }

  public static class RepoItemViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.name_textView)
    TextView nameTextView;
    @Bind(R.id.author_textView)
    TextView authorTextView;
    @Bind(R.id.imageView)
    ImageView imageView;

    Repo repo;
    DemoAdapterOnItemClickListener listener;

    RepoItemViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    @OnClick(R.id.card_view)
    @SuppressWarnings("unused")
    void selectItem(View view) {
      if (listener != null) {
        listener.onItemClick(repo, this);
      }
    }
  }
}
