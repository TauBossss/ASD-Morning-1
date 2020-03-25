package com.packagename.myapp;

import com.packagename.myapp.controller.NoteController;
import com.packagename.myapp.entity.Note;
import com.packagename.myapp.notes.NoteInterface;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.util.List;

@Route
@PWA(name = "Notes App",
        shortName = "Notes App",
        description = "Take Notes.",
        enableInstallPrompt = true)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    public MainView(@Autowired PushNotification service, @Autowired NoteInterface noteInterface) {
        addInput(service, noteInterface);
        addNotes(noteInterface);
    }

    private void addInput(PushNotification service, NoteInterface noteInterface) {
        // Use TextField for standard text input
        TextArea textArea = new TextArea();
        textArea.setPlaceholder("Write here...");
        textArea.getStyle().set("minHeight,", "1000px");
        textArea.getStyle().set("minWidth", "300px");

        TextField textField_filename = new TextField("Enter name of your note:");
/*
        Button button_save = new Button("Save note",
                e -> Notification.show(service.save(textField_filename.getValue(), textArea.getValue())));
*/

        Button button_save = new Button("Save note",
                e -> saveToDatabase(textField_filename.getValue(), textArea.getValue(), noteInterface));

        button_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");

        add(textField_filename, button_save, textArea);
    }

    /**
     * Loads the List with all notes.
     */
    private void addNotes(NoteInterface noteInterface) {

        List<Note> notes = noteInterface.findAll();

        notes.forEach(this::Note);

    }

    private void Note(Note note) {
        //this will be displayed;

        TextArea textArea = new TextArea(note.getTitle_());
        textArea.setPlaceholder(note.getText_());
        textArea.getStyle().set("minHeight,", "1000px");
        textArea.getStyle().set("minWidth", "300px");

        add(textArea);
    }

    public void saveToDatabase(String filename, String text, NoteInterface notes)
    {
        Note note = new Note();
        note.setTitle_(filename);
        note.setText_(text);

        notes.save(note);


    }
}
