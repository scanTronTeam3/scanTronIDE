import java.util.Scanner;
import java.util.Arrays;

class Lab1
{
    public static void main(String[] args) // frick
    {
        Scanner scanTron = new Scanner(System.in);
        Project [] projectList = new Project [1]; 
        int numOfProject = 0;

        while (true)
        {
            System.out.print("Menu: \n-----\n");
            System.out.print("\t1 - Create Projectn\n\t2 - Add File to Project\n\t3 - Print List of Files in Project\n");
            System.out.print("Choose an option. (0 to quit)\n\n");

            System.out.print("->");

            //clear console for cleanliness 
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
                    System.out.print("\nHit any key.\n");
                    scanTron.nextLine();
                    break;
                case 2:
                    scanTron.nextLine();
                    //add file to project
                    System.out.print("Name of file:\n->");
                    JavaFile file = new JavaFile (scanTron.nextLine());

                    System.out.print("Select Project:\n----------\n");
                    for (Project p : projectList)
                    {
                        System.out.print("\t" + p.getName() + "\n");
                    }
                    System.out.print("->");
                    String targetPName = scanTron.nextLine();
                    for (Project p : projectList)
                    {
                        if (targetPName.equals(p.getName()))
                            p.addFile(file);
                    }

                    System.out.print("\nFile added. Hit Enter.\n");
                    scanTron.nextLine();
                    break;
                case 3:
                    scanTron.nextLine(); 
                    //print out files in specific project
                    System.out.print("Select Project:\n----------\n");
                    for (Project p : projectList)
                    {
                        System.out.print("\t" + p.getName() + "\n");
                    }
                    System.out.print("->");
                    String targetName = scanTron.nextLine();
                    for (Project p : projectList)
                    {
                        if (targetName.equals(p.getName()))
                        {
                            if (Arrays.toString(p.getFileList()).equals("[null]"))
                                System.out.print("Project empty.\n");
                            else
                            {
                                System.out.print("Project - "+p.getName()+"\n----------\n");
                                for (String f : p.getFileNames())
                                    System.out.print("\tFile - " + f + "\n");
                            }
                        }
                    }
                    System.out.print("\nHit any key.\n");
                    scanTron.nextLine();
                    break;

                case 0:
                    //quit
                    System.exit(0);
            }
        }
    }
}