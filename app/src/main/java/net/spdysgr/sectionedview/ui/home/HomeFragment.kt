package net.spdysgr.sectionedview.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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

            val homeAdapter = HomeAdapter()
            homeAdapter.addSection(SectionedRecyclerViewAdapter.Section(HomeAdapter.HomeSectionHeader(), HomeAdapter.HomeSectionContent(), null))
            adapter = homeAdapter
        }

        return homeViewBinding.root
    }

    class HomeAdapter: SectionedRecyclerViewAdapter() {
        class HomeSectionHeaderHolderView(viewBinding: FragmentHomeHeaderBinding): RecyclerView.ViewHolder(viewBinding.root) {
            val textView: TextView = viewBinding.textView
        }
        class HomeSectionHeader: SectionHeader() {
            override fun bindViewHolder(holder: RecyclerView.ViewHolder, sectionNumber: Int, positionInSection: Int?) {
                val headerHolder = holder as HomeSectionHeaderHolderView
                val text = "Header of Section No.$sectionNumber."
                headerHolder.textView.text = text
            }

            override fun createViewHolder(viewGroup: ViewGroup): RecyclerView.ViewHolder {
                return HomeSectionHeaderHolderView(FragmentHomeHeaderBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
            }
        }

        class HomeSectionCellHolderView(viewBinding: FragmentHomeCellBinding): RecyclerView.ViewHolder(viewBinding.root) {
            val cellTextView: TextView = viewBinding.cellTextView
            val cellImageView: ImageView = viewBinding.cellImageView
        }
        class HomeSectionContent: SectionContent() {
            override fun getItemCount(): Int {
                return 5
            }

            override fun bindViewHolder(holder: RecyclerView.ViewHolder, sectionNumber: Int, positionInSection: Int?) {
                val cellHolder = holder as HomeSectionCellHolderView
                val text = "Section No.$sectionNumber, Position $positionInSection"
                cellHolder.cellTextView.text = text
            }

            override fun createViewHolder(viewGroup: ViewGroup): RecyclerView.ViewHolder {
                return HomeSectionCellHolderView(FragmentHomeCellBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
            }
        }
    }
}