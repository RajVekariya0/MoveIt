package com.example.shopowner.milkparlor

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.json.JSONArray

class MenuAdapter(internal var context : Context, internal var menuArray : JSONArray) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    lateinit var layoutInflater : LayoutInflater
    lateinit var appconfig : appconfig

    init {
        layoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        appconfig = appconfig()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = layoutInflater.inflate(R.layout.menuitem,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return menuArray.length()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var o = menuArray.getJSONObject(position)
        holder.tvMenuName.setText(o.getString("m_name"))

        var imageUrl = appconfig.url+"menumaster/"+o.getString("m_image")
        Log.e("Image URL",imageUrl)

        var requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.splash_logo)

        Glide.with(context).load(imageUrl).apply(requestOptions).into(holder.menuImage)

    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        lateinit var tvMenuName : TextView
        lateinit var btndelete : ImageView
        lateinit var menuImage : ImageView
        init {
            tvMenuName=view.findViewById(R.id.tvMenuName)
            btndelete=view.findViewById(R.id.btndelete)
            menuImage=view.findViewById(R.id.menuImage)
            tvMenuName.setOnClickListener {
                var o = menuArray.getJSONObject(position)

                var intent = Intent(context,CategoryActivity::class.java)
                intent.putExtra("menu_id",o.getString("m_id"))
                intent.putExtra("menu_name",o.getString("m_name"))
                context.startActivity(intent)

            }

            btndelete.setOnClickListener {
                var o = menuArray.getJSONObject(position)
                (context as MenuActivity).deleteMenu(position, o.getString("m_id"))
            }
        }

    }
}