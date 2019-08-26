package com.aominfosystem.utils;


import com.aominfosystem.config.CreateSystemFile;
import com.aominfosystem.model.CardGroupFile;

import java.util.ArrayList;
import java.util.List;

public class DrawUtils {

    private List<CardGroupFile> cardList = new ArrayList<CardGroupFile>();

    public DrawUtils() {
        cardList = ImportInfoFromText.readFromText(CreateSystemFile.folderName+ "\\" +"CardGroupDate.txt");
        //排序
        for (int i = 0; i < cardList.size(); i++) {
            for (int j = 0; j < cardList.size(); j++) {
                if (cardList.get(i).getProbability() < cardList.get(i).getProbability()) {
                    CardGroupFile t = new CardGroupFile();
                    t.setDescribe(cardList.get(i).getDescribe());
                    t.setProbability(cardList.get(i).getProbability());

                    cardList.set(i, cardList.get(j));
                    cardList.set(j, t);
                }
            }
        }
    }

    public String drawStart() {
        String result = "";

        double random = Math.random();
        List<CardGroupFile> accordList = new ArrayList<CardGroupFile>();
        for (int i = 0; i < cardList.size(); i++) {
            if (random < cardList.get(i).getProbability()) {
                accordList.add(cardList.get(i));
            }
        }

        if (accordList.size() <= 0) {
            result = "没有抽到任何东西";
        }
        if (accordList.size() == 1) {
            result = accordList.get(0).getDescribe();
        }
        if (accordList.size() > 1) {
            double value = accordList.get(0).getProbability();
            List<CardGroupFile> secondary = new ArrayList<CardGroupFile>();
            for (int i = 0; i < accordList.size(); i++) {
                if (accordList.get(i).getProbability() == value) {
                    secondary.add(accordList.get(i));
                }
            }
            if (secondary.size() > 1) {
                double randomTow = Math.random() * secondary.size();
                result = secondary.get((int) Math.floor(randomTow)).getDescribe();
            }

        }

        return result;
    }
}
