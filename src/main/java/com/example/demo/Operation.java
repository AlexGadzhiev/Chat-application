package com.example.demo;

public class Operation {
    private int a;
    private int b;

    public Operation(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int plus(){
        return this.a + this.b;
    }

    public int minus(){
        return this.a - this.b;
    }

    public int div(){
        return this.a / this.b;
    }

    public int times(){
        return this.a * this.b;
    }
}
