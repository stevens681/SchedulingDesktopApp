package Forms;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;


public class AppointmentForm {

    @FXML
    private DatePicker startDate;

//    final Callback<DatePicker, DateCell> dayCellFactory =
//            new Callback<DatePicker, DateCell>() {
//                @Override
//                public DateCell call(final DatePicker datePicker) {
//                    return new DateCell() {
//                        @Override
//                        public void updateItem(LocalDate item, boolean empty) {
//                            super.updateItem(item, empty);
//
//                            if (item.isBefore(
//                                    startDate.getValue().plusDays(1))
//                            ) {
//                                setDisable(true);
//                                setStyle("-fx-background-color: #ffc0cb;");
//                            }
//                        }
//                    };
//                }
//            };





    @FXML
    public void initialize() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date(System.currentTimeMillis());


        //startDate.setValue(LocalDate.of(2020,12,02));
        startDate.setDayCellFactory(days -> {return new DateCell() {
            @FXML
            public void updateItem(LocalDate date, boolean empty) {

                super.updateItem(date, empty);
                setDisable(empty || date.getDayOfWeek() == DayOfWeek.SATURDAY);
                if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                    setStyle("-fx-background-color: #ffc0cb;");
                }

            }
        };

        });
//        startDate.setValue(LocalDate.of(2020,12,05));



    }

}
