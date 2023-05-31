package com.stkpush.ncba.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

public class Cipher {

    private Logger logger = LoggerFactory.getLogger(Cipher.class);
    /**
     * Encrypt data using AES Cipher (CBC) with 128 bit key
     *
     *
     * @param key  - key to use should be 16 bytes long (128 bits)
     * @param iv - initialization vector
     * @param data - data to encrypt
     * @return encryptedData data in base64 encoding with iv attached at end after a :
     */

    /**
     * Decrypt data using AES Cipher (CBC) with 128 bit key
     *
     * @param key  - key to use should be 16 bytes long (128 bits)
     * @param data - encrypted data with iv at the end separate by :
     * @return decrypted data string
     */

    public String EncryptDecrypt(String action, String data) throws IOException {
        String CIPHER_NAME = "AES/CBC/PKCS5PADDING";
        int CIPHER_KEY_LEN = 16; //128 bits
        String key = "0@12!45KG$9qwer@1";
        try {
            if (Objects.equals(action, "encrypt")) {
                String iv = "f@d?b!LK4526&23R";
                try {
                    if (key.length() < CIPHER_KEY_LEN) {
                        int numPad = CIPHER_KEY_LEN - key.length();

                        for (int i = 0; i < numPad; i++) {
                            key += "0"; //0 pad to len 16 bytes
                        }

                    } else if (key.length() > CIPHER_KEY_LEN) {
                        key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
                    }

                    IvParameterSpec initVector = new IvParameterSpec(iv.getBytes("ISO-8859-1"));
                    SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ISO-8859-1"), "AES");

                    javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_NAME);
                    cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, skeySpec, initVector);

                    byte[] encryptedData = cipher.doFinal((data.getBytes()));

                    String base64_EncryptedData = Base64.getEncoder().encodeToString(encryptedData);
                    String base64_IV = Base64.getEncoder().encodeToString(iv.getBytes("ISO-8859-1"));

                    return base64_EncryptedData + ":" + base64_IV;

                } catch (Exception e) {
                    logger.error("AN ERROR OCCURRED WHILE ENCRYPTING OR DECRYPTING DATA<====>" + e.getMessage());
                }

                return null;
            }
            if (Objects.equals(action, "decrypt")) {
                try {
                    if (key.length() < CIPHER_KEY_LEN) {
                        int numPad = CIPHER_KEY_LEN - key.length();

                        for (int i = 0; i < numPad; i++) {
                            key += "0"; //0 pad to len 16 bytes
                        }

                    } else if (key.length() > CIPHER_KEY_LEN) {
                        key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
                    }
                    String[] parts = data.split(":");
                    IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[1]));
                    SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ISO-8859-1"), "AES");

                    javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_NAME);
                    cipher.init(javax.crypto.Cipher.DECRYPT_MODE, skeySpec, iv);
                    byte[] decodedEncryptedData = Base64.getDecoder().decode(parts[0]);
                    byte[] original = cipher.doFinal(decodedEncryptedData);
                    return new String(original);
                } catch (Exception e) {
                    logger.error("AN ERROR OCCURRED WHILE ENCRYPTING OR DECRYPTING DATA<====>" + e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error("AN ERROR OCCURRED WHILE ENCRYPTING OR DECRYPTING DATA<====>" + e.getMessage());
        }

        return null;

    }


}