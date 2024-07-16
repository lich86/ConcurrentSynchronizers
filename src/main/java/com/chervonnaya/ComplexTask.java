package com.chervonnaya;

public class ComplexTask {

    public String execute() {
        return Thread.currentThread().getName() + " executed Complex Task";
    }
}
