package site.morn.util;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A container object which may or may not contain a non-null value. If a value is present, {@code
 * isPresent()} will return {@code true} and {@code get()} will return the value.
 *
 * <p>Additional methods that depend on the presence or absence of a contained
 * value are provided, such as {@link #orElse(Collection)}  orElse()} (return a default value if
 * value not present) and {@link #ifPresent(java.util.function.Consumer) ifPresent()} (execute a
 * block of code if the value is present).
 *
 * <p>This is a <a href="../lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality ({@code ==}), identity
 * hash code, or synchronization) on instances of {@code OptionalCollection} may have unpredictable
 * results and should be avoided.
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/14
 */
public class OptionalCollection<T> {

  /**
   * Common instance for {@code empty()}.
   */
  private static final OptionalCollection<?> EMPTY = new OptionalCollection<>();

  /**
   * If {@link #isPresent()}, the value; if !{@link #isPresent()}, indicates no value is present
   */
  private final Collection<T> value;

  /**
   * Represents a predicate (boolean-valued function) of value.
   */
  private final Predicate<Collection<?>> predicate;

  /**
   * Constructs an empty instance.
   *
   * @implNote Generally only one empty instance, {@link OptionalCollection#EMPTY}, should exist per
   * VM.
   */
  private OptionalCollection() {
    this.value = null;
    this.predicate = new EmptyPredicate();
  }

  /**
   * Constructs an instance with the value present.
   *
   * @param value the non-null value to be present
   * @throws NullPointerException if value is null
   */
  private OptionalCollection(Collection<T> value, Predicate<Collection<?>> predicate) {
    this.value = Objects.requireNonNull(value);
    this.predicate = predicate;
  }

  /**
   * Returns an empty {@code OptionalCollection} instance.  No value is present for this
   * OptionalCollection.
   *
   * @param <T> Type of the non-existent value
   * @return an empty {@code OptionalCollection}
   * @apiNote Though it may be tempting to do so, avoid testing if an object is empty by comparing
   * with {@code ==} against instances returned by {@code Option.empty()}. There is no guarantee
   * that it is a singleton. Instead, use {@link #isPresent()}.
   */
  public static <T> OptionalCollection<T> empty() {
    @SuppressWarnings("unchecked")
    OptionalCollection<T> t = (OptionalCollection<T>) EMPTY;
    return t;
  }

  /**
   * Returns an {@code OptionalCollection} describing the specified value, if non-null, otherwise
   * returns an empty {@code OptionalCollection}.
   *
   * @param <T> the class of the value
   * @param value the possibly-null value to describe
   * @return an {@code OptionalCollection} with a present value if the specified value is non-null,
   * otherwise an empty {@code OptionalCollection}
   */
  public static <T> OptionalCollection<T> ofNullable(Collection<T> value) {
    return value == null ? empty() : new OptionalCollection<>(value, new NullablePredicate());
  }

  /**
   * Returns an {@code OptionalCollection} describing the specified value, if non-empty, otherwise
   * returns an empty {@code OptionalCollection}.
   *
   * @param <T> the class of the value
   * @param value the possibly-null value to describe
   * @return an {@code OptionalCollection} with a present value if the specified value is non-null,
   * otherwise an empty {@code OptionalCollection}
   */
  public static <T> OptionalCollection<T> ofCollection(Collection<T> value) {
    return value == null ? empty() : new OptionalCollection<>(value, new EmptyPredicate());
  }

  /**
   * If a value is present in this {@code OptionalCollection}, returns the value, otherwise throws
   * {@code NoSuchElementException}.
   *
   * @return the non-null value held by this {@code OptionalCollection}
   * @throws NoSuchElementException if there is no value present
   * @see OptionalCollection#isPresent()
   */
  public Collection<T> get() {
    if (value == null) {
      throw new NoSuchElementException("No value present");
    }
    return value;
  }

  /**
   * Return {@code true} if there is a value present, otherwise {@code false}.
   *
   * @return {@code true} if there is a value present, otherwise {@code false}
   */
  public boolean isPresent() {
    return predicate.test(value);
  }

  /**
   * If a value is present, invoke the specified consumer with the value, otherwise do nothing.
   *
   * @param consumer block to be executed if a value is present
   * @throws NullPointerException if value is present and {@code consumer} is null
   */
  public void ifPresent(Consumer<? super Collection<T>> consumer) {
    if (isPresent()) {
      consumer.accept(value);
    }
  }

  /**
   * If a value is present, and the value matches the given predicate, return an {@code
   * OptionalCollection} describing the value, otherwise return an empty {@code
   * OptionalCollection}.
   *
   * @param predicate a predicate to apply to the value, if present
   * @return an {@code OptionalCollection} describing the value of this {@code OptionalCollection}
   * if a value is present and the value matches the given predicate, otherwise an empty {@code
   * OptionalCollection}
   * @throws NullPointerException if the predicate is null
   */
  public OptionalCollection<T> filter(Predicate<? super Collection<T>> predicate) {
    Objects.requireNonNull(predicate);
    if (!isPresent()) {
      return this;
    } else {
      return predicate.test(value) ? this : empty();
    }
  }

  /**
   * If a value is present, apply the provided mapping function to it, and if the result is
   * non-null, return an {@code OptionalCollection} describing the result.  Otherwise return an
   * empty {@code OptionalCollection}.
   *
   * @param <U> The type of the result of the mapping function
   * @param mapper a mapping function to apply to the value, if present
   * @return an {@code OptionalCollection} describing the result of applying a mapping function to
   * the value of this {@code OptionalCollection}, if a value is present, otherwise an empty {@code
   * OptionalCollection}
   * @throws NullPointerException if the mapping function is null
   * @apiNote This method supports post-processing on OptionalCollection values, without the need to
   * explicitly check for a return status.  For example, the following code traverses a stream of
   * file names, selects one that has not yet been processed, and then opens that file, returning an
   * {@code OptionalCollection<FileInputStream>}:
   *
   * <pre>{@code
   *     OptionalCollection<FileInputStream> fis =
   *         names.stream().filter(name -> !isProcessedYet(name))
   *                       .findFirst()
   *                       .map(name -> new FileInputStream(name));
   * }</pre>
   *
   * Here, {@code findFirst} returns an {@code OptionalCollection<String>}, and then {@code map}
   * returns an {@code OptionalCollection<FileInputStream>} for the desired file if one exists.
   */
  public <U> OptionalCollection<U> map(
      Function<? super Collection<T>, ? extends Collection<U>> mapper) {
    Objects.requireNonNull(mapper);
    if (!isPresent()) {
      return empty();
    } else {
      return OptionalCollection.ofNullable(mapper.apply(value));
    }
  }

  /**
   * If a value is present, apply the provided {@code OptionalCollection}-bearing mapping function
   * to it, return that result, otherwise return an empty {@code OptionalCollection}.  This method
   * is similar to {@link #map(Function)}, but the provided mapper is one whose result is already an
   * {@code OptionalCollection}, and if invoked, {@code flatMap} does not wrap it with an additional
   * {@code OptionalCollection}.
   *
   * @param <U> The type parameter to the {@code OptionalCollection} returned by
   * @param mapper a mapping function to apply to the value, if present the mapping function
   * @return the result of applying an {@code OptionalCollection}-bearing mapping function to the
   * value of this {@code OptionalCollection}, if a value is present, otherwise an empty {@code
   * OptionalCollection}
   * @throws NullPointerException if the mapping function is null or returns a null result
   */
  public <U> OptionalCollection<U> flatMap(
      Function<? super Collection<T>, OptionalCollection<U>> mapper) {
    Objects.requireNonNull(mapper);
    if (!isPresent()) {
      return empty();
    } else {
      return Objects.requireNonNull(mapper.apply(value));
    }
  }

  /**
   * Return the value if present, otherwise return {@code other}.
   *
   * @param other the value to be returned if there is no value present, may be null
   * @return the value, if present, otherwise {@code other}
   */
  public Collection<T> orElse(Collection<T> other) {
    return isPresent() ? value : other;
  }

  /**
   * Return the value if present, otherwise invoke {@code other} and return the result of that
   * invocation.
   *
   * @param other a {@code Supplier} whose result is returned if no value is present
   * @return the value if present otherwise the result of {@code other.get()}
   * @throws NullPointerException if value is not present and {@code other} is null
   */
  public Collection<T> orElseGet(Supplier<? extends Collection<T>> other) {
    return isPresent() ? value : other.get();
  }

  /**
   * Return the contained value, if present, otherwise throw an exception to be created by the
   * provided supplier.
   *
   * @param <X> Type of the exception to be thrown
   * @param exceptionSupplier The supplier which will return the exception to be thrown
   * @return the present value
   * @throws X if there is no value present
   * @throws NullPointerException if no value is present and {@code exceptionSupplier} is null
   * @apiNote A method reference to the exception constructor with an empty argument list can be
   * used as the supplier. For example, {@code IllegalStateException::new}
   */
  public <X extends Throwable> Collection<T> orElseThrow(Supplier<? extends X> exceptionSupplier)
      throws X {
    if (isPresent()) {
      return value;
    } else {
      throw exceptionSupplier.get();
    }
  }

  /**
   * 空值断言
   */
  private static class NullablePredicate implements Predicate<Collection<?>> {

    @Override
    public boolean test(Collection<?> ts) {
      return ts != null;
    }
  }

  /**
   * 可空断言
   */
  private static class EmptyPredicate implements Predicate<Collection<?>> {

    @Override
    public boolean test(Collection<?> ts) {
      return ts != null && !ts.isEmpty();
    }
  }
}
