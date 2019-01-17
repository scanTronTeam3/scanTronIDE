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
    }
    
    public String getName()
    {
        return name;
    }

    public void readFile()
    {
        try
        {
            File file = new File (projectName + "/" + name);
            Scanner scan = new Scanner(file);

            int n = 0;

            while (scan.hasNextLine())
            {
                String st = scan.nextLine();
                contents[n]  = st;
                n++;
            }
            contents = Arrays.copyOf(contents, n);
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