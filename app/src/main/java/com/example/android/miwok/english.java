package com.example.android.miwok;

public class english {

    private String mEnglishTranslation;

    private String mMiwokTranslation;

    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    private int mAudioResourceId;

    public english (String EnglishTtranslation, String MiwokTranslation, int AudioResourceId) {
        mEnglishTranslation = EnglishTtranslation;
        mMiwokTranslation = MiwokTranslation;
        mAudioResourceId = AudioResourceId;
    }

    public english (String EnglishTtranslation, String MiwokTranslation, int ImageResource, int AudioResourceId) {
        mImageResourceId = ImageResource;
        mEnglishTranslation = EnglishTtranslation;
        mMiwokTranslation = MiwokTranslation;
        mAudioResourceId = AudioResourceId;
    }

    public String getEnglishTranslation (){
        return mEnglishTranslation;
    }

    public String getMiwokTranslation (){
        return mMiwokTranslation;
    }

    public int getImageResourceId () {
        return mImageResourceId;
    }

    public boolean hasImage(){
     return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getAudioResourceId () {
        return mAudioResourceId;
    }
}
