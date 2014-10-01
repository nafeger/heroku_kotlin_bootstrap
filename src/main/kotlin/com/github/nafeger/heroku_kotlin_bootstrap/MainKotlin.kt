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
import java.util.regex.Pattern
import java.net.URI
import java.sql.Connection
import java.sql.ResultSet
import java.sql.DriverManager
import java.sql.Statement
import kotlin.jdbc.*;
//import kotlin.jdbc.JdbcPackage.*;

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
        if (req.getRequestURI()?.endsWith("/db") as Boolean) {
            showDatabase(resp);
        } else {
            showHome(resp);
        }
    }

    /**
     * Show the default home screen
     */
                // req is commented out since kotlin doesn't like params that aren't used.
    fun showHome(/*req: HttpServletRequest,*/ resp: HttpServletResponse) {
        // Energy is compatible with mass (E=mc2)
        RelativisticModel.select();

            // This may mask the fact that you haven't set ENERGY, but it shows the '?:' operator
        val energy : CharSequence? = System.getenv().get("ENERGY") ?: "74 GeV";

            // Here you can see me trusting java not to hose me...
            // One doesn't usually think of these objects are returning nullable
            // objects, but technically they might.
        resp.getWriter()?.print("energy: "+energy+"...")
        val m = Amount.valueOf(energy)?.to(SI.KILOGRAM)
        resp.getWriter()?.print("Kotlin writes: E=mc^2: " + energy + " = " + m)
    }

    fun showDatabase(resp: HttpServletResponse) {
//        try {
            val connection : Connection = getDbConnection()

//            val stmt : Statement = connection.createStatement()
//        connection.
//            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)")
//            stmt.executeUpdate("INSERT INTO ticks VALUES (now())")
//            val rs : ResultSet = stmt.executeQuery("SELECT tick FROM ticks")

//            var out = "Hello!\n"
//            rs.next().to(rs.getTimestamp("tick"))
//            while (rs.next()) {
//                out = out.plus("Read from DB: " + rs.getTimestamp("tick") + "\n")
//            }

//            resp.getWriter()?.print(out);
//        } catch (Exception e) {
//            resp.getWriter().print("There was an error: " + e.getMessage());
//        }
    }

    fun getDbConnection(): Connection {
        val dbUrlStr = System.getenv("DATABASE_URL")
        if (dbUrlStr == null) {
            throw IllegalArgumentException("DATABASE_URL required")
        }

        val dbUri : URI = URI(dbUrlStr);

        val userAndPass = dbUri.getUserInfo()?.split(":");

        return kotlin.jdbc.JdbcPackage.getConnection("jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath(),
                // See the !! here, that means I'm forcing a coercion to a String from a String?, you probably should't do that.
                userAndPass!!.first(), userAndPass!!.last());

    }
}