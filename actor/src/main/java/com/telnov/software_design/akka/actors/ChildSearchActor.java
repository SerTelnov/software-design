package com.telnov.software_design.akka.actors;

import akka.actor.AbstractActor;
import com.telnov.software_design.akka.search.SearchClient;

public class ChildSearchActor extends AbstractActor {

    private final SearchClient searchClient;

    public ChildSearchActor(SearchClient searchClient) {
        super();
        this.searchClient = searchClient;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg ->
                        getSender().tell(searchClient.search(msg), getSender())
                ).build();
    }
}
