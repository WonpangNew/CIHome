package com.jlu.github.service.impl;

import com.fasterxml.jackson.databind.type.TypeBase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jlu.branch.bean.BranchType;
import com.jlu.common.utils.DateUtil;
import com.jlu.compile.service.ICompileBuildService;
import com.jlu.github.bean.GitHubCommitBean;
import com.jlu.github.bean.HookRepositoryBean;
import com.jlu.github.model.CiHomeModule;
import com.jlu.github.service.IGitHubHookService;
import com.jlu.github.service.IModuleService;
import com.jlu.pipeline.model.PipelineBuild;
import com.jlu.pipeline.service.IPipelineBuildService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by niuwanpeng on 17/4/19.
 */
@Service
public class GitHubHookServiceImpl implements IGitHubHookService{

    @Autowired
    private ICompileBuildService compileBuildService;

    @Autowired
    private IPipelineBuildService pipelineBuildService;

    @Autowired
    private IModuleService moduleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubHookServiceImpl.class);

    private static final Gson GSON = new Gson();

    /**
     * 解析hook信息，触发编译
     * @param hookMessage
     */
    @Override
    public void dealHookMessage(JSONObject hookMessage) {
        GitHubCommitBean commitBean = null;
        HookRepositoryBean repositoryBean = null;
        try {
            String branchName = StringUtils.substringAfterLast(hookMessage.getString("ref"),"refs/heads/");
            BranchType branchType = branchName.equals("master") ? BranchType.TRUNK : BranchType.BRANCH;
            repositoryBean = GSON.fromJson(hookMessage.getString("repository"),
                    new TypeToken<HookRepositoryBean>(){}.getType());
            CiHomeModule ciHomeModule = moduleService.getModuleByUserAndModule(repositoryBean.getOwner().getName(),
                    repositoryBean.getName());
            PipelineBuild pipelineBuild = initPipelineBuild(ciHomeModule, branchName, branchType);
            commitBean = getCommitByHook(hookMessage, pipelineBuild);
            LOGGER.info("解析Json数据成功！commits:{}", commitBean.toString());
        } catch (Exception e) {
            LOGGER.error("解析Json数据失败！hookMessage:{}", hookMessage.toString());
        }
        compileBuildService.hookTriggerCompile(commitBean, repositoryBean);
    }

    /**
     * 解析json数据，获得commit bean
     * @param hookMessage
     * @param pipelineBuild
     * @return
     * @throws Exception
     */
    private GitHubCommitBean getCommitByHook(JSONObject hookMessage, PipelineBuild pipelineBuild) throws Exception {
        GitHubCommitBean commitBean = new GitHubCommitBean();
        JSONArray commitsArray = hookMessage.getJSONArray("commits");
        String commits = commitsArray.getString(0);
        commitBean = GSON.fromJson(commits, new TypeToken<GitHubCommitBean>(){}.getType());
        commitBean.setRef(pipelineBuild.getBranchName());
        commitBean.setBranchType(pipelineBuild.getBranchType());
        commitBean.setModuleId(pipelineBuild.getModuleId());
        commitBean.setPipelineBuildId(pipelineBuild.getId());
        return commitBean;
    }

    /**
     * 初始化pipelineBuild
     * @return
     */
    private PipelineBuild initPipelineBuild(CiHomeModule ciHomeModule, String branchName, BranchType branchType) {
        PipelineBuild pipelineBuild = new PipelineBuild();
        pipelineBuild.setModuleId(ciHomeModule.getId());
        pipelineBuild.setBranchName(branchName);
        pipelineBuild.setCreateTime(DateUtil.getNowDateFormat());
        pipelineBuild.setBranchType(branchType);
        pipelineBuildService.save(pipelineBuild);
        return pipelineBuild;
    }

    public static void main(String[] args) {
        String str = "{\n" +
                "  \"ref\": \"refs/heads/master\",\n" +
                "  \"before\": \"0e1a79252229d5c73a2f480d62dad303c10d56b2\",\n" +
                "  \"after\": \"b4571dad1c35e31d355cf7a50dfddaed20dc0858\",\n" +
                "  \"created\": false,\n" +
                "  \"deleted\": false,\n" +
                "  \"forced\": false,\n" +
                "  \"base_ref\": null,\n" +
                "  \"compare\": \"https://github.com/WonpangNew/CIHome/compare/0e1a79252229...b4571dad1c35\",\n" +
                "  \"commits\": [\n" +
                "    {\n" +
                "      \"id\": \"b4571dad1c35e31d355cf7a50dfddaed20dc0858\",\n" +
                "      \"tree_id\": \"2a99059fca6c83d301dd8119b1d83cca1fda30ee\",\n" +
                "      \"distinct\": true,\n" +
                "      \"message\": \"关联jenkins插件触发编译\",\n" +
                "      \"timestamp\": \"2017-04-18T16:51:55+08:00\",\n" +
                "      \"url\": \"https://github.com/WonpangNew/CIHome/commit/b4571dad1c35e31d355cf7a50dfddaed20dc0858\",\n" +
                "      \"author\": {\n" +
                "        \"name\": \"niuwanpeng\",\n" +
                "        \"email\": \"niuwanpeng@baidu.com\"\n" +
                "      },\n" +
                "      \"committer\": {\n" +
                "        \"name\": \"niuwanpeng\",\n" +
                "        \"email\": \"niuwanpeng@baidu.com\"\n" +
                "      },\n" +
                "      \"added\": [\n" +
                "        \"src/main/java/com/jlu/jenkins/web/JenkinsController.java\"\n" +
                "      ],\n" +
                "      \"removed\": [\n" +
                "\n" +
                "      ],\n" +
                "      \"modified\": [\n" +
                "        \"src/main/java/com/jlu/common/utils/JenkinsUtils.java\",\n" +
                "        \"src/main/java/com/jlu/compile/service/impl/CompileBuildServiceImpl.java\",\n" +
                "        \"src/main/java/com/jlu/jenkins/bean/JenkinsStartCompileBean.java\"\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"head_commit\": {\n" +
                "    \"id\": \"b4571dad1c35e31d355cf7a50dfddaed20dc0858\",\n" +
                "    \"tree_id\": \"2a99059fca6c83d301dd8119b1d83cca1fda30ee\",\n" +
                "    \"distinct\": true,\n" +
                "    \"message\": \"关联jenkins插件触发编译\",\n" +
                "    \"timestamp\": \"2017-04-18T16:51:55+08:00\",\n" +
                "    \"url\": \"https://github.com/WonpangNew/CIHome/commit/b4571dad1c35e31d355cf7a50dfddaed20dc0858\",\n" +
                "    \"author\": {\n" +
                "      \"name\": \"niuwanpeng\",\n" +
                "      \"email\": \"niuwanpeng@baidu.com\"\n" +
                "    },\n" +
                "    \"committer\": {\n" +
                "      \"name\": \"niuwanpeng\",\n" +
                "      \"email\": \"niuwanpeng@baidu.com\"\n" +
                "    },\n" +
                "    \"added\": [\n" +
                "      \"src/main/java/com/jlu/jenkins/web/JenkinsController.java\"\n" +
                "    ],\n" +
                "    \"removed\": [\n" +
                "\n" +
                "    ],\n" +
                "    \"modified\": [\n" +
                "      \"src/main/java/com/jlu/common/utils/JenkinsUtils.java\",\n" +
                "      \"src/main/java/com/jlu/compile/service/impl/CompileBuildServiceImpl.java\",\n" +
                "      \"src/main/java/com/jlu/jenkins/bean/JenkinsStartCompileBean.java\"\n" +
                "    ]\n" +
                "  },\n" +
                "  \"repository\": {\n" +
                "    \"id\": 83954904,\n" +
                "    \"name\": \"CIHome\",\n" +
                "    \"full_name\": \"WonpangNew/CIHome\",\n" +
                "    \"owner\": {\n" +
                "      \"name\": \"WonpangNew\",\n" +
                "      \"email\": \"wonpangnew@gmail.com\",\n" +
                "      \"login\": \"WonpangNew\",\n" +
                "      \"id\": 17978626,\n" +
                "      \"avatar_url\": \"https://avatars0.githubusercontent.com/u/17978626?v=3\",\n" +
                "      \"gravatar_id\": \"\",\n" +
                "      \"url\": \"https://api.github.com/users/WonpangNew\",\n" +
                "      \"html_url\": \"https://github.com/WonpangNew\",\n" +
                "      \"followers_url\": \"https://api.github.com/users/WonpangNew/followers\",\n" +
                "      \"following_url\": \"https://api.github.com/users/WonpangNew/following{/other_user}\",\n" +
                "      \"gists_url\": \"https://api.github.com/users/WonpangNew/gists{/gist_id}\",\n" +
                "      \"starred_url\": \"https://api.github.com/users/WonpangNew/starred{/owner}{/repo}\",\n" +
                "      \"subscriptions_url\": \"https://api.github.com/users/WonpangNew/subscriptions\",\n" +
                "      \"organizations_url\": \"https://api.github.com/users/WonpangNew/orgs\",\n" +
                "      \"repos_url\": \"https://api.github.com/users/WonpangNew/repos\",\n" +
                "      \"events_url\": \"https://api.github.com/users/WonpangNew/events{/privacy}\",\n" +
                "      \"received_events_url\": \"https://api.github.com/users/WonpangNew/received_events\",\n" +
                "      \"type\": \"User\",\n" +
                "      \"site_admin\": false\n" +
                "    },\n" +
                "    \"private\": false,\n" +
                "    \"html_url\": \"https://github.com/WonpangNew/CIHome\",\n" +
                "    \"description\": \"基于Jenkins集成的持续交付平台\",\n" +
                "    \"fork\": false,\n" +
                "    \"url\": \"https://github.com/WonpangNew/CIHome\",\n" +
                "    \"forks_url\": \"https://api.github.com/repos/WonpangNew/CIHome/forks\",\n" +
                "    \"keys_url\": \"https://api.github.com/repos/WonpangNew/CIHome/keys{/key_id}\",\n" +
                "    \"collaborators_url\": \"https://api.github.com/repos/WonpangNew/CIHome/collaborators{/collaborator}\",\n" +
                "    \"teams_url\": \"https://api.github.com/repos/WonpangNew/CIHome/teams\",\n" +
                "    \"hooks_url\": \"https://api.github.com/repos/WonpangNew/CIHome/hooks\",\n" +
                "    \"issue_events_url\": \"https://api.github.com/repos/WonpangNew/CIHome/issues/events{/number}\",\n" +
                "    \"events_url\": \"https://api.github.com/repos/WonpangNew/CIHome/events\",\n" +
                "    \"assignees_url\": \"https://api.github.com/repos/WonpangNew/CIHome/assignees{/user}\",\n" +
                "    \"branches_url\": \"https://api.github.com/repos/WonpangNew/CIHome/branches{/branch}\",\n" +
                "    \"tags_url\": \"https://api.github.com/repos/WonpangNew/CIHome/tags\",\n" +
                "    \"blobs_url\": \"https://api.github.com/repos/WonpangNew/CIHome/git/blobs{/sha}\",\n" +
                "    \"git_tags_url\": \"https://api.github.com/repos/WonpangNew/CIHome/git/tags{/sha}\",\n" +
                "    \"git_refs_url\": \"https://api.github.com/repos/WonpangNew/CIHome/git/refs{/sha}\",\n" +
                "    \"trees_url\": \"https://api.github.com/repos/WonpangNew/CIHome/git/trees{/sha}\",\n" +
                "    \"statuses_url\": \"https://api.github.com/repos/WonpangNew/CIHome/statuses/{sha}\",\n" +
                "    \"languages_url\": \"https://api.github.com/repos/WonpangNew/CIHome/languages\",\n" +
                "    \"stargazers_url\": \"https://api.github.com/repos/WonpangNew/CIHome/stargazers\",\n" +
                "    \"contributors_url\": \"https://api.github.com/repos/WonpangNew/CIHome/contributors\",\n" +
                "    \"subscribers_url\": \"https://api.github.com/repos/WonpangNew/CIHome/subscribers\",\n" +
                "    \"subscription_url\": \"https://api.github.com/repos/WonpangNew/CIHome/subscription\",\n" +
                "    \"commits_url\": \"https://api.github.com/repos/WonpangNew/CIHome/commits{/sha}\",\n" +
                "    \"git_commits_url\": \"https://api.github.com/repos/WonpangNew/CIHome/git/commits{/sha}\",\n" +
                "    \"comments_url\": \"https://api.github.com/repos/WonpangNew/CIHome/comments{/number}\",\n" +
                "    \"issue_comment_url\": \"https://api.github.com/repos/WonpangNew/CIHome/issues/comments{/number}\",\n" +
                "    \"contents_url\": \"https://api.github.com/repos/WonpangNew/CIHome/contents/{+path}\",\n" +
                "    \"compare_url\": \"https://api.github.com/repos/WonpangNew/CIHome/compare/{base}...{head}\",\n" +
                "    \"merges_url\": \"https://api.github.com/repos/WonpangNew/CIHome/merges\",\n" +
                "    \"archive_url\": \"https://api.github.com/repos/WonpangNew/CIHome/{archive_format}{/ref}\",\n" +
                "    \"downloads_url\": \"https://api.github.com/repos/WonpangNew/CIHome/downloads\",\n" +
                "    \"issues_url\": \"https://api.github.com/repos/WonpangNew/CIHome/issues{/number}\",\n" +
                "    \"pulls_url\": \"https://api.github.com/repos/WonpangNew/CIHome/pulls{/number}\",\n" +
                "    \"milestones_url\": \"https://api.github.com/repos/WonpangNew/CIHome/milestones{/number}\",\n" +
                "    \"notifications_url\": \"https://api.github.com/repos/WonpangNew/CIHome/notifications{?since,all,participating}\",\n" +
                "    \"labels_url\": \"https://api.github.com/repos/WonpangNew/CIHome/labels{/name}\",\n" +
                "    \"releases_url\": \"https://api.github.com/repos/WonpangNew/CIHome/releases{/id}\",\n" +
                "    \"deployments_url\": \"https://api.github.com/repos/WonpangNew/CIHome/deployments\",\n" +
                "    \"created_at\": 1488701416,\n" +
                "    \"updated_at\": \"2017-04-06T05:16:43Z\",\n" +
                "    \"pushed_at\": 1492507315,\n" +
                "    \"git_url\": \"git://github.com/WonpangNew/CIHome.git\",\n" +
                "    \"ssh_url\": \"git@github.com:WonpangNew/CIHome.git\",\n" +
                "    \"clone_url\": \"https://github.com/WonpangNew/CIHome.git\",\n" +
                "    \"svn_url\": \"https://github.com/WonpangNew/CIHome\",\n" +
                "    \"homepage\": \"\",\n" +
                "    \"size\": 19895,\n" +
                "    \"stargazers_count\": 0,\n" +
                "    \"watchers_count\": 0,\n" +
                "    \"language\": \"JavaScript\",\n" +
                "    \"has_issues\": true,\n" +
                "    \"has_projects\": true,\n" +
                "    \"has_downloads\": true,\n" +
                "    \"has_wiki\": true,\n" +
                "    \"has_pages\": false,\n" +
                "    \"forks_count\": 0,\n" +
                "    \"mirror_url\": null,\n" +
                "    \"open_issues_count\": 0,\n" +
                "    \"forks\": 0,\n" +
                "    \"open_issues\": 0,\n" +
                "    \"watchers\": 0,\n" +
                "    \"default_branch\": \"master\",\n" +
                "    \"stargazers\": 0,\n" +
                "    \"master_branch\": \"master\"\n" +
                "  },\n" +
                "  \"pusher\": {\n" +
                "    \"name\": \"WonpangNew\",\n" +
                "    \"email\": \"wonpangnew@gmail.com\"\n" +
                "  },\n" +
                "  \"sender\": {\n" +
                "    \"login\": \"WonpangNew\",\n" +
                "    \"id\": 17978626,\n" +
                "    \"avatar_url\": \"https://avatars0.githubusercontent.com/u/17978626?v=3\",\n" +
                "    \"gravatar_id\": \"\",\n" +
                "    \"url\": \"https://api.github.com/users/WonpangNew\",\n" +
                "    \"html_url\": \"https://github.com/WonpangNew\",\n" +
                "    \"followers_url\": \"https://api.github.com/users/WonpangNew/followers\",\n" +
                "    \"following_url\": \"https://api.github.com/users/WonpangNew/following{/other_user}\",\n" +
                "    \"gists_url\": \"https://api.github.com/users/WonpangNew/gists{/gist_id}\",\n" +
                "    \"starred_url\": \"https://api.github.com/users/WonpangNew/starred{/owner}{/repo}\",\n" +
                "    \"subscriptions_url\": \"https://api.github.com/users/WonpangNew/subscriptions\",\n" +
                "    \"organizations_url\": \"https://api.github.com/users/WonpangNew/orgs\",\n" +
                "    \"repos_url\": \"https://api.github.com/users/WonpangNew/repos\",\n" +
                "    \"events_url\": \"https://api.github.com/users/WonpangNew/events{/privacy}\",\n" +
                "    \"received_events_url\": \"https://api.github.com/users/WonpangNew/received_events\",\n" +
                "    \"type\": \"User\",\n" +
                "    \"site_admin\": false\n" +
                "  }\n" +
                "}";
//        JSONObject jsonObject = JSONObject.fromObject(str);
//        new GitHubHookServiceImpl().dealHookMessage(jsonObject);
        System.out.println(StringUtils.substringAfterLast("refs/heads/blog_1-0-0_BRANCH", "refs/heads/"));
    }
}
