import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

class JavaFile
{
    private String projectName;
    private String name;
    private String path;
    private String contents;
    private String extention;
    
    public JavaFile (String n, String projectN, boolean newFile)
    {
        projectName = projectN;
        name = n.substring(n.lastIndexOf("\\") + 1);
        extention = name.substring(name.indexOf(".") + 1);
        path = n;

        if (newFile)
        {
            contents = "class "+name.substring(0, name.indexOf("."))+"\n{\n\tpublic static void main (String [] args)\n\t{\n\n\t}\n}";
            try
            {
                saveFile(contents);
            }
            catch(Exception e){}
        }
        else
            contents = "";

        
        File file = new File(projectN + "/" + name);
        try{file.createNewFile();}catch(Exception e){}finally {}
        readFile();
    }
    
    
    public String getName()
    {
        return name;
    }

    public String getPath()
    {
        return path;
    }

    public void readFile()
    {
        try
        {
            File file = new File (path);
            Scanner scan = new Scanner(file);

            int n = 0;
            String st = "";
            

            while (scan.hasNextLine())
            {
                st += scan.nextLine()+"\n";  
                n++;
            }
            contents = st;
            scan.close();

        } catch (Exception e) {}
    }

    public String getContents()
    {
        return contents;
    }

    public String compileFile()
    {
        if (extention.equals("java"))
        {
            Scanner processOut = new Scanner ("hello sweety");
            String command = "javac \"" + path +"\"";
            try
            {
                Process p = Runtime.getRuntime().exec(command);
                processOut = new Scanner(p.getErrorStream());
            }
            catch(Exception e){
                return "Error in compile function.";}

            String errorStr = "";
            while (processOut.hasNextLine())
                errorStr+=processOut.nextLine()+"\n";

            return command+"\n"+errorStr;
        }
        return "Cannot compile a non java file.";
    }

    public void saveFile(String c) throws IOException
    {
        contents = c;
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(contents);
        writer.close();
    }

    public String runFile()
    {
        if (extention.equals("java"))
        {
            String fileName = path;
            fileName = fileName.substring(0, fileName.indexOf(".java"));

            String className = fileName.substring(fileName.lastIndexOf("\\") + 1);
            String pathToClass = fileName.substring(0, fileName.lastIndexOf("\\"));

            String command = "java -cp \"" + pathToClass + "\"; " + className;
            String output = "";
            try{
                Process p = Runtime.getRuntime().exec(command);
                Scanner processOut = new Scanner(p.getInputStream());
                while (processOut.hasNextLine())
                    output += processOut.nextLine() + "\n";
            }catch(Exception e){}

            return command+"\n"+output;
        }
        return "Cannot run a non java file.";
    }

}
