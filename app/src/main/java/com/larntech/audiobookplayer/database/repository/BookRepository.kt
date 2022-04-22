package com.larntech.audiobookplayer.database.repository

import android.app.Application
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import com.larntech.audiobookplayer.database.dao.BookDao
import com.larntech.audiobookplayer.database.entity.Book
import com.larntech.audiobookplayer.database.room.BookRoomDatabase
import com.larntech.audiobookplayer.database.room.BookRoomDatabase.Companion.getDatabase
import java.io.*
import java.lang.Exception
import java.net.URL

class BookRepository(application: Application) {
    private val bookDao: BookDao?
     val bookLists: LiveData<List<Book>>?
     val db: BookRoomDatabase?

    init {
        db = getDatabase(application)
        bookDao = db!!.bookDao()
        bookLists = bookDao!!.getAllBooks()
    }


    fun insertBooks(books: List<Book>) {
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                for(book in books){
                    if(getBook(book.id) == null){
                       val longId =  db!!.bookDao()!!.insertBook(book)
                        Log.e(" longId ", " ==> $longId")
                    }
                }

                return null
            }

        }.execute()
    }


    fun getBook(id: Int): Book{
        return bookDao!!.getBook(id);
    }


    fun getAllBooks(): LiveData<List<Book>>?{
        return bookLists
    }


    fun updateBook(book: Book){
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg p0: Void?): Void? {

                bookDao!!.updateBook(book)

                return null
            }

        }.execute()

    }

     fun downloadBookAudio(book: Book){
         Log.e(" downloadBookAudio ", " downloadBookAudio ")

         object : AsyncTask<Void?, Void?, Void?>() {
            var isSuccess = false;
            var filePath = ""

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Log.e(" local isSuccess ", " isSuccess "+isSuccess)

                if(isSuccess){
                    book.localPath = filePath
                    updateBook(book)
                }
            }

            override fun doInBackground(vararg p0: Void?): Void? {
                try {
                    var updatedFileName = book.id.toString()
                    var fileDirectory =
                        Environment.getExternalStorageDirectory().toString() + "/Documents/" + updatedFileName+".mp3"

                    val file = File(updatedFileName)

                    if (file.isFile) {
                        file.delete()
                    }


                    val url = URL("https://kamorris.com/lab/audlib/download.php?id=" + book.id)

                    val conection = url.openConnection()
                    conection.connect()
                    // getting file length
                    // getting file length
                    val lenghtOfFile = conection.contentLength

                    // input stream to read file - with 8k buffer

                    // input stream to read file - with 8k buffer
                    val input: InputStream = BufferedInputStream(url.openStream(), 8192)

                    // Output stream to write file

                    // Output stream to write file
                    val output: OutputStream = FileOutputStream(fileDirectory)
                    val data = ByteArray(1024)

                    var count = input.read(data)
                    var total = count
                    while (count != -1) {
                        output.write(data, 0, count)
                        count = input.read(data)
                        total += count
                    }


                    // flushing output

                    // flushing output
                    output.flush()

                    // closing streams

                    // closing streams
                    output.close()
                    input.close()
                    filePath = file.path
                    isSuccess = true;
                }catch (e: Exception){
                    Log.e(" isSuccess "," test "+e.localizedMessage)
                    isSuccess = false
                }
                return null
            }

        }.execute()

    }



}