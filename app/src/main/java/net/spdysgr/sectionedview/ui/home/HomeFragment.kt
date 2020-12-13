package net.spdysgr.sectionedview.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.spdysgr.sectionedview.databinding.FragmentHomeBinding
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
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

            val homeAdapter = HomeAdapter()
            homeAdapter.addSection(SectionedRecyclerViewAdapter.Section(HomeAdapter.HomeSectionHeader(), null, null))
            adapter = homeAdapter
        }

        return homeViewBinding.root
    }

    class HomeAdapter: SectionedRecyclerViewAdapter() {
        class HomeSectionHeaderHolderView(viewBinding: FragmentHomeHeaderBinding): RecyclerView.ViewHolder(viewBinding.root) {
            val textView: TextView = viewBinding.textView
        }
        class HomeSectionHeader: SectionHeader() {
            override fun createViewHolder(viewGroup: ViewGroup): RecyclerView.ViewHolder {
                return HomeSectionHeaderHolderView(FragmentHomeHeaderBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
            }

        }
    }
}