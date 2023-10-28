package io.edpn.backend;

import java.util.List;

public class JavaCompileVersionChecker {

    static void recordPatternMatching(Object obj) {
        if (obj instanceof Point(int x, int y)) {
            System.out.println(x + y);
        }
    }

    public void sequencedLists() {
        var list = List.of("first", "last");
        var first = list.getFirst();
        var last = list.getLast();
    }
    public void switchCasePatternMatching(Object o) {
        switch (o) {
            case Point(int x, int y) -> System.out.printf("position: %d/%d%n", x, y);
            case String s -> System.out.printf("string: %s%n", s);
            default -> System.out.printf("something else: %s%n", o);
        }
    }

    /**
     * The following code shows how to use {@code Optional.isPresent}:
     * {@snippet :
     * if (v.isPresent()) { // @highlight substring="isPresent"
     *     System.out.println("v: " + v.get());
     * }
     * }
     * Where v != null
     */
    public void codeSnippetInApiDocExample() {

    }

    public void virtualThreads(Runnable runnable) {
        Thread.startVirtualThread(runnable);
    }

    record Point(int x, int y) {
    }
}
