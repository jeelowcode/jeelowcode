/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，【允许商用】使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与【本软件竞争】的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.framework.utils.tool;

import cn.hutool.core.util.CharsetUtil;
import com.jeelowcode.framework.exception.JeeLowCodeException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Objects;

/**
 * 完全兼容微信所使用的AES加密工具类
 * aes的key必须是256byte长（比如32个字符），可以使用AesKit.genAesKey()来生成一组key
 *
 */
public class AesUtil {

	public static final Charset DEFAULT_CHARSET = CharsetUtil.CHARSET_UTF_8;

	//密钥
	public static String secretKey="";

	/**
	 * 获取密钥
	 *
	 * @return {String}
	 */
	public static String genAesKey() {
		return StringUtil.random(32);
	}

	/**
	 * 加密
	 *
	 * @param content    文本内容
	 * @param aesTextKey 文本密钥
	 * @return byte[]
	 */
	public static byte[] encrypt(String content, String aesTextKey) {
		return encrypt(content.getBytes(DEFAULT_CHARSET), aesTextKey);
	}

	/**
	 * 加密
	 *
	 * @param content    文本内容
	 * @param charset    编码
	 * @param aesTextKey 文本密钥
	 * @return byte[]
	 */
	public static byte[] encrypt(String content, Charset charset, String aesTextKey) {
		return encrypt(content.getBytes(charset), aesTextKey);
	}

	/**
	 * 加密
	 *
	 * @param content    内容
	 * @param aesTextKey 文本密钥
	 * @return byte[]
	 */
	public static byte[] encrypt(byte[] content, String aesTextKey) {
		return encrypt(content, Objects.requireNonNull(aesTextKey).getBytes(DEFAULT_CHARSET));
	}



	/**
	 * Base64加密
	 *
	 * @param content    文本内容
	 * @param aesTextKey 文本密钥
	 * @return {String}
	 */
	public static String encryptToBase64(String content, String aesTextKey) {
		return Base64Util.encodeToString(encrypt(content, aesTextKey));
	}

	/**
	 * Base64加密
	 *
	 * @param content    内容
	 * @param aesTextKey 文本密钥
	 * @return {String}
	 */
	public static String encryptToBase64(byte[] content, String aesTextKey) {
		return Base64Util.encodeToString(encrypt(content, aesTextKey));
	}



	/**
	 * Base64解密
	 *
	 * @param content    文本内容
	 * @param aesTextKey 文本密钥
	 * @return {String}
	 */
	@Nullable
	public static String decryptFormBase64ToString(@Nullable String content, String aesTextKey) {
		byte[] hexBytes = decryptFormBase64(content, aesTextKey);
		if (hexBytes == null) {
			return null;
		}
		return new String(hexBytes, DEFAULT_CHARSET);
	}

	/**
	 * Base64解密
	 *
	 * @param content    文本内容
	 * @param aesTextKey 文本密钥
	 * @return byte[]
	 */
	@Nullable
	public static byte[] decryptFormBase64(@Nullable String content, String aesTextKey) {
		if (StringUtil.isBlank(content)) {
			return null;
		}
		return decryptFormBase64(content.getBytes(DEFAULT_CHARSET), aesTextKey);
	}

	/**
	 * Base64解密
	 *
	 * @param content    内容
	 * @param aesTextKey 文本密钥
	 * @return byte[]
	 */
	public static byte[] decryptFormBase64(byte[] content, String aesTextKey) {
		return decrypt(Base64Util.decode(content), aesTextKey);
	}

	/**
	 * 解密
	 *
	 * @param content    内容
	 * @param aesTextKey 文本密钥
	 * @return {String}
	 */
	public static String decryptToString(byte[] content, String aesTextKey) {
		return new String(decrypt(content, aesTextKey), DEFAULT_CHARSET);
	}

	/**
	 * 解密
	 *
	 * @param content    内容
	 * @param aesTextKey 文本密钥
	 * @return byte[]
	 */
	public static byte[] decrypt(byte[] content, String aesTextKey) {
		return decrypt(content, Objects.requireNonNull(aesTextKey).getBytes(DEFAULT_CHARSET));
	}

	/**
	 * 解密
	 *
	 * @param content 内容
	 * @param aesKey  密钥
	 * @return byte[]
	 */
	public static byte[] encrypt(byte[] content, byte[] aesKey) {
		return aes(Pkcs7Encoder.encode(content), aesKey, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 加密
	 *
	 * @param encrypted 内容
	 * @param aesKey    密钥
	 * @return byte[]
	 */
	public static byte[] decrypt(byte[] encrypted, byte[] aesKey) {
		return Pkcs7Encoder.decode(aes(encrypted, aesKey, Cipher.DECRYPT_MODE));
	}

	/**
	 * ase加密
	 *
	 * @param encrypted 内容
	 * @param aesKey    密钥
	 * @param mode      模式
	 * @return byte[]
	 */
	private static byte[] aes(byte[] encrypted, byte[] aesKey, int mode) {
		Assert.isTrue(aesKey.length == 32, "IllegalAesKey, aesKey's length must be 32");
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
			cipher.init(mode, keySpec, iv);
			return cipher.doFinal(encrypted);
		} catch (Exception e) {
			throw new JeeLowCodeException(e.getMessage());
		}
	}

	/**
	 * 提供基于PKCS7算法的加解密接口.
	 */
	private static class Pkcs7Encoder {
		private static final int BLOCK_SIZE = 32;

		private static byte[] encode(byte[] src) {
			int count = src.length;
			// 计算需要填充的位数
			int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
			// 获得补位所用的字符
			byte pad = (byte) (amountToPad & 0xFF);
			byte[] pads = new byte[amountToPad];
			for (int index = 0; index < amountToPad; index++) {
				pads[index] = pad;
			}
			int length = count + amountToPad;
			byte[] dest = new byte[length];
			System.arraycopy(src, 0, dest, 0, count);
			System.arraycopy(pads, 0, dest, count, amountToPad);
			return dest;
		}

		private static byte[] decode(byte[] decrypted) {
			int pad = decrypted[decrypted.length - 1];
			if (pad < 1 || pad > BLOCK_SIZE) {
				pad = 0;
			}
			if (pad > 0) {
				return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
			}
			return decrypted;
		}
	}
}
