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
            File file = new File (name);
            Scanner scan = new Scanner(file);

            int n = 0;
            String st = "";


            while (scan.hasNextLine())
            {
                st = scan.nextLine();
                contents[n] = st;
                n++;
                contents = Arrays.copyOf(contents, n+1);
            }
            contents = Arrays.copyOf(contents, contents.length-1);

        } catch (Exception e) {}
    }

    public String getContents()
    {
        String content = "";
        for (String line : contents)
        {
            content+=line+"\n";
        }
        return content;
    }

    public String compileFile()
    {
        Scanner processOut = new Scanner ("hello sweety");
        try
        {
            Process p = Runtime.getRuntime().exec("javac " + name);
            processOut = new Scanner(p.getErrorStream());
        }
        catch(Exception e){
            return "Error in compile function.";}

        String errorStr = "";
        while (processOut.hasNextLine())
            errorStr+=processOut.nextLine()+"\n";

        return errorStr;
    }

    public void saveFile(String c)
    {
        try 
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(name));
            writer.write(c);
            writer.close();
        }catch(Exception e){}
    }

    public String runFile()
    {
        String fileName = name;
        fileName = fileName.substring(0, fileName.indexOf(".java"));
        String command = "java " + fileName;
        String output = "";

        try{
            Process p = Runtime.getRuntime().exec(command);
            Scanner processOut = new Scanner(p.getInputStream());
            while (processOut.hasNextLine())
                output += processOut.nextLine() + "\n";
        }catch(Exception e){}

        return output;
    }

}
