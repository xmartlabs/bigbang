package com.xmartlabs.template.model;

import com.xmartlabs.template.helper.StringUtils;

import org.parceler.Parcel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by remer on 10/12/15.
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Parcel
public class Repo {
  int id;
  String name;
  String full_name;
  Owner owner;
  String html_url;
  String description;
  boolean fork;
  String url;

  public boolean matches(String filter) {
    if (StringUtils.stringIsNullOrEmpty(filter)) {
      return true;
    }

    boolean matches = false;
    String lowerCaseFilter = filter.toLowerCase();

    if (!StringUtils.stringIsNullOrEmpty(name)) {
      //noinspection ConstantConditions
      matches = matches || name.toLowerCase().contains(lowerCaseFilter);
    }

    if (!StringUtils.stringIsNullOrEmpty(full_name)) {
      matches = matches || full_name.toLowerCase().contains(lowerCaseFilter);
    }

    if (!StringUtils.stringIsNullOrEmpty(description)) {
      matches = matches || description.toLowerCase().contains(lowerCaseFilter);
    }

    return matches;
  }

//    // Data returned from Github endpoints
//    {
//        "id": 1,
//        "name": "grit",
//        "full_name": "mojombo/grit",
//        "owner": { ... }
//        "private": false,
//        "html_url": "https://github.com/mojombo/grit",
//        "description": "**Grit is no longer maintained. Check out libgit2/rugged.** Grit gives you object oriented read/write access to Git repositories via Ruby.",
//        "fork": false,
//        "url": "https://api.github.com/repos/mojombo/grit",
//        "forks_url": "https://api.github.com/repos/mojombo/grit/forks",
//        "keys_url": "https://api.github.com/repos/mojombo/grit/keys{/key_id}",
//        "collaborators_url": "https://api.github.com/repos/mojombo/grit/collaborators{/collaborator}",
//        "teams_url": "https://api.github.com/repos/mojombo/grit/teams",
//        "hooks_url": "https://api.github.com/repos/mojombo/grit/hooks",
//        "issue_events_url": "https://api.github.com/repos/mojombo/grit/issues/events{/number}",
//        "events_url": "https://api.github.com/repos/mojombo/grit/events",
//        "assignees_url": "https://api.github.com/repos/mojombo/grit/assignees{/user}",
//        "branches_url": "https://api.github.com/repos/mojombo/grit/branches{/branch}",
//        "tags_url": "https://api.github.com/repos/mojombo/grit/tags",
//        "blobs_url": "https://api.github.com/repos/mojombo/grit/git/blobs{/sha}",
//        "git_tags_url": "https://api.github.com/repos/mojombo/grit/git/tags{/sha}",
//        "git_refs_url": "https://api.github.com/repos/mojombo/grit/git/refs{/sha}",
//        "trees_url": "https://api.github.com/repos/mojombo/grit/git/trees{/sha}",
//        "statuses_url": "https://api.github.com/repos/mojombo/grit/statuses/{sha}",
//        "languages_url": "https://api.github.com/repos/mojombo/grit/languages",
//        "stargazers_url": "https://api.github.com/repos/mojombo/grit/stargazers",
//        "contributors_url": "https://api.github.com/repos/mojombo/grit/contributors",
//        "subscribers_url": "https://api.github.com/repos/mojombo/grit/subscribers",
//        "subscription_url": "https://api.github.com/repos/mojombo/grit/subscription",
//        "commits_url": "https://api.github.com/repos/mojombo/grit/commits{/sha}",
//        "git_commits_url": "https://api.github.com/repos/mojombo/grit/git/commits{/sha}",
//        "comments_url": "https://api.github.com/repos/mojombo/grit/comments{/number}",
//        "issue_comment_url": "https://api.github.com/repos/mojombo/grit/issues/comments{/number}",
//        "contents_url": "https://api.github.com/repos/mojombo/grit/contents/{+path}",
//        "compare_url": "https://api.github.com/repos/mojombo/grit/compare/{base}...{head}",
//        "merges_url": "https://api.github.com/repos/mojombo/grit/merges",
//        "archive_url": "https://api.github.com/repos/mojombo/grit/{archive_format}{/ref}",
//        "downloads_url": "https://api.github.com/repos/mojombo/grit/downloads",
//        "issues_url": "https://api.github.com/repos/mojombo/grit/issues{/number}",
//        "pulls_url": "https://api.github.com/repos/mojombo/grit/pulls{/number}",
//        "milestones_url": "https://api.github.com/repos/mojombo/grit/milestones{/number}",
//        "notifications_url": "https://api.github.com/repos/mojombo/grit/notifications{?since,all,participating}",
//        "labels_url": "https://api.github.com/repos/mojombo/grit/labels{/name}",
//        "releases_url": "https://api.github.com/repos/mojombo/grit/releases{/id}"
//    }
}
