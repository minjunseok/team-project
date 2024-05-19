package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.controller.PostController;
import moyeora.myapp.service.PostService;
import moyeora.myapp.service.SchoolUserService;
import moyeora.myapp.vo.Post;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchController {

    private final PostService postService;
    private final SchoolUserService schoolUserService;

    @GetMapping("search")
    public List<Post> search(
            @RequestParam("schoolNo") int schoolNo,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "filter", required = false) String filter) {

        List<Post> posts;
        if (filter != null && filter.equals("0")) {
            posts = postService.findBySchoolContent(schoolNo, keyword);
        } else {
            posts = postService.findBySchoolUserName(schoolNo, keyword);
        }
        return posts;
    }
}