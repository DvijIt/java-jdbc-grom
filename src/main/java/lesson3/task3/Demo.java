package lesson3.task3;

public class Demo {
    public static void main(String[] args) throws PerformanceTestException {
        Solution solution = new Solution();

        System.out.println("Save performance = " + solution.testSavePerformance());
        System.out.println("Delete all by single query" + solution.testDeletePerformance());

        System.out.println("Delete by ID " + solution.testDeleteByIdPerformance());

        System.out.println("SELECT by single query" + solution.testSelectPerformance());
        System.out.println("SELECT by ID" + solution.testSelectByIdPerformance());
    }
}

