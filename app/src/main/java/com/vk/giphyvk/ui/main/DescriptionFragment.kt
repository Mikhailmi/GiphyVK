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
            id = it.getString(ID)
            title = it.getString(TITLE)
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
        binding.id.text = "$ID = $id"
        binding.title.text =  "$TITLE = $title"
    }

    companion object {
        const val ID = "id"
        const val TITLE = "title"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}