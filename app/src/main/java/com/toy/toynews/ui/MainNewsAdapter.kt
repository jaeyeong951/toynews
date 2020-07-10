package com.toy.toynews.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.toy.toynews.R
import com.toy.toynews.dto.Article
import kotlinx.android.synthetic.main.news_item.view.*
import java.lang.Exception

class MainNewsAdapter(private val newsList: ArrayList<Article>) : RecyclerView.Adapter<MainNewsAdapter.ViewHolder>(){
    class ViewHolder(val view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        newsList[position].let { item->
            var img = item.urlToImage
            item.title.let {
                holder.view.item_title.text = it.substringBefore(" - ")
                holder.view.item_source.text = it.substringAfter(" - ")
            }
            if(img!!.substringBefore("://") == "http"){
                img = img.replace("http","https")
            }
            holder.view.item_date.text = item.publishedAt.substringBefore("T")

            //holder.view.item_image.load(img)
            if(img.isEmpty()){
                //Do Nothing
            }
            else Picasso.get().load(img).into(holder.view.item_image, object : Callback{
                override fun onSuccess() {
                    Log.e("Image Load Success!",img)
                    holder.view.item_loading.visibility = View.GONE
                    holder.view.item_image.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Log.e("Image Load Failed :(",img)
                    Picasso.get().load(R.drawable.default_img).into(holder.view.item_image)
                    holder.view.item_loading.visibility = View.GONE
                    holder.view.item_image.visibility = View.VISIBLE
                }
            })

            holder.view.setOnClickListener {
                val action
                        = MainFragmentDirections.actionMainFragmentToWebViewFragment(item.url)
                it.findNavController().navigate(action)
            }
        }
    }
}