package com.udacity.webcrawler.profiler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * A method interceptor that checks whether {@link Method}s are annotated with the {@link Profiled}
 * annotation. If they are, the method interceptor records how long the method invocation took.
 */
final class ProfilingMethodInterceptor implements InvocationHandler {

  private final Clock clock;
  private final Object target;
  private ProfilingState state;

  // TODO: You will need to add more instance fields and constructor arguments to this class.
  ProfilingMethodInterceptor(Clock clock, Object target, ProfilingState state) {
    this.clock = Objects.requireNonNull(clock);
    this.target = Objects.requireNonNull(target);
    this.state = Objects.requireNonNull(state);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    // TODO: This method interceptor should inspect the called method to see if it is a profiled
    //       method. For profiled methods, the interceptor should record the start time, then
    //       invoke the method using the object that is being profiled. Finally, for profiled
    //       methods, the interceptor should record how long the method call took, using the
    //       ProfilingState methods.
    Object result;
    Instant startTime = null;
    boolean profiled = method.getAnnotation(Profiled.class) != null;
    // inspect the called method to see if it is a profiled method

    if (profiled) {
      // For profiled methods, the interceptor should record the start time
      startTime = clock.instant();
    }
      try {
        // invoke the method using the object that is being profiled
        // for profiled methods, the interceptor should record how long the method call took,
        // using the ProfilingState methods
        result = method.invoke(target, args);
      } catch (InvocationTargetException e) {
          throw e.getTargetException();
      } catch (Throwable t) {
          throw new RuntimeException(t);
      } finally {
        // even if it throws an exception, any @Profiled method should still have
        // its running time recorded
        if(profiled) {
          Duration duration = Duration.between(startTime, clock.instant());
          state.record(target.getClass(), method, duration);
        }
      }

    return result;
  }
}
