package com.telnov.software_design.akka;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import com.telnov.software_design.akka.actors.MasterSearchActor;

import static akka.pattern.PatternsCS.ask;

public class Main {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("system");

        final var scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Type your search request \n\n");
            final var searchRequest = scanner.nextLine();

            if (searchRequest.equals("EXIT")) {
                break;
            }

            ActorRef parent = system.actorOf(Props.create(MasterSearchActor.class), "master");

            final var response = ask(parent, searchRequest, Timeout.apply(1, TimeUnit.MINUTES))
                    .toCompletableFuture()
                    .join();

            System.out.print(response.toString());
        }
    }
}
