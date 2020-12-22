package net.spdysgr.sectionedview.sectioned_recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseSectionElement {
    abstract fun bindViewHolder(holder: RecyclerView.ViewHolder, sectionNumber:Int, positionInSection: Int?)
    abstract fun createViewHolder(viewGroup: ViewGroup): RecyclerView.ViewHolder
}

abstract class SectionHeader: BaseSectionElement()
abstract class SectionContentCell: BaseSectionElement()
abstract class SectionFooter: BaseSectionElement()