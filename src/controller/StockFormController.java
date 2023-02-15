package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.CustomerModel;
import model.ItemModel;
import model.StockModel;
import org.bytedeco.javacpp.presets.opencv_core;
import tm.ItemTM;
import to.CustomerTo;
import to.StockTo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class StockFormController {

    public AnchorPane AddMedicineContext;
    public AnchorPane AddFoodContext;
    public JFXComboBox stockType;
    public JFXTextField txtName;
    public JFXTextField txtQty;
    public JFXTextField txtId;
    public JFXTextField txtPrice;
    public TableView<ItemTM> tblItem;
    public TableColumn<ItemTM, String> colId;
    public TableColumn<ItemTM, String> colName;
    public TableColumn<ItemTM, String> colType;
    public TableColumn<ItemTM, String> colQty;
    public TableColumn<ItemTM, String> colPrice;
    private final ObservableList<ItemTM>itemTMObservableList=FXCollections.observableArrayList();

    public void  initialize(){
        setCellFactory();
        refreshTable();

        String [] types = {"FOOD","MEDICINE"};
        ObservableList<String> types2 = FXCollections.observableArrayList(types);

        stockType.setItems(types2);

        AddFoodContext.setTranslateX(1700);
        AddMedicineContext.setTranslateX(1700);


    }

    private void refreshTable() {
        itemTMObservableList.clear();
        try {
            itemTMObservableList.addAll(ItemModel.getAll().stream().map(i->new ItemTM(i.getId(),i.getName(),i.getType(),i.getQty(),i.getPrice())).collect(Collectors.toList()));
        } catch (SQLException | ClassNotFoundException e) {
            // mokakhari wenna dapan
        }
    }

    private void setCellFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public StockTo makeStock(){
        String id = txtId.getText();
        String name = txtName.getText();
       SingleSelectionModel type = stockType.getSelectionModel();
        int qty = Integer.parseInt(txtQty.getText());
        double price =  Double.parseDouble(txtPrice.getText());


        return new StockTo(id,name,type,qty,price);

    }

    public void updateStock(){
       StockTo stock = makeStock();

        try {
            boolean b = StockModel.updateStock(stock);
            if(b){
                new Alert(Alert.AlertType.INFORMATION,"Updated").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void deleteCustomer(){
      StockTo stock  = makeStock();
        //Confirmation AlERT START
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do You Want To Delete " + stock.getName()
                + " From The System...This Cannot Be Recover", ButtonType.YES, ButtonType.NO).showAndWait();
        boolean no = buttonType.get().getText().equalsIgnoreCase("NO");
        if(no){
            new Alert(Alert.AlertType.WARNING,"Customer Not Deleted").show();
            return;
        }
        //Confirmation AlERT END
        try {
            //Send Data To Database Start
            boolean b = StockModel.deleteStock(stock);
            //Send Data To Database END
            if(b){
                new Alert(Alert.AlertType.INFORMATION,"Stock Delete Success").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Stock Not Deleted").show();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void btnUpdateMed(ActionEvent actionEvent) {
    }

    public void btnSaveMed(ActionEvent actionEvent) {
        AddMedicineContext.setTranslateX(1400);

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
        slide.setNode(AddFoodContext);

        slide.setToX(0);
        slide.play();

        AddMedicineContext.setTranslateX(-176);
        slide.setOnFinished((ActionEvent e)-> {

        });

    }

    public void btnDeleteMed(ActionEvent actionEvent) {
    }

    public void btnCancelMed(ActionEvent actionEvent) {
        AddMedicineContext.setTranslateX(1400);
    }

    public void btnUpdateFood(ActionEvent actionEvent) {
    }

    public void btnSaveFood(ActionEvent actionEvent) {
       AddFoodContext.setTranslateX(1400);

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
        slide.setNode( AddMedicineContext);

        slide.setToX(0);
        slide.play();

        AddFoodContext.setTranslateX(-176);
        slide.setOnFinished((ActionEvent e)-> {

        });
        String id = txtId.getText();
        String name = txtName.getText();
        String type = stockType.getSelectionModel().getSelectedItem().toString();
        int qty = Integer.parseInt(txtQty.getText());
        double price = Double.parseDouble(txtPrice.getText());
        StockTo stock = new StockTo(id,name,type,qty,price);

        try {
            boolean b = StockModel.addStock(stock);
            if(b){
                new Alert(Alert.AlertType.INFORMATION,"Success").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Failed").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void btnDeleteFood(ActionEvent actionEvent) {
    }

    public void btnCancelFood(ActionEvent actionEvent) {
        AddFoodContext.setTranslateX(1400);
    }

    public void btnAddMedicine(ActionEvent actionEvent) {

        AddFoodContext.setTranslateX(1700);

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
        slide.setNode(AddMedicineContext);

        slide.setToX(0);
        slide.play();

        AddMedicineContext.setTranslateX(-176);
        slide.setOnFinished((ActionEvent e)-> {

        });
    }

    public void btnAddFood(ActionEvent actionEvent) {
        AddMedicineContext.setTranslateX(1700);

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
        slide.setNode(AddFoodContext);

        slide.setToX(0);
        slide.play();

        AddFoodContext.setTranslateX(-176);
        slide.setOnFinished((ActionEvent e)-> {

        });
    }

    public void priceOnAction(MouseEvent mouseEvent) {

    }
}
