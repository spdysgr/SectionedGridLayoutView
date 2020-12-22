package net.spdysgr.sectionedview.sectioned_recyclerview

abstract class SectionContentHandler {
    abstract fun getItemCount(): Int
    abstract fun getContentCellJavaClassName(sectionNumber: Int, positionInSection: Int): String
}