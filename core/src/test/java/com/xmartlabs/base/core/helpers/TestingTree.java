package com.xmartlabs.base.core.helpers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Objects;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import timber.log.Timber;

public class TestingTree extends Timber.Tree {
  public static final String DEFAULT_NOT_FOUND_TREE_NODE = "Tree node not found";
  private static final TestingTreeNode NODE_NOT_FOUND = TestingTreeNode.builder().tag(DEFAULT_NOT_FOUND_TREE_NODE)
      .build();
  private static final String INCORRECT_EXCEPTION_THROWN = "Incorrect exception thrown";

  @Getter
  private List<TestingTreeNode> loggingTree = new ArrayList<>();

  @Override
  protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable t) {
    TestingTreeNode loggingNode = TestingTreeNode.builder()
        .message(message)
        .priority(priority)
        .tag(tag)
        .throwable(t)
        .build();

    loggingTree.add(loggingNode);
  }

  @NonNull
  public TestingTreeNode getLogTreeNodeWithTag(@Nullable String tag) {
    return Stream.ofNullable(loggingTree)
        .filter(node -> Objects.equals(node.getTag(), tag))
        .findFirst()
        .orElse(NODE_NOT_FOUND);
  }

  public long getLogTreeNodesCountWithTag(@Nullable String tag) {
    return Stream.ofNullable(loggingTree)
        .filter(node -> Objects.equals(node.getTag(), tag))
        .count();
  }

  @NonNull
  public String getLogTreeExceptionDetailMessage(@Nullable final String detailMessage) {
    return Stream.ofNullable(loggingTree)
        .map(node -> node.getThrowable().getMessage())
        .filter(message -> Objects.equals(message, detailMessage))
        .findFirst()
        .orElse(INCORRECT_EXCEPTION_THROWN);
  }
}


