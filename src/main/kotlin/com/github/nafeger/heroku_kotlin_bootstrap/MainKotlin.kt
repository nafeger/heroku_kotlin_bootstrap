package com.github.nafeger.heroku_kotlin_bootstrap

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import javax.measure.unit.SI
import org.eclipse.jetty.servlet.ServletHolder
import org.jscience.physics.model.RelativisticModel
import org.jscience.physics.amount.Amount

class MainKotlin : HttpServlet() {
    // Kotlin has a class object where this main method makes more sense, but it's not working for me, so it remains
    // commented out.

//    class object {
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
//    }

    /**
     * This method overrides java's doGet method.
     *
     * Notice the parameters are specified with a trailing ? mark, Kotlin never seems to let
     * you forget that Java isn't really trustworthy.
     */
    protected override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        if (req == null || resp == null) {
            throw IllegalArgumentException("Request and Response required.");
        }
        showHome(resp);
    }

    /**
     * Show the default home screen
     */
                // req is commented out since kotlin doesn't like params that aren't used.
    fun showHome(/*req: HttpServletRequest,*/ resp: HttpServletResponse) {
        // Energy is compatible with mass (E=mc2)
        RelativisticModel.select();

        val energy : String? = System.getenv().get("ENERGY");

            // Here you can see me trusting java not to hose me...
            // One doesn't usually think of these objects are returning nullable
            // objects, but technically they might.
        resp.getWriter()?.print("energy: "+energy+"...");
        val m = Amount.valueOf(energy)?.to(SI.KILOGRAM);
        resp.getWriter()?.print("Kotlin writes: E=mc^2: " + energy + " = " + m);
    }
}