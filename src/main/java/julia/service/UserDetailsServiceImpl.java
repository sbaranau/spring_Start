package julia.service;

import julia.dao.SecurityDao;
import julia.entity.SecurityUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Security service
 * @author Ivan Shyrma
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final long fired = 3;

    @Autowired
    private SecurityDao securityDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            SecurityUser userDetails = securityDao.readByLogin(username);

            if (userDetails == null) {
                throw new UsernameNotFoundException("User not found - " + username);
            } else if (userDetails.getStatusId() == fired) {
                throw new UsernameNotFoundException("User fired - " + username);
            }
            return userDetails;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User not found - " + username);
        }
    }

}
