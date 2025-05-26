package fr.mirage.api.utils;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

@UtilityClass
public class Utils {

    public final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final SecureRandom RANDOM = new SecureRandom();

    public String generateRandomString(int length) {
        if (length <= 0) throw new RuntimeException();

        var result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }

        return result.toString();
    }

    public <T> void ifTrue(T object, Predicate<T> predicate, Consumer<T> consumer) {
        if (predicate.test(object))
            consumer.accept(object);
    }

    public <T> void ifFalse(T object, Predicate<T> predicate, Consumer<T> consumer) {
        if (!predicate.test(object))
            consumer.accept(object);
    }

    public <T> void range(int start, int end, T object, BiConsumer<T, Integer> consumer) {
        for (int i = start; i < end; i++)
            consumer.accept(object, i);
    }

    public void range(int start, int end, IntConsumer consumer) {
        for (int i = start; i < end; i++)
            consumer.accept(i);
    }

    public <T> void range(int start, int end, T object, Consumer<T> consumer) {
        for (int i = start; i < end; i++)
            consumer.accept(object);
    }

    public void range(int start, int end, Runnable runnable) {
        for (int i = start; i < end; i++)
            runnable.run();
    }

    public <T> void biRange(int iterate, T object, BiConsumer<T, Integer> consumer) {
        range(0, iterate, object, consumer);
    }

    public void range(int iterate, IntConsumer consumer) {
        range(0, iterate, consumer);
    }

    public <T> void range(int iterate, T object, Consumer<T> consumer) {
        range(0, iterate, object, consumer);
    }

    public void range(int iterate, Runnable runnable) {
        range(0, iterate, runnable);
    }

}