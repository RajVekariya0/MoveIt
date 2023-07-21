package com.example.shopowner.milkparlor

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_insert_menu.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream

class InsertMenuActivity : AppCompatActivity() {

    lateinit var myPref: MyPref
    lateinit var appconfig: appconfig
    var imageBitmap : Bitmap? = null
    var myUtility = MyUtility()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_menu)
        supportActionBar?.setTitle("Insert Menu")
        appconfig=appconfig()
        myPref=MyPref(this)

        menuImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(Intent.createChooser(intent, "Select Image"), 111)
        }


        btnmenusubmit.setOnClickListener {

            if(imageBitmap==null)
            {
                Toast.makeText(this,"Please! Attach Image",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val baos = ByteArrayOutputStream()
            if (imageBitmap == null) return@setOnClickListener
            imageBitmap?.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b = baos.toByteArray()

            submitData(Base64.encodeToString(b, Base64.DEFAULT))


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Log.e("Result Code",resultCode.toString())
        // Log.e("Result Data",data?.toString())
        if (requestCode == 111 &&
                resultCode == Activity.RESULT_OK && data != null &&
                data.data != null) {
           var filePath = data.data

            Log.e("Result Code", resultCode.toString())
            Log.e("Result Data", data?.toString())
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                Log.e("File Path", filePath.toString())
                //E/File Path: content://media/external/images/media/16576
                //if(imageBitmap.)

                menuImage.setImageBitmap(imageBitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Image", "Browse", e)
                try {
                    var imagePath = myUtility.getUriRealPath(this, filePath!!)
                    imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(imagePath))
                    Log.e("File Path", filePath.toString())
                    //if(imageBitmap.)

                    menuImage.setImageBitmap(imageBitmap)
                } catch (e: Exception) {
                    Log.e("Image1", "Browse1", e)
                }


            }

        }
    }

    fun submitData(imageString : String)
    {
        var pDialog= ProgressDialog(this)
        pDialog.setTitle("process...")
        pDialog.setCancelable(true)
        pDialog.show()

        var url=appconfig.url+"menumaster/createmenu.php"//?s_id="+myPref.getUserId()+"&m_name="+txtmenuname.text.toString()
        Log.e("Create Menu URL",url)

        Toast.makeText(this,"create menu", Toast.LENGTH_LONG).show()

        var stringRequest= object : StringRequest(Request.Method.POST,url,

                Response.Listener {
                    pDialog.dismiss()
                    Log.e("Response",it)

                    var json= JSONObject(it)
                    Toast.makeText(this,json.getString("message"), Toast.LENGTH_SHORT).show();
                    finish()
                },
                Response.ErrorListener {
                    pDialog.dismiss()
                    Log.e("Volley","Error",it)
                }
        ){
            override fun getParams(): MutableMap<String, String> {
                var map = HashMap<String,String>()
                map.put("s_id",myPref.getUserId().toString())
                map.put("m_name",txtmenuname.text.toString())
                map.put("m_image",imageString)
                return map
            }
        }
        VolleyApplication.instance?.addRequestQueue(stringRequest)
    }


}
