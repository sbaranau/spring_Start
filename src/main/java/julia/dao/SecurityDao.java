package julia.dao;

import julia.entity.SecurityUser;

/**
 * @author Ivan Shyrma
 *
 */
public interface SecurityDao {

    /**
     * @param login login
     * @return Security
     */
    SecurityUser readByLogin(String login);

}
