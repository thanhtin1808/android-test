package com.example.cubandroidtest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cubandroidtest.R
import com.example.cubandroidtest.databinding.FragmentDetailBinding
import com.example.cubandroidtest.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        sharedViewModel.selectedArticle.observe(viewLifecycleOwner) { article ->
            with(binding) {
                Glide.with(requireActivity())
                    .load(article.urlToImage.orEmpty())
                    .placeholder(android.R.drawable.ic_menu_gallery) // Placeholder while loading
                    .error(android.R.drawable.ic_menu_report_image) // Error image if loading fails
                    .into(ivDetailImage)
                tvDetailTitle.text = article.title.orEmpty()
                tvDetailDescription.text = article.description.orEmpty()
                tvDetailContent.text = article.content.orEmpty()
                tvNewsWebUrl.text = article.url.orEmpty()
                tvAuthor.text = resources.getString(R.string.home_detail_author, article.author.orEmpty())
                val convertedDate = DateUtils.formatIsoDateToReadable(article.publishedAt.orEmpty())
                tvPublishedAt.text = resources.getString(R.string.home_detail_published_at, convertedDate)
            }
            binding.ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}