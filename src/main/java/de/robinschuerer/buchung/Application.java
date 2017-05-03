package de.robinschuerer.buchung;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import de.robinschuerer.buchung.ui.UiView;

@SpringBootApplication
public class Application extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launchApp(
            Application.class,
            UiView.class,
            args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
