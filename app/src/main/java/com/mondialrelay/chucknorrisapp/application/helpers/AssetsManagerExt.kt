package com.mondialrelay.chucknorrisapp.application.helpers

import android.content.res.AssetManager

fun <T> AssetManager.get(key: String, byDefault: T?): T? {
    return with(configProperties) {
        if (isEmpty) {
            with(open("config.properties")) {
                configProperties.load(this)
                close()
            }
        }
        configProperties
            .getProperty(key)
            ?.run {
//                @Suppress("UNCHECKED_CAST")
                when (byDefault) {
                    is Int -> toInt()
                    is Long -> toLong()
                    is Float -> toFloat()
                    is Boolean -> toBoolean()
                    is String -> this
                    else -> byDefault
                } as T
            }
            ?: byDefault
    }

//    if (configProperties.isEmpty) {
//        with(open("config.properties")) {
//            configProperties.load(this)
//            close()
//        }
//    }
//
//    return configProperties.getProperty(key)?.run {
//        @Suppress("UNCHECKED_CAST")
//        when (byDefault) {
//            is Int -> toInt()
//            is Long -> toLong()
//            is Float -> toFloat()
//            is Boolean -> toBoolean()
//            is String -> this
//            else -> byDefault
//        } as? T
//    } ?: byDefault

}

fun AssetManager.get(key: String): String? {
    return get(key, "")
}

private val configProperties by lazy { java.util.Properties() }
