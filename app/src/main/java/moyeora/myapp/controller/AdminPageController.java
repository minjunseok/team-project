package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminPageController {

  @GetMapping("form")
  public void form() {

  }

  @GetMapping("index")
  public void index() {

  }

  @GetMapping("table")
  public void table() {

  }

  @GetMapping("user")
  public void user() {

  }

  @GetMapping("calendar")
  public void calendar() {

  }

  @GetMapping("school")
  public void school() {}

  @GetMapping("post")
  public void post() {
  }

  @GetMapping("chart")
  public void chart() {

  }
}
