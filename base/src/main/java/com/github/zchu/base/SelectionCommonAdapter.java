package com.github.zchu.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.ObjectsCompat;

import java.util.List;

public abstract class SelectionCommonAdapter<T> extends CommonAdapter<T> {

    private OnSelectedItemListener onSelectedItemListener;
    private T selectedItem = null;

    public SelectionCommonAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    public SelectionCommonAdapter(List<T> data) {
        super(data);
    }

    @Override
    protected void convert(CommonViewHolder helper, T item) {
        convert(helper, item, getItemSelected(helper, item));
    }

    protected abstract void convert(CommonViewHolder helper, T item, boolean isSelected);

    protected boolean getItemSelected(@NonNull CommonViewHolder helper, @NonNull T item) {
        return ObjectsCompat.equals(this.selectedItem, item);
    }


    public int getSelectedPosition() {
        return mData.indexOf(selectedItem);
    }


    public void setSelectedAdapterPosition(int selectedPosition, boolean isNotifyListener) {
        if (selectedPosition < 0 || selectedPosition >= mData.size()) {
            return;
        }
        T newItem = mData.get(selectedPosition);
        if (ObjectsCompat.equals(this.selectedItem, newItem)) {
            return;
        }

        T oldItem = this.selectedItem;
        int oldPosition = mData.indexOf(oldItem);
        boolean hasOld = oldPosition > -1 && oldPosition < mData.size() + getHeaderLayoutCount();
        this.selectedItem = newItem;
        onSelectedItem(hasOld ? oldItem : null, newItem);

        if (isNotifyListener) {
            if (onSelectedItemListener != null) {
                onSelectedItemListener.onSelectedItem(this, hasOld ? oldPosition : null, selectedPosition);
            }
        }
        if (selectedPosition < mData.size() + getHeaderLayoutCount()) {
            notifyItemChanged(selectedPosition + getHeaderLayoutCount());
        } else {
            notifyDataSetChanged();
        }
        if (hasOld) {
            notifyItemChanged(oldPosition + getHeaderLayoutCount());
        }
    }

    public void setSelectedItem(T newItem, boolean isNotifyListener) {
        setSelectedAdapterPosition(mData.indexOf(newItem), isNotifyListener);
    }


    public void setSelectedAdapterPosition(int selectedPosition) {
        setSelectedAdapterPosition(selectedPosition, true);
    }


    protected void onSelectedItem(@Nullable T oldItem, @NonNull T newItem) {


    }


    @Nullable
    public T getSelectedItem() {
        return selectedItem;
    }

    public void reset() {
        selectedItem = null;
        notifyDataSetChanged();
    }

    public void setOnSelectedItemListener(OnSelectedItemListener onSelectedItemListener) {
        this.onSelectedItemListener = onSelectedItemListener;
    }

    public interface OnSelectedItemListener {
        void onSelectedItem(SelectionCommonAdapter adapter, @Nullable Integer oldPosition, int newPosition);
    }


}
