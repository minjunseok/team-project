package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.PostService;
import moyeora.myapp.vo.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

  private final PostService postService;


//  @GetMapping("schoolist")
//  public void findyBySchoolPostList(Model model,int schoolNo) {
//
//
//  }

//  @GetMapping("/list")
//  public void schoolPostList(Model model) {
//    System.out.printf("posts",postService.findBySchoolPost());
//    model.addAttribute("posts",postService.findBySchoolPost());
//


  }

