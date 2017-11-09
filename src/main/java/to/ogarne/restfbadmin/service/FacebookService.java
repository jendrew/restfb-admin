package to.ogarne.restfbadmin.service;

import com.restfb.FacebookClient;
import com.restfb.types.Account;
import com.restfb.types.User;
import to.ogarne.restfbadmin.web.model.Post;

import java.util.List;

public interface FacebookService {

    void initialize(FacebookClient.AccessToken accessToken);
    User getUser();
    List<Account> getAccounts();
    String publish(String pageId, Post post);

}
