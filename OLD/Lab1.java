import java.util.Scanner;
import java.util.Arrays;
import java.io.BufferedReader;
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
            System.out.print("\t1 - Create Project\n\t2 - Add File to Project\n\t3 - Print List of Files in Project\n\t4 - Set Working Project\n\t5 - Print Project\n\t6 - Compile\n\t7 - Run\n");
            System.out.print("Choose an option. (0 to quit)\n\n");

            System.out.print("->");

            switch (scanTron.nextInt())
            {
                case 1:
                    scanTron.nextLine();
                    
                    //create project
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

                    //print out files in project
                    System.out.print("Project - "+projectList[workingProjectIndex].getName()+"\n----------\n");
                    for (JavaFile f : projectList[workingProjectIndex].getFileList())
                        System.out.print("\tFile - " + f.getName() + "\n");

                    System.out.print("\nHit Enter.\n");
                    scanTron.nextLine();
                    break;

                case 4:
                    scanTron.nextLine();

                    //change working project
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
                    scanTron.nextLine();

                    //print file contents of project
                    for (JavaFile j : projectList[workingProjectIndex].getFileList())
                    {
                        System.out.print("\nFile " + j.getName() + "\n");
                        System.out.print("----------\n");
                        projectList[workingProjectIndex].getFileList()[0].printFile();
                    }
                    System.out.print("\nHit Enter.\n");
                    scanTron.nextLine();
                    break;
                
                case 6:
                    //compile project
                    for (JavaFile j : projectList[workingProjectIndex].getFileList())
                    {
                        scanTron.nextLine();
                        Process p = Runtime.getRuntime().exec("javac " + projectList[workingProjectIndex].getName() + "/" + j.getName());
                        p.waitFor();
                        Scanner processOut = new Scanner(p.getErrorStream());
                        System.out.print("\ncompiling " + j.getName() + ": javac " + projectList[workingProjectIndex].getName() + "/" + j.getName());
                        while (processOut.hasNextLine())
                            System.out.print(processOut.nextLine() + "\n");

                        System.out.print("\nPress any key.\n");
                        scanTron.nextLine();
                    }
                    break;
                    
                case 7:
                    scanTron.nextLine();

                    //run project
                    projectList[workingProjectIndex].run();
                    System.out.print("\nPress any key.\n");
                    scanTron.nextLine();
                    break;

                case 0:
                    System.exit(0);
            }

            //reload all files
            for (Project p : projectList)
                p.refresh();
        }
    }
}