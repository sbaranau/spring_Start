package julia.web.mvc;

/**
 * Фильтр поиска пользователей
 * @author vandronov
 */
public class SearchUsersFilter {

    /**Фильтр по уволенным*/
    private Boolean withFired = false;
    /**Фильтр по строке (отдел или должность или ФИО)*/
    private String searchString;
    /**Фильтр Загружать записи начиная...*/
    private int offset = 0;

    public SearchUsersFilter(final Boolean withFired) {
        this.withFired = withFired;
        this.searchString = "";
    }

    public SearchUsersFilter() {
    }

    public Boolean getWithFired() {
        return withFired;
    }

    public void setWithFired(Boolean withFired) {
        this.withFired = withFired;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
