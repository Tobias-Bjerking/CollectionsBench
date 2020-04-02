package se.su.dsv.online_concurrent;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

public enum OnlineConcurrentListFact {

        COW_LIST(CopyOnWriteArrayList::new);

        public final Supplier<CopyOnWriteArrayList<Object>> maker;

        private OnlineConcurrentListFact(Supplier<CopyOnWriteArrayList<Object>> maker) {
            this.maker = maker;
        }
}

