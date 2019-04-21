
package com.example.tdm_project

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.tdm_project.adapters.horizCardAdapter
import com.example.tdm_project.adapters.vertCardAdapter
import com.example.tdm_project.data.Topic
import com.example.tdm_project.data.getList
import com.example.tdm_project.data.news
import com.example.tdm_project.sharedPreferences.PreferencesProvider
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    lateinit var pref : PreferencesProvider
    lateinit var rootView : View
    lateinit var customHAdapter : horizCardAdapter
    lateinit var customVAdapter : vertCardAdapter
    var newsList = ArrayList<news>()
    var topicsList = ArrayList<Topic>()



    companion object {
        fun getInstance() = HomeFragment()
    }



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //set the view
        rootView = inflater.inflate(R.layout.home_fragment, container, false)
        pref = PreferencesProvider(rootView.context)

        GetTopics()
        newsList = getList()


        val  btnChange= rootView.findViewById<Button>(R.id.btn_changeLang)
        btnChange.setOnClickListener {
            showChangeLanguageDialog()
        }

        val btnHoriz = rootView.findViewById<ImageButton>(R.id.btn_horizt_display)
        btnHoriz.setOnClickListener{
            intialiserHorizontally()
        }

        val btnVert = rootView.findViewById<ImageButton>(R.id.btn_vert_display)
        btnVert.setOnClickListener{
            intialiserVertically()
        }

        return rootView
    }

    private fun intialiserVertically() {
        val rv = rootView.findViewById<RecyclerView>(R.id.recyler_view_news)
        val layout = LinearLayoutManager(rootView.context)
        layout.orientation = LinearLayoutManager.VERTICAL
        rv.layoutManager = layout
        customVAdapter = vertCardAdapter(rootView.context,newsList)
        rv.adapter = customVAdapter
    }

    private fun intialiserHorizontally() {
        val rv = rootView.findViewById<RecyclerView>(R.id.recyler_view_news)
        val layout = LinearLayoutManager(rootView.context)
        layout.orientation = LinearLayoutManager.HORIZONTAL
        rv.layoutManager = layout
        customHAdapter = horizCardAdapter(rootView.context,newsList)
        rv.adapter = customHAdapter
    }


    private fun showChangeLanguageDialog () {

        val languagesList= arrayOf("Français","العربية")
        val mBuilder = AlertDialog.Builder(context)
        mBuilder.setTitle("Changer La Langue")
        mBuilder.setSingleChoiceItems( languagesList,-1) { dialog , i: Int ->

       if (i==0) {
           setLocal("fr")
           activity!!.recreate()
       }
       else
       {
           setLocal("ar")
           activity!!.recreate()
       }

     dialog.dismiss()

        }

        mBuilder.setNeutralButton("Annuler") { dialog, which: Int ->
            dialog.dismiss()


        }

      var mDialog = mBuilder.create()
        mDialog.show()


    }

    private fun setLocal(lang: String) {
        var locale = Locale(lang)
        Locale.setDefault(locale)
        var config = Configuration()
        config.locale =locale
        activity!!.baseContext.resources.updateConfiguration(config,activity!!.baseContext.resources.displayMetrics)
        val editor = activity!!.getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()

    }
    private fun loadLocate() {
        val sharedPreferences = activity!!.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocal(language)
    }


    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun GetTopics(){
        var layout = rootView.findViewById<LinearLayoutCompat>(R.id.Topics_buttom_holder)
        var style = 0
        var num = 0

        topicsList = pref.loadTopicsList()
        topicsList.forEach {

            var btn =  AppCompatButton(rootView.context)
            val draw = rootView.resources.getDrawable(it.IconLink,null)

            btn.setCompoundDrawablesWithIntrinsicBounds(draw,null,null,null)
            btn.setPadding(45,0,45,0)
            btn.compoundDrawablePadding = 20
            btn.maxWidth = 450
            btn.minWidth = 350
            btn.minHeight = 200
            val titre = it.title
            btn.text = titre
            btn.setTextColor(rootView.resources.getColor(R.color.white , null))

            when (num){
                0-> style = R.drawable.style_blue
                1-> style = R.drawable.style_red
                2-> style = R.drawable.style_green
            }

            num = (num+1)%3

            btn.background = rootView.resources.getDrawable(style,null)

            btn.setOnClickListener {
                chargeNews(titre)
            }

            layout.addView(btn)
        }
    }

    private fun chargeNews(titre: String?) {
         newsList.forEach {
             if (it.category != titre) newsList.remove(it)
         }
    }
}