package com.vianfitri.githubuserapp3.ui.follows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.vianfitri.githubuserapp3.R
import com.vianfitri.githubuserapp3.adapter.FollowsAdapter
import com.vianfitri.githubuserapp3.databinding.FragmentFollowsBinding
import com.vianfitri.githubuserapp3.datasource.DetailResponse

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