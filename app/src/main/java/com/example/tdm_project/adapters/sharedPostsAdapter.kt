package com.example.tdm_project.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.tdm_project.ArticleReadingActivity
import com.example.tdm_project.R
import com.example.tdm_project.data.news
import com.example.tdm_project.sharedPreferences.WebBrowserActivity
import com.squareup.picasso.Picasso

class sharedPostsAdapter (val context: Context, val news : ArrayList<news>) : RecyclerView.Adapter<sharedPostsAdapter.ViewHolder> (){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val newsCard = LayoutInflater.from(context).inflate(R.layout.post_shared_view,p0,false)
        return ViewHolder(newsCard)
    }
    override fun getItemCount(): Int {
        return  news.size
    }

    override fun onBindViewHolder(p0: sharedPostsAdapter.ViewHolder, p1: Int) {
        val newsContent = news[p1]
        p0.bind(newsContent)
    }
    fun updateList(newlist: List<news>) {
        news.clear()
        news.addAll(newlist)
        this.notifyDataSetChanged()
    }


    inner class ViewHolder (private val objet: View) : RecyclerView.ViewHolder(objet){

        fun bind(item : news) {
            objet.findViewById<TextView>(R.id.news_title).text = item.Title
            objet.findViewById<TextView>(R.id.news_date).text = item.Date + " By"
            objet.findViewById<TextView>(R.id.news_descrp).text = item.Second_title
            objet.findViewById<TextView>(R.id.news_writer).text = item.Writer
            val img =objet.findViewById<ImageView>(R.id.news_image)
            Picasso
                .get() // give it the context
                .load(item.Image)
                .into(img)



            val intent = Intent(context, WebBrowserActivity::class.java)
            objet.setOnClickListener {
                intent.putExtra("article", item)
                context.startActivity(intent)
            }

        }

    }
        }



