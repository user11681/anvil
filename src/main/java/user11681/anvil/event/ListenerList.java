package user11681.anvil.event;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ListenerList<E> implements Iterable<EventListener<E>> {
    protected final List<EventListener<E>> delegate;

    public ListenerList() {
        this.delegate = new ArrayList<>();
    }

    public void add(final Class<E> clazz, final Consumer<E> consumer, final int priority, final boolean persist) {
        final EventListener<E> listener = new EventListener<>(clazz, consumer, priority, persist);
        final List<EventListener<E>> delegate = this.delegate;
        final int size = delegate.size();
        int index = size;

        for (int i = 0; i < size; i++) {
            final EventListener<E> other = delegate.get(i);

            if (other.equals(listener)) {
                final int comparison = listener.compareTo(other);

                if (comparison <= 0) {
                    index = i + 1;
                } else {
                    index = i;
                    break;
                }
            }
        }

        delegate.add(index, listener);
    }

    public int size() {
        return this.delegate.size();
    }

    @Override
    @Nonnull
    public Iterator<EventListener<E>> iterator() {
        return this.delegate.iterator();
    }
}
