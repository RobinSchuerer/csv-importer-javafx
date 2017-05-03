package de.robinschuerer.buchung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;

import de.robinschuerer.buchung.domain.Login;

@Service
public class LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private RestTemplate restTemplate;

    public boolean login(final String email, final String password) {

        try {
            final ResponseEntity<ObjectNode> loginResponse = restTemplate.postForEntity("http://accounting.xnoname.com/api/identity/action/login",
                Login
                    .builder()
                    .withEmail(email)
                    .withPassword(password)
                    .build(), ObjectNode.class);

            if(loginResponse.getStatusCode() == HttpStatus.OK){
                LOGGER.info("Verbunden!");
                return true;
            }

            return false;
        }catch (Exception exception){
            LOGGER.info("Fehler beim Verbinden!");
            return false;
        }
    }
}
