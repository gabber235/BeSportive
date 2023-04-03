package nl.tue.besportive.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<B extends ViewDataBinding, T> extends RecyclerView.Adapter<BaseViewHolder<B>> {
    private List<T> items;

    private final int layoutId;

    protected abstract void bind(B binding, T item);

    public BaseAdapter(List<T> items, int layoutId) {
        this.items = items;
        this.layoutId = layoutId;
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder<B> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        B binder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
        return new BaseViewHolder<>(binder);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<B> holder, int position) {
        bind(holder.getBinding(), items.get(position));
        holder.getBinding().executePendingBindings();
    }

    @LayoutRes
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
