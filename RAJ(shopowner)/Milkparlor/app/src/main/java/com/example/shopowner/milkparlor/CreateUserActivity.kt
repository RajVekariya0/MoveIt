package com.example.shopowner.milkparlor

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.shopowner.milkparlor.R
import kotlinx.android.synthetic.main.activity_create_user.*
import org.json.JSONObject

class CreateUserActivity : AppCompatActivity() {

    lateinit var appconfig: appconfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        supportActionBar?.setTitle("Registration")

        appconfig= appconfig()


        fun isValidPassword(txtpassword:String?):Boolean{
            txtpassword?.let {
                val passwordpattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{4,}$"
                val passwordmatcher=Regex(passwordpattern)

                return passwordmatcher.find(txtpassword)!=null
            }?: return false
        }


        btnSignup.setOnClickListener {
            if(isValidPassword(txtPassword.text.toString())==false)
            {
                txtPassword.setError("Must have 1 digit,1 Symbol, 1 Capital alphabet")
                return@setOnClickListener
            }

            if(chbcondition.isChecked==false)
            {
                Log.e("checkbox","Agree our Terms & conditions")
                return@setOnClickListener
            }

            if (txtPassword.text.toString().equals(
                            txtConfirmpassword.text.toString()
                    ))
            {
                CreateUser()
            }
            else
            {
                Toast.makeText(this,"Password Not Match",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun CreateUser()
    {
        var pDialog=ProgressDialog(this)
                pDialog.setTitle("process...")
        pDialog.setCancelable(true)
        pDialog.show()

        var url=appconfig.url+"shopowner/createuser.php?s_name="+txtName.text.toString()+"&s_add="+txtadd.text.toString()+"&s_mobileno="+txtmobno.text.toString()+"&s_password="+txtPassword.text.toString()
        Log.e("Create User URL",url)


        Toast.makeText(this,"create user",Toast.LENGTH_LONG).show()

        var stringRequest=StringRequest(Request.Method.GET,url,

                Response.Listener {
                    pDialog.dismiss()
                    Log.e("Response",it)

                    var json=JSONObject(it)
                    Toast.makeText(this,json.getString("message"),Toast.LENGTH_SHORT).show();
                    finish()
                },
                Response.ErrorListener {
                    pDialog.dismiss()
                    Log.e("Volley","Error",it)
                }
        )
        VolleyApplication.instance?.addRequestQueue(stringRequest)
    }
}

