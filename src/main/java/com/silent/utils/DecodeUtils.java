package com.silent.utils;

import org.apache.tomcat.util.buf.HexUtils;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author zhao
 * @date 2021/11/18 15:18
 */
public class DecodeUtils {

    public static void main(String[] args) throws Exception {
//        String s = "5639386D43343149437863745874626C444B7436324C696E4972353733476550334A59335959503669434F68312B51664271434C4B346D2B4F306E47666F336953503574396159694463556D0D0A2F4E2F4557633442716679536D495A396B4E4154644A694D4E51716B72386E6A3666335736366D6A624676616B7573646D4D6C59486E5559746D4F775050686E7067747266617239744D4D520D0A706C5579585667545A45682F464677496D306F3536536537366D2B5939415A7A7A613465457359654F6C4D557472464C55682F416D4668633371427974646944535A765163424A4A4455666E0D0A50794F434E485A523845736C754E72732B556F614C613554784777464E2B724C61425556517644434E6C4277336D51752B726F59335265796D54586F7A672F516E2F62473567633D";
        String secret="a1620c7dc0225341";
//        System.out.println(decode(toBytes(s), secret));

        String s  = "{\"optStatus\":0,\"msgType\":\"6\",\"city\":\"南宁市\",\"alt\":0,\"lon\":\"108.34505\",\"time\":\"2021-11-22 12:42:38\",\"plateNumber\":\"桂A07A56\",\"deviceId\":\"100100027883\",\"lat\":\"22.79256\",\"speed\":0,\"status\":0,\"direction\":334}";

        byte[] bytes = encrypt(s, secret);

        System.out.println(HexUtils.toHexString(bytes));

        System.out.println(decode(bytes, secret));


    }

    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }


    public static byte[] encrypt(String sSrc, String sKey) throws Exception {
        byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return new BASE64Encoder().encode(encrypted).getBytes();
    }


    public static String decode(byte[] bytes, String sKey) throws BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec key = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] original = cipher.doFinal(Base64.getMimeDecoder().decode(bytes));
        return new String(original);
    }



}
