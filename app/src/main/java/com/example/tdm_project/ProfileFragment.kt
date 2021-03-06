
package com.example.tdm_project

import android.content.Intent
import android.content.res.Resources
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.tdm_project.adapters.sharedPostsAdapter
import com.example.tdm_project.adapters.vertCardAdapter
import com.example.tdm_project.data.SharedSavedNews
import com.example.tdm_project.data.news


class ProfileFragment : Fragment() {
    lateinit var rootView : View
    lateinit var sharedAdapter : sharedPostsAdapter
    lateinit var rv : RecyclerView
    var newsList = ArrayList<news>()

    companion object {
        val ARG_PSEUDO = "PSEUDO"
        val ARG_URI = "PHOTO"
        fun getInstance() = ProfileFragment()


        fun newInstance(pseudo: String?, uri : String?): ProfileFragment {
            val fragment = ProfileFragment()

            val bundle = Bundle().apply {
                putString(ARG_PSEUDO,pseudo)
                putString(ARG_URI,uri)
            }

            fragment.arguments = bundle

            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         rootView = inflater.inflate(R.layout.profile_fragment,
                       container, false)
        var pseudoText = rootView.findViewById<TextView>(R.id.profile_pseudo)
        var profileView = rootView.findViewById<ImageView>(R.id.profile_photo)
        newsList = SharedSavedNews.getListSharedPosts()
        intialiserVertically()

          rootView.findViewById<Button>(R.id.param).setOnClickListener {
              // preparé l'activité d'ajout
              val intent = Intent(rootView.context, ParameterActivity::class.java)
              // lancer l'activité
              startActivity(intent)

          }

        val newPseudo = arguments?.getString("PSEUDO")
        var stringImageUri = arguments?.getString("PHOTO")

            if (newPseudo != null ) pseudoText.text=newPseudo
            if (stringImageUri != null ) {
                var imageUri = Uri.parse(stringImageUri)
                profileView.setImageURI(imageUri)
            }

        if (  stringImageUri != null) Log.i("URI_PROFILE", stringImageUri)
        else Log.i("URI_PROFILE","NULLLL")

        return rootView
    }



    private fun intialiserVertically() {
        rv = rootView.findViewById<RecyclerView>(R.id.recyler_view_sahred_post)
        val layout = LinearLayoutManager(rootView.context)
        layout.orientation = LinearLayoutManager.VERTICAL
        rv.layoutManager = layout
        sharedAdapter = sharedPostsAdapter(rootView.context,newsList)
        rv.adapter = sharedAdapter
    }


}