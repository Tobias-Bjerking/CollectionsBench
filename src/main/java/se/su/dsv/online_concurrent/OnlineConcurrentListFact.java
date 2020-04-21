package se.su.dsv.online_concurrent;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

public enum OnlineConcurrentListFact {

        COW_LIST(CopyOnWriteArrayList::new),
        CDL_LIST(ConcurrentLinkedDeque::new);

        public final Supplier<Collection<Object>> maker;

        private OnlineConcurrentListFact(Supplier<Collection<Object>> maker) {
            this.maker = maker;
        }
}

