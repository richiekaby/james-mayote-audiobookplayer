package com.larntech.audiobookplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.larntech.audiobookplayer.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppViewModel: ViewModel() {

    private val mutableSelectedBook = MutableLiveData<Book>()
    val selectedBook: LiveData<Book> get() = mutableSelectedBook

    private val mutableSearchedBooks = MutableLiveData<List<Book>>()
    val booksResponse: LiveData<List<Book>> get() =mutableSearchedBooks;

    private val bookResponseMessage = MutableLiveData<String>()
    val searchBookMessage: LiveData<String> get() = bookResponseMessage;

    fun selectedBook(book: Book) {
        mutableSelectedBook.value = book
    }


    fun searchBook(term: String?) {
        val loginResponseCall: Call<List<Book>> =
            ApiClient.service.searchBooks(term)
        loginResponseCall.enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                if(response.isSuccessful){
                    if(response.body()!!.isNotEmpty()) {
                        mutableSearchedBooks.postValue(response.body())
                    }else{
                        bookResponseMessage.postValue("No book found")
                    }
                }else{
                    bookResponseMessage.postValue("No book found")
                }
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                bookResponseMessage.postValue("An error occurred while fetching books "+t.localizedMessage)
            }
        })
    }



}