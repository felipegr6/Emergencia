package br.com.fgr.emergencia.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Helper {

    private static final String RESCUEE_PREFERENCES = "rescuee_preferences";
    private static final String GCM_REGID = "gcmRegId";
    private static final String RAIO_MAXIMO = "raio_maximo";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    public static final int SALT_BYTE_SIZE = 32;
    public static final int HASH_BYTE_SIZE = 32;
    public static final int PBKDF2_ITERATIONS = 1000;

    public static final int ITERATION_INDEX = 0;
    public static final int SALT_INDEX = 1;
    public static final int PBKDF2_INDEX = 2;

    public static String getRegistrationGCM(Context context) {

        SharedPreferences preferences = context.getSharedPreferences(RESCUEE_PREFERENCES, Context.MODE_MULTI_PROCESS);

        return preferences.getString(GCM_REGID, "");

    }

    public static boolean setRegistrationGCM(Context context, String registrationID) {

        SharedPreferences preferences = context.getSharedPreferences(RESCUEE_PREFERENCES, Context.MODE_MULTI_PROCESS);

        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(GCM_REGID, registrationID);

        return editor.commit();

    }

    public static float getRaioMaximo(Context context) {

        SharedPreferences preferences = context.getSharedPreferences(RESCUEE_PREFERENCES, Context.MODE_MULTI_PROCESS);
        float raio;

        raio = preferences.getFloat(RAIO_MAXIMO, -1);

        if (raio == -1)
            raio = 5;

        return raio;

    }

    public static boolean setRaioMaximo(Context context, float raioMaximo) {

        SharedPreferences preferences = context.getSharedPreferences(RESCUEE_PREFERENCES, Context.MODE_MULTI_PROCESS);

        final SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(RAIO_MAXIMO, raioMaximo);

        return editor.commit();

    }

    public static boolean validarEmail(String email) {

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();

    }

    public static boolean validarSenha(String senha) {

        if (senha != null && senha.length() >= 6)
            return true;
        else
            return false;

    }

    public static String createSalt() throws NoSuchAlgorithmException {

        // Generate a random salt
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        return toHex(salt);

    }

    public static String createHash(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        // Hash the password
        byte[] hash = pbkdf2(password.toCharArray(), salt.getBytes(), PBKDF2_ITERATIONS, HASH_BYTE_SIZE);

        // format iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toHex(salt.getBytes()) + ":" + toHex(hash);

    }

    public static boolean validatePassword(String password, String salt, String correctHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        // Decode the hash into its parameters
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[ITERATION_INDEX]);
        byte[] saltByte = fromHex(salt);
        byte[] hash = fromHex(params[PBKDF2_INDEX]);
        // Compute the hash of the provided password, using the same salt,
        // iteration count, and hash length
        byte[] testHash = pbkdf2(password.toCharArray(), saltByte, iterations, hash.length);

        // Compare the hashes in constant time. The password is correct if
        // both hashes match.
        return slowEquals(hash, testHash);

    }

    private static boolean slowEquals(byte[] a, byte[] b) {

        int diff = a.length ^ b.length;

        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];

        return diff == 0;

    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);

        return skf.generateSecret(spec).getEncoded();

    }

    private static byte[] fromHex(String hex) {

        byte[] binary = new byte[hex.length() / 2];

        for (int i = 0; i < binary.length; i++)
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);

        return binary;

    }

    private static String toHex(byte[] array) {

        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();

        if (paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;

    }

}