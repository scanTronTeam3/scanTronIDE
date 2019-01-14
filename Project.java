import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner; 

class Project
{
    private String name;
    private JavaFile [] files;
    private int numOfFiles = 0;

    public Project(String n)
    {
        files = new JavaFile [1];
        name = n;

        initProject();
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

        writeConfig();//save change to projects config
    }

    public JavaFile [] getFileList()
    {
        return files;
    }

    public String [] getFileNames()
    {
        /*
            This method creates and returns an array of names corisponding to the files array.
            */
        String [] fileNames = new String [files.length];
        int x = 0;
        for (JavaFile f : files)
        {
            fileNames[x] = f.getName();
            x++;
        }

        return fileNames;
    }

    private void writeConfig()
    {
        /*
            This method basically saves the config file in the event of a change to the file list, ect., of
            project.

            Should produce a file with each line being a file name/path.
            */
        String config = "";
        for (JavaFile f : files) //add each file in project's file list to a string, separated by '\n'
            config += (f.getName() + "\n");
        config = config.substring(0, config.length()-1); //remove last '\n' from string so there isn't an empty line in config file

        //write config string to config file
        try  
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(name+"/STIConfig.txt"));
            writer.write(config);
            writer.close();
        }catch (Exception e){}
    }

    private void initProject() 
    {
        /*
            This method creates and/or loads into memory the config file made to contain the project's
            files. 
            */

        //the block below creates a file object, and creates a folder and config file, if they don't
        //already exist
        boolean existed = false;
        File file = new File(name);
        file.mkdirs();
        file = new File(name+"/STIConfig.txt");
        try{existed = file.createNewFile();}catch(Exception e){}finally {}
        
        if (!existed)
        {
            //load the file paths from the already existing config file
            try
            {
                System.out.print("hi");
                Scanner scan = new Scanner(file);
                while(scan.hasNextLine())
                {
                    if (numOfFiles < files.length)
                        files[numOfFiles] = new JavaFile(scan.nextLine());
                    else 
                    {
                        files = Arrays.copyOf(files, files.length+1);
                        files[numOfFiles] = new JavaFile(scan.nextLine());
                    }
                    numOfFiles++;
                }
            }
            catch(Exception e){}
        }

        if (existed)
            System.out.print("\tProject loaded.\n");
        else 
            System.out.print("\tProject initialized.\n");
    }
}