package com.example.shopowner.milkparlor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.json.JSONArray

class CategoryAdapter(internal var context : Context, internal var categoryArray : JSONArray) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    lateinit var layoutInflater : LayoutInflater
    lateinit var appconfig : appconfig

    init {
        layoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        appconfig=appconfig()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = layoutInflater.inflate(R.layout.categoryitem,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return categoryArray.length()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var o = categoryArray.getJSONObject(position)
        holder.tvCategoryName.setText(o.getString("category_name"))
        holder.tvPrice.setText(o.getString("price"))

        var imageUrl = appconfig.url+"menudetails/"+o.getString("c_image")

        var requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.splash_logo)

        Glide.with(context).load(imageUrl).apply(requestOptions).into(holder.categoryImage)


    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        lateinit var tvCategoryName : TextView
        lateinit var tvPrice : TextView
        lateinit var btncategorydelete:ImageView
        lateinit var btncategoryupdate:ImageView
        lateinit var categoryImage : ImageView

        init {
                    tvCategoryName=view.findViewById(R.id.tvCategoryName)
                    tvPrice=view.findViewById(R.id.tvPrice)
                    categoryImage=view.findViewById(R.id.categoryImage)
                    btncategorydelete=view.findViewById(R.id.btncategorydelete)
                    btncategoryupdate=view.findViewById(R.id.btncategoryupdate)

            btncategoryupdate.setOnClickListener {
                var o=categoryArray.getJSONObject(position)
                //(context as CategoryActivity).updateCategory(position,o.getString("detail_id"))
                //startActivity(Intent(context, CategoryActivity::class.java))
                var intent = Intent(context,InsertCategoryActivity::class.java)
                var bundle = Bundle()
                bundle.putString("data",o.toString())
                intent.putExtras(bundle)
                context.startActivity(intent)

            }

            btncategorydelete.setOnClickListener{
                var o=categoryArray.getJSONObject(position)
                (context as CategoryActivity).deleteCategory(position,o.getString("detail_id"))
            }
/*            var o=categoryArray.getJSONObject(position)

            var intent=Intent(context,categoryArray::class.java)
            intent.putExtra("detail_id",o.getString("detail_id"))
            intent.putExtra("category_name",o.getString("category_name"))
            intent.putExtra("price",o.getString("price"))
            context.startActivity(intent)*/
        }
    }
}