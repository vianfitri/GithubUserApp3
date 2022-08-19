package com.vianfitri.githubuserapp3.ui.follows

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vianfitri.githubuserapp3.R
import com.vianfitri.githubuserapp3.adapter.FollowsAdapter
import com.vianfitri.githubuserapp3.databinding.FragmentFollowsBinding
import com.vianfitri.githubuserapp3.datasource.DetailResponse
import com.vianfitri.githubuserapp3.ui.detail.DetailActivity

class FollowsFragment : Fragment() {
    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!
    private val adapter: FollowsAdapter by lazy {
        FollowsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowsBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    @RequiresApi(33)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val user = arguments?.getParcelable(ARG_PARCEL, DetailResponse::class.java)

        if (index == 1) {
            if (user != null) {
                user.login?.let {
                    val mIndex = 1
                    setViewModel(it, mIndex)
                }
            }
        } else {
            if (user != null) {
                user.login?.let {
                    val mIndex = 2
                    setViewModel(it, mIndex)
                }
            }
        }
    }

    private fun setViewModel(username: String, index: Int) {
        val followsViewModel: FollowsViewModel by viewModels {
            FollowsViewModelFactory(username)
        }

        followsViewModel.isLoading.observe(viewLifecycleOwner) {
            showProgressBar(it)
        }

        if (index == 1) {
            followsViewModel.followers.observe(viewLifecycleOwner) { follsResponse ->
                follsResponse?.let {
                    if(follsResponse.isEmpty()){
                        binding.follNoData.text = getString(R.string.no_follower)
                        binding.follNoData.visibility = View.VISIBLE
                    } else {
                        binding.follNoData.visibility = View.GONE
                        adapter.addDataToList(follsResponse)
                        setFollowsData(follsResponse)
                    }
                }
            }
        } else {
            followsViewModel.following.observe(viewLifecycleOwner) { follsResponse ->
                follsResponse?.let {
                    if(follsResponse.isEmpty()){
                        binding.follNoData.text = getString(R.string.no_following)
                        binding.follNoData.visibility = View.VISIBLE
                    } else {
                        binding.follNoData.visibility = View.GONE
                        adapter.addDataToList(follsResponse)
                        setFollowsData(follsResponse)
                    }
                }
            }
        }
    }

    private fun setFollowsData(followsResponse: ArrayList<DetailResponse>){
        if(followsResponse.isNotEmpty()) {
            val layoutManager = LinearLayoutManager(view?.context)
            binding.rvFollows.layoutManager = layoutManager
            binding.rvFollows.adapter = adapter
            adapter.setOnItemClickCallback(object : FollowsAdapter.OnItemClickCallback {
                override fun onItemClicked(user: DetailResponse) {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USER, user)
                    startActivity(intent)
                }
            })
        }
    }

    private fun showProgressBar(state: Boolean) {
        if(state) {
            binding.progressBarFollows.visibility = View.VISIBLE
        } else {
            binding.progressBarFollows.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_PARCEL = "user_model"

        @JvmStatic
        fun newInstance(index: Int, detailResponse: DetailResponse) =
            FollowsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                    putParcelable(ARG_PARCEL, detailResponse)
                }
            }
    }
}