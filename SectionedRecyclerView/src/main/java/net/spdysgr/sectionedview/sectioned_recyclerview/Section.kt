package net.spdysgr.sectionedview.sectioned_recyclerview

class Section {
    var sectionHeader: SectionedRecyclerViewAdapter.SectionHeader? = null
    var sectionContent: SectionedRecyclerViewAdapter.SectionContent? = null
    var sectionFooter: SectionedRecyclerViewAdapter.SectionFooter? = null

    fun hasHeader(): Boolean {
        return sectionHeader?.let{ true } ?: false
    }

    fun getItemCountInContent(): Int {
        return sectionContent?.getItemCount() ?: 0
    }

    fun hasFooter(): Boolean {
        return sectionFooter?.let{ true } ?: false
    }

    fun header(newSectionHeader: SectionedRecyclerViewAdapter.SectionHeader) {
        sectionHeader = newSectionHeader
    }

    fun content(newSectionContent: SectionedRecyclerViewAdapter.SectionContent) {
        sectionContent = newSectionContent
    }

    fun footer(newSectionFooter: SectionedRecyclerViewAdapter.SectionFooter) {
        sectionFooter = newSectionFooter
    }
}
