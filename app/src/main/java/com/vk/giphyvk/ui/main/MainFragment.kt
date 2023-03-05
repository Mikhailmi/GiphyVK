package com.vk.giphyvk.ui.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.vk.giphyvk.R
import com.vk.giphyvk.databinding.FragmentMainBinding
import com.vk.giphyvk.gifPackage.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.ArrayList

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private var scope = CoroutineScope(Job() + Dispatchers.Main)
    private var job: Job? = null
    private var offset = 0
    private var adapter = GifAdapter { gifImage, string -> onItemClick(gifImage, string) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText = binding.message
        val searchButton = binding.submit
        val nextButton = binding.next
        val previousButton = binding.back
        binding.viewModel = viewModel // используется для отображения прогрессбара
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerView.adapter = adapter
        searchButton.isEnabled = searchButtonEnabled
        if (adapter.imageLink.isNotEmpty()) {
            for (i in adapter.imageLink.indices){
            adapter.notifyItemChanged(i)
            }
        }
        editText.addTextChangedListener {
            searchButton.isEnabled = editText.text?.isNotEmpty()!!
            searchButtonEnabled = editText.text?.isNotEmpty()!!
        }

        searchButton.setOnClickListener {
            job?.cancel()
            job = scope.launch {
                val response = viewModel.onButtonClicked(editText.text.toString(), 25, 0)
                val imageListGif = response.data
                if (adapter.imageLink.isNotEmpty()) {
                    adapter.imageLink.clear()
                    adapter.imageDescription.clear()
                }
                for (i in imageListGif.indices) {
                    val gif = imageListGif[i]
                    val images = gif.images
                    val url = ((images as Map<*, *>)[DOWNSIZED] as Map<*, *>)[URL]
                    adapter.imageLink.add(url as String)
                    val arrayList = ArrayList<String>()
                    arrayList.add(gif.id)
                    arrayList.add(gif.embed_url)
                    arrayList.add(gif.title)
                    adapter.imageDescription.add(arrayList)
                    adapter.notifyItemChanged(i)
                }
                if (response.pagination.total_count > 25) {
                    nextButton.visibility = View.VISIBLE
                    nextButtonVisibility = View.VISIBLE
                }
                previousButton.visibility = View.GONE
                previousButtonVisibility = View.GONE
            }
        }

        nextButton.setOnClickListener {
            job?.cancel()
            job = scope.launch {
                offset += 25
                val response = viewModel.onButtonClicked(editText.text.toString(), 25, offset)
                val imageListGif = response.data
                if (adapter.imageLink.isNotEmpty()) {
                    adapter.imageLink.clear()
                    adapter.imageDescription.clear()
                }
                for (i in imageListGif.indices) {
                    val gif = imageListGif[i]
                    val images = gif.images
                    val url = ((images as Map<*, *>)[DOWNSIZED] as Map<*, *>)[URL]
                    adapter.imageLink.add(url as String)
                    val arrayList = ArrayList<String>()
                    arrayList.add(gif.id)
                    arrayList.add(gif.embed_url)
                    arrayList.add(gif.title)
                    adapter.imageDescription.add(arrayList)
                    adapter.notifyItemChanged(i)
                }
                if (response.pagination.total_count > (offset + 25)) {
                    nextButton.visibility = View.VISIBLE
                    nextButtonVisibility = View.VISIBLE
                } else {
                    nextButton.visibility = View.GONE
                    nextButtonVisibility = View.GONE
                }
                previousButton.visibility = View.VISIBLE
                previousButtonVisibility = View.VISIBLE
            }
        }

        previousButton.setOnClickListener {
            job?.cancel()
            job = scope.launch {
                offset -= 25
                val response = viewModel.onButtonClicked(editText.text.toString(), 25, offset)
                val imageListGif = response.data
                if (adapter.imageLink.isNotEmpty()) {
                    adapter.imageLink.clear()
                    adapter.imageDescription.clear()
                }
                for (i in imageListGif.indices) {
                    val gif = imageListGif[i]
                    val images = gif.images
                    val url = ((images as Map<*, *>)[DOWNSIZED] as Map<*, *>)[URL]
                    adapter.imageLink.add(url as String)
                    val arrayList = ArrayList<String>()
                    arrayList.add(gif.id)
                    arrayList.add(gif.embed_url)
                    arrayList.add(gif.title)
                    adapter.imageDescription.add(arrayList)
                    adapter.notifyItemChanged(i)
                }
                if (offset >= 25) {
                    previousButton.visibility = View.VISIBLE
                    previousButtonVisibility = View.VISIBLE
                } else {
                    previousButton.visibility = View.GONE
                    previousButtonVisibility = View.GONE
                }
                nextButtonVisibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val nextButton = binding.next
        val previousButton = binding.back
        nextButton.visibility = nextButtonVisibility
        previousButton.visibility = previousButtonVisibility
    }

    private var nextButtonVisibility: Int = View.GONE
    private var previousButtonVisibility: Int = View.GONE
    private var searchButtonEnabled = false

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(OFFSET, offset)
        outState.putInt(NEXT_BUTTON_VISIBILITY, nextButtonVisibility)
        outState.putInt(PREVIOUS_BUTTON_VISIBILITY, previousButtonVisibility)
        outState.putBoolean(SEARCH_BUTTON_ENABLED, searchButtonEnabled)
        outState.putStringArrayList(IMAGE_LINK, adapter.imageLink)
        for (i in adapter.imageDescription.indices){
            outState.putStringArrayList("$IMAGE_DESCRIPTION$i", adapter.imageDescription[i])
        }
        super.onSaveInstanceState(outState)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            offset = savedInstanceState.getInt(OFFSET)
            nextButtonVisibility = savedInstanceState.getInt(NEXT_BUTTON_VISIBILITY)
            previousButtonVisibility = savedInstanceState.getInt(PREVIOUS_BUTTON_VISIBILITY)
            searchButtonEnabled = savedInstanceState.getBoolean(SEARCH_BUTTON_ENABLED)
            adapter.imageLink = savedInstanceState.getStringArrayList(IMAGE_LINK) as ArrayList<String>
            for (i in adapter.imageLink.indices){
                adapter.imageDescription.add(savedInstanceState.getStringArrayList("$IMAGE_DESCRIPTION$i")!!)
            }
        }
    }

    private fun onItemClick(item: String, args: List<String>) {
        parentFragmentManager.commit {
            val bundle = Bundle().apply {
                putString(ID, args[0])
                putString(TITLE, args[1])
            }
            replace<DescriptionFragment>(R.id.container, args = bundle)
            addToBackStack(DescriptionFragment::class.java.simpleName)
        }
    }

    companion object {
        fun newInstance() = MainFragment()
        const val DOWNSIZED = "downsized"
        const val URL = "url"
        const val OFFSET = "offset"
        const val NEXT_BUTTON_VISIBILITY = "nextButtonVisibility"
        const val PREVIOUS_BUTTON_VISIBILITY = "previousButtonVisibility"
        const val SEARCH_BUTTON_ENABLED = "searchButtonEnabled"
        const val IMAGE_LINK = "imageLink"
        const val IMAGE_DESCRIPTION = "imageDescription"
        const val ID = "id"
        const val TITLE = "title"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}