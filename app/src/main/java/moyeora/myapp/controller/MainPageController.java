package moyeora.myapp.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class MainPageController {

  @GetMapping("")
  public void index() {

  }

}
