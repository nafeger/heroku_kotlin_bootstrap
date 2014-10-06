package com.github.nafeger.heroku_kotlin_bootstrap

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder

/**
 * Created by nfeger on 10/5/14.
 */
fun main(args: Array<String>) {
    // Note: Kotlin doesn't trust java, and rightly so, Java loves it some null
    val portRequest: String? = System.getenv("PORT")
    val port: Int
    if (portRequest == null) {
        throw IllegalArgumentException("Must specify environment PORT")
    }
    port = Integer.parseInt(portRequest as String)

    //Note: 'new' is passe
    val server = Server(port)
    val context = ServletContextHandler(ServletContextHandler.SESSIONS)
    context.setContextPath("/")
    server.setHandler(context)
    context.addServlet(ServletHolder(MainKotlin()), "/*")
    server.start()
    server.join()
}
