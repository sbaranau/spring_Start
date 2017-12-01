package by.tehnologia.regexp;

/**
 * @author Aleksandr Golovnyov
 */
public final class Regexp {

    private Regexp() {
    }

    public static final String EMAIL = 
            "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";

    public static final String IP_ADDRESS =
            "(^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.)"
                    + "{3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$)";

    public static final String HOSTNAME =
            "(^(?=.*[a-zA-Z])(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)"
                    + "+([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$)";

    public static final String HOSTNAME_OR_IP_ADDRESS = Regexp.IP_ADDRESS + "|" + Regexp.HOSTNAME;

    public static final String TWO_DIGIT_NUMBER = "[1-9]{1}[0-9]{0,2}";
    public static final String THREE_DIGIT_NUMBER = "[1-9]{1}[0-9]{0,2}";
    public static final String FOUR_DIGIT_NUMBER = "[1-9]{1}[0-9]{0,3}";
    public static final String BOOLEAN_VALUE = "(true)|(false)";
    public static final String ONLY_DIGITS = "[1-9]";
    public static final String UNSIGNED_REAL_NUMBER = "[0-9]*\\,?[0-9]*";
    public static final String UNSIGNED_REAL_NUMBER_EXCEPT_ZERO = "(?!^0*\\,?0*$)" + UNSIGNED_REAL_NUMBER;
    
}
