package com.example.cubandroidtest.ui


import LanguagePickerDialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cubandroidtest.R
import com.example.cubandroidtest.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var newsArticleAdapter: NewsArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
        observeSearchAndLanguage()
    }

    private fun initView() {
        initAdapter()
        binding.tvSelectedLanguage.text = sharedViewModel.selectedLanguage.value
        binding.ivLanguage.setOnClickListener {
            showLanguagePickerDialog()
        }
    }

    private fun showLanguagePickerDialog() {
        val dialog =
            sharedViewModel.selectedLanguage.value?.let {
                LanguagePickerDialogFragment(selectedLanguage = it) { language ->
                    sharedViewModel.setLanguage(language)
                    binding.tvSelectedLanguage.text = language
                }
            }
        dialog?.show(childFragmentManager, "LanguagePicker")
    }

    private fun initAdapter() {
        newsArticleAdapter = NewsArticleAdapter { newItem ->
            sharedViewModel.selectArticle(newItem)
            findNavController().navigate(R.id.action_HomeFragment_to_DetailFragment)
        }

        binding.rvNews.apply {
            adapter = newsArticleAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeData() {
        homeViewModel.newsList.observe(viewLifecycleOwner) { newsList ->
            newsArticleAdapter.submitList(newsList)
        }

        homeViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeSearchAndLanguage() {
        val languageFlow = sharedViewModel.selectedLanguage
            .asFlow()
            .filterNotNull()

        homeViewModel.observeSearchAndLanguage(languageFlow)

        binding.etSearch.addTextChangedListener { editable ->
            homeViewModel.onSearchQueryChanged(editable?.toString().orEmpty())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
