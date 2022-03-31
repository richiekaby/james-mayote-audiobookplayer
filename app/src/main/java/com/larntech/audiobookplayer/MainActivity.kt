package com.larntech.audiobookplayer


import android.app.SearchManager
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.larntech.audiobookplayer.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var fm: FragmentManager;
    private lateinit var btnSearch: Button;
    private lateinit var progressBar: ProgressBar;
    private lateinit var bookList: BookList;
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDate();
        handleIntent(intent)
    }


    private fun initDate(){
        btnSearch = findViewById(R.id.btnSearch)
        progressBar = findViewById(R.id.progress_bar)
        bookList =  BookList();
        clickListener()

        fm = supportFragmentManager

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            displayFragments(1);
        }
        else {
            displayFragments(2);
        }
        handleViewModel();
    }

    private fun handleViewModel(){
        val orientation = resources.configuration.orientation

        viewModel.selectedBook.observe(this) { book ->
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                loadBookListFragment(BookDetailsFragment.newInstance(book));
            }
        }

        viewModel.searchBookMessage.observe(this){
            if(it != null){
                handleProgressBar(false)
                showMessage(it)
            }
        }

        viewModel.booksResponse.observe(this){
            handleProgressBar(false)
            setBookCollections(it)
        }
    }

    private fun handleProgressBar(show: Boolean){
        if(show){
            progressBar.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.GONE

        }

    }

    private fun clickListener(){

        btnSearch.setOnClickListener {
            onSearchRequested();
        }

    }

    private fun displayFragments(type: Int){

        if (type == 1) {
            loadBookListFragment(BookListFragment.newInstance(bookList.getBookArray()))
        } else {
            loadBookListFragment(BookListFragment.newInstance(bookList.getBookArray()));
            loadBookDetailFragment();
        }
    }

    private fun loadBookListFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.book_lists, fragment,"list")
        transaction.addToBackStack(null)
        transaction.commit()

    }

    private fun loadBookDetailFragment() {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.book_details, BookDetailsFragment.newInstance(),"detail")
        transaction.addToBackStack(null)
        transaction.commit()

    }


    private fun setBookCollections(books: List<Book>){


        var list =  books;
        bookList.setBookArray(ArrayList(list))
        displayFragments(1);
    }

    class BookList{

        private  var bookArray: ArrayList<Book> = arrayListOf();

        fun setBookArray(bookArray: ArrayList<Book>){
            this.bookArray = bookArray
        }

        fun getBookArray():ArrayList<Book>{
            return bookArray;
        }

        fun addBook(book: Book){
            this.bookArray.toMutableList().add(book)
        }

        fun removeBook(book: Book){
            this.bookArray.toMutableList().remove(book)
        }

        fun getBook(position: Int):Book{
            return this.bookArray.toMutableList()[position]
        }


        fun getBookSize():Int{
            return this.bookArray.toMutableList().size
        }


    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }



    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                handleProgressBar(true)
                viewModel.searchBook(query)
            }
        }
    }

    private fun showMessage(message: String){
        Toast.makeText(this@MainActivity,message,Toast.LENGTH_LONG).show()
    }

}