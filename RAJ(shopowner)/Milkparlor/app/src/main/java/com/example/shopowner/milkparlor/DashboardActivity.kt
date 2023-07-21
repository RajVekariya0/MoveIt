package com.example.shopowner.milkparlor

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.SimpleAdapter
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.example.shopowner.milkparlor.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*
import org.json.JSONObject

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var myPref: MyPref
    var bname=ArrayList<HashMap<String,String>>()
    var appConfig=appconfig()

    var allpermissions = ArrayList<String>()
    var permissionToRequest = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        appConfig= appconfig()
        myPref=MyPref(this)

        supportActionBar?.setTitle("Milk Parlor")
        setSupportActionBar(toolbar)

        showorder()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        var tvName = nav_view.getHeaderView(0).findViewById<TextView>(R.id.tvName)
        tvName.setText(myPref.getname())
        nav_view.setNavigationItemSelectedListener(this)

        allpermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        allpermissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        allpermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        allpermissions.add(Manifest.permission.CAMERA)

        if(CheckMorAbove())
        {
            permissionToRequest=findUnaskedPermission(allpermissions)

            if(permissionToRequest.size>0)
            {
                ActivityCompat.requestPermissions(
                        this,
                        permissionToRequest.toArray(arrayOfNulls<String>(permissionToRequest.size)),
                        101
                )
            }
        }

         val navigationView=findViewById<View>(R.id.nav_view) as NavigationView

        navigationView.setNavigationItemSelectedListener(this)
        val header=navigationView.getHeaderView(0)

        val imageView=header.findViewById(R.id.imageView) as ImageView
        var imageurl=appConfig.url+"shopowner/"+myPref.getImagePath()
        Glide.with(this).load(imageurl).into(imageView)

    }

    fun findUnaskedPermission(permission : ArrayList<String>) : ArrayList<String>
    {
        var lst = ArrayList<String>()

        permission.forEach {
            if(ContextCompat.checkSelfPermission(this,it)!=
                    PackageManager.PERMISSION_GRANTED)
                lst.add(it)
        }

        return lst
    }



    fun CheckMorAbove() : Boolean
    {
        return Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_userprofie -> {
                startActivity(Intent(this, UserProfileActivity::class.java))
            }
            R.id.nav_changepassword -> {
                startActivity(Intent(this, ChangePasswordActivity::class.java))
            }
            R.id.nav_menu -> {
                startActivity(Intent(this, MenuActivity::class.java))
            }
            R.id.nav_aboutus -> {
                startActivity(Intent(this, AboutusActivity::class.java))
            }
            R.id.nav_contactus -> {
                startActivity(Intent(this, ContectusActivity::class.java))
            }
            R.id.nav_logout ->
            {
                myPref.ClearData()
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun showorder() {
        var pDialog = ProgressDialog(this)
        pDialog.setMessage("Loading......")
        pDialog.setCancelable(true)
        pDialog.show()
        bname.clear()
        var url = appConfig.url + "orderdetail/shoporder.php?s_id=${myPref.getUserId()}"
        Log.e("URL", url)

        var stringRequest = StringRequest(Request.Method.GET, url, Response.Listener {
            pDialog.dismiss()
            Log.e("Response", it)
            var jsonObject = JSONObject(it)
            if (jsonObject.getInt("status") == 1) {
                var jsonArray = jsonObject.getJSONArray("shoporder")
                rvorder.adapter=orderadapter(this,jsonArray)
            }

    }, Response.ErrorListener {
            pDialog.dismiss()
            Log.e("Volley", "Error", it)

        })
        VolleyApplication.instance?.addRequestQueue(stringRequest)


       /* val navigationView=findViewById<View>(R.id.nav_view) as NavigationView

        navigationView.setNavigationItemSelectedListener(this)
        val header=navigationView.getHeaderView(0)

        val imageView=header.findViewById(R.id.imageView) as ImageView
        var imageurl=appConfig.url+"shopowner/"+myPref.getImagePath()
        Glide.with(this).load(imageurl).into(imageView)

        val tvName=header.findViewById(R.id.tvName) as TextView
        val name=appConfig.url+"shopowner/"+myPref.getname()*/

    }

}
