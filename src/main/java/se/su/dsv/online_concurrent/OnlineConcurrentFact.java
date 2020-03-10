package se.su.dsv.online_concurrent;

import java.util.function.Supplier;
import se.su.dsv.OnlineAdaptiveConcurrentDataStructure;


public enum OnlineConcurrentFact {

    ONLINE_ADAPTIVE(() ->
            new se.su.dsv.OnlineAdaptiveConcurrentDataStructure()),
    WRAPPED_MAP(() ->
            new se.su.dsv.OnlineAdaptiveConcurrentDataStructure(OnlineAdaptiveConcurrentDataStructure.State.MAP, false)),
    WRAPPED_LIST(() ->
            new se.su.dsv.OnlineAdaptiveConcurrentDataStructure(OnlineAdaptiveConcurrentDataStructure.State.LIST, false));


    public final Supplier<OnlineAdaptiveConcurrentDataStructure<Object>> maker;

    private OnlineConcurrentFact(Supplier<OnlineAdaptiveConcurrentDataStructure<Object>> maker) {
        this.maker = maker;
    }
}
