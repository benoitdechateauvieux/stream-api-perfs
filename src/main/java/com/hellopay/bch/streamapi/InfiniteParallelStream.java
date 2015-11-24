package com.hellopay.bch.streamapi;

import java.util.stream.IntStream;

/**
 * Created by benoit on 11/24/15.
 */
public class InfiniteParallelStream {

    public static void main(String... args) {
        IntStream.iterate(0, i -> (i+1)%2)
                .parallel()
                .distinct()
                .limit(10)
                .forEach(System.out::println);
    }
}
