/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.collect.testing.testers;

import com.google.common.collect.testing.Helpers;
import com.google.common.collect.testing.features.CollectionFeature;
import static com.google.common.collect.testing.features.CollectionFeature.ALLOWS_NULL_VALUES;
import com.google.common.collect.testing.features.CollectionSize;
import java.util.Collection;
import java.util.Set;

/**
 * Tests {@link java.util.Set#equals}.
 *
 * @author George van den Driessche
 */
public class SetEqualsTester<E> extends AbstractSetTester<E> {
  public void testEquals_otherSetWithSameElements() throws Exception {
    assertTrue(
        "A Set should equal any other Set containing the same elements.",
        getSet().equals(Helpers.copyToSet(getSampleElements())));
  }

  @CollectionSize.Require(absent=CollectionSize.ZERO)
  public void testEquals_otherSetWithDifferentElements() throws Exception {
    Set<E> other = Helpers.copyToSet(getSampleElements(getNumElements() - 1));
    other.add(getSubjectGenerator().samples().e3);
    assertFalse(
        "A Set should not equal another Set containing different elements.",
        getSet().equals(other)
    );
  }

  @CollectionSize.Require(absent=CollectionSize.ZERO)
  @CollectionFeature.Require(ALLOWS_NULL_VALUES)
  public void testEquals_containingNull() {
    Collection<E> elements = getSampleElements(getNumElements() - 1);
    elements.add(null);

    collection = getSubjectGenerator().create(elements.toArray());
    assertTrue("A Set should equal any other Set containing the same elements,"
        + " even if some elements are null.",
        getSet().equals(Helpers.copyToSet(elements)));
  }

  @CollectionSize.Require(absent=CollectionSize.ZERO)
  public void testEquals_otherContainsNull() {
    Collection<E> elements = getSampleElements(getNumElements() - 1);
    elements.add(null);
    Set<E> other = Helpers.copyToSet(elements);

    assertFalse(
        "Two Sets should not be equal if exactly one of them contains null.",
        getSet().equals(other));
  }

  @CollectionSize.Require(absent=CollectionSize.ZERO)
  public void testEquals_smallerSet() throws Exception {
    Collection<E> fewerElements = getSampleElements(getNumElements() - 1);
    assertFalse("Sets of different sizes should not be equal.",
        getSet().equals(Helpers.copyToSet(fewerElements)));
  }

  public void testEquals_largerSet() {
    Collection<E> moreElements = getSampleElements(getNumElements() + 1);
    assertFalse("Sets of different sizes should not be equal.",
        getSet().equals(Helpers.copyToSet(moreElements)));
  }

  public void testEquals_list() {
    assertFalse("A List should never equal a Set.",
        getSet().equals(Helpers.copyToList(getSet())));
  }
}
