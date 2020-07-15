package com.toy.toynews.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.toy.toynews.R
import com.toy.toynews.dto.Article
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_item.*
import kotlinx.android.synthetic.main.news_item.view.*
import java.lang.Exception

class MainNewsAdapter(private val newsList: ArrayList<Article>, val listener: OnItemClickListener) : RecyclerView.Adapter<MainNewsAdapter.ViewHolder>(){
    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("LOG", "onBingViewHolder of position $position is called!!")
        newsList[position].let { item->
            var img = item.urlToImage
            item.title.let {
                holder.item_title.text = it.substringBefore(" - ")
                holder.item_source.text = it.substringAfter(" - ")
            }
            if(img!!.substringBefore("://") == "http"){
                img = img.replace("http","https")
            }
            holder.item_date.text = item.publishedAt.substringBefore("T")
            //holder.view.item_image.load(img)
            if(img.isEmpty()){
                //Do Nothing
            }
            else Picasso.get().load(img).into(holder.item_image, object : Callback{
                override fun onSuccess() {
                    //Log.e("Image Load Success!",img)
                    holder.item_loading.visibility = View.GONE
                    holder.item_image.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    //Log.e("Image Load Failed :(",img)
                    //Picasso.get().load(R.drawable.default_img).into(holder.item_image)
                    //holder.item_image.load(R.drawable.default_img)
                    holder.item_image.load(R.drawable.default_img)
                    holder.item_loading.visibility = View.GONE
                    holder.item_image.visibility = View.VISIBLE
                }
            })

            holder.view.setOnClickListener {
                listener.onItemClick(holder.itemView, position)
            }
        }
    }
}