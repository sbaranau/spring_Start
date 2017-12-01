package julia.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Дополнительный утилитный класс для работы с коллекциями.
 *
 * @author Artiom_Buevich
 *
 */
public final class CollectionsUtils {

    private CollectionsUtils() {
    }

    public static <T> boolean exist(List<? extends T> list, T key, Comparator<? super T> c) {
        return (Collections.binarySearch(list, key, c) >= 0);
    }

}
