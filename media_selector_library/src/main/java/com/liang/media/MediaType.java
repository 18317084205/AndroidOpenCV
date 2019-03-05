package com.liang.media;

import android.support.v4.util.ArraySet;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public enum MediaType {
    // ============== images ==============
    JPEG("image/jpeg", arraySetOf(
            "jpg",
            "jpeg"
    )),
    PNG("image/png", arraySetOf(
            "png"
    )),
    GIF("image/gif", arraySetOf(
            "gif"
    )),
    BMP("image/x-ms-bmp", arraySetOf(
            "bmp"
    )),
    WEBP("image/webp", arraySetOf(
            "webp"
    )),

    // ============== audio ==============
    MP3("audio/mpeg", arraySetOf(
            "mp3",
            "mp3"
    )),
    WAV("audio/x-wav", arraySetOf(
            "x-wav",
            "wav"
    )),
    AMR("audio/amr", arraySetOf(
            "amr"
    )),
    MIDI("audio/midi", arraySetOf(
            "midi",
            "mid"
    )),
    FLAC("audio/flac", arraySetOf(
            "flac"
    )),

    // ============== videos ==============
    MPEG("video/mpeg", arraySetOf(
            "mpeg",
            "mpg"
    )),
    MP4("video/mp4", arraySetOf(
            "mp4",
            "m4v"
    )),
    QUICKTIME("video/quicktime", arraySetOf(
            "mov"
    )),
    THREEGPP("video/3gpp", arraySetOf(
            "3gp",
            "3gpp"
    )),
    THREEGPP2("video/3gpp2", arraySetOf(
            "3g2",
            "3gpp2"
    )),
    MKV("video/x-matroska", arraySetOf(
            "mkv"
    )),
    WEBM("video/webm", arraySetOf(
            "webm"
    )),
    TS("video/mp2ts", arraySetOf(
            "ts"
    )),
    AVI("video/avi", arraySetOf(
            "avi"
    ));

    private final String mMimeTypeName;
    private final Set<String> mExtensions;

    MediaType(String mimeTypeName, Set<String> extensions) {
        mMimeTypeName = mimeTypeName;
        mExtensions = extensions;
    }

    public static Set<MediaType> ofAll() {
        return EnumSet.allOf(MediaType.class);
    }

    public static Set<MediaType> of(MediaType type, MediaType... rest) {
        return EnumSet.of(type, rest);
    }


    public static Set<MediaType> ofImage() {
        return EnumSet.of(JPEG, PNG, GIF, BMP, WEBP);
    }

    public static Set<MediaType> ofAudio() {
        return EnumSet.of(MP3, WAV, MIDI, AMR, FLAC);
    }

    public static Set<MediaType> ofVideo() {
        return EnumSet.of(MPEG, MP4, QUICKTIME, THREEGPP, THREEGPP2, MKV, WEBM, TS, AVI);
    }

    public static Set<MediaType> ofImageAndVideo() {
        Set<MediaType> mediaTypes = ofImage();
        mediaTypes.addAll(ofVideo());
        return mediaTypes;
    }

    public static boolean isImage(String mimeType) {
        if (mimeType == null) return false;
        return mimeType.startsWith("image");
    }

    public static boolean isVideo(String mimeType) {
        if (mimeType == null) return false;
        return mimeType.startsWith("video");
    }

    public static boolean isGif(String mimeType) {
        if (mimeType == null) return false;
        return mimeType.equals(MediaType.GIF.toString());
    }

    private static Set<String> arraySetOf(String... suffixes) {
        return new ArraySet<>(Arrays.asList(suffixes));
    }

    public static boolean isAudio(String mimeType) {
        if (mimeType == null) return false;
        return mimeType.startsWith("audio");
    }

    @Override
    public String toString() {
        return mMimeTypeName + "/" + mExtensions.toArray()[0];
    }

}
