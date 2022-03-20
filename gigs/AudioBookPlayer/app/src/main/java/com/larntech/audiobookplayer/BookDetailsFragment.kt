package com.larntech.audiobookplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class BookDetailsFragment : Fragment() {

    //2
    companion object {

        fun newInstance(): BookDetailsFragment {
            return BookDetailsFragment()
        }
    }

    //3
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.frag_book_details, container, false)

        return view;
    }



}