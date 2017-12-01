package julia.web.utils;

public final class CustomCollectionEditorUtils {

    public static Long convertElementIntoId(Object element) {
        Long id = null;
        if (element instanceof String && !((String) element).equals("")) {
            // From the JSP 'element' will be a String
            try {
                id = Long.parseLong((String) element);
            } catch (NumberFormatException e) {
                return null;
            }
        } else if (element instanceof Long) {
            // From the database 'element' will be a Long
            id = (Long) element;
        }
        return id;
    }

    private CustomCollectionEditorUtils() {
    }


}
