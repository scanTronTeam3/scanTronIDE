import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.lang.*;

class Lab1
{
    public static void main (String [] args) throws Exception
    {
        Scanner scanTron = new Scanner(System.in);
        Project [] projectList = new Project [1]; 
        int numOfProject = 0;
        int workingProjectIndex = 0;

        while (true)
        {
            System.out.print("Menu: \n-----\n");
            System.out.print("\t1 - Create Projectn\n\t2 - Add File to Project\n\t3 - Print List of Files in Project\n\t4 - Set Working Project\n\t5 - Print Project\n\t6 - Compile\n");
            System.out.print("Choose an option. (0 to quit)\n\n");

            System.out.print("->");

            switch (scanTron.nextInt())
            {
                case 1:
                    //create project
                    scanTron.nextLine();

                    System.out.print("Enter project name?\n->");

                    if (numOfProject < projectList.length)
                        projectList[numOfProject] = new Project(scanTron.nextLine());
                    else 
                    {
                        projectList = Arrays.copyOf(projectList, projectList.length+1);
                        projectList[numOfProject] = new Project(scanTron.nextLine());
                    }
                    numOfProject++;

                    System.out.print("Project created. Hit any key.\n");
                    scanTron.nextLine();
                    break;
                case 2:
                    scanTron.nextLine();

                    //add file to project
                    System.out.print("Name of file:\n->");
                    JavaFile file = new JavaFile (scanTron.nextLine(), projectList[workingProjectIndex].getName());

                    projectList[workingProjectIndex].addFile(file);

                    System.out.print("File added. Hit Enter.\n");
                    scanTron.nextLine();
                    break;
                case 3:
                    scanTron.nextLine(); 

                    System.out.print("Project - "+projectList[workingProjectIndex].getName()+"\n----------\n");
                    for (JavaFile f : projectList[workingProjectIndex].getFileList())
                        System.out.print("\tFile - " + f.getName() + "\n");

                    System.out.print("\nHit Enter.\n");
                    scanTron.nextLine();
                    break;

                case 4:
                    scanTron.nextLine();

                    System.out.print("Select Project #:\n----------\n");
                    int n = 0;
                    for (Project p : projectList)
                    {
                        System.out.print("\t" + n + " - "+ p.getName() + "\n");
                        n++;
                    }

                    System.out.print("\n->");

                    workingProjectIndex = scanTron.nextInt();
                    
                    System.out.print("\nHit Enter.\n");
                    scanTron.nextLine();
                    break;

                case 5:
                    for (JavaFile j : projectList[workingProjectIndex].getFileList())
                    {
                        System.out.print("\nFile " + j.getName() + "\n");
                        System.out.print("----------\n");
                        projectList[workingProjectIndex].getFileList()[0].readFile();
                        projectList[workingProjectIndex].getFileList()[0].printFile();
                    }
                    System.out.print("\nHit Enter.\n");
                    scanTron.nextLine();
                    break;
                
                case 6:
                    for (JavaFile j : projectList[workingProjectIndex].getFileList())
                    {
                        scanTron.nextLine();
                        Runtime r = Runtime.getRuntime();
                        Process p = r.exec("javac " + projectList[workingProjectIndex].getName() + "/" + j.getName());
                        p.waitFor();
                        System.out.print(j.getName() + " javac " + projectList[workingProjectIndex].getName() + "/" + j.getName());
                        scanTron.nextLine();
                    }
                    break;
                case 0:
                    System.exit(0);
            }
        }
    }
}