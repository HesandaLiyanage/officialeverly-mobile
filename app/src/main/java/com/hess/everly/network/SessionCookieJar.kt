package com.hess.everly.network

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class SessionCookieJar : CookieJar {
    private var cookies: List<Cookie> = emptyList()

    override fun saveFromResponse(url: HttpUrl, newCookies: List<Cookie>) {
        if (newCookies.isNotEmpty()) {
            this.cookies = newCookies // Tomcat sends JSESSIONID here upon login
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookies // Retrofit will attach the JSESSIONID to all future requests
    }
}
