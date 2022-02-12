package net.larntech.rv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity : AppCompatActivity(), UsersAdapter.clickListener {

    private lateinit var rvUsers: RecyclerView;
    private lateinit var toolBar: Toolbar;
    private lateinit var usersAdapter: UsersAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData();
    }

    private fun initData(){
        rvUsers = findViewById(R.id.rvRecyclerView)
        toolBar = findViewById(R.id.toolBar);
        usersAdapter = UsersAdapter(this);
        supportToolBar();
        setRecyclerView();
        showData(populateUsers())


    }

    private fun supportToolBar(){
        this.setSupportActionBar(toolBar)
        this.supportActionBar!!.title = ""
    }

    private fun setRecyclerView(){
        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvUsers.adapter = usersAdapter;
    }

    private fun populateUsers(): List<UserModel>{
        val userModelList = ArrayList<UserModel>()

        var userRichard = UserModel("Richard");
        var userAlice = UserModel("Alice")
        var userHannah = UserModel("Hannah")
        var userDavid = UserModel("David")

        userModelList.add(userRichard);
        userModelList.add(userAlice)
        userModelList.add(userHannah)
        userModelList.add(userDavid)

        Log.e("toMutableList  "," ==> "+userModelList.size)

        return userModelList;

    }

    private fun showData(userModelList: List<UserModel>){
        usersAdapter.setData(userModelList)
    }

    override fun clickedUser(userModel: UserModel) {
        startActivity(Intent(this, SelectedUserActivity::class.java).putExtra("username",userModel.userName))
      Log.e(" userModel "," Clicked user "+userModel.userName)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu);
        var menuItem = menu!!.findItem(R.id.searchview);
        var searchView:SearchView = menuItem.actionView as SearchView

        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var searchChar = newText;

                usersAdapter.filter.filter(newText)
                return true;
            }

        })



        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId;

        if(id == R.id.searchview){
            return true;
        }


        return true;

    }
}