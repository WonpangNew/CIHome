package com.jlu.test.dao.impl;

import com.jlu.common.db.dao.AbstractBaseDao;
import com.jlu.test.dao.IUserDao;
import com.jlu.test.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by Wonpang New on 2016/9/6.
 */
@Repository
public class UserDaoImpl extends AbstractBaseDao<User> implements IUserDao{
}
