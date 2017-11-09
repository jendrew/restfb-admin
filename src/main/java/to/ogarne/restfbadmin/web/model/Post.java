package to.ogarne.restfbadmin.web.model;

import org.springframework.web.multipart.MultipartFile;
import to.ogarne.restfbadmin.web.validation.PostScheduleTime;

import javax.validation.constraints.Size;
import java.util.List;

public class Post {


    private String message;
    private String linkUrl;
    private String picture;
    private String name;
    private String caption;
    private String description;

    @PostScheduleTime
    private String date;
    private MultipartFile image;

    @Size(min = 1, message = "Please, select at least one account")
    private List<Long> accounts;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Long> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Long> accounts) {
        this.accounts = accounts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
