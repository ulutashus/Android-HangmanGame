package ulutashus.androidmvc;

public interface ICommand<T>
{
    void execute(T arg);
}
