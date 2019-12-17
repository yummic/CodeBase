package fun.clclcl.yummic.codebase.sample.springboot.controller;

import fun.clclcl.yummic.codebase.sample.springboot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("/hello")
    @ResponseBody
    public Message getHello() {
        Message msg = messageService.queryMessage("Meng");
        debug(msg);
        return msg;
    }

    private void debug(Object obj) {
        System.out.println("**********");
        System.out.println(obj);
        System.out.println("**********");

    }
}
