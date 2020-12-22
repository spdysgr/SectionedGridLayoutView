package net.spdysgr.sectionedview.sectioned_recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class SectionedRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var sectionList: MutableList<Section> = mutableListOf()
    private var viewTypeAndSectionElementMapping: MutableList<BaseSectionElement> = mutableListOf()

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
            sectionContentCellList.forEach { registerViewTypeMapping(it) }
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
        return getViewTypeAsInt(position)
    }

    private fun getViewTypeAsInt(position: Int): Int {
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
                val sectionContentHandler = sectionList[sectionNumber].sectionContentHandler
                sectionContentHandler ?: return null
                return sectionList[sectionNumber].getSectionContentCell(
                    sectionContentHandler.getContentCellJavaClassName(sectionNumber, position - positionCnt))
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