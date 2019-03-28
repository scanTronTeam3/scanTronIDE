import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

class Project
{
    private String name;
    private JavaFile [] files;
    private int numOfFiles = 0;

    public Project(String n)
    {
        files = new JavaFile [1];
        name = n;

        File file = new File(name);
        file.mkdirs();
    }

    public String getName()
    {
        return name;
    }

    public void addFile(JavaFile file)
    {
        if (numOfFiles < files.length)
            files[numOfFiles] = file;
        else 
        {
            files = Arrays.copyOf(files, files.length+1);
            files[numOfFiles] = file;
        }
        numOfFiles++;
    }

    public JavaFile [] getFileList()
    {
        return files;
    }

    public void refresh()
    {
        if (numOfFiles > 0)
            for (JavaFile f : files)
                f.readFile();
    }

    public void run() throws Exception
    {
        String fileName = files[files.length-1].getName();
        fileName = fileName.substring(0, fileName.indexOf(".java"));
        String command = "java -cp " + name + " " + fileName;
        Process p = Runtime.getRuntime().exec(command);
        Scanner processOut = new Scanner(p.getInputStream());

        while (processOut.hasNextLine())
            System.out.print(processOut.nextLine() + "\n");
    }
}