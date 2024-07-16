package com.chervonnaya;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComplexTaskExecutor {
    List<String> partialResults = new CopyOnWriteArrayList<>();
    private final ExecutorService executorService;
    private final CyclicBarrier barrier;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.executorService = Executors.newFixedThreadPool(numberOfTasks);
        this.barrier = new CyclicBarrier(numberOfTasks, this::combineResults);
    }

    public void executeTasks(int numberOfTasks) {
        for (int i = 0; i < numberOfTasks; i++) {
            executorService.execute(() -> {
                try {
                ComplexTask task = new ComplexTask();
                partialResults.add(task.execute());
                barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void combineResults() {
        System.out.println("All tasks completed. Combining results...");
        partialResults.forEach(System.out::println);
        partialResults.clear();
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
