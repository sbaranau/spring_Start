package julia.utils;

/**
 * Расширение утилитного класса Integer.
 *
 * @author Artiom_Buevich
 *
 */
public final class IntegerExt {

    public static int defaultZero(Integer i) {
        if (i == null) {
            return 0;
        }
        return i.intValue();
    }

    private IntegerExt() {
    }

}
