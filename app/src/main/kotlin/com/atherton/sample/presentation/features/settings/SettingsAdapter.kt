package com.atherton.sample.presentation.features.settings

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atherton.sample.R
import com.atherton.sample.presentation.util.extension.inflateLayout
import com.atherton.sample.presentation.util.glide.GlideRequests
import com.atherton.sample.presentation.util.image.ImageLoader
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_setting.*

class SettingsAdapter(
    private val imageLoader: ImageLoader,
    private val glideRequests: GlideRequests,
    private val onClickListener: (Setting) -> Unit
) : ListAdapter<Setting, SettingsAdapter.ViewHolder>(SettingsDiffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = parent.inflateLayout(R.layout.item_setting)
        val viewHolder = ViewHolder(view, imageLoader, glideRequests)
        viewHolder.itemView.setOnClickListener {
            onClickListener.invoke(getItem(viewHolder.adapterPosition))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long = getItem(position).id

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    class ViewHolder(
        override val containerView: View,
        private val imageLoader: ImageLoader,
        private val glideRequests: GlideRequests
    ) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(setting: Setting) {
            imageLoader.load(
                with = glideRequests,
                drawableResId = setting.logoResId,
                into = settingIconImageView
            )
            settingTitleTextView.text = setting.title
        }

        fun clear() {
            imageLoader.clear(with = glideRequests, imageView = settingIconImageView)
        }
    }

    companion object {
        private object SettingsDiffCallback : DiffUtil.ItemCallback<Setting>() {

            override fun areItemsTheSame(oldItem: Setting, newItem: Setting): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Setting, newItem: Setting): Boolean {
                return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.logoResId == newItem.logoResId
            }
        }
    }
}
