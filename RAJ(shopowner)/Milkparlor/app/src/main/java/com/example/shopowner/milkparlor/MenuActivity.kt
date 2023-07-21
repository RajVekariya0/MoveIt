package com.example.shopowner.milkparlor

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.shopowner.milkparlor.R
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.menuitem.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.FieldPosition

class MenuActivity : AppCompatActivity() {

    var appConfig= appconfig()
    lateinit var myPref: MyPref
    var menuArray = JSONArray()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        supportActionBar?.setTitle("Menu")
        myPref= MyPref(this)

        fab.setOnClickListener { view ->
            startActivity(Intent(this, InsertMenuActivity::class.java))
        }

        rvMenu.setHasFixedSize(true)
        rvMenu.layoutManager=LinearLayoutManager(this)
  //      rvMenu.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        //getAllMenu()



}


    override fun onResume() {
        super.onResume()
        getAllMenu()
    }

    fun deleteMenu(position : Int,m_id:String)
    {
        var pDialog=ProgressDialog(this)
        pDialog.setMessage("Loading...")
        pDialog.setCancelable(true)
        pDialog.show()


        var url:String=appConfig.url+"menumaster/deletemenu.php?s_id="+myPref.getUserId()+"&m_id="+m_id
        Log.e("Login Url",url)

        try {


            var stringRequest = StringRequest(Request.Method.GET,
                    url,
                    Response.Listener {
                        pDialog.dismiss()
                        Log.e("Response", it)
                        var jsonObject = JSONObject(it)

                        Toast.makeText(this,jsonObject.getString("message"), Toast.LENGTH_LONG).show()

                        if (jsonObject.getInt("status") == 1) {
     //                       Log.e("Deleted data", menuArray.getJSONObject(position).toString())
                            menuArray.remove(position)
                            //var menuArray=jsonObject.getJSONArray("menu")
                            Log.e("MenuData", menuArray.toString())
                            rvMenu.adapter = MenuAdapter(this, menuArray)
                        }
                    },
                    Response.ErrorListener {
                        pDialog.dismiss()
                        Log.e("Volly", "Error", it)
                    }
            )

            VolleyApplication.instance?.addRequestQueue(stringRequest)
        }
        catch (e :Exception)
        {
            Log.e("Exception","Error",e)
        }
    }


    fun getAllMenu()
    {
        var pDialog= ProgressDialog(this)
        pDialog.setMessage("Retriving Data")
        pDialog.setCancelable(true)
        pDialog.show()

        var url:String=appConfig.url+"menumaster/getmenu.php?s_id="+myPref.getUserId()
        Log.e("Login Url",url)

        var stringRequest= StringRequest (Request.Method.GET,
                url,
                Response.Listener {
                    pDialog.dismiss()
                    Log.e("Response",it)
                    var jsonObject= JSONObject(it)

                    //Toast.makeText(this,jsonObject.getString("message"), Toast.LENGTH_LONG).show()

                    if(jsonObject.getInt("status")==1) {

                       menuArray=jsonObject.getJSONArray("menu")
                        Log.e("MenuData",menuArray.toString())
                        rvMenu.adapter=MenuAdapter(this,menuArray)
                    }
                },
                Response.ErrorListener {
                    pDialog.dismiss()
                    Log.e("Volly","Error",it)
                }
        )

        VolleyApplication.instance?.addRequestQueue(stringRequest)
    }
}
