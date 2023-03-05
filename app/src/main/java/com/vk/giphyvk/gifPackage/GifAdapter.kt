package com.vk.giphyvk.gifPackage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vk.giphyvk.R
import com.vk.giphyvk.databinding.ItemBinding
import java.util.ArrayList

class GifAdapter(
    private val onClick: (String, List<String>) -> Unit
) : PagingDataAdapter<GifImage, GifAdapter.GifViewHolder>(DiffUtilCallback()), java.io.Serializable {

    var imageLink: ArrayList<String> = ArrayList()
    var imageDescription: ArrayList<ArrayList<String>> = ArrayList()


    override fun getItemCount(): Int = imageLink.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflater, parent, false)

        return GifViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gif = imageLink[position]
        val description = imageDescription[position]
        val context = holder.itemView.context

        with(holder.binding) {

            Glide.with(context).load(gif)
                .error(R.drawable.cat)
      //          .placeholder(R.drawable.cat1)
                .into(imageView)
        }
        holder.binding.root.setOnClickListener{
            onClick(gif, description)
        }
    }
    class GifViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)
}

class DiffUtilCallback : DiffUtil.ItemCallback<GifImage>() {
    override fun areItemsTheSame(oldItem: GifImage, newItem: GifImage): Boolean =
        oldItem.photo == newItem.photo


    override fun areContentsTheSame(oldItem: GifImage, newItem: GifImage): Boolean = oldItem == newItem

}