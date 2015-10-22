package com.linkallcloud.core.security;

public class Blowfish extends Encryptor {

    public Blowfish(String keySeed) {
        super(Encryptor.ENC_Blowfish, keySeed);
    }

    public Blowfish(String keySeed, String encoding) {
        super(Encryptor.ENC_Blowfish, keySeed, encoding);
    }

}
