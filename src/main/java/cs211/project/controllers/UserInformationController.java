package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.models.UserList;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class UserInformationController {
    @FXML private Label usernameLabel;
    @FXML private Label nameLabel;
    @FXML private TableView<Event> activeEventTableView;
    @FXML private TableView<Event> eventHistoryTableView;
    @FXML private ImageView profileImageView;
    @FXML private Button changeProfileButton;
    @FXML private Button cancelButton;
    private User currentUser;
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private Event event;
    private Datasource<UserList> userListDataSource;
    private UserList userList;
    private Datasource<List<String[]>> joinEventDataSource;
    private List<String[]> joinEventData;
    File selectedImage = null;

    public void initialize() {
        currentUser = (User) FXRouter.getData();

        userListDataSource = new UserListFileDataSource("data", "userData.csv");
        userList = userListDataSource.readData();

        eventListDatasource = new EventListFileDatasource("data", "eventList.csv");
        eventList = eventListDatasource.readData();

        joinEventDataSource = new JoinEventFileDataSource("data", "joinEventData.csv");
        joinEventData = joinEventDataSource.readData();

        checkFileIsExisted("userData.csv");
        showUser();
        showActiveTable(eventList);
        showHistoryTable(eventList);
    }

    private void checkFileIsExisted(String fileName) {
        File file = new File("data");
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = "data" + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void showUser() {
        nameLabel.setText(currentUser.getName());
        usernameLabel.setText(currentUser.getUsername());
        profileImageView.setImage(currentUser.getProfilePicture());
    }

    private void showActiveTable(EventList eventList) {
        TableColumn<Event, String> eventNameColumn = new TableColumn<>("Name");
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));

        TableColumn<Event, String> eventCategoryColumn = new TableColumn<>("Category");
        eventCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("eventCategory"));

        TableColumn<Event, String> eventStartDateColumn = new TableColumn<>("Start Date");
        eventStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventStartDate"));

        TableColumn<Event, String> eventEndDateColumn = new TableColumn<>("End Date");
        eventEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventEndDate"));

        eventNameColumn.prefWidthProperty().bind(activeEventTableView.widthProperty().multiply(0.36));
        eventCategoryColumn.prefWidthProperty().bind(activeEventTableView.widthProperty().multiply(0.30));
        eventStartDateColumn.prefWidthProperty().bind(activeEventTableView.widthProperty().multiply(0.17));
        eventEndDateColumn.prefWidthProperty().bind(activeEventTableView.widthProperty().multiply(0.17));

        activeEventTableView.getColumns().clear();
        activeEventTableView.getColumns().addAll(eventNameColumn, eventCategoryColumn, eventStartDateColumn, eventEndDateColumn);
        activeEventTableView.getItems().clear();

        ZoneId thaiTimeZone = ZoneId.of("Asia/Bangkok");
        LocalDate currentDate = LocalDate.now(thaiTimeZone);

        for (String[] data : joinEventData) {
            String username = data[0];
            String eventUUID = data[1];
            if (username.equals(currentUser.getUsername())) {
                event = eventList.findEventByUUID(eventUUID);
                if (event.getEndDate().isAfter(currentDate)) {
                    activeEventTableView.getItems().add(event);
                } else {
                    activeEventTableView.getItems();
                }
            }
        }
    }

    private void showHistoryTable(EventList eventList) {
        TableColumn<Event, String> eventNameColumn = new TableColumn<>("Name");
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));

        TableColumn<Event, String> eventCategoryColumn = new TableColumn<>("Category");
        eventCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("eventCategory"));

        TableColumn<Event, String> eventStartDateColumn = new TableColumn<>("Start Date");
        eventStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventStartDate"));

        TableColumn<Event, String> eventEndDateColumn = new TableColumn<>("End Date");
        eventEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventEndDate"));

        eventNameColumn.prefWidthProperty().bind(eventHistoryTableView.widthProperty().multiply(0.36));
        eventCategoryColumn.prefWidthProperty().bind(eventHistoryTableView.widthProperty().multiply(0.30));
        eventStartDateColumn.prefWidthProperty().bind(eventHistoryTableView.widthProperty().multiply(0.17));
        eventEndDateColumn.prefWidthProperty().bind(eventHistoryTableView.widthProperty().multiply(0.17));

        eventHistoryTableView.getColumns().clear();
        eventHistoryTableView.getColumns().addAll(eventNameColumn, eventCategoryColumn, eventStartDateColumn, eventEndDateColumn);
        eventHistoryTableView.getItems().clear();

        ZoneId thaiTimeZone = ZoneId.of("Asia/Bangkok");
        LocalDate currentDate = LocalDate.now(thaiTimeZone);

        for (String[] data : joinEventData) {
            String username = data[0];
            String eventUUID = data[1];
            if (username.equals(currentUser.getUsername())) {
                event = eventList.findEventByUUID(eventUUID);
                if (event.getEndDate().isBefore(currentDate)) {
                    eventHistoryTableView.getItems().add(event);
                } else {
                    eventHistoryTableView.getItems();
                }
            }
        }
    }

    @FXML
    public void changeProfile() throws IOException {
        chooseFile();

        if(selectedImage != null) {
            Image profileImage = new Image(selectedImage.toURI().toString());
            profileImageView.setImage(profileImage);

            String selectedImagePath = selectedImage.getAbsolutePath();
            String targetDirectoryPath = "data/profile_picture";
            Path targetDirectory = Path.of(targetDirectoryPath);

            String fileType = Files.probeContentType(Paths.get(selectedImage.getAbsolutePath()));
            String currentProfilePicName = currentUser.getProfilePictureName();
            Path existingProfilePicPath = targetDirectory.resolve(currentProfilePicName);

            if (Files.exists(existingProfilePicPath)) {
                try {
                    Files.delete(existingProfilePicPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            String newProfilePicFileName = currentUser.getUsername() + "." + (fileType.substring(6));
            currentUser.setProfilePic(newProfilePicFileName);

            Path targetFilePath = targetDirectory.resolve(newProfilePicFileName);
            try {
                Files.copy(Path.of(selectedImagePath), targetFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (User user : userList.getUsers()) {
                if (user.getUsername().equals(currentUser.getUsername())) {
                    user.setProfilePic(newProfilePicFileName);
                    break;
                }
            }
            userListDataSource.writeData(userList);
        }
    }

    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All image files","*.jpg","*.png", "*.jpeg", "*.webp",  "*.jfif" , "*.pjpeg" , "*.pjp"));

        Stage stage = (Stage) changeProfileButton.getScene().getWindow();
        selectedImage = fileChooser.showOpenDialog(stage);
    }

    @FXML
    private void cancelChangeProfilePic() {
        selectedImage = null;
        profileImageView.setImage(new Image(getClass().getResource("/cs211/project/images/default.png").toExternalForm()));

        String targetDirectoryPath = "data/profile_picture";
        Path targetDirectory = Path.of(targetDirectoryPath);
        String currentProfilePicName = currentUser.getProfilePictureName();
        Path existingProfilePicPath = targetDirectory.resolve(currentProfilePicName);

        if (Files.exists(existingProfilePicPath)) {
            try {
                Files.delete(existingProfilePicPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String profilePic = "default.png";
        currentUser.setProfilePic(profilePic);

        for (User user : userList.getUsers()) {
            if (user.getUsername().equals(currentUser.getUsername())) {
                user.setProfilePic(profilePic);
                break;
            }
        }
        userListDataSource.writeData(userList);
    }
}
