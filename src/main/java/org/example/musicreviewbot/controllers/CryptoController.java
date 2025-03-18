package org.example.musicreviewbot.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

@RestController
@CrossOrigin()
@RequestMapping("/crypto")
public class CryptoController {
    private KeyPair serverKeyPair;
    private String clientPublicKey;

    public CryptoController() {
        generateServerKeys();
    }

    // Генерация ключей сервера
    private void generateServerKeys() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            serverKeyPair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/set-client-public-key")
    public void setClientPublicKey(@RequestBody Map<String, String> payload) {
        clientPublicKey = payload.get("publicKey").replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    @PostMapping("/verify")
    public Map<String, String> verifyMessage(@RequestBody Map<String, String> payload) {
        String signedMessage = payload.get("signedMessage");
        String originalMessage = payload.get("message");

        try {
            // Хэшируем сообщение на сервере
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalMessage.getBytes(StandardCharsets.UTF_8));
            String hashedMessage = bytesToHex(hash);

            // Декодируем публичный ключ
            PublicKey clientKey = KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(clientPublicKey)));

            // Проверяем подпись
            Signature verify = Signature.getInstance("SHA256withRSA");
            verify.initVerify(clientKey);
            verify.update(hashedMessage.getBytes(StandardCharsets.UTF_8)); // Используем хэшированное сообщение
            boolean isVerified = verify.verify(Base64.getDecoder().decode(signedMessage));

            return Map.of("status", isVerified ? "Verified" : "Not Verified");
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Verification failed");
        }
    }

    @GetMapping("/get-server-public-key")
    public Map<String, String> getServerPublicKey() {
        String publicKey = Base64.getEncoder().encodeToString(serverKeyPair.getPublic().getEncoded());
        return Map.of("publicKey", publicKey);
    }




    @GetMapping("/generate-message")
    public Map<String, String> generateMessage() {
        String message = "Random Message from Server";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            String hashedMessage = bytesToHex(hash);
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(serverKeyPair.getPrivate());
            sign.update(hashedMessage.getBytes(StandardCharsets.UTF_8));
            String signedMessage = Base64.getEncoder().encodeToString(sign.sign());
            return Map.of("message", message, "signedMessage", signedMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Failed to generate message");
        }
    }

}
