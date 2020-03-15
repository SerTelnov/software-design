package com.telnov.software_design.akka.actors;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.telnov.software_design.akka.search.SearchAggregator;
import com.telnov.software_design.akka.search.SearchClientStub;
import com.telnov.software_design.akka.search.SearchResult;
import scala.concurrent.duration.FiniteDuration;


public class MasterSearchActor extends AbstractActor {

    private static final Set<SearchAggregator> AGGREGATORS = EnumSet.allOf(SearchAggregator.class);
    private static final int TIMOUT_PERIOD = 15;

    private static final TimeoutMsg TIMOUT_MSG = new TimeoutMsg();
    private static final StopMsg STOP_MSG = new StopMsg();

    private final NameFactory nameFactory;
    private ActorRef requestSender;

    private List<SearchResult> responseList;

    public MasterSearchActor() {
        super();
        this.nameFactory = new NameFactory();
        this.responseList = new ArrayList<>();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, request -> {
                    this.requestSender = getSender();

                    AGGREGATORS.forEach(aggregator -> getContext()
                            .actorOf(
                                    Props.create(ChildSearchActor.class, new SearchClientStub(aggregator)),
                                    nameFactory.nextName()
                            ).tell(request, self())
                    );

                    context().system().scheduler()
                            .scheduleOnce(
                                    FiniteDuration.apply(TIMOUT_PERIOD, TimeUnit.SECONDS),
                                    self(),
                                    TIMOUT_MSG,
                                    context().system().dispatcher(),
                                    ActorRef.noSender()
                            );
                }).match(SearchResult.class, response -> {
                    responseList.add(response);

                    if (responseList.size() == AGGREGATORS.size()) {
                        sendSearchResult();
                    }
                }).match(TimeoutMsg.class, ignored -> sendSearchResult())
                .match(StopMsg.class, ignored -> getContext().stop(this.self()))
                .build();
    }

    private void sendSearchResult() {
        requestSender.tell(responseList, self());
        self().tell(STOP_MSG, self());
    }

    private static class NameFactory {

        private int count = 0;

        public String nextName() {
            return "child" + count++;
        }
    }

    private static final class TimeoutMsg {
    }

    private static final class StopMsg {
    }
}
