package com.shuwoom.apihooker.hookers;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.shuwoom.apihooker.common.Config;

public class NetworkHooker extends Hooker {

	public static final String NAME = "Network";

	public NetworkHooker() {
		super(NetworkHooker.NAME);
	}

	@Override
	public void hook() {
		hookIOBridgeClass();
		attachOnAbstractHttpClientClass();
		attachOnHttpGetClass();
		attachOnUrlClass();
		attachOnIOExceptionClass();
		attachOnSocketClass();
		attachOnProxyClass();
		attachOnServerSocketClass();
		attachOnSSLCertificateSocketFactoryClass();
		attachOnSSLParametersClass();
		attachOnSSLContextClass();
		attachOnHttpURLConnection();
		attachOnHttpsURLConnection();
		attachOnWebviewClass();
		attachOnWebSettingsClass();
	}

	private void hookIOBridgeClass() {
		String className = "libcore.io.IoBridge";
		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();
		
		// methodsToHook.put("open", 1); This is in common with filesystem
		// operation.
		methodsToHook.put("recvfrom", 1);
		// methodsToHook.put("read", 1);

		// methodsToHook.put("write", 2);
		methodsToHook.put("sendto", 2);

		methodsToHook.put("getSocketLocalAddress", 1);
		methodsToHook.put("getSocketLocalPort", 1);

		methodsToHook.put("closeSocket", 1);
		methodsToHook.put("connectErrno", 1);
		methodsToHook.put("connect", 2);
		methodsToHook.put("bind", 1);
		
		try {
			hookMethods(null, className, methodsToHook);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(className);
			sb.append("=>").append(e.toString());
			Log.v(Config.DEBUG_ERROR, sb.toString());
		}
	}

	private void attachOnAbstractHttpClientClass() {
		String className = "org.apache.http.impl.client.AbstractHttpClient";
		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("execute", 2);
		methodsToHook.put("getAuthSchemes", 1);
		methodsToHook.put("getCookiesStore", 1);
		methodsToHook.put("getCredentialProvider", 1);
		methodsToHook.put("getParams", 1);
		methodsToHook.put("setParams", 2);

		try {
			hookMethods(null, className, methodsToHook);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(className);
			sb.append("=>").append(e.toString());
			Log.v(Config.DEBUG_ERROR, sb.toString());
		}

	}
	
	private void attachOnHttpGetClass() {
		String className = "org.apache.http.client.methods.HttpGet";
		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("HttpGet", 2);
		methodsToHook.put("getMethod", 1);
		methodsToHook.put("getURI", 1);

		try {
			hookMethods(null, className, methodsToHook);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(className);
			sb.append("=>").append(e.toString());
			Log.v(Config.DEBUG_ERROR, sb.toString());
		}

	}
	
	private void attachOnUrlClass() {
		String className = "java.net.URL";
		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("URL", 0);
		methodsToHook.put("getAuthority", 0);
		methodsToHook.put("getContent", 0);
		methodsToHook.put("getDefaultPort", 0);
		methodsToHook.put("getFile", 0);
		methodsToHook.put("getHost", 0);
		methodsToHook.put("getPath", 0);
		methodsToHook.put("getPort", 0);
		methodsToHook.put("getProtocol", 0);
		methodsToHook.put("getQuery", 0);
		methodsToHook.put("openConnection", 1);
		methodsToHook.put("openStream", 1);
		methodsToHook.put("toURI", 0);

		hookMethods(null, className, methodsToHook);
	}
	
	private void attachOnIOExceptionClass() {
		String className = "java.io.IOException";
		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("IOException", 0);

		try {
			hookMethods(null, className, methodsToHook);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(className);
			sb.append("=>").append(e.toString());
			Log.v(Config.DEBUG_ERROR, sb.toString());
		}
	}
	
	private void attachOnSocketClass() {
		String className = "java.net.Socket";
		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("Socket", 2);
		methodsToHook.put("bind", 2);
		methodsToHook.put("close", 1);
		methodsToHook.put("connect", 2);
		methodsToHook.put("getInetAddress", 1);
		methodsToHook.put("getInputStream", 1);
		methodsToHook.put("getLocalAddress", 1);
		methodsToHook.put("getLocalPort", 1);
		methodsToHook.put("getLocalSocketAddress", 1);
		methodsToHook.put("getOutputStream", 1);
		methodsToHook.put("getPort", 1);
		methodsToHook.put("sendUrgentData", 2);
		methodsToHook.put("getPort", 1);
		methodsToHook.put("setReuseAddress", 1);
		methodsToHook.put("setSocketImplFactory", 1);
		methodsToHook.put("setTcpNoDelay", 1);
		methodsToHook.put("shutdownInput", 1);
		methodsToHook.put("shutdownOutput", 1);

		try {
			hookMethods(null, className, methodsToHook);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(className);
			sb.append("=>").append(e.toString());
			Log.v(Config.DEBUG_ERROR, sb.toString());
		}

	}
	
	private void attachOnProxyClass() {
		String className = "java.net.Proxy";
		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("Proxy", 0);

		try {
			hookMethods(null, className, methodsToHook);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(className);
			sb.append("=>").append(e.toString());
			Log.v(Config.DEBUG_ERROR, sb.toString());
		}

	}
	
	private void attachOnServerSocketClass() {
		String className = "java.net.ServerSocket";
		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("ServerSocket", 0);
		methodsToHook.put("accept", 0);
		methodsToHook.put("bind", 0);

		try {
			hookMethods(null, className, methodsToHook);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(className);
			sb.append("=>").append(e.toString());
			Log.v(Config.DEBUG_ERROR, sb.toString());
		}

	}
	
	private void attachOnSSLCertificateSocketFactoryClass() {
		String className = "android.net.SSLCertificateSocketFactory";
		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("SSLCertificateSocketFactory", 0);
		methodsToHook.put("getDefault", 0);
		methodsToHook.put("createSocket", 0);
		methodsToHook.put("getHttpSocketFactory", 0);
		methodsToHook.put("getNpnSelectedProtocol", 0);
		methodsToHook.put("setHostname", 0);
		methodsToHook.put("setKeyManager", 0);
		methodsToHook.put("setNpnProtocols", 0);
		methodsToHook.put("setTrustManagers", 0);
		methodsToHook.put("setUseSessionTickets", 0);

		try {
			hookMethods(null, className, methodsToHook);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(className);
			sb.append("=>").append(e.toString());
			Log.v(Config.DEBUG_ERROR, sb.toString());
		}

	}
	
	private void attachOnSSLParametersClass() {
		String className = "javax.net.ssl.SSLParameters";
		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("SSLParameters", 0);
		methodsToHook.put("setCipherSuites", 0);
		methodsToHook.put("setNeedClientAuth", 0);
		methodsToHook.put("setProtocols", 0);
		methodsToHook.put("setWantClientAuth", 0);

		try {
			hookMethods(null, className, methodsToHook);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(className);
			sb.append("=>").append(e.toString());
			Log.v(Config.DEBUG_ERROR, sb.toString());
		}

	}
	
	private void attachOnSSLContextClass() {
		String className = "javax.net.ssl.SSLContext";
		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("getInstance", 0);
		methodsToHook.put("init", 0);
		methodsToHook.put("createSSLEngine", 0);
		methodsToHook.put("getDefault", 0);

		try {
			hookMethods(null, className, methodsToHook);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(className);
			sb.append("=>").append(e.toString());
			Log.v(Config.DEBUG_ERROR, sb.toString());
		}

	}
	
	private void attachOnHttpURLConnection() {
		String className = "java.net.HttpURLConnection";
		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("HttpURLConnection", 0);
		methodsToHook.put("getErrorStream", 0);
		methodsToHook.put("getFollowRedirects", 0);
		methodsToHook.put("getHeaderFieldDate", 0);
		methodsToHook.put("getInstanceFollowRedirects", 0);
		methodsToHook.put("getPermission", 0);
		methodsToHook.put("setChunkedStreamingMode", 0);
		methodsToHook.put("setFixedLengthStreamingMode", 0);
		methodsToHook.put("setFollowRedirects", 0);
		methodsToHook.put("setInstanceFollowRedirects", 0);
		methodsToHook.put("setRequestMethod", 0);
		methodsToHook.put("setDoOutput", 0);

		try {
			hookMethods(null, className, methodsToHook);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(className);
			sb.append("=>").append(e.toString());
			Log.v(Config.DEBUG_ERROR, sb.toString());
		}

	}
	
	private void attachOnHttpsURLConnection() {
		final String className = "javax.net.ssl.HttpsURLConnection";

		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("getDefaultHostnameVerifier", 0);
		methodsToHook.put("getDefaultSSLSocketFactory", 0);
		methodsToHook.put("getHostnameVerifier", 0);
		methodsToHook.put("getLocalPrincipal", 0);
		methodsToHook.put("getSSLSocketFactory", 0);
		methodsToHook.put("setDefaultHostnameVerifier", 0);
		methodsToHook.put("setDefaultSSLSocketFactory", 0);
		methodsToHook.put("setHostnameVerifier", 0);
		methodsToHook.put("setSSLSocketFactory", 0);

		try {
			hookMethods(null, className, methodsToHook);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(className);
			sb.append("=>").append(e.toString());
			Log.v(Config.DEBUG_ERROR, sb.toString());
		}
	}
	
	private void attachOnWebviewClass() {
		String className = "android.webkit.WebView";
		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("addJavascriptInterface", 2);
		methodsToHook.put("capturePicture", 2);
		methodsToHook.put("clearSslPreferences", 1);
		methodsToHook.put("evaluateJavascript", 2);
		methodsToHook.put("findAddress", 0);
		methodsToHook.put("getCertificate", 0);
		methodsToHook.put("getSettings", 0);

		methodsToHook.put("isPrivateBrowsingEnabled", 0);
		methodsToHook.put("loadData", 2);
		methodsToHook.put("loadDataWithBaseURL", 2);
		methodsToHook.put("loadUrl", 2);
		methodsToHook.put("postUrl", 0);
		methodsToHook.put("removeJavascriptInterface", 2);
		methodsToHook.put("restoreState", 1);
		methodsToHook.put("savePassword", 1);
		methodsToHook.put("saveState", 1);
		methodsToHook.put("setCertificate", 2);
		methodsToHook.put("setHttpAuthUsernamePassword", 2);
		methodsToHook.put("setWebContentsDebuggingEnabled", 2);

		try {
			hookMethods(null, className, methodsToHook);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(className);
			sb.append("=>").append(e.toString());
			Log.v(Config.DEBUG_ERROR, sb.toString());
		}
	}
	
	private void attachOnWebSettingsClass() {
		String className = "android.webkit.WebSettings";
		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("setAllowContentAccess", 2);
		methodsToHook.put("setAllowFileAccess", 2);
		methodsToHook.put("setDatabaseEnabled", 2);
		methodsToHook.put("setDatabasePath", 2);
		methodsToHook.put("setJavaScriptEnabled", 2);
		methodsToHook.put("setSavePassword", 2);

		try {
			hookMethods(null, className, methodsToHook);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(className);
			sb.append("=>").append(e.toString());
			Log.v(Config.DEBUG_ERROR, sb.toString());
		}
	}
}
