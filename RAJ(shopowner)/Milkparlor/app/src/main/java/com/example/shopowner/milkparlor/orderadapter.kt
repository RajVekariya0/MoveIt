package com.example.shopowner.milkparlor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import org.json.JSONArray

class orderadapter (internal var context:Context,internal var orderArray : JSONArray): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var viewHolder = ViewHolder()
        var view = layoutInflater.inflate(R.layout.order,parent,false)

        viewHolder.txtorderno=view.findViewById(R.id.txtorderno)
        viewHolder.txtdeliveryboyname=view.findViewById(R.id.txtdeliveryboyname)
        viewHolder.txtdeliveryboymobno=view.findViewById(R.id.txtdeliveryboymobno)
        viewHolder.txtorderdate=view.findViewById(R.id.txtorderdate)
        viewHolder.txttransactiontype=view.findViewById(R.id.txttransactiontype)
        viewHolder.txtamount=view.findViewById(R.id.txtamount)
        viewHolder.btnDetail=view.findViewById(R.id.btnDetail)

        view.setTag(viewHolder)

        var o = orderArray.getJSONObject(position)

        viewHolder.txtorderno?.setText(o.getString("o_id").toString())
        viewHolder.txtdeliveryboyname?.setText(o.getString("d_name").toString())
        viewHolder.txtdeliveryboymobno?.setText(o.getString("d_mobileno").toString())
        viewHolder.txtorderdate?.setText(o.getString("order_date").toString())
        viewHolder.txttransactiontype?.setText(o.getString("transaction_type").toString())
        viewHolder.txtamount?.setText(o.getString("amount").toString())


        viewHolder.btnDetail?.setOnClickListener {

            var o = orderArray.getJSONObject(position)

            if(!o.has("details")){
                Toast.makeText(context,"Details Not Available",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            var bundle = Bundle()
            bundle.putString("details",o.getJSONArray("details").toString())
            var intent = Intent(context,OrderActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)

        }


        return view
    }

    override fun getItem(position: Int): Any {
        return orderArray.getJSONObject(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return orderArray.length()
    }

    lateinit var layoutInflater: LayoutInflater


    init {
        layoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }


    inner class ViewHolder
    {
        var txtorderno : TextView? = null
        var txtdeliveryboyname : TextView? = null
        var txtdeliveryboymobno : TextView? = null
        var txtorderdate : TextView? = null
        var txttransactiontype : TextView? = null
        var txtamount : TextView? = null
        var btnDetail : ImageView?=null
    }

}