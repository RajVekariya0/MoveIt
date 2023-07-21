package com.example.shopowner.milkparlor

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.shopowner.milkparlor.R
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.categoryitem.*
import org.json.JSONArray
import org.json.JSONObject

class CategoryActivity : AppCompatActivity() {

    var appConfig= appconfig()
    lateinit var myPref: MyPref
    var categoryArray=JSONArray()

    var menuId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        supportActionBar?.setTitle("Category")
        myPref= MyPref(this)


        rvCategory.setHasFixedSize(true)
        rvCategory.layoutManager=LinearLayoutManager(this)

        menuId=intent.getStringExtra("menu_id")
        supportActionBar?.setTitle( intent.getStringExtra("menu_name")+"'s Category")
        rvCategory.setHasFixedSize(true)
        rvCategory.layoutManager= LinearLayoutManager(this)
        //      rvMenu.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)


        Cfab.setOnClickListener { view ->
            //startActivity()
            var intent = Intent(this, InsertCategoryActivity::class.java)
            var bundle = Bundle()
            bundle.putString("menu_id",menuId)
            intent.putExtras(bundle)
                    startActivity(intent)

        }
    }

    override fun onResume() {
        super.onResume()
        getAllCategory()
    }


    fun deleteCategory(position:Int,detail_id:String) {
        var pDialog = ProgressDialog(this)
        pDialog.setMessage("Loading...")
        pDialog.setCancelable(true)
        pDialog.show()

        var url: String = appConfig.url + "menudetails/deletecategory.php?detail_id=" + detail_id + "&m_id=" + menuId
        Log.e("Login Url", url)

        try {


            var stringRequest = StringRequest(Request.Method.GET,
                    url,
                    Response.Listener {
                        pDialog.dismiss()
                        Log.e("Response", it)
                        var jsonObject = JSONObject(it)

                        //Toast.makeText(this,jsonObject.getString("message"), Toast.LENGTH_LONG).show()

                        if (jsonObject.getInt("status") == 1) {
                            categoryArray.remove(position)
                            // var categoryArray = jsonObject.getJSONArray("category")
                            Log.e("CategoryData", categoryArray.toString())
                            rvCategory.adapter = CategoryAdapter(this, categoryArray)
                        }
                    },
                    Response.ErrorListener {
                        pDialog.dismiss()
                        Log.e("Volly", "Error", it)
                    }
            )
            VolleyApplication.instance?.addRequestQueue(stringRequest)

        } catch (e: Exception) {
            Log.e("Exception", "Error", e)
        }
    }
    fun getAllCategory(){
        var pDialog= ProgressDialog(this)
        pDialog.setMessage("Retriving Data")
        pDialog.setCancelable(true)
        pDialog.show()

        var url:String=appConfig.url+"menudetails/getcategory.php?m_id="+menuId
        Log.e("Login Url",url)

        var stringRequest= StringRequest (Request.Method.GET,
                url,
                Response.Listener {
                    pDialog.dismiss()
                    Log.e("Response",it)
                    var jsonObject= JSONObject(it)

                    //Toast.makeText(this,jsonObject.getString("message"), Toast.LENGTH_LONG).show()

                    if(jsonObject.getInt("status")==1) {

                        categoryArray=jsonObject.getJSONArray("category")
                        Log.e("CategoryData",categoryArray.toString())
                        rvCategory.adapter=CategoryAdapter(this,categoryArray)
                    }                },
                Response.ErrorListener {
                    pDialog.dismiss()
                    Log.e("Volly","Error",it)
                }
        )
        VolleyApplication.instance?.addRequestQueue(stringRequest)
    }
    }

