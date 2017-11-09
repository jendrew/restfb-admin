package to.ogarne.restfbadmin.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import to.ogarne.restfbadmin.model.User;

public interface UserService extends UserDetailsService {

    Iterable<User> findAll();
    User findOne(Long id);
    User findByUsername(String username);
    void save(User task);


}
