package org.image;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageFx {

    public static Image convertToJavaFXImage(byte[] raw, final int width, final int height) {
        WritableImage image = new WritableImage(width, height);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(raw);
            BufferedImage read = ImageIO.read(bis);
            image = SwingFXUtils.toFXImage(read, null);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return image;
    }

    public static byte[] getByteArrayImage(Image image) throws IOException {

        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image,null);
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage,"png",s);
        byte[] data = s.toByteArray();
        s.close();
        return data;
    }

    public void addImage(TextField textField, ImageView imageView) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG/PNG Files","*jpg","*png"));
        File file = fileChooser.showOpenDialog(null);

        if(file!=null){
            textField.setText(file.getAbsolutePath());

            String imageUrl;
            imageUrl = file.toURI().toURL().toExternalForm();
            Image image = new Image(imageUrl);
            imageView.setImage(image);
        }
    }

    public static byte[] getimage(TextField textField) {
        if (textField.getText().equals("")) {
            return null;
        }else{
            Path path = Paths.get(textField.getText());
            byte[] data = null;
            try {
                data = Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

    }



}