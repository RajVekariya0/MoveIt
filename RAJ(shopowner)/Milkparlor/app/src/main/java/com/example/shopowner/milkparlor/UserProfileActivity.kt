package com.example.shopowner.milkparlor

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shopowner.milkparlor.R
import kotlinx.android.synthetic.main.activity_user_profile.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream

class UserProfileActivity : AppCompatActivity() {
    lateinit var myPref: MyPref
    var imageBitmap: Bitmap? = null
    var filePath: Uri? = null
    var myUtility = MyUtility()

    var appConfig = appconfig()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        //setSupportActionBar(Toolbar)
        supportActionBar?.setTitle("User Profile")
        myPref= MyPref(this)


        ivprofile.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(Intent.createChooser(intent, "Select Image"), 111)

        }

        var imageurl=appConfig.url+"shopowner/"+myPref.getImagePath()

        var requestOption = RequestOptions()
        requestOption.placeholder(R.drawable.userprofile)


        Glide.with(this).load(imageurl).apply(requestOption).into(ivprofile)
       //Glide.with(this).load(imageurl).placeholder(R.drawable.userprofile.into(ivprofile)

        txtname.setText(myPref.getname())
        txtadd.setText(myPref.getadd())
        txtmobno.setText(myPref.getmobileno() )


        btnSubmit.setOnClickListener {
            if(imageBitmap==null)
            {
                imageBitmap=(ivprofile.drawable as BitmapDrawable).bitmap
            }
            if(imageBitmap==null)
            {
                Toast.makeText(this,"Please! Attach Image",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            val baos = ByteArrayOutputStream()
            if (imageBitmap == null) return@setOnClickListener
            imageBitmap?.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b = baos.toByteArray()

            UpdateUser(Base64.encodeToString(b, Base64.DEFAULT))
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

                ivprofile.setImageBitmap(imageBitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Image", "Browse", e)
                try {
                    var imagePath = myUtility.getUriRealPath(this, filePath!!)
                    imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(imagePath))
                    Log.e("File Path", filePath.toString())
                    //if(imageBitmap.)

                    ivprofile.setImageBitmap(imageBitmap)
                } catch (e: Exception) {
                    Log.e("Image1", "Browse1", e)
                }


            }

        }
    }



    fun UpdateUser(imageString : String)
    {
        var pDialog = ProgressDialog(this)
        pDialog.setTitle("User Authentication")
        pDialog.setCancelable(true)
        pDialog.show()

        var url = appConfig.url+"shopowner/updateuser.php?"//s_id="+myPref.getUserId()+"&s_name="+txtname.text.toString()+"&s_add="+txtadd.text.toString()+"&s_mobileno="+txtmobno.text.toString()
        Log.e("Create User URL",url)

        Toast.makeText(this,"update profile", Toast.LENGTH_LONG).show()


        var stringRequest= object : StringRequest(Request.Method.POST,url,

            Response.Listener {
                pDialog.dismiss()
                Log.e("Response",it)

                var json= JSONObject(it)
                if(json.getInt("status")==1)
                {
                    myPref.setImagePath(json.getString("imagename"))
                    myPref.setname(txtname.text.toString())
                    myPref.setadd(txtadd.text.toString())
                    myPref.setmobileno(txtmobno.text.toString())
                }
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
                map.put("s_name",txtname.text.toString())
                map.put("s_add",txtadd.text.toString())
                map.put("s_mobileno",txtmobno.text.toString())
                map.put("s_image",imageString)
                return map
            }
        }

        VolleyApplication.instance?.addRequestQueue(stringRequest)
    }
}
