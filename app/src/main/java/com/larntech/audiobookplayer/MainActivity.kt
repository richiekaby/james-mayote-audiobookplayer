package com.larntech.audiobookplayer


import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer


class MainActivity : AppCompatActivity() {
    private lateinit var fm: FragmentManager;
    private lateinit var bookList: BookList;
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDate();
    }


    private fun initDate(){
        setBookCollections()

        fm = supportFragmentManager

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            displayFragments(1);
        }
        else {
            displayFragments(2);
        }

        viewModel.selectedBook.observe(this) { book ->
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                loadBookListFragment(BookDetailsFragment.newInstance(book));
            }
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


    private fun setBookCollections(){


        var list =  arrayListOf(
            Book("Book 0", "Author 0"),
            Book("Book 1", "Author 1"),
            Book("Book 2", "Author 2"),
            Book("Book 3", "Author 3"),
            Book("Book 4", "Author 4"),
            Book("Book 5", "Author 5"),
            Book("Book 6", "Author 6"),
            Book("Book 7", "Author 7"),
            Book("Book 8", "Author 8"),
            Book("Book 9", "Author 9")
        )

        bookList = BookList();
        bookList.setBookArray(list)

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
}