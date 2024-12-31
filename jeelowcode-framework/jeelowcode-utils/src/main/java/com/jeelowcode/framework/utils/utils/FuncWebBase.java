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
package com.jeelowcode.framework.utils.utils;


import cn.hutool.core.util.CharsetUtil;
import com.jeelowcode.framework.utils.tool.IpUtils;
import com.jeelowcode.framework.utils.tool.StringUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 网络工具包集合，工具类快捷方式
 *
 */
public class FuncWebBase {


	public static HttpServletRequest getRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		return requestAttributes == null ? null : ((ServletRequestAttributes)requestAttributes).getRequest();
	}

	//获取ip
	public static String getIp(ContentCachingRequestWrapper request) {
		try{
			String ipAddr = IpUtils.getIpAddr(request);
			return ipAddr;
		}catch (Exception e){

		}
		return "";
	}

	//获取请求地址
	public static String getPath(String uriStr) {
		URI uri;
		try {
			uri = new URI(uriStr);
		} catch (URISyntaxException var3) {
			throw new RuntimeException(var3);
		}

		return uri.getPath();
	}

	//获取参数
	public static String getRequestParams(ContentCachingRequestWrapper request) {
		try {
			String queryString = request.getQueryString();
			if (StringUtil.isNotBlank(queryString)) {
				return (new String(queryString.getBytes(CharsetUtil.ISO_8859_1), CharsetUtil.UTF_8)).replaceAll("&amp;", "&").replaceAll("%22", "\"");
			} else {
				String charEncoding = request.getCharacterEncoding();
				if (charEncoding == null) {
					charEncoding = "UTF-8";
				}

				byte[] buffer = getRequestBody(request.getInputStream()).getBytes();
				String str = (new String(buffer, charEncoding)).trim();
				if (StringUtil.isBlank(str)) {
					StringBuilder sb = new StringBuilder();
					Enumeration parameterNames = request.getParameterNames();

					while(parameterNames.hasMoreElements()) {
						String key = (String)parameterNames.nextElement();
						String value = request.getParameter(key);
						StringUtil.appendBuilder(sb, new CharSequence[]{key, "=", value, "&"});
					}

					str = StringUtil.removeSuffix(sb.toString(), "&");
				}

				return str.replaceAll("&amp;", "&");
			}
		} catch (Exception var9) {
			var9.printStackTrace();
			return "";
		}
	}

	//获取body参数 转为字符串
	public static String getRequestBody(ServletInputStream servletInputStream) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(servletInputStream, StandardCharsets.UTF_8));

			String line;
			while((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException var16) {
			var16.printStackTrace();
		} finally {
			if (servletInputStream != null) {
				try {
					servletInputStream.close();
				} catch (IOException var15) {
					var15.printStackTrace();
				}
			}

			if (reader != null) {
				try {
					reader.close();
				} catch (IOException var14) {
					var14.printStackTrace();
				}
			}

		}

		return sb.toString();
	}

	//-------------------------------


}
