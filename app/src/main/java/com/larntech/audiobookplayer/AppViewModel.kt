package com.larntech.audiobookplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel: ViewModel() {

    private val mutableSelectedBook = MutableLiveData<Book>()
    val selectedBook: LiveData<Book> get() = mutableSelectedBook

    fun selectedBook(book: Book) {
        mutableSelectedBook.value = book
    }


}