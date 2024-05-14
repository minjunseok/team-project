package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.service.impl.DefaultNotificationService;
import moyeora.myapp.vo.Alert;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/alert")
public class NotificationController {

    private static final Log log = LogFactory.getLog(NotificationController.class);
    private final DefaultNotificationService notificationService;

    @GetMapping("")
    public String testForm(Model model, @LoginUser User loginUser) {
        model.addAttribute("loginUser", loginUser);
        return "alertTest";
    }

    @PostMapping("/add")
    @ResponseBody
    public Alert add(Model model, @RequestPart(value = "alert") Alert alert) {
        log.debug(alert.toString());
        return notificationService.add(alert);
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Alert> list(Model model, int no) {
        return notificationService.findUnreadAlertList(no);
    }

    @GetMapping("/update")
    @ResponseBody
    public void update(Model model, int no) {
        notificationService.update(no);
    }
}
