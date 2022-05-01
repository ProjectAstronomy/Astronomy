package com.project.core.ui

import android.view.View
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder<T>>() {
    private val _isNeededToLoadInFlow = MutableStateFlow(false)
    val isNeededToLoadInFlow: StateFlow<Boolean> get() = _isNeededToLoadInFlow

    protected abstract val differ: AsyncListDiffer<T>

    var items: List<T>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        _isNeededToLoadInFlow.value = (position * 100 / itemCount) > 80
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(t: T)
    }
}