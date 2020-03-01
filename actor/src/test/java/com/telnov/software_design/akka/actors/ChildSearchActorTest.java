package com.telnov.software_design.akka.actors;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import com.telnov.software_design.akka.search.SearchAggregator;
import com.telnov.software_design.akka.search.SearchClientStub;
import com.telnov.software_design.akka.search.SearchResult;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import scala.concurrent.duration.FiniteDuration;

class ChildSearchActorTest {

    private static ActorSystem system;

    @BeforeAll
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterAll
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testIt() {
        new TestKit(system) {
            {
                final Props props = Props.create(ChildSearchActor.class, new SearchClientStub(SearchAggregator.YANDEX));
                final ActorRef master = system.actorOf(props);

                within(FiniteDuration.apply(1, TimeUnit.SECONDS),
                        () -> {
                            master.tell("hello", getRef());
                            expectMsgAnyClassOf(SearchResult.class);
                            return null;
                        });
            }
        };
    }
}
