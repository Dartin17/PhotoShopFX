package com.example.photoshopfx;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class MainController implements Initializable {
    public ImageView imageView;
    public MenuItem tonsCinza;
    public MenuItem pretoBranco;
    public MenuItem EspelharHorizontal;
    public MenuItem EspelharVertical;
    public MenuItem negativo;
    public MenuItem salvar;
    public MenuItem salvarComo;
    public Button btAbrir;
    public Button btSalvar;
    public Button btEspelharH;
    public Button btEspelharV;
    public Button btDesenhar;
    public Button btSobre;
    private boolean imagemModif = false;
    private boolean desenhar = false;
    private boolean salvo = false;
    private File fileAtual;

    public boolean isImagemModif() {
        return imagemModif;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image;
        image = new Image("file:///D:/cavalo.jpg");
        imageView.setImage(image);
        imageView.setFitWidth(image.getWidth());
        imageView.setFitHeight(image.getHeight());


    }

    public void onFechar(Stage stage) {
        if (!salvo) {
            stage.setOnCloseRequest((e) -> {
                if (this.isImagemModif()) {
                    e.consume();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmação de Saída");
                    alert.setHeaderText("Você tem alterações não salvas!");
                    alert.setContentText("Deseja salvar as alterações antes de sair?");
                    ButtonType btSalvar = new ButtonType("Salvar");
                    ButtonType btNaoSalvar = new ButtonType("Não Salvar");
                    ButtonType btCancelar = new ButtonType("Cancelar");
                    alert.getButtonTypes().setAll(new ButtonType[]{btSalvar, btNaoSalvar, btCancelar});
                    Optional<ButtonType> escolha = alert.showAndWait();
                    if (escolha.get() == btSalvar) {
                        this.onSalvar((ActionEvent) null);
                        stage.close();
                    } else if (escolha.get() == btNaoSalvar) {
                        stage.close();
                    } else {
                        e.consume();
                    }
                }
            });
        }
    }

    public void onAbrir(ActionEvent actionEvent) {
        FileChooser fileChooser;
        fileChooser=new FileChooser();
        fileChooser.setInitialDirectory(new File("c://"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Todas imagens", "*.jpg","*.jpeg","*.png","*.bmp","*.webp")
                ,new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
                ,new FileChooser.ExtensionFilter("PNG", "*.png")
                ,new FileChooser.ExtensionFilter("BMP", "*.bmp")
                ,new FileChooser.ExtensionFilter("GIF","*.gif")
                ,new FileChooser.ExtensionFilter("JPG","*.jpg")
        );
        File file=fileChooser.showOpenDialog(null);
        if(file!=null)
        {
            Image image;
            image=new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(image.getWidth());
            imageView.setFitHeight(image.getHeight());
            fileAtual = file;
            this.tonsCinza.setDisable(false);
            this.pretoBranco.setDisable(false);
            this.EspelharHorizontal.setDisable(false);
            this.EspelharVertical.setDisable(false);
            this.negativo.setDisable(false);
            this.salvar.setDisable(false);
            this.salvarComo.setDisable(false);
            this.btSalvar.setDisable(false);
            this.btEspelharH.setDisable(false);
            this.btEspelharV.setDisable(false);
            this.btDesenhar.setDisable(false);
        }
    }

    public void onSalvar(ActionEvent actionEvent) {
        if (fileAtual != null) {
            try {
                BufferedImage bimg = SwingFXUtils.fromFXImage(imageView.getImage(), null);
                ImageIO.write(bimg, "png", fileAtual);
                imagemModif = false;
                salvo = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public void onSalvarComo(ActionEvent actionEvent) {
        if(fileAtual != null) {
            Stage stage = (Stage) imageView.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[]{
                    new FileChooser.ExtensionFilter("JPEG", new String[]{"*.jpg"})
                    , new FileChooser.ExtensionFilter("PNG", new String[]{"*.png"})
                    , new FileChooser.ExtensionFilter("BMP", new String[]{"*.bmp"})});
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                Image image = this.imageView.getImage();
                BufferedImage bimg = SwingFXUtils.fromFXImage(image, null);

                try {
                    ImageIO.write(bimg, "png", file);
                    imagemModif = false;
                    salvo = true;
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro ao salvar imagem");
                    alert.setHeaderText(null);
                    alert.setContentText("Não foi possível salvar a imagem.");
                    alert.showAndWait();
                }


            }
        }
    }

    public void onSair(ActionEvent actionEvent) {
        if (!salvo) {
            if (this.isImagemModif()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmação de Saída");
                alert.setHeaderText("Você tem alterações não salvas!");
                alert.setContentText("Deseja salvar as alterações antes de sair?");
                ButtonType btSalvar = new ButtonType("Salvar");
                ButtonType btNaoSalvar = new ButtonType("Não Salvar");
                ButtonType btCancelar = new ButtonType("Cancelar");
                alert.getButtonTypes().setAll(new ButtonType[]{btSalvar, btNaoSalvar, btCancelar});
                Optional<ButtonType> opcao = alert.showAndWait();
                if (opcao.isPresent()) {
                    if (opcao.get() == btSalvar)
                        this.onSalvar(null);
                    Platform.exit();

                }
            }
        }
        else
            Platform.exit();
    }

    public void onTonsCinza(ActionEvent actionEvent) {
        Image image = imageView.getImage();
        image = Conversora.tonsCinza(image);
        imageView.setImage(image);
        imagemModif = true;
        salvo = false;
    }

    public void onPretoBranco(ActionEvent actionEvent) {
        Image image = imageView.getImage();
        image = Conversora.pretoBranco(image);
        imageView.setImage(image);
        imagemModif = true;
        salvo = false;
    }

    public void onEspelharHorizontal(ActionEvent actionEvent) {
        Image image = imageView.getImage();
        image = Conversora.espelharHorizontal(image);
        imageView.setImage(image);
        imagemModif = true;
        salvo = false;
    }

    public void onEspelharVertical(ActionEvent actionEvent) {
        Image image = imageView.getImage();
        image = Conversora.espelharVertical(image);
        imageView.setImage(image);
        imagemModif = true;
        salvo = false;
    }

    public void onNegativo(ActionEvent actionEvent) {
        Image image = imageView.getImage();
        image = Conversora.negativo(image);
        imageView.setImage(image);
        imagemModif = true;
        salvo = false;
    }

    public void onSobre(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sobre PhotoShopFX");
        alert.setHeaderText("PhotoShopFX 1.0");
        alert.setContentText("Programa para edição de imagens\n\nDesenvolvido por Eduardo Pereira de Almeida");
        alert.showAndWait();
    }

    public void onDetectarBordas(ActionEvent actionEvent) {
        Image image = imageView.getImage();
        image = Conversora.detectarBordasIJ(image);
        imageView.setImage(image);
        imagemModif = true;
        salvo = false;
    }

    public void onErosao(ActionEvent actionEvent) {
        Image image = imageView.getImage();
        image = Conversora.erosaoIJ(image);
        imageView.setImage(image);
        imagemModif = true;
        salvo = false;
    }

    public void onGaussianBlur(ActionEvent actionEvent) {
        Image image = imageView.getImage();
        image = Conversora.gaussianBlurIJ(image);
        imageView.setImage(image);
        imagemModif = true;
        salvo = false;
    }

    public void onDilatar(ActionEvent actionEvent) {
        Image image = imageView.getImage();
        image = Conversora.dilatarIJ(image);
        imageView.setImage(image);
        imagemModif = true;
        salvo = false;
    }

    public void desenho(){
        Image image = imageView.getImage();
        BufferedImage bimg = SwingFXUtils.fromFXImage(image,null);
        Graphics2D graphics2D = bimg.createGraphics();
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(3));
        AtomicReference<Double> lastX = new AtomicReference<>((double) 0);
        AtomicReference<Double> lastY = new AtomicReference<>((double) 0);

        imageView.setOnMousePressed(evento ->{
            lastX.set(evento.getX());
            lastY.set(evento.getY());
        });

        imageView.setOnMouseDragged(evento ->{
            double y = evento.getY();
            double x = evento.getX();

            if(x >= 0 && x < image.getHeight() && y>=0 && y < image.getWidth()){
                graphics2D.drawLine(lastX.get().intValue(), lastY.get().intValue(), (int) x, (int) y);

                lastX.set(x);
                lastY.set(y);

                imageView.setImage(SwingFXUtils.toFXImage(bimg, null));

            }
        });
        imagemModif = true;
        salvo = false;
    }

    public void onDesenhar(ActionEvent actionEvent) {
        desenhar = !desenhar;
        if(desenhar){
            desenho();
        }
        else{
            imageView.setOnMousePressed(null);
            imageView.setOnMouseDragged(null);
        }
    }


}