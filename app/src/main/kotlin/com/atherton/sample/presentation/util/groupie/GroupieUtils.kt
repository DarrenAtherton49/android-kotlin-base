package com.atherton.sample.presentation.util.groupie

import android.view.View
import com.xwray.groupie.Item
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder

typealias ViewHolder = GroupieViewHolder

abstract class Item : Item<ViewHolder> {

    constructor() : super()
    constructor(id: Long) : super(id)

    override fun createViewHolder(itemView: View): ViewHolder = ViewHolder(itemView)
}
