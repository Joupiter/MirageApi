package fr.mirage.api.utils.func;

public interface TriFunction<A, B, C, R> {

    R apply(A a, B b, C c);

}