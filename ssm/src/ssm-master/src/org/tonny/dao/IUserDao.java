package org.tonny.dao;

import java.util.List;

import org.tonny.model.User;

public interface IUserDao
{
	public User getUserById(int id);

	public int update(User user);

	public List<User> getAll();

    public int deleteById(int id);

    public int add(User user);

    public boolean isAuthorizedUser(String userName, String pwd);

	public List<User> getUserListByCondition(User user);
}
