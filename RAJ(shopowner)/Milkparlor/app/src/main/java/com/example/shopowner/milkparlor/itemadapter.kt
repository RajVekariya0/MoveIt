package com.example.shopowner.milkparlor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.json.JSONArray

class itemadapter (internal var context: Context, internal var itemArray : JSONArray): BaseAdapter()  {
    private val url = "http://192.168.43.156/milk/"
    var appConfig =appconfig()
    lateinit var myPref: MyPref
    lateinit var appconfig: appconfig

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var viewHolder = ViewHolder()
        var view = layoutInflater.inflate(R.layout.orderinfo,parent,false)

        viewHolder.txtitemname=view.findViewById(R.id.txtitemname)
        viewHolder.txtitemqty=view.findViewById(R.id.txtitemqty)
        viewHolder.txtitemprice=view.findViewById(R.id.txtitemprice)
        viewHolder.txtoimage=view.findViewById(R.id.txtoimage)

        view.setTag(viewHolder)

        var o = itemArray.getJSONObject(position)

        viewHolder.txtitemname?.setText(o.getString("category_name").toString())
        viewHolder.txtitemqty?.setText(o.getString("o_quentity").toString())
        viewHolder.txtitemprice?.setText(o.getString("price").toString())

        var imageUrl=url+"menudetails/"+o.getString("c_image")
        var requestOptions= RequestOptions()
        requestOptions.placeholder(R.drawable.splash_logo)
        Log.e("Image URL",imageUrl)
        Glide.with(context).load(imageUrl).apply(requestOptions).into(viewHolder.txtoimage!!)

        return view
    }

    override fun getItem(position: Int): Any {
        return itemArray.getJSONObject(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return itemArray.length()
    }

    lateinit var layoutInflater: LayoutInflater

    init {
        layoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        myPref= MyPref(context)
        appconfig=appconfig()

    }
    inner class ViewHolder
    {
        var txtitemname : TextView? = null
        var txtitemqty : TextView? = null
        var txtitemprice : TextView? = null
        var txtoimage : ImageView? = null
    }

}
