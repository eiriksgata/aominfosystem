package com.aominfosystem.pulg;


public interface MusicPulg {

    public String musicPlay(String parameter, long fromqq);
    public String musicFind(String parameter, long fromqq);
    public String musicPlayList(String paramenter);
    public String musicAdd(String parameter,long fromqq);
    public String musicDelete(String paramenter,long fromqq);
    public String musicDownload(String paramentr,long fromqq);

    public String returnMusicHelpMessage();

}
