package com.xmartlabs.bigbang.core.helpers

import com.xmartlabs.bigbang.core.observers.ObservableResult

import timber.log.Timber

class TestingTree : Timber.Tree() {
  companion object {
    val DEFAULT_NOT_FOUND_TREE_NODE = "Tree node not found"
    private val NODE_NOT_FOUND = TestingTreeNode(0, DEFAULT_NOT_FOUND_TREE_NODE)
    private val INCORRECT_EXCEPTION_THROWN = "Incorrect exception thrown"
    
    fun log(observableResult: ObservableResult) = Timber.tag(observableResult.toString()).i(observableResult.toString())
  }
  
  private val loggingTree = ArrayList<TestingTreeNode>()

  override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
    loggingTree.add(TestingTreeNode(priority, tag, message, t))
  }

  private fun getLogTreeNodeWithTag(tag: String) =
      loggingTree.filter { it.tag == tag }.firstOrNull() ?: NODE_NOT_FOUND

  fun getLogTreeNodeWithTag(observableResult: ObservableResult) =
      getLogTreeNodeWithTag(observableResult.toString())

  private fun getLogTreeNodesCountWithTag(tag: String) = loggingTree.filter { it.tag == tag }.count()

  fun getLogTreeNodesCountWithTag(observableResultTag: ObservableResult) =
      getLogTreeNodesCountWithTag(observableResultTag.toString())

  private fun getLogTreeExceptionDetailMessage(detailMessage: String) = loggingTree
      .map { it.throwable?.message }
      .filterNotNull()
      .filter { it == detailMessage }
      .firstOrNull()
      ?: INCORRECT_EXCEPTION_THROWN

  fun getLogTreeExceptionDetailMessage(detailMessage: ObservableResult) =
      getLogTreeExceptionDetailMessage(detailMessage.toString())
}
