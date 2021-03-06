package me.bokov.tasks.modules.languagechooser;

import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SessionScoped
@Named
@Getter
@Setter
public class LanguageChooserBean implements Serializable {

    private String currentLanguage = Faces.getLocale ().getLanguage ();
    private List<Locale> supportedLocales = new ArrayList<> ();

    @PostConstruct
    public void onInit () {

        currentLanguage = Faces.getLocale ().getLanguage ();
        populateSupportedLocales ();

    }

    private void populateSupportedLocales () {

        supportedLocales.clear ();
        supportedLocales.addAll (Faces.getSupportedLocales ());

    }

    public void onLocaleChanged () {

        for (Locale locale : supportedLocales) {
            if (locale.getLanguage ().equalsIgnoreCase (getCurrentLanguage ())) {
                Faces.setLocale (locale);
                break;
            }
        }

    }

}
