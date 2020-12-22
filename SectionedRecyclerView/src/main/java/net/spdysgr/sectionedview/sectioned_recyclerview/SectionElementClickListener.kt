package net.spdysgr.sectionedview.sectioned_recyclerview

interface SectionElementClickListener {
    fun onClick(sectionNumber: Int, positionInSection: Int)
}