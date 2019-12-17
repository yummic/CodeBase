package fun.clclcl.yummic.codebase.sample.springboot.service;

import org.springframework.stereotype.Component;

@Component("userDao")
public class UserDaoImpl implements UserDao {

    @Override
    public String getUser() {
        throw new UnsupportedOperationException("Cannot connect to db server.");
    }
}
