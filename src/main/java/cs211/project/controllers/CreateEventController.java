package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.models.User;
import cs211.project.services.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class CreateEventController {
    private User currentUser;
    @FXML private TextField eventNameTextField;
    @FXML private TextField eventInfoTextField;
    @FXML private TextField placeTextField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Label errorLabel;
    @FXML private Label eventImageErrorLabel;
    @FXML private ImageView eventImageView;
    @FXML private Button uploadImageButton;
    @FXML private ChoiceBox<String> eventChoiceBox;
    File selectedImage = null;

    private Datasource<EventList> datasource;
    private Event event;
    private EventList eventList;
    private String[] eventCategories = {"งานแสดงสินค้า", "เทศกาล", "อบรมสัมนา", "บ้านและของแต่งบ้าน"
            ,"อาหารและเครื่องดื่ม", "บันเทิง","คอนเสิร์ต/แฟนมีตติ้ง", "ท่องเที่ยว", "ศิลปะ/นิทรรศการ/ถ่ายภาพ", "กีฬา"
            , "ศาสนา", "สัตว์เลี้ยง", "ธุรกิจ/อาชีพ/การศึกษา", "อื่น ๆ"};

    @FXML
    public void initialize() {
        currentUser = (User) FXRouter.getData();
        datasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = datasource.readData();

        eventChoiceBox.getItems().addAll(eventCategories);

        eventImageErrorLabel.setText("");
        errorLabel.setText("");
    }

    @FXML
    private void createEvent() throws IOException {
        String eventName = eventNameTextField.getText().trim();
        String eventImage = "";
        String eventInfo = eventInfoTextField.getText().trim();
        String eventCategory = eventChoiceBox.getSelectionModel().getSelectedItem();;
        String place = placeTextField.getText().trim();

        LocalDate localStartDate = startDatePicker.getValue();
        LocalDate localEndDate = endDatePicker.getValue();
        String startDate = localStartDate.toString();
        String endDate = localEndDate.toString();

        String eventOwner = currentUser.getUsername();

        String filePath = "data/eventList.csv";
        File file = new File(filePath);
        FileInputStream fileInputStream = null;

        if (eventName.equals("") || selectedImage==null || eventInfo.equals("") || eventCategory.equals("")
                || place.equals("") || localStartDate==null || localEndDate==null) {
            if (localEndDate.isBefore(localStartDate))
                errorLabel.setText("End date should be greater than start date.");
            else
                errorLabel.setText("Please fill in the required information.");

            if (selectedImage==null)
                eventImageErrorLabel.setText("*Please upload the event image.");
            return;
        }

        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        InputStreamReader inputStreamReader = new InputStreamReader(
                fileInputStream,
                StandardCharsets.UTF_8
        );
        BufferedReader buffer = new BufferedReader(inputStreamReader);
        ArrayList<String> allEvent = new ArrayList<String>();
        String line = "";

        try {
            while ((line = buffer.readLine()) != null) {
                if (line.equals(""))
                    continue;

                String[] data = line.split(",");
                String eventNameInFile = data[0].trim();

                if (eventNameInFile.equals(eventName)) {
                    errorLabel.setText("This event is already exist.");
                    eventNameTextField.setText("");
                    return;
                }
                allEvent.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                fileOutputStream,
                StandardCharsets.UTF_8
        );
        BufferedWriter bufferWrite = new BufferedWriter(outputStreamWriter);

        eventImage = eventName + "." + (Files.probeContentType(Paths.get(selectedImage.getAbsolutePath())).substring(6));
        String newEvent = eventName + "," + eventImage + "," + eventInfo + "," + eventCategory + "," + place
                + "," + startDate + "," + endDate + "," + eventOwner+ ",-1,,";

        try {
            for(String thisEvent : allEvent) {
                bufferWrite.append(thisEvent);
                bufferWrite.append('\n');
            }
            bufferWrite.append(newEvent);
            bufferWrite.append('\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                bufferWrite.flush();
                bufferWrite.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(selectedImage != null) {
            String selectedImagePath = selectedImage.getAbsolutePath();
            String targetDirectoryPath = "data/eventPicture";
            Path targetDirectory = Path.of(targetDirectoryPath);
            String fileType = Files.probeContentType(Paths.get(selectedImage.getAbsolutePath()));
            Path targetFilePath = targetDirectory.resolve(eventName + "." + (fileType.substring(6)));
            try {
                Files.copy(Path.of(selectedImagePath), targetFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        goToMyEvents();
    }

    @FXML
    private void goToMyEvents() {
        try {
            FXRouterPane.goTo("my-events");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void uploadImage() {
        chooseFile();
        if (selectedImage != null) {
            Image image = new Image(selectedImage.getPath());
            eventImageView.setImage(image);
        } else {
            eventImageErrorLabel.setText("*Please upload the event image.");
        }
    }

    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All image files","*.jpg","*.png", "*.jpeg", "*.webp",  "*.jfif" , "*.pjpeg" , "*.pjp"));

        Stage stage = (Stage) uploadImageButton.getScene().getWindow();
        selectedImage = fileChooser.showOpenDialog(stage);
    }

}
