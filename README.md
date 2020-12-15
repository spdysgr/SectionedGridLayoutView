# SectionedGridLayoutView: RecyclerView general-purpose framework
SectionedGridLayoutView is a framework that inherits RecyclerView and implements section header and footer, and grid layout. The cells that make up the header, footer, and grid can use different Fragments for each section. In addition, cells with multiple types of Fragments can be placed in one grid layout.

# Prerequisite
You must specify StaggeredGridLayout as the layout manager.

# Glossary and architecture
## SectionHeader
SectionHeader is the element that appears at the top of the section. It's automatically displayed in full width.

## SectionContent
SectionContent is an area that displays multiple cells in a grid pattern. The span of the grid can be specified by the StaggeredGridLayout.

## SectionContentCell
SectionContentCell is an element that appears in the SectionContent grid. One SectionContent can contain multiple types of SectionContentCell.

## SectionFooter
SectionFooter is the area displayed at the bottom of the section. It is automatically displayed in full width.

## Section
Section consists of SectionHeader, SectionContent and SectionFooter. Each can contain only the required components. For example, you can configure a Section with just the SectionHeader and SectionContent.
