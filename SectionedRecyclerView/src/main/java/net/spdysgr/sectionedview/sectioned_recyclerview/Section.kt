package net.spdysgr.sectionedview.sectioned_recyclerview

class Section {
    var sectionHeader: SectionedRecyclerViewAdapter.SectionHeader? = null
    var sectionContentCellList: MutableList<SectionedRecyclerViewAdapter.SectionContentCell> = mutableListOf()
    var sectionFooter: SectionedRecyclerViewAdapter.SectionFooter? = null

    var sectionContentHandler: SectionedRecyclerViewAdapter.SectionContentHandler? = null

    fun hasHeader(): Boolean {
        return sectionHeader?.let{ true } ?: false
    }

    fun getItemCountInContent(): Int {
        return sectionContentHandler?.getItemCount() ?: 0
    }

    fun hasFooter(): Boolean {
        return sectionFooter?.let{ true } ?: false
    }

    fun getSectionContentCell(javaClassName: String): SectionedRecyclerViewAdapter.SectionContentCell? {
        return sectionContentCellList.firstOrNull { it.javaClass.name == javaClassName }
    }

    fun header(newSectionHeader: SectionedRecyclerViewAdapter.SectionHeader) {
        sectionHeader = newSectionHeader
    }

    fun cell(newSectionContentCell: SectionedRecyclerViewAdapter.SectionContentCell) {
        sectionContentCellList.add(newSectionContentCell)
    }

    fun footer(newSectionFooter: SectionedRecyclerViewAdapter.SectionFooter) {
        sectionFooter = newSectionFooter
    }

    fun contentHandler(newSectionContentHandler: SectionedRecyclerViewAdapter.SectionContentHandler) {
        sectionContentHandler = newSectionContentHandler
    }
}
