package com.vk.giphyvk.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vk.giphyvk.databinding.DescriptionFragmentBinding

class DescriptionFragment : Fragment(){

    private var _binding: DescriptionFragmentBinding? = null
    private val binding get() = _binding!!
    private var id: String? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString("id")
            title = it.getString("title")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DescriptionFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.id.text = "id = $id"
        binding.title.text =  "title = $title"
    }
}