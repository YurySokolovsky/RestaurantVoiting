package uv.sokolovsky.service;

import uv.sokolovsky.model.User;
import uv.sokolovsky.to.UserTo;
import uv.sokolovsky.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(User user);

    void update(UserTo userTo);

    List<User> getAll();

    void enable(int id, boolean enable);
}