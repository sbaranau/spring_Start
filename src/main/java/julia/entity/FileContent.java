package julia.entity;

/**
 * Файл с содержимым
 * @author vandronov
 */
public class FileContent {

    /**
     * Содержимое
     */
    private byte[] content;

    /**
     * mime
     */
    private String mimeType;

    /**
     * Наименование файла
     */
    private String name;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
