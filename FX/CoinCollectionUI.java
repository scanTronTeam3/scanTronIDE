import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class CoinCollectionUI extends Application
{
   CoinCollection collection;
   int index;
   Label yearLabel, valueLabel, denomLabel, indexLabel;
   TextField yearField, valueField, denomField;
   Button prevButton, nextButton, addButton, saveButton;
   
   public void start(Stage primaryStage)
   {
      collection = new CoinCollection();
      
      yearLabel = new Label("Year: ");
      yearLabel.setMinWidth(50);
      yearField = new TextField();
      yearField.setMinWidth(300);
      
      valueLabel = new Label("Value: ");
      valueLabel.setMinWidth(50);
      valueField = new TextField();
      valueField.setMinWidth(300);
      
      denomLabel = new Label("Denom: ");
      denomLabel.setMinWidth(50);
      denomField = new TextField();
      denomField.setMinWidth(300);
      
      index = 0;
      indexLabel = new Label("  0  ");
      
      prevButton = new Button("previous");
		prevButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
      			processPrevButton(e);
            }
        });
      
      nextButton = new Button("next");
		nextButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
      			processNextButton(e);
            }
        });
        
      addButton = new Button("add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
      			processAddButton(e);
            }
        });
        
      saveButton = new Button("save");
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
      			processSaveButton(e);
            }
        });
        
      FlowPane pane = new FlowPane(yearLabel, yearField,
                           valueLabel, valueField,
                           denomLabel, denomField,
                           prevButton, nextButton, 
                           addButton, saveButton, 
                           indexLabel);
      Scene theScene = new Scene(pane, 360, 200);
      primaryStage.setTitle("Example Text Field");
      primaryStage.setScene(theScene);
      primaryStage.show();
   }
   
   public void processPrevButton(ActionEvent event)
   {
  // /*
      Coin c = collection.getCoin(index-1);
      if (c  != null)
      {
         yearField.setText(""+c.getYear());
         valueField.setText(""+c.getValue());
         denomField.setText(c.getDenom());
         index--;
         indexLabel.setText("  " + index + "  ");
      }
  // */
   }

   public void processNextButton(ActionEvent event)
   {
  // /*
      Coin c = collection.getCoin(index+1);
      if (c  != null)
      {
         yearField.setText(""+c.getYear());
         valueField.setText(""+c.getValue());
         denomField.setText(c.getDenom());
         index++;
         indexLabel.setText("  " + index + "  ");
      }
   //*/
   }

   public void processAddButton(ActionEvent event)
   {
  // /*
      int year = Integer.parseInt(yearField.getText());
      double value = Double.parseDouble(valueField.getText());
      String denom = denomField.getText();
      Coin c = new Coin(year, value, denom);
      index = collection.add(c);
      indexLabel.setText("  " + index + "  ");
  // */
   }

   public void processSaveButton(ActionEvent event)
   {
//   /*
      int year = Integer.parseInt(yearField.getText());
      double value = Double.parseDouble(valueField.getText());
      String denom = denomField.getText();
      Coin c = collection.getCoin(index);
      c.save(year, value, denom);
  // */
   }

   public static void main(String[] args)
   {
      launch(args);
   }
}

