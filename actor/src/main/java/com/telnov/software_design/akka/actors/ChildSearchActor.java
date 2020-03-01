package com.telnov.software_design.akka.actors;

import akka.actor.UntypedActor;
import com.telnov.software_design.akka.search.SearchClient;

public class ChildSearchActor extends UntypedActor {

    private final SearchClient searchClient;

    public ChildSearchActor(SearchClient searchClient) {
        super();
        this.searchClient = searchClient;
    }

    @Override
    public void onReceive(Object o) {
        if (o instanceof String) {
            final var msg = (String) o;
            getSender().tell(searchClient.search(msg), getSender());
        }
    }
}
