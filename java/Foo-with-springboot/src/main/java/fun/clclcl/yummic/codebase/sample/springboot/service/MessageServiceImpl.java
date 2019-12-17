package fun.clclcl.yummic.codebase.sample.springboot.service;

import fun.clclcl.yummic.codebase.sample.springboot.controller.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private UserDao userDao;

    @Override
    public Message queryMessage(String username) {
        //String username = userDao.getUser();
        return new Message(1L, String.format("Hello, %s", username));
    }
}
