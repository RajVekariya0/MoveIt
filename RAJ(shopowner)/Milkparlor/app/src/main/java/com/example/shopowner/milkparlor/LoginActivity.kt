package com.example.shopowner.milkparlor

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.shopowner.milkparlor.R
import com.google.firebase.iid.FirebaseInstanceId
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_login.*
class LoginActivity : AppCompatActivity() {

    var appConfig= appconfig()
    lateinit var myPref: MyPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

       myPref= MyPref(this)


        if(myPref.getUserId()!=null)
        {
            finish()
            startActivity(Intent(this, DashboardActivity::class.java))
            return
        }

        tvCreateAccount.setOnClickListener {
            startActivity(Intent(this, CreateUserActivity::class.java))
        }


        fun isValidPassword(txtpassword:String?):Boolean{
             txtpassword?.let {
              val passwordpattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{4,}$"
              val passwordmatcher=Regex(passwordpattern)

              return passwordmatcher.find(txtpassword)!=null
            }?: return false
        }


        txtlogin.setOnClickListener{
            var fcmToken = ""

            if(isValidPassword(txtpassword.text.toString())==false)
            {
                txtpassword.setError("Must have 1 digit,1 Symbol, 1 Capital alphabet")
                return@setOnClickListener
            }

            try {
                fcmToken=FirebaseInstanceId.getInstance().getToken().toString()
            }
            catch (e:Exception)
            {
                Log.e("Token","Error",e)
                return@setOnClickListener
            }

            if(fcmToken.length==0) return@setOnClickListener



            var pDialog=ProgressDialog(this)
            pDialog.setMessage("Verify User")
            pDialog.setCancelable(true)
            pDialog.show()

            var url:String=appConfig.url+"shopowner/loginuser.php?mobno="+txtmobno.text.toString()+"&password="+txtpassword.text.toString()+"&tokenid="+fcmToken

            Log.e("Login Url",url)
            var stringRequest= StringRequest (Request.Method.GET,
                    url,
                    Response.Listener {
                        pDialog.dismiss()
                        Log.e("Response",it)
                        var jsonObject=JSONObject(it)

                        Toast.makeText(this,jsonObject.getString("message"),Toast.LENGTH_LONG).show()

                        if(jsonObject.getInt("status")==1) {
                            myPref.setUserId(jsonObject.getString("s_id"))
                            myPref.setname(jsonObject.getString("s_name"))
                            myPref.setpassword(jsonObject.getString("s_password"))
                            myPref.setadd(jsonObject.getString("s_add"))
                            myPref.setmobileno(jsonObject.getString("s_mobileno"))
                            myPref.setImagePath(jsonObject.getString("s_image"))
                            finish()
                            startActivity(Intent(this, DashboardActivity::class.java))
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
}
