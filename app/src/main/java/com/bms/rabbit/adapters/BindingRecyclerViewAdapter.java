package com.bms.rabbit.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

public class BindingRecyclerViewAdapter extends RecyclerView.Adapter<BindingRecyclerViewAdapter.ViewHolder> {

    @NonNull
    private final WeakReferenceOnListChangedCallback callback = new WeakReferenceOnListChangedCallback(this);
    int brVarId;
    int layoutId;

    private ObservableList items;
    private int recyclerViewRefCount = 0;

    public BindingRecyclerViewAdapter(int brVarId, int layoutId) {
        this.brVarId = brVarId;
        this.layoutId = layoutId;
    }

    public void setItems(@Nullable ObservableList items) {

        if (this.items == items) {
            return;
        }

        if (recyclerViewRefCount > 0) {

            if (this.items != null) {
                this.items.removeOnListChangedCallback(callback);
            }

            if (items != null) {
                items.addOnListChangedCallback(callback);
            }
        }
        this.items = items;
        notifyDataSetChanged();
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        if (recyclerViewRefCount == 0 && items != null) {
            items.addOnListChangedCallback(callback);
        }
        recyclerViewRefCount += 1;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerViewRefCount -= 1;
        if (recyclerViewRefCount == 0 && items != null) {
            items.removeOnListChangedCallback(callback);
        }
    }

    @Override
    public final ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(layoutId, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public final void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Observable item = (Observable) items.get(position);
        if (viewHolder.binding != null) {
            viewHolder.binding.setVariable(brVarId, item);
            viewHolder.binding.executePendingBindings();
        }

    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        if (holder.binding != null) {
            holder.binding.unbind();
            holder.binding.getRoot().destroyDrawingCache();
        }
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemViewType(int position) {
        return this.layoutId;
    }


    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        ViewHolder(View view) {
            super(view);
            this.binding = DataBindingUtil.bind(view);
        }
    }


    private class WeakReferenceOnListChangedCallback extends ObservableList.OnListChangedCallback<ObservableList> {
        final WeakReference<BindingRecyclerViewAdapter> adapterRef;

        WeakReferenceOnListChangedCallback(BindingRecyclerViewAdapter adapter) {
            this.adapterRef = new WeakReference<>(adapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            BindingRecyclerViewAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }

            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, final int positionStart, final int itemCount) {
            BindingRecyclerViewAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }

            adapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, final int positionStart, final int itemCount) {
            BindingRecyclerViewAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            adapter.notifyItemRangeInserted(positionStart, itemCount);
            adapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, final int fromPosition, final int toPosition, final int itemCount) {
            BindingRecyclerViewAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }

            for (int i = 0; i < itemCount; i++) {
                adapter.notifyItemMoved(fromPosition + i, toPosition + i);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, final int positionStart, final int itemCount) {
            BindingRecyclerViewAdapter adapter = adapterRef.get();
            if (adapter == null) {

                return;
            }
            adapter.notifyItemRangeRemoved(positionStart, itemCount);
        }


    }
}
