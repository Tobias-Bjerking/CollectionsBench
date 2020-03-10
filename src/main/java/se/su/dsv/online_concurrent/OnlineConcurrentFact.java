package se.su.dsv.online_concurrent;

import java.util.List;
import java.util.function.Supplier;
import se.su.dsv.OnlineAdaptiveConcurrentDataStructure;


public enum OnlineConcurrentFact {

    ONLINE_ADAPTIVE(se.su.dsv.OnlineAdaptiveConcurrentDataStructure::new);

    public final Supplier<OnlineAdaptiveConcurrentDataStructure<Object>> maker;

    private OnlineConcurrentFact(Supplier<OnlineAdaptiveConcurrentDataStructure<Object>> maker) {
        this.maker = maker;
    }
}
