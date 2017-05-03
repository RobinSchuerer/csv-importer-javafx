package de.robinschuerer.buchung.ui;

import java.io.IOException;
import java.io.OutputStream;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class TextAreaOutputStream extends OutputStream {

        private TextArea textArea;

        public TextAreaOutputStream(TextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {

            if (Platform.isFxApplicationThread()) {
                textArea.appendText(String.valueOf((char) b));
            } else {
                Platform.runLater(() -> textArea.appendText(String.valueOf((char) b)));
            }
        }
    }