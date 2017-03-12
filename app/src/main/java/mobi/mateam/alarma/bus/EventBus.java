package mobi.mateam.alarma.bus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class EventBus<T> {
  private final Subject<T, T> subject;

  public EventBus() {
    this(PublishSubject.<T>create());
  }

  public EventBus(Subject<T, T> subject) {
    this.subject = subject;
  }

  public <E extends T> void post(E event) {
    subject.onNext(event);
  }

  public Observable<T> observe() {
    return subject;
  }

  public <E extends T> Observable<E> observeEvents(Class<E> eventClass) {
    return subject.ofType(eventClass);//pass only events of specified type, filter all other
  }
}
