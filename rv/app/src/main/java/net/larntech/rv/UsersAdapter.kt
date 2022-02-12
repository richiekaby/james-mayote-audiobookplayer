package net.larntech.rv

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class UsersAdapter(clickedUser: clickListener): RecyclerView.Adapter<UsersAdapter.VHUsersAdapter>(), Filterable {


    private var userModelList: List<UserModel> = ArrayList()
    private var userModelListFiltered: List<UserModel> = ArrayList()
    private lateinit var context: Context;
    private var clickedUser = clickedUser;


    fun setData(userModel: List<UserModel>){
        this.userModelList = userModel;
        this.userModelListFiltered = userModel;
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHUsersAdapter {
        context = parent.context;
        var view = VHUsersAdapter(LayoutInflater.from(context).inflate(R.layout.row_users,parent,false));
        return view;

    }

    override fun onBindViewHolder(holder: VHUsersAdapter, position: Int) {
        val user = userModelList.get(position);
        val username = user.userName;
        val prefix = username.substring(0,1);
        Log.e(" username", " ==> "+username)

        holder.tvPrefix.text = prefix;
        holder.tvUserName.text = username;

        holder.itemView.setOnClickListener {
            clickedUser.clickedUser(userModelList.get(position))
        }

    }

    override fun getItemCount(): Int {
       return userModelList.size;
    }

    class VHUsersAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvUserName = itemView.findViewById<TextView>(R.id.tvUsername)
        var tvPrefix = itemView.findViewById<TextView>(R.id.tvPrefix)
    }

    interface clickListener{
        fun clickedUser(userModel: UserModel);
    }

    override fun getFilter(): Filter {

        var filter = object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults = FilterResults();
                if(p0 == null || p0.isEmpty()){
                    filterResults.count = userModelListFiltered.size
                    filterResults.values = userModelListFiltered
                }else{
                    var searchChar = p0.toString().toString();
                    val users = ArrayList<UserModel>()

                    for(userModel in userModelListFiltered){
                        if(userModel.userName.toLowerCase().contains(searchChar.toLowerCase())){
                            users.add(userModel)
                        }
                    }

                    filterResults.count = users.size;
                    filterResults.values = users;


                }

                return filterResults;

            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                userModelList = p1!!.values as List<UserModel>;
                notifyDataSetChanged()
            }

        }

        return filter;
    }


}


