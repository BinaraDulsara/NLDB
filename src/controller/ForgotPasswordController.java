package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import service.EmailConnector;
import service.EmailSender;
import service.ProgessBar;
import to.DTOTransfer;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ForgotPasswordController extends DialogPane implements Initializable {
    public AnchorPane AddPorkContext;
    public JFXTextField txtTextOTP;
    public Text txtOTPVerification;
    public JFXTextField txtNewPassword;
    public int progressCollector;
    public Text txtLoadingText;
    public ProgressBar progressInd;
    public JFXTextField txtReEnterPassword;
    EmailSender emailSender;
    LoadingScreen loadingScreen;
    String OTP;
    String password;

    public ForgotPasswordController() {

    }

    public ForgotPasswordController(String OTP) {
        this.OTP = OTP;
    }

    public void checkOTP(){

        OTP= EmailSender.OTP;
        String enOTP=txtTextOTP.getText();
        System.out.println(OTP+" Check");
        System.out.println(txtTextOTP.getText());
        loadingScreen = new ForgotPasswordController.LoadingScreen(progressInd, txtLoadingText);
        startProgress();
        System.out.println(progressCollector);



    }

    public void btnCheckOnAction(ActionEvent actionEvent) {
        checkOTP();
    }

    public void btnChangePassword(ActionEvent actionEvent) {
        if (txtNewPassword.getText().toString().equals(txtReEnterPassword.getText().toString())){
            password=txtNewPassword.getText().toString();
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION,"DO YOU WANT TO CHANGE PASSWORD",ButtonType.YES,ButtonType.NO).showAndWait();
            if (buttonType.get()==ButtonType.YES){
                LoginFormController loginFormController=new LoginFormController();
                loginFormController.setPassword(password);
            }if (buttonType.get()==ButtonType.NO){
                txtNewPassword.setText(null);
                txtReEnterPassword.setText(null);
            }

        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"PASSWORD IS INCORRECT").show();
            txtNewPassword.setText(null);
            txtReEnterPassword.setText(null);
        }
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    void startProgress() {
        Thread thread = new Thread(loadingScreen);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtNewPassword.setDisable(true);
        txtReEnterPassword.setDisable(true);
    }

    public class LoadingScreen implements Runnable {
        ProgressBar prbProgressBar;
        Text txtloadingText;
        public LoadingScreen(ProgressBar prbProgressBar, Text txtloadingText) {
            this.prbProgressBar = prbProgressBar;
            this.txtloadingText = txtLoadingText;
        }

        @Override
        public void run() {
            for(int i=1; prbProgressBar.getProgress() <= 1; i++) {
                int k= i;
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        prbProgressBar.setProgress(prbProgressBar.getProgress() +0.01);
                        txtLoadingText.setText(k+"%...");
                    }
                });

                synchronized (this){
                    try{
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                }
            }
            txtLoadingText.setText("100%...");
            String enOTP=txtTextOTP.getText();
            if (OTP.equals(enOTP)) {
                txtTextOTP.clear();
                txtOTPVerification.setText("OTP is correct");
                txtNewPassword.setDisable(false);
                txtReEnterPassword.setDisable(false);

            } else {
                txtTextOTP.clear();
                txtOTPVerification.setText("OTP is incorrect");
            }
            progressCollector=100;
        }
    }
}

