package com.packagename.myapp;

import com.packagename.myapp.controller.NoteController;
import com.packagename.myapp.entity.Note;
import com.packagename.myapp.notes.NoteInterface;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.util.List;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = true)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    @Autowired
    NoteInterface noteInterface;

    NoteController noteController = new NoteController();

    public MainView(@Autowired PushNotification service) {

        NotesLayout notesLayout = new NotesLayout();

        List<Note> test = noteController.getNotes();



        // Text area for the note
        TextArea textArea = new TextArea("Note");
        textArea.getStyle().set("minHeight,", "1000px");
        textArea.getStyle().set("minWidth", "300px");
        textArea.setPlaceholder(test.get(0).getText_());

        //String text = textArea.getValue();

        // Use TextField for standard text input
        TextField textField_filename = new TextField("Enter name of your note:");

        Button button_save = new Button("Save note",
                e -> Notification.show(service.save(textField_filename.getValue(), textArea.getValue())));

        button_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        //button_save.addClickShortcut(Key.ENTER);



        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");

        add(textField_filename, button_save, textArea, notesLayout);
    }

}
