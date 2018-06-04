package com.ppjun.android.arms.utils

import android.content.Context
import java.io.*
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by ppjun on 3/12/18.
 * 用法 var apple = Preference(context,key,value)
 * apple="22222" 自己写入
 * apple2=apple 自己读取
 */
class Preference<T>(val context: Context,val  name: String,val  default: T) : ReadWriteProperty<Any?, T> {

    private val SPNAME = "config"


    val prefs by lazy {
        context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE)
    }

    private fun <U> findPreference(name: String, default: U): U = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> deSerialization(getString(name, serialize(default)))
        }

        res as U
    }

    private fun <U> putPreference(name: String, value: U) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> putString(name, serialize(value))
        }.apply()

    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }


    @Throws(IOException::class)
   private  fun <U> serialize(obj: U): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    fun clearPreference() {
        prefs.edit().clear().apply()
    }

    fun clearPreference(key: String) {
        prefs.edit().remove(key).apply()
    }

  private   fun <U> deSerialization(str: String): U {
        val readStr = URLDecoder.decode(str, "UTF-8")
        val byteArrayinputStream = ByteArrayInputStream(
                readStr.toByteArray(charset("ISO-8859-1"))
        )
        val objectInputStream = ObjectInputStream(byteArrayinputStream)
        val obj = objectInputStream.readObject() as U
        objectInputStream.close()
        byteArrayinputStream.close()
        return obj

    }


}