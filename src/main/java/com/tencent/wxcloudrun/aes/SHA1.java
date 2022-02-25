/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package com.tencent.wxcloudrun.aes;

import cn.hutool.crypto.digest.DigestUtil;
import java.util.Arrays;

/**
 * SHA1 class
 *
 * 计算公众平台的消息签名接口.
 */
class SHA1 {

	/**
	 * 用SHA1算法生成安全签名
	 * @param token 票据
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @param encrypt 密文
	 * @return 安全签名
	 * @throws AesException 
	 */
	public static String getSHA1(String token, String timestamp, String nonce, String encrypt)
			throws AesException {
		try {
			String[] array = new String[] { token, timestamp, nonce, encrypt };
			// 字符串排序
			Arrays.sort(array);
			StringBuilder sb = new StringBuilder();
			for (String s : array) {
				sb.append(s);
			}
			return DigestUtil.sha1Hex(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ComputeSignatureError);
		}
	}
}
