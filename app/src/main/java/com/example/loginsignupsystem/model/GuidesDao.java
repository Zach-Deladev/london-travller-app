package com.example.loginsignupsystem.model;

import java.util.List;

public interface GuidesDao {

    long addGuide(Guides guide);

    Guides getGuideById(int id);

    List<Guides> getAllGuides();

    List<Guides> getGuidesByLanguage(String language);
}
