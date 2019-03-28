import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

class JavaFile
{
    private String projectName;
    private String name;
    private String [] contents;

    public JavaFile (String n, String projectN)
    {
        projectName = projectN;
        name = n;
        contents = new String [1000];

        File file = new File(projectN + "/" + name);
        try{file.createNewFile();}catch(Exception e){}finally {}

        readFile();
    }
    
    public String getName()
    {
        return name;
    }

    public void readFile()
    {
        contents = new String [1];
        try
        {
            File file = new File (projectName + "/" + name);
            Scanner scan = new Scanner(file);

            int n = 0;
            String st = "";

            while (scan.hasNextLine())
            {
                st = scan.nextLine();
                contents[n] = st;
                System.out.print(st);
                n++;
                contents = Arrays.copyOf(contents, n+1);
            }

        } catch (Exception e) {}
    }

    public void printFile()
    {
        int n = 0;
        for (String line : contents)
        {
            n++;
            System.out.print("\t" +  n + " - " + line + "\n");
        }
    }
}