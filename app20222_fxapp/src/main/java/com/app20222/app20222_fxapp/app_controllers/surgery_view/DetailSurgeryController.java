package com.app20222.app20222_fxapp.app_controllers.surgery_view;
import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.constants.apis.ApiConstants;
import com.app20222.app20222_fxapp.dto.file_attach.FileAttachDTO;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryAssignmentDTO;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryDetailDTO;
import com.app20222.app20222_fxapp.enums.fileAttach.FileTypeEnum;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class DetailSurgeryController implements Initializable {

    @FXML
    private AnchorPane detailSurgeryPane;
    @FXML
    private Text diseaseGroupType;
    @FXML
    private Text surgeryDetailCode;
    @FXML
    private Text surgeryDetailCreatedAt;
    @FXML
    private Text surgeryDetailCreatedBy;
    @FXML
    private TextArea surgeryDetailDescription;
    @FXML
    private Text surgeryDetailDiseaseGroupName;
    @FXML
    private Text surgeryDetailEndedAt;
    @FXML
    private Text surgeryDetailEstimatedEndAt;
    @FXML
    private Text surgeryDetailName;
    @FXML
    private Text surgeryDetailPatientName;
    @FXML
    private Text surgeryDetailResult;
    @FXML
    private Text surgeryDetailStartedAt;
    @FXML
    private Text surgeryDetailStatus;
    @FXML
    private Text surgeryDetailSurgeryRoom;
    @FXML
    private TableView<SurgeryAssignmentDTO> surgeryDetailLstAssignmentTable;
    @FXML
    private TableColumn<SurgeryAssignmentDTO, String> surgeryDetailAssigneeCode;
    @FXML
    private TableColumn<SurgeryAssignmentDTO, String> surgeryDetailAssigneeName;
    @FXML
    private TableColumn<SurgeryAssignmentDTO, String> surgeryDetailSurgeryRole;
    @FXML
    private TableColumn<SurgeryAssignmentDTO, Long> surgeryDetailAssignmentStt;
    @FXML
    private TableView<FileAttachDTO> surgeryDetailLstFileTable;
    @FXML
    private TableColumn<FileAttachDTO, String> surgeryDetailFileAction;
    @FXML
    private TableColumn<FileAttachDTO, String> surgeryDetailFileLocation;
    @FXML
    private TableColumn<FileAttachDTO, String> surgeryDetailFileName;
    @FXML
    private TableColumn<FileAttachDTO, Long> surgeryDetailFileStt;
    @FXML
    private TableColumn<FileAttachDTO, String> surgeryDetailFileType;
    @FXML
    private Button editSurgeryBtn;
    private List<FileAttachDTO> lstFileAttach = new ArrayList<>();
    private  List<SurgeryAssignmentDTO> lstAssignment = new ArrayList<>();
    private SurgeryDetailDTO surgeryDetailDTO;
    private SurgeryController surgeryController;
    private static final String filePathSuffix = "http://" + ApiConstants.API_LOCAL_IP + ":" + ApiConstants.SERVER_PORT + ApiConstants.DEFAULT_API_PATH;

    public void setSurgeryDetail(SurgeryDetailDTO surgeryDetailDTO) {
        // Set values for the Text components
        this.surgeryDetailDTO = surgeryDetailDTO;
        setTextField(diseaseGroupType, surgeryDetailDTO.getDiseaseGroupName());
        setTextField(surgeryDetailCode, surgeryDetailDTO.getCode());
        setTextField(surgeryDetailCreatedAt, formatDateOrEmpty(surgeryDetailDTO.getCreatedAt()));
        setTextField(surgeryDetailCreatedBy, surgeryDetailDTO.getCreatedBy());
        surgeryDetailDescription.setText(surgeryDetailDTO.getDescription() != null ? surgeryDetailDTO.getDescription() : "Chưa có thông tin");
        setTextField(surgeryDetailDiseaseGroupName, surgeryDetailDTO.getDiseaseGroupName());
        setTextField(surgeryDetailEndedAt, formatDateOrEmpty(surgeryDetailDTO.getEndedAt()));
        setTextField(surgeryDetailEstimatedEndAt, formatDateOrEmpty(surgeryDetailDTO.getEstimatedEndAt()));
        setTextField(surgeryDetailName, surgeryDetailDTO.getName());
        setTextField(surgeryDetailPatientName, surgeryDetailDTO.getPatientName());
        setTextField(surgeryDetailResult, surgeryDetailDTO.getResult());
        setTextField(surgeryDetailStartedAt, formatDateOrEmpty(surgeryDetailDTO.getStartedAt()));
        setTextField(surgeryDetailStatus, surgeryDetailDTO.getStatus());
        setTextField(surgeryDetailSurgeryRoom, surgeryDetailDTO.getSurgeryRoom());

        this.lstFileAttach = surgeryDetailDTO.getLstFileAttach();
        this.lstAssignment = surgeryDetailDTO.getLstAssignment();

        ObservableList<SurgeryAssignmentDTO> observableAssignmentList = FXCollections.observableArrayList(lstAssignment);
        this.surgeryDetailLstAssignmentTable.setItems(observableAssignmentList);

        ObservableList<FileAttachDTO> observableFileList = FXCollections.observableArrayList(lstFileAttach);
        this.surgeryDetailLstFileTable.setItems(observableFileList);
    }

    // Khởi tạo SurgeryController (dùng cho gọi lại api danh sách)
    public void setSurgeryController(SurgeryController surgeryController) {
        this.surgeryController = surgeryController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpTable();
    }
    public void setUpTable(){
        setupAssigmentTableColumns();
        setupFileTableColumns();
        setupDownloadButtons();
    }
    public void setupAssigmentTableColumns() {
        // Set up the PropertyValueFactory to map the data fields to the table columns
        this.surgeryDetailAssignmentStt.setCellValueFactory(param -> {
            int index = param.getTableView().getItems().indexOf(param.getValue());
            return new SimpleLongProperty(index + 1).asObject();
        });
        surgeryDetailAssigneeCode.setCellValueFactory(new PropertyValueFactory<>("assigneeCode"));
        surgeryDetailAssigneeName.setCellValueFactory(new PropertyValueFactory<>("assigneeName"));
        surgeryDetailSurgeryRole.setCellValueFactory(new PropertyValueFactory<>("surgeryRole"));
    }

    public void setupFileTableColumns() {
        // Set up the PropertyValueFactory to map the data fields to the table columns
        this.surgeryDetailFileStt.setCellValueFactory(param -> {
            int index = param.getTableView().getItems().indexOf(param.getValue());
            return new SimpleLongProperty(index + 1).asObject();
        });
        surgeryDetailFileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        surgeryDetailFileLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        surgeryDetailFileType.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    public void downloadFile(String fileUrl, String filePath) {
        try {
            FileUtils.copyURLToFile(new URL(fileUrl), new File(filePath), 10000, 10000);
            System.out.println("File đã được tải xuống thành công!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupDownloadButtons() {
        Callback<TableColumn<FileAttachDTO, String>, TableCell<FileAttachDTO, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<FileAttachDTO, String> call(TableColumn<FileAttachDTO, String> param) {
                        final TableCell<FileAttachDTO, String> cell = new TableCell<FileAttachDTO, String>() {
                            private final Button downloadButton = new Button();
                            {
                                FontAwesomeIconView downloadIcon = new FontAwesomeIconView(FontAwesomeIcon.UPLOAD);
                                downloadIcon.setStyle(
                                        " -fx-cursor: hand ;"
                                                + "-glyph-size:24px;"
                                                + "-fx-fill:#00E676;"
                                );
                                downloadButton.setGraphic(downloadIcon);
                                downloadButton.getStyleClass().add("edit-button");

                                // Handle edit button action
                                downloadButton.setOnAction(event -> {
                                    FileAttachDTO file = getTableView().getItems().get(getIndex());
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id", String.valueOf(file.getFileId()));
                                    String fileUrl = Objects.equals(file.getType(), FileTypeEnum.DOCUMENT.getType()) ? filePathSuffix + file.getLocation() : file.getLocation();
                                    String suggestedFileName = file.getFileName();

                                    FileChooser fileChooser = new FileChooser();
                                    fileChooser.setInitialFileName(suggestedFileName);

                                    File selectedFile = fileChooser.showSaveDialog(detailSurgeryPane.getScene().getWindow());

                                    if (selectedFile != null) {
                                        String fileName = selectedFile.getAbsolutePath();
                                        downloadFile(fileUrl, fileName);
                                    }
                                });
                                setGraphic(downloadButton);
                            }

                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(downloadButton);
                                }
                            }
                        };
                        return cell;
                    }
                };

        surgeryDetailFileAction.setCellFactory(cellFactory);
    }
    // Utility method to format Date in the desired pattern
    public void setTextField(Text textInputControl, String text) {
        if (text != null) {
            textInputControl.setText(text);
        } else {
            textInputControl.setText("Chưa có thông tin");
        }
    }

    public String formatDateOrEmpty(Date date) {
        if (date == null) {
            return "Chưa có thông tin";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        return sdf.format(date);
    }

    // Mở cập nhat ca phau thuat
    @FXML
    public void openUpdateDialog() {
        detailSurgeryPane.getScene().getWindow().hide();
        String fxmlPath = "views/surgery_view/create.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            AddSurgeryController addSurgeryController = loader.getController();
            addSurgeryController.setSurgeryController(surgeryController);
            addSurgeryController.setUpdateMode(true);
            addSurgeryController.setSurgeryDetailDTO(surgeryDetailDTO);
            addSurgeryController.initialize(null, null);
            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.setTitle("Cập nhật ca phẫu thuật");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
