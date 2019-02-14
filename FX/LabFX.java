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
import javafx.scene.control.DialogEvent;
import javafx.geometry.NodeOrientation;

import javafx.scene.layout.*;
import javafx.event.*;
import javafx.geometry.Orientation;
import javafx.collections.*;
import java.util.Optional;

import java.util.ArrayList;

public class LabFX extends Application
{
    MenuBar menuBar;
    Menu fileMenu, editMenu;   
    MenuItem newProject, newFile, compileB, runB;

    ToolBar toolBar;
    Button compileButton, runButton, saveButton;

    ListView<Label> explorerView;
    TextArea compileOutput;
    
    TabPane fileView;
    ArrayList<String> tabIDs;
    ArrayList<TextArea> fileTextAreas;
    String selectedTabID;

    Project project;

    Stage primaryStage;

    @Override
    public void start(Stage st)
    {
        primaryStage = st;

        BorderPane root = new BorderPane();

        compileOutput = new TextArea();
        fileView = new TabPane();
        tabIDs = new ArrayList<String>();
        fileTextAreas = new ArrayList<TextArea>();

        BorderPane center = new BorderPane();
        center.setCenter(fileView);
        center.setBottom(compileOutput);

        compileButton = new Button("_Compile");
        runButton = new Button("Run");
        saveButton = new Button("_Save");
        toolBar = new ToolBar(saveButton, compileButton, runButton);

        compileButton.setOnAction(this::compile);
        runButton.setOnAction(this::runFile);
        saveButton.setOnAction(this::saveFile);

        menuBar = new MenuBar();
        fileMenu = new Menu("File");
        editMenu = new Menu("Edit");

        newProject = new MenuItem("New Project");
        newFile = new MenuItem("Add File");
        newProject.setOnAction(this::createProject);
        newFile.setOnAction(this::addFile);

        compileB = new MenuItem("Compile");
        compileB.setOnAction(this::compile);
        editMenu.getItems().add(compileB);

        fileMenu.getItems().addAll(newProject, newFile);
        menuBar.getMenus().addAll(fileMenu, editMenu); 

        explorerView = new ListView<Label>();
        explorerView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) ->
            {
                for (JavaFile file : project.getFiles())
                    if (file.getName().equals(newValue.getText()))
                    {
                        //check if tab exists
                        boolean tabExists = false;
                        for(String tID : tabIDs)
                            if (tID.equals(file.getName()))
                                tabExists = true;

                        //create tab
                        if(!tabExists)
                        {
                            Tab t = new Tab(file.getName());
                            t.setId(file.getName());
                            fileTextAreas.add(new TextArea(file.getContents()));
                            fileTextAreas.get(fileTextAreas.size()-1).setId(file.getName());
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
                        // fileEditor.setText(file.getContents());
                    }
            }
        );

        //keep track of selected file 
        fileView.getSelectionModel().selectedItemProperty().addListener(
            (obs,ov,nv) ->
            {
                selectedTabID = nv.getId();
            }
        );

        root.setLeft(explorerView);
        root.setCenter(center);
        root.setTop(new VBox(menuBar, toolBar));

        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }

    public void createProject(ActionEvent e)
    {
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Add File");
        d.setHeaderText("Create Project");
        d.setContentText("Name of project:");
        String name = d.showAndWait().get();
        project = new Project(name);
        primaryStage.setTitle(name); 
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
                file.saveFile(contents);
        }
    }

    public void addFile(ActionEvent e)
    {
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Add File");
        d.setHeaderText("Add File");
        d.setContentText("Path to file:");
        Optional<String> fileName = d.showAndWait();
        JavaFile file = new JavaFile(fileName.get(), project.getName());
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
