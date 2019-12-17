package fun.clclcl.yummic.codebase.sample.springboot.service;

import fun.clclcl.yummic.codebase.sample.springboot.controller.Message;

public interface MessageService {
    Message queryMessage(String name);
}
