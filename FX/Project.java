import java.util.ArrayList;
import java.util.Observable;
import javafx.beans.value.ObservableValue;

class Project
{
    private String name; 
    private ArrayList<JavaFile> files;

    public Project(String n)
    {
        name = n;
        files = new ArrayList<JavaFile> ();
    }

    public void addFile(JavaFile file)
    {
        files.add(file);
    }

    public ArrayList<JavaFile> getFiles()
    {
        return files;
    }

    public String getName()
    {
        return name;
    }

    public void refresh()
    {
        for (JavaFile f : files)
            f.readFile();
    }
}