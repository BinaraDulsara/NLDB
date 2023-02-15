package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.ItemModel;
import tm.ItemTM;
import to.ItemTo;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

public class StockFormController {

    public AnchorPane AddMedicineContext;
    public AnchorPane AddFoodContext;
    public TableView<ItemTM> tblItem;
    public TableColumn<ItemTM, String> colId;
    public TableColumn<ItemTM, String> colName;
    public TableColumn<ItemTM, String> colType;
    public TableColumn<ItemTM, String> colQty;
    public TableColumn<ItemTM, String> colPrice;
    private final ObservableList<ItemTM>itemTMObservableList=FXCollections.observableArrayList();
    public JFXTextField txtMedicineName;
    public JFXTextField txtMedicineQty;
    public JFXTextField txtMedicineId;
    public JFXButton btnMedicineUpdate;
    public JFXButton btnMedicineSave;
    public JFXButton btnMedicineDelete;
    public JFXButton btnMedicineCancel;
    public JFXTextField txtMedicinePrice;
    public JFXTextField txtFoodName;
    public JFXTextField txtFoodQty;
    public JFXTextField txtFoodId;
    public JFXButton btnFoodUpdate;
    public JFXButton btnFoodSave;
    public JFXButton btnFoodDelete;
    public JFXButton btnFoodCansel;
    public JFXTextField txtFoodPrice;

    public void  initialize(){
        setCellFactory();
        refreshTable();

        AddFoodContext.setTranslateX(1700);
        AddMedicineContext.setTranslateX(1700);
        AddFoodContext.setVisible(false);
        AddMedicineContext.setVisible(false);

    }

    private void refreshTable() {
        itemTMObservableList.clear();
        try {
            itemTMObservableList.addAll(ItemModel.getAll().stream().map(i->new ItemTM(i.getId(),i.getName(),i.getType(),i.getQty(),i.getPrice())).collect(Collectors.toList()));
            tblItem.setItems(itemTMObservableList);
            tblItem.getSelectionModel().clearSelection();;
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,e.getMessage()).show();
        }
    }

    private void setCellFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    public void btnAddMedicine(ActionEvent actionEvent) {

        AddFoodContext.setTranslateX(1700);
        AddFoodContext.setVisible(false);

        AddMedicineContext.setVisible(true);

        resetMedicine();
        btnMedicineSave.setDisable(false);
        btnMedicineUpdate.setDisable(true);
        btnMedicineDelete.setDisable(true);

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
        slide.setNode(AddMedicineContext);

        slide.setToX(0);
        slide.play();

        AddMedicineContext.setTranslateX(-176);
        slide.setOnFinished((ActionEvent e)-> {

        });
    }

    private void resetMedicine(){
        try {
            txtMedicineId.setText(ItemModel.getNextId());
            txtMedicineName.setText(null);
            txtMedicineQty.setText(null);
            txtMedicinePrice.setText(null);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,e.getMessage()).show();
        }
    }

    private void resetFood() {
        try {
            txtFoodId.setText(ItemModel.getNextId());
            txtFoodName.setText(null);
            txtFoodQty.setText(null);
            txtFoodPrice.setText(null);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,e.getMessage()).show();
        }
    }

    public void btnAddFood(ActionEvent actionEvent) {
        AddMedicineContext.setTranslateX(1700);
        AddMedicineContext.setVisible(false);

        AddFoodContext.setVisible(true);

        resetFood();

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
        slide.setNode(AddFoodContext);

        slide.setToX(0);
        slide.play();

        AddFoodContext.setTranslateX(-176);
        slide.setOnFinished((ActionEvent e)-> {

        });
    }


    public void btnMedicineCancelOnAction(ActionEvent actionEvent) {
        AddMedicineContext.setTranslateX(1400);
        AddMedicineContext.setVisible(false);
    }

    public void btnMedicineDeleteOnAction(ActionEvent actionEvent) {
        String id = txtMedicineId.getText();
        Optional<ButtonType> optional = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?").showAndWait();
        if (!optional.isPresent())
            return;
        if (optional.get()==ButtonType.OK){
            try {
                if (ItemModel.delete(id)) {
                    btnMedicineCancelOnAction(actionEvent);
                    refreshTable();
                    new Alert(Alert.AlertType.WARNING, "Food Deleted").show();
                } else
                    throw new SQLException("Fail to delete food !");
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
            }
        }

    }

    public void btnMedicineUpdateOnACtion(ActionEvent actionEvent) {
        String id = txtMedicineId.getText();
        String name = txtMedicineName.getText();
        String type = "MEDICINE";
        int qty = Integer.parseInt(txtMedicineQty.getText());
        double price = Double.parseDouble(txtMedicinePrice.getText());
        ItemTo itemTo=new ItemTo(id,name,qty,price,type);
        try {
            if(ItemModel.update(itemTo)){
                refreshTable();
                btnMedicineCancelOnAction(actionEvent);
                new Alert(Alert.AlertType.INFORMATION,"Medicine updated !").show();
            }else
                throw new SQLException("Medicine update fail !");
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,e.getMessage()).show();
        }
    }

    public void btnMedicineSaveOnAction(ActionEvent actionEvent) {
        String id = txtMedicineId.getText();
        String name = txtMedicineName.getText();
        String type = "MEDICINE";
        int qty = Integer.parseInt(txtMedicineQty.getText());
        double price = Double.parseDouble(txtMedicinePrice.getText());
        ItemTo itemTo=new ItemTo(id,name,qty,price,type);
        try {
            if(ItemModel.save(itemTo)){
                refreshTable();
                resetMedicine();
                new Alert(Alert.AlertType.INFORMATION,"Medicine saved !").show();
            }else
                throw new SQLException("Medicine save fail !");
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,e.getMessage()).show();
        }
    }

    public void tblClick(MouseEvent mouseEvent) {
        if (tblItem.getSelectionModel()==null || tblItem.getSelectionModel().isEmpty()){
            return;
        }
        ItemTM itemTM = tblItem.getSelectionModel().getSelectedItem();
        if (itemTM.getType().equals("MEDICINE")){

            AddFoodContext.setVisible(false);

            txtMedicineId.setText(itemTM.getId());
            txtMedicineName.setText(itemTM.getName());
            txtMedicineQty.setText(String.valueOf(itemTM.getQty()));
            txtMedicinePrice.setText(String.valueOf(itemTM.getPrice()));

            btnMedicineSave.setDisable(true);
            btnMedicineUpdate.setDisable(false);
            btnMedicineDelete.setDisable(false);

            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.2));
            slide.setNode(AddMedicineContext);

            slide.setToX(0);
            slide.play();

            AddMedicineContext.setTranslateX(-176);
            slide.setOnFinished((ActionEvent e)-> {

            });

        }else if (itemTM.getType().equals("FOOD")){
            AddMedicineContext.setVisible(false);

            txtFoodId.setText(itemTM.getId());
            txtFoodName.setText(itemTM.getName());
            txtFoodQty.setText(String.valueOf(itemTM.getQty()));
            txtFoodPrice.setText(String.valueOf(itemTM.getPrice()));

            btnFoodSave.setDisable(true);
            btnFoodUpdate.setDisable(false);
            btnFoodDelete.setDisable(false);

            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.2));
            slide.setNode(AddFoodContext);

            slide.setToX(0);
            slide.play();

            AddFoodContext.setTranslateX(-176);
            slide.setOnFinished((ActionEvent e)-> {

            });
        }
    }

    public void btnFoodUpdateOnAction(ActionEvent actionEvent) {
        String id = txtFoodId.getText();
        String name = txtFoodName.getText();
        String type = "FOOD";
        int qty = Integer.parseInt(txtFoodQty.getText());
        double price = Double.parseDouble(txtFoodPrice.getText());
        ItemTo itemTo=new ItemTo(id,name,qty,price,type);
        try {
            if(ItemModel.update(itemTo)){
                refreshTable();
                btnFoodCanselOnACtion(actionEvent);
                new Alert(Alert.AlertType.INFORMATION,"Food updated !").show();
            }else
                throw new SQLException("Food update fail !");
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,e.getMessage()).show();
        }
    }

    public void btnFoodSaveOnAction(ActionEvent actionEvent) {
        String id = txtFoodId.getText();
        String name = txtFoodName.getText();
        String type = "FOOD";
        int qty = Integer.parseInt(txtFoodQty.getText());
        double price = Double.parseDouble(txtFoodPrice.getText());
        ItemTo itemTo=new ItemTo(id,name,qty,price,type);
        try {
            if(ItemModel.save(itemTo)){
                refreshTable();
                resetFood();
                new Alert(Alert.AlertType.INFORMATION,"Food saved !").show();
            }else
                throw new SQLException("Food save fail !");
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,e.getMessage()).show();
        }
    }

    public void btnFoodDeleteOnACtion(ActionEvent actionEvent) {
        String id = txtFoodId.getText();
        Optional<ButtonType> optional = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?").showAndWait();
        if (!optional.isPresent())
            return;
        if (optional.get()==ButtonType.OK){
            try {
                if (ItemModel.delete(id)) {
                    btnFoodCanselOnACtion(actionEvent);
                    refreshTable();
                    new Alert(Alert.AlertType.WARNING, "Food Deleted").show();
                } else
                    throw new SQLException("Fail to delete food !");
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
            }
        }
    }

    public void btnFoodCanselOnACtion(ActionEvent actionEvent) {
        AddFoodContext.setTranslateX(1400);
        AddFoodContext.setVisible(false);
    }
}
