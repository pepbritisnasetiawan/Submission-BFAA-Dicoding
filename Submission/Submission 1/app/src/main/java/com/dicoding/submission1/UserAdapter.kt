package com.dicoding.submission1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*
import kotlin.collections.ArrayList

class UserAdapter(private var users: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = filterUsers[position]

        Glide.with(holder.itemView.context)
            .load(user.imgUser)
            .apply(RequestOptions().override(300, 300))
            .into(holder.imgItemPhoto)
        with(holder) {
            holder.tvName.text = user.name
            holder.tvUsername.text = user.username
            holder.tvCompany.text = user.company
            itemView.setOnClickListener { onItemClickDetail.onItemClicked(filterUsers[holder.absoluteAdapterPosition]) }
        }
    }

    override fun getItemCount(): Int = filterUsers.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgItemPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_item_username)
        var tvCompany: TextView = itemView.findViewById(R.id.tv_item_company)

    }


    private var filterUsers: ArrayList<User> = users
    private lateinit var onItemClickDetail: OnItemClickCallBack

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickDetail = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: User)
    }

    // Handle Search
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val itemSearch = constraint.toString()
                filterUsers = if (itemSearch.isEmpty()) users else {
                    val itemList = ArrayList<User>()
                    for (item in users) {
                        val name = item.name?.lowercase(Locale.ROOT)?.contains(
                            itemSearch.lowercase(Locale.ROOT)
                        )
                        val userName = item.username?.lowercase(Locale.ROOT)?.contains(
                            itemSearch.lowercase(Locale.ROOT)
                        )
                        if (name!! || userName!!) {
                            itemList.add(item)
                        }
                    }
                    itemList
                }
                val filterResults = FilterResults()
                filterResults.values = filterUsers
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterUsers = results?.values as ArrayList<User>
                notifyDataSetChanged()
            }
        }
    }
}