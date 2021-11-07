package org.tei;

public class ClassInitDeadLock {

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                // trigger supper class initialize
                PascalCaseStrategy strategy = PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE;
            }
        }, "Thread-A");

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                // trigger sub class initialize
                PascalCaseStrategy strategy = new PascalCaseStrategy();
            }
        }, "Thread-B");

        // When the demo program ClassInitDeadLock started, it could not exit automatically.
        // Thread-A holding PropertyNamingStrategy class , and waiting PascalCaseStrategy class initialize,
        // while Thread-B holding PascalCaseStrategy class, and waiting PropertyNamingStrategy class initialize,
        // deadlock occurred!
        threadA.start();
        Thread.sleep(100);
        threadB.start();
    }

    public static class PropertyNamingStrategy {
        static {
            try {
                // sleep 1s for deadlock reproduction
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // ignore
            }
        }

        public static final PascalCaseStrategy PASCAL_CASE_TO_CAMEL_CASE = new PascalCaseStrategy();
    }

    public static class PascalCaseStrategy extends PropertyNamingStrategy {
    }
}