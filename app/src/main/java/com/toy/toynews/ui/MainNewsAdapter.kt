package com.toy.toynews.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.toy.toynews.R
import com.toy.toynews.dto.Article
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_item.*

class MainNewsAdapter(private val listener: (View, Int) -> Unit) : RecyclerView.Adapter<MainNewsAdapter.ViewHolder>(){
    var newsList = emptyList<Article>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View
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
            val img = item.urlToImage

            item.title.let {
                holder.item_title.text = it.substringBefore(" - ")
                holder.item_source.text = it.substringAfter(" - ")
            }

            holder.item_date.text = item.publishedAt.substringBefore("T")
            if(img!!.isEmpty()){
                //Do Nothing
            }
            else {
                holder.item_image.load(img) {
                    holder.item_loading.visibility = View.GONE
                    holder.item_image.visibility = View.VISIBLE
                    crossfade(true)
                    placeholder(R.drawable.default_img)
                }
            }

//            Picasso.get().load(img).into(holder.item_image, object : Callback{
//                override fun onSuccess() {
//                    //Log.e("Image Load Success!",img)
//                    holder.item_loading.visibility = View.GONE
//                    holder.item_image.visibility = View.VISIBLE
//                }
//                override fun onError(e: Exception?) {
//                    Log.e("Image Load Failed :(",img)
//                    Log.e("Failed title", item.title)
//                    Log.e("TAG", e.toString())
//                    //Picasso.get().load(R.drawable.default_img).into(holder.item_image)
//                    //holder.item_image.load(R.drawable.default_img)
//                    holder.item_image.load(R.drawable.default_img)
//                    holder.item_loading.visibility = View.GONE
//                    holder.item_image.visibility = View.VISIBLE
//                }
//            })
            ViewCompat.setTransitionName(holder.item_container, item.url)
            holder.view.setOnClickListener { listener }
        }
    }
}