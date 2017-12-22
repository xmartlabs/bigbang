package com.xmartlabs.base.core

import android.support.v4.util.Pair
import com.google.gson.Gson
import com.xmartlabs.bigbang.core.helper.ObjectHelper
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Test

class ObjectHelperTest {
  @Test
  fun deepCopy() {
    val `object` = createDummyObject()
    val objectHelper = ObjectHelper(Gson())

    val deepCopy = objectHelper.deepCopy<DummyObject>(`object`)
    assertThat(`object` === deepCopy, `is`(false))
  }

  @Test
  fun dummyAndDummyCopyAreNotSameObject() {
    val dummyAndDeepCopyOfDummy = dummyAndDeepCopyOfDummy
    val `object` = dummyAndDeepCopyOfDummy.first
    val deepCopy = dummyAndDeepCopyOfDummy.second

    assertThat(`object` === deepCopy, `is`(false))
  }

  @Test
  fun dummyAndDummyCopyContentsAreNotSameObjects() {
    val dummyAndDeepCopyOfDummy = dummyAndDeepCopyOfDummy
    val `object` = dummyAndDeepCopyOfDummy.first
    val deepCopy = dummyAndDeepCopyOfDummy.second

    assertThat(deepCopy?.otherObject, notNullValue())
    assertThat(deepCopy?.otherObject?.otherObject, notNullValue())
    assertThat(`object`?.otherObject === deepCopy?.otherObject, `is`(false))
    assertThat(`object`?.otherObject === deepCopy?.otherObject, `is`(false))
    assertThat(`object`?.otherObject?.otherObject === deepCopy?.otherObject?.otherObject, `is`(false))
  }

  @Test
  fun dummyAndDummyCopyHaveSameContent() {
    val dummyAndCopy = dummyAndDeepCopyOfDummy
    val `object` = dummyAndCopy.first
    val deepCopy = dummyAndCopy.second

    assertThat(`object`?.otherObject == deepCopy?.otherObject, `is`(true))
    assertThat(`object`?.otherObject?.integer == deepCopy?.otherObject?.integer, `is`(true))
    assertThat(`object`?.string == deepCopy?.string, `is`(true))
    assertThat(`object`?.otherObject?.string == deepCopy?.otherObject?.string, `is`(true))
  }

  private fun createDummyObject() = DummyObject(1, "something", DummyObject(2, "something else", DummyObject()))

  private val dummyAndDeepCopyOfDummy: Pair<DummyObject, DummyObject>
    get() {
      val `object` = createDummyObject()
      val objectHelper = ObjectHelper(Gson())

      val deepCopy = objectHelper.deepCopy<DummyObject>(`object`)
      return Pair(`object`, deepCopy)
    }

  @Test
  fun xIsGreaterThanYByOne() {
    assertThat(ObjectHelper.compare(2, 1), `is`(1))
  }

  @Test
  fun xIsGreaterThanYByALot() {
    assertThat(ObjectHelper.compare(500, 1), `is`(1))
  }

  @Test
  fun xIsLessThanYByOne() {
    assertThat(ObjectHelper.compare(1, 2), `is`(-1))
  }

  @Test
  fun xIsLessThanYByALot() {
    assertThat(ObjectHelper.compare(1, 500), `is`(-1))
  }

  @Test
  fun xIsEqualY() {
    assertThat(ObjectHelper.compare(1, 1), `is`(0))
  }
  
  private data class DummyObject(
    var integer: Int = 0,
    var string: String? = null,
    var otherObject: DummyObject? = null
  )
}
