package net.spdysgr.sectionedview.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import net.spdysgr.sectionedview.databinding.FragmentHomeBinding
import net.spdysgr.sectionedview.databinding.FragmentHomeCellBinding
import net.spdysgr.sectionedview.databinding.FragmentHomeHeaderBinding
import net.spdysgr.sectionedview.sectioned_recyclerview.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeViewBinding: FragmentHomeBinding
    private lateinit var onClickListener: SectionElementClickListener

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        onClickListener = object : SectionElementClickListener {
            override fun onClick(sectionNumber: Int, positionInSection: Int) {
                Log.i("HomeFragment", "section:$sectionNumber, position:$positionInSection")
            }
        }

        homeViewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewBinding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            val homeAdapter = SectionedRecyclerViewAdapter()
            homeAdapter.build {
                section {
                    header(HomeSectionHeader())
                    contentHandler(HomeSectionContentHandler())
                    cell(HomeSectionContentCell(onClickListener))
                }
            }
            adapter = homeAdapter
        }

        return homeViewBinding.root
    }

    class HomeSectionHeaderHolderView(val viewBinding: FragmentHomeHeaderBinding): RecyclerView.ViewHolder(viewBinding.root)
    inner class HomeSectionHeader: SectionHeader() {
        override fun bindViewHolder(holder: RecyclerView.ViewHolder, sectionNumber: Int, positionInSection: Int?) {
            if(holder is HomeSectionHeaderHolderView) {
                val text = "Header of Section No.$sectionNumber."
                holder.viewBinding.textView.text = text
            }
        }

        override fun createViewHolder(viewGroup: ViewGroup): RecyclerView.ViewHolder {
            return HomeSectionHeaderHolderView(FragmentHomeHeaderBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
        }
    }

    class HomeSectionContentCellHolderView(val viewBinding: FragmentHomeCellBinding): RecyclerView.ViewHolder(viewBinding.root)
    inner class HomeSectionContentCell(private val listener: SectionElementClickListener?): SectionContentCell() {
        override fun bindViewHolder(holder: RecyclerView.ViewHolder, sectionNumber: Int, positionInSection: Int?) {
            if(holder is HomeSectionContentCellHolderView) {
                val text = "Section No.$sectionNumber, Position $positionInSection"
                holder.viewBinding.cellTextView.text = text

                if(listener == null) {
                    return
                }
                holder.viewBinding.root.setOnClickListener {
                    positionInSection?.let{
                        listener.onClick(sectionNumber, positionInSection)
                    }
                }
            }
        }

        override fun createViewHolder(viewGroup: ViewGroup): RecyclerView.ViewHolder {
            return HomeSectionContentCellHolderView(FragmentHomeCellBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
        }
    }

    inner class HomeSectionContentHandler: SectionContentHandler() {
        override fun getItemCount(): Int {
            return 5
        }

        override fun getContentCellJavaClassName(sectionNumber: Int, positionInSection: Int): String {
            return HomeSectionContentCell::class.java.name
        }
    }
}