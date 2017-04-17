package com.xmartlabs.base.core.observers;

import android.support.annotation.Nullable;

import com.annimon.stream.Objects;
import com.annimon.stream.Stream;

import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import timber.log.Timber;

abstract class MainObserversWithErrorHandlingTest {
  static final String OBSERVABLE_SUCCESS = "Entered success from Observable";
  static final String OBSERVABLE_ON_COMPLETE = "Entered onComplete from Observable";
  static final String OBSERVABLE_DO_ON_ERROR = "Entered do on error from Observable";
  static final String OBSERVABLE_ON_NEXT = "Entered on next from Observable";
  static final String OBSERVABLE_SUBSCRIBE = "Entered subscribe from Observable";
  static final String DEFAULT_NOT_FOUND_TREE_NODE = "Tree node not found";
  static final String ENTERED_HOOK_ERROR_HANDLE = "Entered hook error handle";
  static final String EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK = "Exception while executing Testing Action";
  private final String INCORRECT_EXCEPTION_THROWN = "Incorrect exception thrown";
  private final TestingTreeNode nodeNotFound = new TestingTreeNode().setTag(DEFAULT_NOT_FOUND_TREE_NODE);
  @Getter
  private final Consumer<? super Throwable> errorHandlingAction = t -> Timber.tag(ENTERED_HOOK_ERROR_HANDLE).i(ENTERED_HOOK_ERROR_HANDLE);

  @Getter
  private final Consumer<? super Throwable> errorHandlingActionWithErrorInside = t -> {
    throw new Exception(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK);
  };

  private TestingTree testingTree = new TestingTree();

  @Before
  public void setUp() {
    Timber.plant(testingTree);
    setObserverSubscriber();
  }

  protected abstract void setObserverSubscriber();

  TestingTreeNode getLogTreeNodeWithTag(@Nullable String tag) {
    return Stream.ofNullable(testingTree.getLoggingTree())
        .filter(node -> Objects.equals(node.getTag(), tag))
        .findFirst()
        .orElse(nodeNotFound);
  }

  long getLogTreeNodesCountWithTag(@Nullable String tag) {
    return Stream.ofNullable(testingTree.getLoggingTree())
        .filter(node -> Objects.equals(node.getTag(), tag))
        .count();
  }

  String getLogTreeExceptionDetailMessage(@Nullable final String detailMessage) {
    return Stream.ofNullable(testingTree.getLoggingTree())
        .map(node -> node.getThrowable().getMessage())
        .filter(message -> Objects.equals(message, detailMessage))
        .findFirst()
        .orElse(INCORRECT_EXCEPTION_THROWN);
  }

  private final class TestingTree extends Timber.Tree {
    @Getter
    private List<TestingTreeNode> loggingTree = new ArrayList<>();

    @Override
    protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable t) {
      TestingTreeNode loggingNode = new TestingTreeNode()
          .setMessage(message)
          .setPriority(priority)
          .setTag(tag)
          .setThrowable(t);

      loggingTree.add(loggingNode);
    }
  }

  @Data
  @Accessors(chain = true)
  class TestingTreeNode {
    int priority;
    String tag;
    String message;
    Throwable throwable;
  }
}
