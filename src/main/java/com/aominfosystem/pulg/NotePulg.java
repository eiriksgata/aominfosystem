package com.aominfosystem.pulg;

import org.springframework.stereotype.Component;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.pulg
 * @date:2019/8/26
 **/

public interface NotePulg {
    public String noteControl(String parameter, long fromqq);
    public String deleteNote(String parameter, long fromqq);
    public String openNote(String parameter, long fromqq);
    public String findNoteList(String parameter, long fromqq);
    public String returnNotehelpMessage();

}
