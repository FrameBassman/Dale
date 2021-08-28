package tech.romashov.dale.application.web.thread;

import org.hamcrest.MatcherAssert;
import org.junit.Ignore;
import org.junit.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class BooksTest {
    @Test @Ignore
    public void addsAndRetrievesSingle() {
        Books books = new Books();
        String title = "Elegant Objects";
        int id = books.add(title);
        assert books.title(id).equals(title);
    }

    @Test @Ignore
    public void addsAndRetrievesCountDown() throws Exception {
        Books books = new Books();
        int threads = 10;
        ExecutorService service = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean running = new AtomicBoolean();
        AtomicInteger overlaps = new AtomicInteger();
        Collection<Future<Integer>> futures =
                new ArrayList<>(threads);
        for (int t = 0; t < threads; ++t) {
            final String title = String.format("Book #%d", t);
            futures.add(
                    service.submit(
                            () -> {
                                latch.await();
                                if (running.get()) {
                                    overlaps.incrementAndGet();
                                }
                                running.set(true);
                                int id = books.add(title);
                                running.set(false);
                                return id;
                            }
                    )
            );
        }
        latch.countDown();
        Set<Integer> ids = new HashSet<>();
        for (Future<Integer> f : futures) {
            ids.add(f.get());
        }
        assertThat(overlaps.get(), greaterThan(0));
        System.out.println(overlaps.get());
        assertThat(ids.size(), equalTo(threads));
    }

    @Test @Ignore
    public void addsAndRetrieves() {
        Books books = new Books();
        MatcherAssert.assertThat(
                t -> {
                    String title = String.format(
                            "Book #%d", t.getAndIncrement()
                    );
                    int id = books.add(title);
                    return books.title(id).equals(title);
                },
                new RunsInThreads<>(new AtomicInteger(), 10)
        );
    }
}
