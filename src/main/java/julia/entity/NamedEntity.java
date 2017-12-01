package julia.entity;

/**
 * @author Ivan Shyrma
 */
public class NamedEntity extends Entity implements INamedEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
