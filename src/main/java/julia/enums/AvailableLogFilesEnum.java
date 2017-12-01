package julia.enums;

/**
 * Имена log файлов
 *
 * @author Alexander Demidovich
 */
public enum AvailableLogFilesEnum {

    TECH_DOCUMENT_AUTO_ARCHIVING("tech_document_auto_archiving"),
    JOURNAL_DOC_AUTO_ARCHIVING("journal_doc_auto_archiving"),
    JOURNAL_DOC_AUTO_ACTUALIZATION("journal_doc_auto_actualization");

    private String fileName;

    AvailableLogFilesEnum(String fileName){
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
