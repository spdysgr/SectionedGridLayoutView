package net.spdysgr.sectionedview.sectioned_recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


abstract class SectionedRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class Section() {
        var sectionHeader: SectionHeader? = null
        var sectionContent: SectionContent? = null
        var sectionFooter: SectionFooter? = null

        fun hasHeader(): Boolean {
            return sectionHeader?.let{ true } ?: false
        }

        fun getItemCountInContent(): Int {
            return sectionContent?.getItemCount() ?: 0
        }

        fun hasFooter(): Boolean {
            return sectionFooter?.let{ true } ?: false
        }

        fun header(newSectionHeader: SectionHeader) {
            sectionHeader = newSectionHeader
        }

        fun content(newSectionContent: SectionContent) {
            sectionContent = newSectionContent
        }

        fun footer(newSectionFooter: SectionFooter) {
            sectionFooter = newSectionFooter
        }
    }

    private var sectionList: MutableList<Section> = mutableListOf()
    private var viewTypeAndSectionElementMapping: MutableList<BaseSectionElement> = mutableListOf()

    abstract class BaseSectionElement {
        abstract fun bindViewHolder(holder: RecyclerView.ViewHolder, sectionNumber:Int, positionInSection: Int?)
        abstract fun createViewHolder(viewGroup: ViewGroup): RecyclerView.ViewHolder
    }

    abstract class SectionHeader: BaseSectionElement() {
    }

    abstract class SectionContent: BaseSectionElement() {
        abstract fun getItemCount(): Int
    }

    abstract class SectionFooter: BaseSectionElement() {
    }

    fun build(lambda: SectionedRecyclerViewAdapter.() -> Unit) {
        lambda()
    }

    fun section(lambda: Section.() -> Unit) {
        val section = Section()
        sectionList.add(section)
        section.lambda()

        val registerViewTypeMapping: (BaseSectionElement) -> Unit = { sectionElement ->
            viewTypeAndSectionElementMapping.firstOrNull{ it == sectionElement } ?: viewTypeAndSectionElementMapping.add(sectionElement)
        }
        section.apply {
            sectionHeader?.let { registerViewTypeMapping(it) }
            sectionContent?.let { registerViewTypeMapping(it) }
            sectionFooter?.let { registerViewTypeMapping(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val sectionElement = viewTypeAndSectionElementMapping[viewType]
        return sectionElement.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sectionElement = getViewTypeAsSectionElement(position)
        val sectionNumber = getSectionNumber(position)
        val positionInSection = getPositionInSection(position)

        sectionNumber ?: return
        sectionElement?.bindViewHolder(holder, sectionNumber, positionInSection)

        if(sectionElement is SectionHeader || sectionElement is SectionFooter) {
            val layoutParams = holder.itemView.layoutParams
            if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                layoutParams.isFullSpan = true
            }
        }
    }

    override fun getItemCount(): Int {
        var positionCnt = 0
        for(sectionNumber in (0 until sectionList.size)) {
            if (hasHeader(sectionNumber)) {
                positionCnt++
            }

            positionCnt += getContentCountInSection(sectionNumber)

            if (hasFooter(sectionNumber)) {
                positionCnt++
            }
        }
        return positionCnt
    }

    override fun getItemViewType(position: Int): Int {
        return getViewTypeAsInt(position) ?: -1 // TODO -1 isn't special value
    }

    private fun getViewTypeAsInt(position: Int): Int? {
        return viewTypeAndSectionElementMapping.indexOf(getViewTypeAsSectionElement(position))
    }

    private fun hasHeader(sectionNumber: Int): Boolean {
        return sectionList[sectionNumber].hasHeader()
    }
    private fun getContentCountInSection(sectionNumber: Int): Int {
        return sectionList[sectionNumber].getItemCountInContent()
    }
    private fun hasFooter(sectionNumber: Int): Boolean {
        return sectionList[sectionNumber].hasFooter()
    }

    private fun getViewTypeAsSectionElement(position: Int): BaseSectionElement? {
        var positionCnt = 0
        for(sectionNumber in (0 until sectionList.size)) {
            if (hasHeader(sectionNumber)) {
                if (positionCnt == position) {
                    return sectionList[sectionNumber].sectionHeader
                }
                positionCnt++
            }

            val contentCountInSection = getContentCountInSection(sectionNumber)
            if (positionCnt <= position && position < positionCnt + contentCountInSection) {
                return sectionList[sectionNumber].sectionContent
            }
            positionCnt += contentCountInSection

            if (hasFooter(sectionNumber)) {
                if (positionCnt == position) {
                    return sectionList[sectionNumber].sectionFooter
                }
                positionCnt++
            }
        }
        return null
    }

    private fun getSectionNumber(position: Int): Int? {
        var positionCnt = 0
        for(sectionNumber in (0 until sectionList.size)) {
            if (hasHeader(sectionNumber)) {
                if (positionCnt == position) {
                    return sectionNumber
                }
                positionCnt++
            }

            val contentCountInSection = getContentCountInSection(sectionNumber)
            if (positionCnt <= position && position < positionCnt + contentCountInSection) {
                return sectionNumber
            }
            positionCnt += contentCountInSection

            if (hasFooter(sectionNumber)) {
                if (positionCnt == position) {
                    return sectionNumber
                }
                positionCnt++
            }
        }
        return null
    }

    private fun getPositionInSection(position: Int): Int? {
        var positionCnt = 0
        for(sectionNumber in (0 until sectionList.size)) {
            if (hasHeader(sectionNumber)) {
                if (positionCnt == position) {
                    return null
                }
                positionCnt++
            }

            val contentCountInSection = getContentCountInSection(sectionNumber)
            if (positionCnt <= position && position < positionCnt + contentCountInSection) {
                return position - positionCnt
            }
            positionCnt += contentCountInSection

            if (hasFooter(sectionNumber)) {
                if (positionCnt == position) {
                    return null
                }
                positionCnt++
            }
        }
        return null
    }
}