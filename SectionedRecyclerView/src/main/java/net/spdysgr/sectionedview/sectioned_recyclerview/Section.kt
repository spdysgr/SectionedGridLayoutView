package net.spdysgr.sectionedview.sectioned_recyclerview

class Section {
    var sectionHeader: SectionHeader? = null
    var sectionContentCellList: MutableList<SectionContentCell> = mutableListOf()
    var sectionFooter: SectionFooter? = null

    var sectionContentHandler: SectionContentHandler? = null

    fun hasHeader(): Boolean {
        return sectionHeader?.let{ true } ?: false
    }

    fun getItemCountInContent(): Int {
        return sectionContentHandler?.getItemCount() ?: 0
    }

    fun hasFooter(): Boolean {
        return sectionFooter?.let{ true } ?: false
    }

    fun getSectionContentCell(javaClassName: String): SectionContentCell? {
        return sectionContentCellList.firstOrNull { it.javaClass.name == javaClassName }
    }

    fun header(newSectionHeader: SectionHeader) {
        sectionHeader = newSectionHeader
    }

    fun cell(newSectionContentCell: SectionContentCell) {
        sectionContentCellList.add(newSectionContentCell)
    }

    fun footer(newSectionFooter: SectionFooter) {
        sectionFooter = newSectionFooter
    }

    fun contentHandler(newSectionContentHandler: SectionContentHandler) {
        sectionContentHandler = newSectionContentHandler
    }
}
