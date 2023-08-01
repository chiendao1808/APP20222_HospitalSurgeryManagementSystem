package com.app20222.app20222_fxapp.app_controllers.profile_view;

import com.app20222.app20222_fxapp.dto.responses.users.ProfileUserDTO;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.users.UserAPIService;
import com.app20222.app20222_fxapp.utils.DateUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ProfileController  {

    @FXML
    private Text profileUser;
    @FXML
    private Text profileUserAddress;
    @FXML
    private Text profileUserBirthDate;
    @FXML
    private Text profileUserCode;
    @FXML
    private Text profileUserDepartment;
    @FXML
    private Text profileUserEmail;
    @FXML
    private Text profileUserIdentificationNum;
    @FXML
    private Text profileUserIdentityType;
    @FXML
    private Text profileUserName;
    @FXML
    private Text profileUserPhone;
    @FXML
    private Text profileUserRole;
    @FXML
    private Text profileUserId;

    private UserAPIService userAPIService;

    public ProfileController(Text profileUser, Text profileUserId, Text profileUserCode, Text profileUserName,
                             Text profileUserIdentityType, Text profileUserIdentificationNum, Text profileUserBirthDate,
                             Text profileUserEmail, Text profileUserPhone, Text profileUserAddress,
                             Text profileUserDepartment, Text profileUserRole) {
        this.profileUser = profileUser;
        this.profileUserId = profileUserId;
        this.profileUserCode = profileUserCode;
        this.profileUserName = profileUserName;
        this.profileUserIdentityType = profileUserIdentityType;
        this.profileUserIdentificationNum = profileUserIdentificationNum;
        this.profileUserBirthDate = profileUserBirthDate;
        this.profileUserEmail = profileUserEmail;
        this.profileUserPhone = profileUserPhone;
        this.profileUserAddress = profileUserAddress;
        this.profileUserDepartment = profileUserDepartment;
        this.profileUserRole = profileUserRole;
    }

    public void initializeProfile() {
        userAPIService = new UserAPIService();
        initializeInformation();
    }

    public void initializeInformation() {
        ProfileUserDTO profile = getDataFromDataSource();
        if (profile != null) {
            profileUserId.setText(profile.getId() != null ? String.valueOf(profile.getId()) : "Chưa có thông tin");
            profileUserCode.setText(profile.getCode() != null ? profile.getCode() : "Chưa có thông tin");
            profileUserName.setText(profile.getName() != null ? profile.getName() : "Chưa có thông tin");
            profileUserIdentityType.setText(profile.getIdentityType() != null ? profile.getIdentityType() : "Chưa có thông tin");
            profileUserIdentificationNum.setText(profile.getIdentificationNum() != null ? profile.getIdentificationNum() : "Chưa có thông tin");
            profileUserBirthDate.setText(profile.getBirthDate() != null ? String.valueOf(profile.getBirthDate()) : "Chưa có thông tin");
            profileUserEmail.setText(profile.getEmail() != null ? profile.getEmail() : "Chưa có thông tin");
            profileUserPhone.setText(profile.getPhoneNumber() != null ? profile.getPhoneNumber() : "Chưa có thông tin");
            profileUserAddress.setText(profile.getAddress() != null ? profile.getAddress() : "Chưa có thông tin");
            profileUser.setText(profile.getUserName() != null ? profile.getUserName() : "Chưa có thông tin");
            profileUserDepartment.setText(profile.getDepartment() != null ? profile.getDepartment() : "Chưa có thông tin");
            profileUserRole.setText(profile.getRole() != null ? profile.getRole() : "Chưa có thông tin");
        } else {
            // If profile is null, set all Text elements to "Chưa có thông tin"
            profileUserId.setText("Chưa có thông tin");
            profileUserCode.setText("Chưa có thông tin");
            profileUserName.setText("Chưa có thông tin");
            profileUserIdentityType.setText("Chưa có thông tin");
            profileUserIdentificationNum.setText("Chưa có thông tin");
            profileUserBirthDate.setText("Chưa có thông tin");
            profileUserEmail.setText("Chưa có thông tin");
            profileUserPhone.setText("Chưa có thông tin");
            profileUserAddress.setText("Chưa có thông tin");
            profileUser.setText("Chưa có thông tin");
            profileUserDepartment.setText("Chưa có thông tin");
            profileUserRole.setText("Chưa có thông tin");
        }
    }


    // Get the user profile from the data source
    private ProfileUserDTO getDataFromDataSource() {
        try {
            return userAPIService.getCurrentUserProfile();
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception.getExceptionResponse());
            return null;
        }
    }

  
}
