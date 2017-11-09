package to.ogarne.restfbadmin.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import to.ogarne.restfbadmin.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
