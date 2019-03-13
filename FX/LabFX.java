import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.text.TextFlow;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.geometry.NodeOrientation;

import javafx.scene.layout.*;
import javafx.event.*;
import javafx.geometry.Orientation;
import javafx.stage.FileChooser;
import javafx.collections.*;
import javafx.beans.value.ObservableValue;

import java.util.Optional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LabFX extends Application
{
    /*
        declare Objects
        */
    MenuBar menuBar;
    Menu fileMenu, editMenu;   
    MenuItem newProject, newFile, compileB, runB;

    ToolBar toolBar;
    Button compileButton, runButton, saveButton, refreshButton;

    ListView<Label> explorerView;

    TextArea compileOutput;
    
    TabPane fileView;
    ArrayList<String> tabIDs; //list of all open tabIds
    ArrayList<TextArea> fileTextAreas; //text areas for tabs
    String selectedTabID; //currently selected tab

    Project project; //project class object

    Stage scanTron; //javaFX stage

    @Override
    public void start(Stage st)
    {
        scanTron = st;

        BorderPane root = new BorderPane();

        compileOutput = new TextArea();
        fileView = new TabPane();
        tabIDs = new ArrayList<String>();
        fileTextAreas = new ArrayList<TextArea>();
        
        compileButton = new Button("_Compile");
        runButton = new Button("_Run");
        saveButton = new Button("_Save");
        refreshButton = new Button("Refresh");
        toolBar = new ToolBar(saveButton, compileButton, runButton, refreshButton);

        //set functions
        compileButton.setOnAction(this::compile);
        runButton.setOnAction(this::runFile);
        saveButton.setOnAction(this::saveFile);
        refreshButton.setOnAction(this::refreshFiles);
        
        menuBar = new MenuBar();
        fileMenu = new Menu("File");
        editMenu = new Menu("Edit");
        
        newProject = new MenuItem("Create Project");
        newFile = new MenuItem("Open File");
        compileB = new MenuItem("Compile");
        
        //set functions
        newProject.setOnAction(this::createProject);
        newFile.setOnAction(this::addFile);
        compileB.setOnAction(this::compile);
        /*
            grey out this button until a project is created
            */
        newFile.setDisable(true);

        //add buttons to menu bar
        editMenu.getItems().add(compileB);
        fileMenu.getItems().addAll(newProject, newFile);
        menuBar.getMenus().addAll(fileMenu, editMenu); 
        
        explorerView = new ListView<Label>();
        explorerView.getSelectionModel().selectedItemProperty().addListener(this::handleNewTab);
                
        //keep track of selected file 
        fileView.getSelectionModel().selectedItemProperty().addListener(this::handleTabSelect);

        //define layouts
        BorderPane center = new BorderPane();
        center.setCenter(fileView);
        center.setBottom(compileOutput);
        
        root.setLeft(explorerView);
        root.setCenter(center);
        root.setTop(new VBox(menuBar, toolBar));
        
        Scene sc = new Scene(root, 700, 500);
        scanTron.setScene(sc);
        scanTron.show();
    }

    public void handleNewTab(ObservableValue<? extends Label> observable, Label oldValue, Label newValue)
    {
        /*
            find this javafile object for the requested new tab (requesed by clicking the file in the side menu),
            check if the tab already exists
            if not, create it
            */
        for (JavaFile file : project.getFiles())
            if (file.getName().equals(newValue.getText()))
            {
                //check if tab exists
                boolean tabExists = false;
                for(String tID : tabIDs)
                    if (tID.equals(file.getPath()))
                        tabExists = true;
                
                //create tab
                if(!tabExists)
                {
                    /*
                        create tab object, set it's id to the file path
                        create a text area object, give it the same id, set it's contents, and add it to list of text areas
                        add tabID to list of tabID's, used to keep track of which tabs are open

                        set handler to remove the tab at request (requested by clicking x on tab),
                        removing the tabID from list of tabID list, and remove text area from list of text areas
                        */
                    Tab t = new Tab(file.getName());
                    t.setId(file.getPath());
                    fileTextAreas.add(new TextArea(file.getContents()));
                    fileTextAreas.get(fileTextAreas.size()-1).setId(file.getPath());
                    t.setContent(fileTextAreas.get(fileTextAreas.size()-1));
                    
                    //remove ID from arraylist on close
                    t.setOnCloseRequest(event ->
                    {
                        for(int x = 0; x < tabIDs.size(); x++)
                        {
                            if (tabIDs.get(x).equals(t.getId()))
                            tabIDs.remove(x);
                            if (fileTextAreas.get(x).getId().equals(t.getId()));
                            fileTextAreas.remove(x);
                        }
                    });
                    
                    tabIDs.add(t.getId());
                    fileView.getTabs().add(t);
                }
            }
    }

    public void handleTabSelect(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue)
    {
        if (newValue != null)
        {
            selectedTabID = newValue.getId();
            
            //debug:
            String out = "\nOpened tabs:\n";
            for (String id : tabIDs)
                out+="- "+id+"\n";
            System.out.print(out);
        }
    }
                
    public void createProject(ActionEvent e)
    {
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Add File");
        d.setHeaderText("Create Project");
        d.setContentText("Name of project:");
        String name = d.showAndWait().get();
        project = new Project(name);
        scanTron.setTitle("ScanTron IDE " + name); 
        newFile.setDisable(false);
    }
    
    public void refreshFiles(ActionEvent e)
    {
        project.refresh();
        for (TextArea t : fileTextAreas)
        {
            for (JavaFile f : project.getFiles())
            {
                if (f.getName() == t.getId())
                t.setText(f.getContents());
            }
        }
    }
    
    public void saveFile(ActionEvent e)
    {
        String contents = "";
        for (TextArea t : fileTextAreas)
        if (t.getId().equals(selectedTabID))
        contents = t.getText();
        
        for (JavaFile file : project.getFiles())
        {
            if (file.getName().equals(selectedTabID))
            {
                try
                {
                    file.saveFile(contents);
                }
                catch (IOException a)
                {
                    Alert alert = new Alert(AlertType.ERROR,
                    a.getMessage(),
                    ButtonType.OK);
                    alert.showAndWait();
                }
            }
        }
    }
    
    public void addFile(ActionEvent e)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File selectedFile = fileChooser.showOpenDialog(scanTron);
        
        JavaFile file = new JavaFile(selectedFile.getAbsolutePath(), project.getName());
        project.addFile(file);
        explorerView.getItems().add(new Label(file.getName()));
    }
    
    public void compile(ActionEvent e)
    {
        String out = "hi - scanTron - compiling\n";
        compileOutput.setText(out);
        
        for (JavaFile file : project.getFiles())
        {
            if (file.getName().equals(explorerView.getSelectionModel().getSelectedItem().getText()));
                out += file.compileFile();

        }
        
        compileOutput.setText(out);
    }

    public void runFile(ActionEvent e)
    {
        String out = "hi - scanTron - running\n";
        compileOutput.setText(out);

        for (JavaFile f: project.getFiles())
        {
            if (f.getName().equals(selectedTabID))
                out += f.runFile();
        }

        compileOutput.setText(out);

    }

    public static void main(String[] args) 
    {
        launch(args);
    }
};
