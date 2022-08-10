package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.web.HTMLEditor;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Arrays;

public class TextEditorFormController {
    File file1;
    private File savePath;
    private String readMsg;
    private File destDir;

    private File srcFile;
    private String txtByteBuffer;
    public HTMLEditor txtEditor;
    public MenuItem mnuNew;
    public MenuItem mnuOpen;
    public MenuItem mnuSave;
    public MenuItem mnuPrint;
    public MenuItem mnuClose;
    public MenuItem mnuCut;
    public MenuItem mnuCopy;
    public MenuItem mnuPaste;
    public MenuItem mnuSelectAll;
    public MenuItem mnuAbout;

    public void initialize(){
        mnuNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtEditor.setHtmlText("");

            }
        });
        mnuClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });
        mnuAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    URL resource = this.getClass().getResource("/view/AboutForm.fxml");
                    Parent container = FXMLLoader.load(resource);
                    Scene scene = new Scene(container);

                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("About");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                    stage.centerOnScreen();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void mnuNewOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/TextEditorForm.fxml");
        Parent container = FXMLLoader.load(resource);
        Scene editorScene = new Scene(container);
        Stage primaryStage = new Stage();
        primaryStage.setScene(editorScene);
        primaryStage.setTitle("Simple Java FX Text Editor");
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public void mnuOpenOnAction(ActionEvent actionEvent) throws IOException {
        selectFile();//value assigned to File srcFiles
        readFile(srcFile.toString());//value assign to String readMsg
        writeToEditor();
    }

    public void mnuSaveOnAction(ActionEvent actionEvent) throws IOException {
        selectDirectory();//assign path of the saving directory to File destDir
        file1 = new File(destDir.toString() + "/a.txt");
        writeToFile(file1.toString());
    }

    public void mnuPrintOnAction(ActionEvent actionEvent) {
    }

    public void selectFile() throws IOException {  //open
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Select a file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files (*.*)", "*.*"));


        srcFile = fileChooser.showOpenDialog(txtEditor.getScene().getWindow());
        if (srcFile != null) {
            System.out.println(srcFile.length());
        } else {
            System.out.println(1);
        }
    }

    public void selectDirectory(){ //to save
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a destination folder to save");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        destDir = directoryChooser.showDialog(txtEditor.getScene().getWindow());
    }

    public void readFile(String path) throws IOException { //to write on html editor
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);

        int[] byteBuffer = new int[51];
        for (int i = 0; i < byteBuffer.length; i++) {
            byteBuffer[i] = fis.read();
            System.out.print((char) byteBuffer[i]);
        }
        System.out.println(Arrays.toString(byteBuffer));
        fis.close();
        readMsg = Arrays.toString(byteBuffer);
    }

    public void writeFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }

        String something = txtEditor.getHtmlText();
        something = something.replaceAll("\\<.*?\\>", "");
        byte[] bytes = something.getBytes();

        FileOutputStream fos = new FileOutputStream(file, true);

        for (int i = 0; i < bytes.length; i++) {
            fos.write(bytes[i]);
        }

        fos.close();
    }

    public void writeToFile(String filePath) throws IOException { //when save a file
        writeFile(filePath);
    }

    public void writeToEditor() throws IOException { //when open file
        String something = readMsg;
        byte[] bytes = something.getBytes();

        FileOutputStream fos = new FileOutputStream(file1);

        for (int i = 0; i < bytes.length; i++) {
            fos.write(bytes[i]);
        }

        fos.close();
    }
}
