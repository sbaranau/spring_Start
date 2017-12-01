package julia.web.webdav;

import julia.entity.Data;
import julia.service.data.DataService;
import net.sf.webdav.ITransaction;
import net.sf.webdav.IWebdavStore;
import net.sf.webdav.StoredObject;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Date;

public class DocumentStorage implements IWebdavStore {

    @Autowired
    private DataService dataService;

    public DocumentStorage() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public InputStream getResourceContent(final ITransaction transaction, final String resourceUri) {
        return getFileWithData(resourceUri);
    }

    @Override
    public long setResourceContent(final ITransaction transaction, final String resourceUri, final InputStream content, final String contentType, final String characterEncoding) {
        try {
            final Data data = getData(resourceUri);
            data.setData(IOUtils.toByteArray(content));
            updateData(data);
            return data.getData().length;
        } catch (final IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long getResourceLength(final ITransaction transaction, final String path) {
        if (!availableExpansion(path)) {
            return 0;
        }
        final Data fileData = getData(path);
        return fileData.getData().length;
    }

    @Override
    public StoredObject getStoredObject(final ITransaction transaction, final String uri) {
        final StoredObject so = new StoredObject();
        if (!availableExpansion(uri)) {
            so.setFolder(true);
        } else {
            so.setFolder(false);
        }
        so.setCreationDate(new Date(123));
        so.setLastModified(new Date(123));
        so.setResourceLength(getResourceLength(transaction, uri));
        return so;
    }

    /**
     * Доступные расширения файла для редактирования через WebDav
     *
     * @param uri путь
     * @return true - заканчивается на существующее расширение / false - иначе
     */
    private boolean availableExpansion(final String uri) {
        return uri.endsWith(".odt");
    }

    /**
     * Получить содержимое документа
     *
     * @param uri путь
     * @return поток с содержимым документа
     */
    private InputStream getFileWithData(final String uri) {
        final Data fileData = getData(uri);
        return new ByteArrayInputStream(fileData.getData());
    }

    /**
     * Получить содержимое документа по полученному пути
     *
     * @param uri путь
     * @return объект документа
     */
    private Data getData(final String uri) {
        return dataService.getDataById(Long.valueOf(uri.split("/")[1]));
    }

    /**
     * Обновляет содержимое документа
     *
     * @param data содержимое документа
     */
    private void updateData(final Data data) {
        dataService.update(data);
    }

//    UNUSED METHODS
    @Override
    public ITransaction begin(Principal principal) {
        return null;
    }

    @Override
    public void checkAuthentication(ITransaction transaction) {

    }

    @Override
    public void commit(ITransaction transaction) {

    }

    @Override
    public void rollback(ITransaction transaction) {

    }

    @Override
    public void createFolder(ITransaction transaction, String folderUri) {

    }

    @Override
    public void createResource(ITransaction transaction, String resourceUri) {

    }

    @Override
    public String[] getChildrenNames(ITransaction transaction, String folderUri) {
        return null;
    }

    @Override
    public void removeObject(ITransaction transaction, String uri) {

    }
}
