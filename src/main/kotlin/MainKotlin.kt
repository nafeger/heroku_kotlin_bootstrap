/**
 * Created by nfeger on 9/28/14.
 */
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.jscience.physics.model.RelativisticModel
import org.jscience.physics.amount.Amount
import javax.measure.quantity.Mass
import org.eclipse.jetty.servlet.ServletHolder

class MainKotlin : HttpServlet() {

    fun main(args: Array<String>) {
        val portRequest : String? = System.getenv("PORT")
        val port : Int
        if (portRequest == null) {
            throw IllegalArgumentException("Must specify environment PORT")
        }
        port = Integer.parseInt(portRequest as String)
        val server = Server(port)
        val context = ServletContextHandler(ServletContextHandler.SESSIONS)
        context.setContextPath("/")
        server.setHandler(context)
        context.addServlet(ServletHolder(MainKotlin()),"/*")
        server.start()
        server.join()
    }

    protected override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
//        if (req.getRequestURI().endsWith("/db")) {
//            showDatabase(req,resp);
//        } else {
            showHome(req,resp);
//        }
    }


    fun showHome(req: HttpServletRequest? , resp: HttpServletResponse?) {

        // Energy is compatible with mass (E=mc2)
        // Energy is compatible with mass (E=mc2)
        RelativisticModel.select();

        val energy = System.getenv().get("ENERGY");

        val m = Amount.valueOf(energy)?.to(javax.measure.unit.SI.KILOGRAM);
        resp?.getWriter()?.print("Kotlin writes: E=mc^2: " + energy + " = " + m);
    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//    throws ServletException, IOException {
//
//        if (req.getRequestURI().endsWith("/db")) {
//            showDatabase(req,resp);
//        } else {
//            showHome(req,resp);
//        }
//    }

}