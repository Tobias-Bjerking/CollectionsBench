package se.su.dsv.online_concurrent;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public enum OnlineConcurrentFact {

    JDK_COPYONWRITEARRAYLIST(java.util.concurrent.CopyOnWriteArrayList::new);

    public final Supplier<List<Object>> maker;

    private OnlineConcurrentFact(Supplier<List<Object>> maker) {
        this.maker = maker;
    }
}
