package com.telnov.software_design.akka.actors;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import com.telnov.software_design.akka.search.SearchAggregator;
import com.telnov.software_design.akka.search.SearchClientStub;
import com.telnov.software_design.akka.search.SearchResult;


public class MasterSearchActor extends UntypedAbstractActor {

    private static final Set<SearchAggregator> AGGREGATORS = EnumSet.allOf(SearchAggregator.class);

    private final NameFactory nameFactory;
    private ActorRef requestSender;

    private List<SearchResult> responseList;

    public MasterSearchActor() {
        super();
        this.nameFactory = new NameFactory();
        this.responseList = new ArrayList<>();
    }

    @Override
    public void onReceive(Object o) {
        if (o instanceof String) {
            this.requestSender = getSender();
            final var searchRequest = (String) o;

            AGGREGATORS.forEach(aggregator -> {
                final var child = getContext().actorOf(
                        Props.create(ChildSearchActor.class, new SearchClientStub(aggregator)),
                        nameFactory.nextName());

                child.tell(searchRequest, self());
            });
        } else if (o instanceof SearchResult) {
            final var searchResponse = (SearchResult) o;
            responseList.add(searchResponse);

            if (responseList.size() == AGGREGATORS.size()) {
                getContext().stop(this.self());
                requestSender.tell(responseList, self());
            }
        }
    }

    private static class NameFactory {

        private int count = 0;

        public String nextName() {
            return "child" + count++;
        }
    }
}
