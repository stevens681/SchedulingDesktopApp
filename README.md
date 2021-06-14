## **Scheduling Desktop App**

This application allows the user to add a new customer and collect of the information needed to contact the customer,
then the user can add an appointment for services. 

Author: Fernando Rosa, frosa1@wgu.edu

Version: 1.0

Date: 3/15/2021

IntelliJ IDEA 2020.3.2 (Community Edition)
Build #IC-203.7148.57, built on January 25, 2021
Runtime version: 11.0.9.1+11-b1145.77 amd64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.



To use the application you will need to login

Username: test
Password: test

On this screen you can select a language also it takes the language of your system.


![img_1.png](img_1.png)

After logged in at the screen you can add costumers, modify the selected customer, add an appointment to the selected customer,
or delete a customer(to delete the customer all the appointments belonging to the customer need to be deleted first).

![img_10.png](img_10.png)


Adding a new customer all the information need to be added properly then click "Add Customer".


![img_2.png](img_2.png)

Modifying an existing customer, the application prepopulate the customer's info. Modify it and save the customer.

![img_3.png](img_3.png)

Scheduling Appointments allows you to view the customer's appointments, add a new one, modify an existing one, 
see the details of the appointment, or delete appointments.

![img_4.png](img_4.png)

To add an appointment make sure all the information is filled up properly.

![img_5.png](img_5.png)

Modifying an existing appointment, the application prepopulate the appointment's info. Modify it and save the appointment.

![img_6.png](img_6.png)

Appointment's details

![img_7.png](img_7.png)

Report

![img_8.png](img_8.png)

*************************************************************************************************************
                                    More Functionalities
This function checks for every appointment every minute and if there is an appointment for today it will 
display all the appointments for the day, also y there is an appointment coming up in the next 15 minutes 
it also will show the appointment.

`public static void alarm() {
`

        String msg ="There are appointments today at: ";
        for (Appointment a:  DataBase.getAllAppointments()){

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

            try {
                Date date, nowDate;

                date = dateFormat.parse(convertZone(a.getStart()+" UTC"));
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.MINUTE, -15);
                int hour = c.get(Calendar.HOUR_OF_DAY), min = c.get(Calendar.MINUTE);

                nowDate = dateFormat.parse(convertZone(time()));
                c.setTime(nowDate);
                int nowHour = c.get(Calendar.HOUR_OF_DAY), nowMin = c.get(Calendar.MINUTE);

                if(splitDate(convertZone(time()), 0).equals(splitDate(convertZone(a.getStart()+" UTC"), 0))){
                    todayAppointments = true;
                    msg += "\n" +hour ;

                    if(hour == nowHour && nowMin >= min){
                        String aptMsg = "Tittle: " + a.getTittle() + "\nAt: "+ convertZone(a.getStart()+" UTC");
                        Object[] options = {"Remind me in a minute",
                                "Dismiss"};
                        int n = JOptionPane.showOptionDialog(null,
                                aptMsg, "Alarm", JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE,null, options, options[1]);

                        if(n == 1)
                            alarmFlag = false;
                    }
                }

            }
            catch (ParseException parseException){
                System.out.println(parseException);

            }

            if(todayAppointments){
                Object[] options = {"Remind me in a minute",
                        "Dismiss"};
                int n = JOptionPane.showOptionDialog(null,
                        msg, "Alarm", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,null, options, options[1]);

                if(n == 1)
                    todayAppointments = false;
            }
        }
    }`

This function will check for the calendar, it will mark red weekends and past days, also it will check 
if there is another appointment for that day and if there is one it will remove it from the time picker, 
if all the available times are already taken that day will be blocked.

`private void daysAvailable(){`

        String[] data = {};

        ObservableList<String> inData = FXCollections.observableArrayList();

        for(Appointment appointment: DataBase.getAllAppointments()){
            if(appointment.getStart().contains(" "))
                inData.add(splitDate(appointment.getStart()));
            else
                inData.add(appointment.getStart());
        }

        Map<String, Integer> result = new HashMap<>();
        for(String s : inData){

            if(result.containsKey(s)){

                result.put(s, result.get(s)+1);
                if(result.containsValue(5)){
                    data = addString(data.length, data, s);
                }
            }else{

                result.put(s, 1);
            }
        }

            String[] finalData = data;

            startDate.setDayCellFactory(days -> new DateCell() {

            @FXML
            public void updateItem(LocalDate date, boolean empty) {

                super.updateItem(date, empty);
                LocalDate todayNow = LocalDate.now();
                setDisable(empty || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY
                        || date.compareTo(todayNow) < 0);
                if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                        date.compareTo(todayNow) < 0) {
                    setStyle("-fx-background-color: #EF5350;");
                }

                for(int i = 0; i< finalData.length; i++){
                    String test = finalData[i];
                    DateTimeFormatter  formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate indate = LocalDate.parse(test, formatter);
                    if(date.equals(indate)){
                        setDisable(date.equals(indate));
                        setStyle("-fx-background-color: #A6ACAF;");
                    }
                }
            }
        });
    }
*************************************************************************************************************

A3f

MySQL Connecto driver version: mysql-connector-java-8.0.19

When I was working with the Database I found an error that did not allow me to delete one of my entries from the 
database, it was telling me that I was using a foring key. Now I think the error went away, I just noticed it just in case.