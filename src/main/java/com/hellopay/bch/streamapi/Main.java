package com.hellopay.bch.streamapi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by benoit on 11/23/15.
 */
public class Main {
    private static List<Transaction> transactions;
    private static int nbRuns = 100;

    public static void main(String... args) {
        //prepare data
        transactions = new ArrayList<>(10000000);
        for (int i=0; i<10000000; i++) {
            transactions.add(new Transaction(i, new BigDecimal("100000"),
                    (i%3==0)?Transaction.TransactionType.TopUp:Transaction.TransactionType.Checkout));
        }
        //run withoutStream
        long execTime;
        long total = 0;
        for (int i=0; i<nbRuns; i++) {
            execTime = runWithoutStream();
            System.out.println(i+"> "+execTime);
            total += execTime;
        }
        System.out.println("Average: "+total/nbRuns);

        //run withStream
        total = 0;
        for (int i=0; i<nbRuns; i++) {
            execTime = runWithStream();
            System.out.println(i+"> "+execTime);
            total += execTime;
        }
        System.out.println("Average: "+total/nbRuns);
    }

    private static long runWithoutStream() {
        long startTime = System.currentTimeMillis();
        List<Transaction> topUpTransactions = new ArrayList<>();
        List<Long> transactionsIds = new ArrayList<>();

        for(Transaction t: transactions){
            if(t.getType() == Transaction.TransactionType.TopUp){
                topUpTransactions.add(t);
            }
        }
        Collections.sort(topUpTransactions, new Comparator<Transaction>(){
            public int compare(Transaction t1, Transaction t2){
                return t2.getValue().compareTo(t1.getValue());
            }
        });
        for(Transaction t: topUpTransactions){
            transactionsIds.add(t.getId());
        }
        return System.currentTimeMillis()-startTime;
    }

    private static long runWithStream() {
        long startTime = System.currentTimeMillis();
        List<Long> transactionsIds;

        Stream<Transaction> stream = transactions.stream();
        transactionsIds = stream
                .filter(t -> t.getType() == Transaction.TransactionType.TopUp)
                .sorted((t1, t2) -> t2.getValue().compareTo(t1.getValue()))
                .map(Transaction::getId)
                .collect(toList());
        return System.currentTimeMillis()-startTime;
    }
}
