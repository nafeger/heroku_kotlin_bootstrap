import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.jscience.physics.model.RelativisticModel
import org.jscience.physics.amount.Amount
import org.eclipse.jetty.servlet.ServletHolder

/**
 * This is my attempt at porting the Main class provided by Heroku. I hope to make it consistent with the Kotlin
 * design, but that will take time.
 */
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
            throw java.lang.IllegalArgumentException("Request and Response required.");
        }
        //        if (req.getRequestURI().endsWith("/db")) {
        //            showDatabase(req,resp);
        //        } else {
        showHome(req, resp);
        //        }
    }

    /**
     * Show the default home screen
     */
    fun showHome(req: HttpServletRequest, resp: HttpServletResponse) {
        // Energy is compatible with mass (E=mc2)
        RelativisticModel.select();

        val energy : String? = System.getenv().get("ENERGY");

            // Here you can see me trusting java not to hose me...
            // One doesn't usually think of these objects are returning nullable
            // objects, but clearly they might.
        resp.getWriter()?.print("energy: "+energy+"...");
        val m = Amount.valueOf(energy)?.to(javax.measure.unit.SI.KILOGRAM);
        resp.getWriter()?.print("Kotlin writes: E=mc^2: " + energy + " = " + m);
    }
}