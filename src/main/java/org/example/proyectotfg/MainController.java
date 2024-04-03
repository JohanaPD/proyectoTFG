package org.example.proyectotfg;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.proyectotfg.excepciones.ThereIsNoView;
import org.example.proyectotfg.mediadores.Mediator;
import org.example.proyectotfg.mediadores.MediatorAcceso;
import org.example.proyectotfg.mediadores.MediatorProfile;
import org.example.proyectotfg.mediadores.ViewController;

import java.io.IOException;
import java.net.URL;

public class MainController implements Mediator, MediatorAcceso, MediatorProfile{

    private Stage mainStage;
    private Mediator mediatorAplicado;

    public MainController(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setMediator(Mediator mediator) {
        this.mediatorAplicado = mediator;
    }

    /*   ================================================================================================
        ======================================vista login =====================================================*/

    @Override
    public void openLogin() throws ThereIsNoView {
        try {
            mainStage.setTitle("Bienvenidos!!");
            //loadView("entradaView.fxml");
            loadView("/org/example/proyectotfg/login-general.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("La vista no existe o no se pudo cargar: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error desconocido: " + e.getMessage(), e);
        }
    }

 /*   ================================================================================================
        ====================================CARGAR TODOS LOS SCENE ==============================*/

    private void loadView(String s) throws IOException, ThereIsNoView {
      try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
     /*   URL fxmlUrl = getClass().getResource("/org/example/proyectotfg/" +s);
        System.out.println(fxmlUrl);*/
          System.out.println("Desde el maincontroller " + loader.getLocation());
  /*          Parent root = loader.load();
            ViewController controller = loader.getController();
            if (controller != null) {
                controller.setMediator(mediatorAplicado);
            } else {
                throw new IOException("El controlador para " + s + " no se pudo inicializar.");
            }
            mainStage.setScene(new Scene(root));
            mainStage.show();*/

          Parent root = loader.load();
          ViewController controller = loader.getController();
          controller.setMediator(mediatorAplicado);
          mainStage.setScene(new Scene(root));
          mainStage.show();
      } catch (Exception e) {
              e.printStackTrace(); // Esto imprimirá el error específico en la consola
              throw new ThereIsNoView("La vista no existe o no se pudo cargar: " + e.getMessage());
          }
    }

}
