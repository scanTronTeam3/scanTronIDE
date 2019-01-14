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
            System.out.print("\t1 - Create Project\n\t2 - Add File to Project\n\t3 - Print List of Files in Project\n");
            System.out.print("Choose an option. (0 to quit)\n\n");

            System.out.print("->");

            //clear console for cleanliness 
            switch (scanTron.nextInt())
            {
                case 1:
                    //create project
                    scanTron.nextLine();
                    System.out.print("Enter project name?\n->");
                    //test if project list array is at full capacity,
                    if (numOfProject < projectList.length)//if not, just add it.
                        projectList[numOfProject] = new Project(scanTron.nextLine());
                    else //if so, up capacity by 1
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
                    JavaFile file = new JavaFile (scanTron.nextLine());//create file named by user

                    System.out.print("Select Project:\n----------\n");
                    for (Project p : projectList)//print all the projects
                    {
                        System.out.print("\t" + p.getName() + "\n");
                    }

                    System.out.print("->");
                    String targetPName = scanTron.nextLine();//get name of selected project

                    for (Project p : projectList)//search for project with same name as inputted
                    {
                        if (targetPName.equals(p.getName()))//name of project equals input, add already created file to project
                            p.addFile(file);
                    }

                    System.out.print("\nFile added. Hit Enter.\n");
                    scanTron.nextLine();
                    break;

                case 3:
                    scanTron.nextLine(); 
                    //print out files in specific project
                    System.out.print("Select Project:\n----------\n");
                    for (Project p : projectList)//print projects
                    {
                        System.out.print("\t" + p.getName() + "\n");
                    }

                    System.out.print("->");
                    String targetName = scanTron.nextLine();//get name of project from user

                    for (Project p : projectList)
                    {
                        if (targetName.equals(p.getName()))//name of project equals inputted name 
                        {
                            //check if project file list is empty
                            if (Arrays.toString(p.getFileList()).equals("[null]"))
                                System.out.print("Project empty.\n");
                            else
                            {
                                System.out.print("\nProject - "+p.getName()+"\n----------\n");
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