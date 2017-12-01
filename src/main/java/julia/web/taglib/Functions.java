package julia.web.taglib;



/**
 * Возвращает окончание для поля возраста.
 *
 * @author Artiom_Buevich
 *
 */
public final class Functions {

    /**
     * Конструктор.
     *
     */
    private Functions() {
    }


    public static String suffix(Integer age) {
        int residue = age % 10;
        if (age > 4 && age < 21) {
            return "лет";
        } else if (residue == 1) {
            return "год";
        } else if (residue >= 2 && residue <= 4) {
            return "года";
        }
        return "лет";
    }

    public static String initial(String name, String middleName) {
        if (name != null && !name.isEmpty() && middleName != null && !middleName.isEmpty()) {
            return new StringBuilder().append(name.charAt(0)).append(". ").append(middleName.charAt(0))
                    .append('.').toString();
        }
        return null;
    }

}
