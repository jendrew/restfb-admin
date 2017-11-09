package to.ogarne.restfbadmin.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import to.ogarne.restfbadmin.service.FacebookService;
import to.ogarne.restfbadmin.web.model.Post;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PublishController {


    private Path rootLocation;

    FacebookService facebookService;

    public PublishController(FacebookService facebookService) {
        this.facebookService = facebookService;

    }


    @RequestMapping("/publish")
    public String helloFacebook(Model model) {

        if (!model.containsAttribute("post")) {
            model.addAttribute("post", new Post());
        }

        model.addAttribute("accounts", facebookService.getAccounts());


        return "publish";
    }


    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public String getPostAndPost(@RequestParam("file") MultipartFile multipartFile,
                                 @Valid Post post, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes, Model model) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.post", bindingResult);
            redirectAttributes.addFlashAttribute("post", post);
            return "redirect:/publish";
        }


        if (multipartFile.getSize() > 0) {
            post.setImage(multipartFile);
        }


        List<String> resultLinks = new ArrayList<>();

        post.getAccounts().stream()
                .forEach(pageId -> {
                    resultLinks.add(facebookService.publish(String.valueOf(pageId), post));
                });

        model.addAttribute("links", resultLinks);


        return "result";


    }

    private List<String> sendPhoto(Post post, MultipartFile multipartFile) throws IOException {

        List<String> resultLinks = new ArrayList<>();

        post.getAccounts().stream()
                .forEach(pageId -> {
                    resultLinks.add(facebookService.publish(String.valueOf(pageId), post));
                });

        return resultLinks;
    }

    private List<String> sendOther(Post post) {

        List<String> resultLinks = new ArrayList<>();

        post.getAccounts().stream()
                .forEach(pageId -> {
                    resultLinks.add(facebookService.publish(String.valueOf(pageId), post));
                });

        return resultLinks;
    }


}
