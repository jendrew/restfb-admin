package to.ogarne.restfbadmin.service;

import com.restfb.*;
import com.restfb.types.Account;
import com.restfb.types.FacebookType;
import com.restfb.types.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import to.ogarne.restfbadmin.web.model.Post;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FacebookServiceImpl implements FacebookService {

    private List<Account> accounts;
    private FacebookClient.AccessToken accessToken;

    public FacebookServiceImpl() {

    }

    public void initialize(FacebookClient.AccessToken accessToken) {
        this.accessToken = accessToken;
        accounts = getAccounts();
    }

    public User getUser() {
        User user = fc(accessToken).fetchObject("/me", User.class, Parameter.with("fields", "name"));
        return user;
    }


    public List<Account> getAccounts() {

        List<Account> accounts = new ArrayList<>();

        Connection<Account> allAccounts = fc(accessToken).fetchConnection("/me/accounts", Account.class);
        for (List<Account> accountPage : allAccounts) {
            for (Account account : accountPage) {
                accounts.add(account);
            }
        }

        return accounts;
    }

    @Override
    public String publish(String pageId, Post post) {

        ArrayList<Parameter> params = new ArrayList<>();

        if (post.getMessage() != null && !post.getMessage().isEmpty()) {
            params.add(Parameter.with("message", post.getMessage()));
        }

        if (post.getLinkUrl() != null && !post.getLinkUrl().isEmpty()) {
            params.add(Parameter.with("link", post.getLinkUrl()));
        }

        if (post.getPicture() != null && !post.getPicture().isEmpty()) {
            params.add(Parameter.with("picture", post.getPicture()));
        }

        if (post.getName() != null && !post.getName().isEmpty()) {
            params.add(Parameter.with("name", post.getName()));
        }

        if (post.getCaption() != null && !post.getCaption().isEmpty()) {
            params.add(Parameter.with("caption", post.getCaption()));
        }

        if (post.getDescription() != null && !post.getDescription().isEmpty()) {
            params.add(Parameter.with("description", post.getDescription()));
        }

        if (post.getDate() != null && !post.getDate().isEmpty()) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            try {
                Date date = df.parse(post.getDate());
                params.add(Parameter.with("scheduled_publish_time", date.getTime() / 1000));
                params.add(Parameter.with("published", false));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Parameter[] paramlist = params.toArray(new Parameter[params.size()]);


        if (post.getImage() == null) {
            FacebookType publishedPost = fc(getAccessToken(pageId))
                    .publish(pageId + "/feed", FacebookType.class,
                            paramlist);

            return publishedPost.getId();

        } else {
            try {
                MultipartFile file = post.getImage();
                BinaryAttachment photo = BinaryAttachment.with(file.getOriginalFilename(), file.getBytes());
                FacebookType publishedPost = fc(getAccessToken(pageId))
                        .publish(pageId + "/photos",
                                FacebookType.class,
                                photo,
                                paramlist);
                return publishedPost.getId();

            } catch (IOException e) {
                e.printStackTrace();
                return "error";

            }

        }

    }


    private String getAccessToken(String id) {
        Account account = accounts.stream()
                .filter(ac -> ac.getId().equals(id))
                .findFirst()
                .orElse(null);
        return (account == null) ? null : account.getAccessToken();

    }

    private FacebookClient fc(FacebookClient.AccessToken at) {
        return new DefaultFacebookClient(at.getAccessToken(), Version.VERSION_2_10);
    }

    private FacebookClient fc(String at) {
        return new DefaultFacebookClient(at, Version.VERSION_2_10);
    }


}
