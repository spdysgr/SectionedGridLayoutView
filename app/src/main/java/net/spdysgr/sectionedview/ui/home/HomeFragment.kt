package net.spdysgr.sectionedview.ui.home

import android.os.Bundle
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
import net.spdysgr.sectionedview.sectioned_recyclerview.SectionedRecyclerViewAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeViewBinding: FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewBinding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            val homeAdapter = SectionedRecyclerViewAdapter()
            homeAdapter.build {
                section {
                    header(HomeSectionHeader())
                    content(HomeSectionContent())
                }
            }
            adapter = homeAdapter
        }

        return homeViewBinding.root
    }

    class HomeSectionHeaderHolderView(viewBinding: FragmentHomeHeaderBinding): RecyclerView.ViewHolder(viewBinding.root) {
        val viewBinding = viewBinding
    }
    inner class HomeSectionHeader: SectionedRecyclerViewAdapter.SectionHeader() {
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

    class HomeSectionCellHolderView(viewBinding: FragmentHomeCellBinding): RecyclerView.ViewHolder(viewBinding.root) {
        val viewBinding = viewBinding
    }
    inner class HomeSectionContent: SectionedRecyclerViewAdapter.SectionContent() {
        override fun getItemCount(): Int {
            return 5
        }

        override fun bindViewHolder(holder: RecyclerView.ViewHolder, sectionNumber: Int, positionInSection: Int?) {
            if(holder is HomeSectionCellHolderView) {
                val text = "Section No.$sectionNumber, Position $positionInSection"
                holder.viewBinding.cellTextView.text = text
            }
        }

        override fun createViewHolder(viewGroup: ViewGroup): RecyclerView.ViewHolder {
            return HomeSectionCellHolderView(FragmentHomeCellBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
        }
    }
}