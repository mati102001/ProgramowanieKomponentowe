package pl.comp.viewproject;

import java.util.ListResourceBundle;

public class AuthorsBundle extends ListResourceBundle {

    private final Object[][] resources = {
            {"autor_1", "Mateusz Kaczmarski"}, {"autor_2", "Krzysztof Izydorczyk"}
    };

    @Override
    protected Object[][] getContents() {
        return resources;
    }
}

