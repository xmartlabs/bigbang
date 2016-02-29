package com.xmartlabs.template.model;

import org.parceler.Parcel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by remer on 10/12/2015.
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Parcel
public class Owner {
  String login;
  int id;
  String avatar_url;
  String gravatar_id;
  String url;
  String html_url;

//    // Data returned from Github endpoints
//    {
//        "login": "mojombo",
//        "id": 1,
//        "avatar_url": "https://avatars.githubusercontent.com/u/1?v=3",
//        "gravatar_id": "",
//        "url": "https://api.github.com/users/mojombo",
//        "html_url": "https://github.com/mojombo",
//        "followers_url": "https://api.github.com/users/mojombo/followers",
//        "following_url": "https://api.github.com/users/mojombo/following{/other_user}",
//        "gists_url": "https://api.github.com/users/mojombo/gists{/gist_id}",
//        "starred_url": "https://api.github.com/users/mojombo/starred{/owner}{/repo}",
//        "subscriptions_url": "https://api.github.com/users/mojombo/subscriptions",
//        "organizations_url": "https://api.github.com/users/mojombo/orgs",
//        "repos_url": "https://api.github.com/users/mojombo/repos",
//        "events_url": "https://api.github.com/users/mojombo/events{/privacy}",
//        "received_events_url": "https://api.github.com/users/mojombo/received_events",
//        "type": "User",
//        "site_admin": false
//    }
}
