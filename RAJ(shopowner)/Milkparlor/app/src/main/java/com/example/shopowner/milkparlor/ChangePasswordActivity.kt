package com.example.shopowner.milkparlor

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_change_password.*
import org.json.JSONObject

class ChangePasswordActivity : AppCompatActivity() {
var appConfig = appconfig()
    lateinit var myPref: MyPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        supportActionBar?.setTitle("Chnage password")
        fun isValidPassword(txtNewpassword:String?):Boolean{
            txtNewPassword?.let {
                val passwordpattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{4,}$"
                val passwordmatcher=Regex(passwordpattern)

                return passwordmatcher.find(txtNewPassword.text)!=null
            }?: return false
        }

        myPref= MyPref(this)
                btnChangePassword.setOnClickListener {

                    if(isValidPassword(txtNewPassword.text.toString())==false)
                    {
                        txtNewPassword.setError("Must have 1 digit,1 Symbol, 1 Capital alphabet")
                        return@setOnClickListener
                    }
                    if (txtNewPassword.text.toString().equals(
                                    txtConfirmPassword.text.toString()
                            ))
                    {

                        ChangePassword()
                    }
                    else
                    {
                    Toast.makeText(this,"Password is not correct",Toast.LENGTH_SHORT).show()
                    }
        }
    }

    fun ChangePassword()
    {
        var pDialog = ProgressDialog(this)
        pDialog.setMessage("Changing Password")
        pDialog.setCancelable(true)
        pDialog.show()

        var url = appConfig.url+"shopowner/changepassword.php?s_id=${myPref.getUserId()}&s_currentpassword=${txtCurrentPassword.text.toString()}&newpassword=${txtNewPassword.text.toString()}"
        Log.e("Change Password URL",url)

        var stringRequest = StringRequest(Request.Method.GET,url,
            Response.Listener {
                pDialog.dismiss()
                var jsonObject = JSONObject(it)
                Toast.makeText(this,jsonObject.getString("message"),Toast.LENGTH_LONG).show()
                if(jsonObject.getInt("status")==1) finish()

            },
            Response.ErrorListener {
                pDialog.dismiss()
                Log.e("Volley","Error",it)
            })

        VolleyApplication.instance?.addRequestQueue(stringRequest)

    }

}



