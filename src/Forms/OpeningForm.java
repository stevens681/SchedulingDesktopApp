package Forms;

import Utilities.Appointment;
import Utilities.Customer;
import Utilities.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

public class OpeningForm {

    @FXML
    private GridPane gridPaneThisWeek;
    @FXML
    private GridPane gridPaneThisMonth;

    private Pane paneContainer;
    private Label paneLabel;

    /**
     * The cancel button takes you back to the main form
     * @param e ActionEvent
     * @throws IOException Failed to go back to the main form
     * */
    public void cancel(ActionEvent e)throws IOException {

        Main.callForms(e, "MainForm");
    }
    public void test(ActionEvent e) throws IOException{

        LocalDate today = LocalDate.parse(Main.splitDate(Main.time(), 0));

        int rowWeek = 0, colWeek = 0, colMonth = 0, rowMonth = 0;

       for(Customer c : DataBase.getAllCustomers()){

           String customerName = "Customer Name: " + c.getName();

           for (Appointment a : c.getAllAppointments()){

               LocalDate apt = LocalDate.parse(Main.splitDate(a.getStart(), 0));
               Period p = Period.between(today, apt);

               if(p.getDays() <= 7 && p.getDays() >= 0) {

                   paneLabel = new Label();
                   paneLabel.setWrapText(true);
                   paneLabel.setMaxWidth(95);
                   paneLabel.setTextAlignment(TextAlignment.CENTER);
                   paneLabel.setTranslateY(10);
                   paneLabel.setFont(new Font("Roboto Thin", 10));
                   paneLabel.setText(customerName + "\nStart: " + a.getStart());

                   paneContainer = new Pane();

                   if (colWeek == 0)
                       paneContainer.setStyle("-fx-background-color: #ffb3ba; -fx-border-style: solid; -fx-border-width: 1px; -fx-border-color:#000;");
                   if (colWeek == 1)
                       paneContainer.setStyle("-fx-background-color: #ffdfba; -fx-border-style: solid; -fx-border-width: 1px; -fx-border-color:#000; ");
                   if (colWeek == 2)
                       paneContainer.setStyle("-fx-background-color: #ffffba; -fx-border-style: solid; -fx-border-width: 1px; -fx-border-color:#000; ");

                   paneContainer.setPrefWidth(100);
                   paneContainer.setPrefHeight(100);
                   paneContainer.getChildren().add(paneLabel);

                   gridPaneThisWeek.add(paneContainer, colWeek, rowWeek);

                   colWeek++;

               }

               if(p.getDays() <= 30 && p.getDays() >= 8){

                    paneLabel = new Label();
                    paneLabel.setWrapText(true);
                    paneLabel.setMaxWidth(95);
                    paneLabel.setTextAlignment(TextAlignment.CENTER);
                    paneLabel.setTranslateY(10);
                    paneLabel.setFont(new Font("Roboto Thin", 10));
                    paneLabel.setText(customerName + "\nStart: " + a.getStart());

                    paneContainer = new Pane();

                   if(colMonth == 0)
                       paneContainer.setStyle("-fx-background-color: #bae1ff; -fx-border-style: solid; -fx-border-width: 1px; -fx-border-color:#000; ");
                   if(colMonth == 1)
                       paneContainer.setStyle("-fx-background-color: #baffc9; -fx-border-style: solid; -fx-border-width: 1px; -fx-border-color:#000; ");
                   if(colMonth == 2)
                       paneContainer.setStyle("-fx-background-color: #ffdfba; -fx-border-style: solid; -fx-border-width: 1px; -fx-border-color:#000; ");

                   paneContainer.setPrefWidth(100);
                   paneContainer.setPrefHeight(100);

                   paneContainer.getChildren().add(paneLabel);
                   gridPaneThisMonth.add(paneContainer, colMonth, rowMonth);
                   colMonth++;
               }

               if(colWeek==3 ){
                   rowWeek++;
                   colWeek = 0;
               }

               if(colMonth==3 ) {
                   rowMonth++;
                   colMonth = 0;

               }
           }
       }
    }
}
