public class Parameters {
    private static Parameters parametersInstance = new Parameters();

    public static Parameters getInstance() {
        return parametersInstance;
    }

    public int numberOfLines = 0;
    public int numberOfTasks = 0;

    private Parameters() {
    }
}
