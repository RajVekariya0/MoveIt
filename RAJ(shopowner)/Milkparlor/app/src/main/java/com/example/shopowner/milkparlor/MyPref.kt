package com.example.shopowner.milkparlor

import android.content.Context
import android.content.SharedPreferences

class MyPref(internal var context:Context) {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    private val key_s_id="s_id"
    private val key_s_name="s_name"
    private val key_s_password="s_password"
    private val key_s_add="s_add"
    private val key_s_mobileno="s_mobileno"
    private val key_s_image="s_image"

    init {
        sharedPreferences=context.getSharedPreferences("xyz",Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()
    }

    fun setImagePath(s_image : String)
    {
        editor.putString(key_s_image,s_image)
        editor.commit()
    }

    fun getImagePath():String
    {
        return sharedPreferences.getString(key_s_image,"")
    }



    fun setUserId(userId:String)
    {
        editor.putString(key_s_id,userId)
        editor.commit()
    }
    fun getUserId() :String?
    {
        return sharedPreferences.getString(key_s_id,null)
    }

    fun setname(name:String)
    {
        editor.putString(key_s_name,name)
        editor.commit()
    }
    fun getname() :String?
    {
        return sharedPreferences.getString(key_s_name,null)
    }


    fun setpassword(password:String)
    {
        editor.putString(key_s_password,password)
        editor.commit()
    }
    fun getpassword() :String?
    {
        return sharedPreferences.getString(key_s_password,null)
    }

    fun setadd(add:String)
    {
        editor.putString(key_s_add,add)
        editor.commit()
    }
    fun getadd() :String?
    {
        return sharedPreferences.getString(key_s_add,null)
    }

    fun setmobileno(mobileno:String)
    {
        editor.putString(key_s_mobileno,mobileno)
        editor.commit()
    }
    fun getmobileno() :String?
    {
        return sharedPreferences.getString(key_s_mobileno,null)
    }


    fun ClearData()
    {
        editor.clear()
        editor.commit()
    }
}