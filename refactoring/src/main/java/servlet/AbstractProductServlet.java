package servlet;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ParametersAreNonnullByDefault
public abstract class AbstractProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            doRequest(req, resp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    protected abstract void doRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    protected void enrichResponseHttpInfo(HttpServletResponse response, List<String> info) throws IOException {
        response.getWriter().println("<html><body>");
        info.forEach(str -> uncheckedWrite(response, str));
        response.getWriter().println("</body></html>");
    }

    private void uncheckedWrite(HttpServletResponse response, String info) {
        try {
            response.getWriter().println(info);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
