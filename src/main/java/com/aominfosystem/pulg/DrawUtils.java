package com.aominfosystem.pulg;


import com.aominfosystem.config.CreateSystemFile;
import com.aominfosystem.model.DrawDataFile;
import com.aominfosystem.utils.ImportInfoFromText;

import java.util.ArrayList;
import java.util.List;


public class DrawUtils {

    private static List<DrawDataFile> cardList = new ArrayList<DrawDataFile>();


    public DrawUtils() {
        cardList = ImportInfoFromText.readFromText(CreateSystemFile.folderName+ "\\" +"CardGroupDate.txt");
        //排序
        for (int i = 0; i < cardList.size(); i++) {
            for (int j = 0; j < cardList.size(); j++) {
                if (cardList.get(i).getProbability() < cardList.get(i).getProbability()) {
                    DrawDataFile t = new DrawDataFile();
                    t.setDescribe(cardList.get(i).getDescribe());
                    t.setProbability(cardList.get(i).getProbability());

                    cardList.set(i, cardList.get(j));
                    cardList.set(j, t);
                }
            }
        }
    }

    public static String drawStart() {
        String result = "";

        double random = Math.random();
        List<DrawDataFile> accordList = new ArrayList<DrawDataFile>();
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
            List<DrawDataFile> secondary = new ArrayList<DrawDataFile>();
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
