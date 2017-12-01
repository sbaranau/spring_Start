package julia.web.webdav;

import net.sf.webdav.WebDavServletBean;

import javax.servlet.ServletException;

public class WebDavServlet extends WebDavServletBean {

    public void init() throws ServletException {
        boolean lazyFolderCreationOnPut = getInitParameter("lazyFolderCreationOnPut") != null
                && getInitParameter("lazyFolderCreationOnPut").equals("1");

        final String dftIndexFile = getInitParameter("default-index-file");
        final String insteadOf404 = getInitParameter("instead-of-404");
        int noContentLengthHeader = getIntInitParameter("no-content-length-headers");
        super.init(new DocumentStorage(), dftIndexFile, insteadOf404, noContentLengthHeader, lazyFolderCreationOnPut);
    }

    private int getIntInitParameter(final String key) {
        return getInitParameter(key) == null ? -1 : Integer.parseInt(getInitParameter(key));
    }

}
