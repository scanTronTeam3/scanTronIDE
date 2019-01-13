import java.io.File;
import java.io.IOException;
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
    }

    public JavaFile [] getFileList()
    {
        return files;
    }

    public String [] getFileNames()
    {
        String [] fileNames = new String [files.length];
        int x = 0;
        for (JavaFile f : files)
        {
            fileNames[x] = f.getName();
            x++;
        }

        return fileNames;
    }

    private void initProject() 
    {
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