package com.jlu.github.dao.impl;

import com.jlu.common.db.dao.AbstractBaseDao;
import com.jlu.github.dao.IGitHubCommitDao;
import com.jlu.github.model.GitHubCommit;
import org.springframework.stereotype.Repository;

/**
 * Created by niuwanpeng on 17/4/25.
 */
@Repository
public class GitHubCommitDaoImpl extends AbstractBaseDao<GitHubCommit> implements IGitHubCommitDao {
}
