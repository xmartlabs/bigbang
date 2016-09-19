package com.xmartlabs.template.helper;

import com.annimon.stream.Collector;
import com.annimon.stream.function.BiConsumer;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Supplier;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by medina on 19/09/2016.
 */
public class CollectorsHelper {
  /**
   * Returns a {@code Collector} that fills new {@code Set} with input elements.
   *
   * @param <T> the type of the input elements
   * @return a {@code Collector}
   */
  public static <T> Collector<T, ?, SortedSet<T>> toSortedSet() {
    return new CollectorsImpl<>(TreeSet::new, TreeSet::add);
  }

  /**
   * Copied from {@code com.annimon.Stream.CollectorsImpl}.
   * @param <T>
   * @param <A>
   * @param <R>
   */
  private static final class CollectorsImpl<T, A, R> implements Collector<T, A, R> {
    private final Supplier<A> supplier;
    private final BiConsumer<A, T> accumulator;
    private final Function<A, R> finisher;

    public CollectorsImpl(Supplier<A> supplier, BiConsumer<A, T> accumulator) {
      this(supplier, accumulator, null);
    }

    public CollectorsImpl(Supplier<A> supplier, BiConsumer<A, T> accumulator, Function<A, R> finisher) {
      this.supplier = supplier;
      this.accumulator = accumulator;
      this.finisher = finisher;
    }

    @Override
    public Supplier<A> supplier() {
      return supplier;
    }

    @Override
    public BiConsumer<A, T> accumulator() {
      return accumulator;
    }

    @Override
    public Function<A, R> finisher() {
      return finisher;
    }
  }
}
