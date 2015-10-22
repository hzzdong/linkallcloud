// For licensing, see: http://helium.knownspace.org/license.html
package com.linkallcloud.core.security.ssl;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;


/**
 * A simple TrustManager implementation that accepts
 * all certificates without validation.
 *
 * Taken from 
 * http://www.javaworld.com/javatips/jw-javatip115.html
 * Java Tip 115: Secure JavaMail with JSSE
 * Add secure, SSL-based connections to JavaMail
 * By Eugen Kuleshov and Dmitry I. Platonoff 
 *
 */
public class DummyTrustManager implements X509TrustManager {
    public boolean isClientTrusted(X509Certificate[] cert) {
        return true;
    }

    public boolean isServerTrusted(X509Certificate[] cert) {
        return true;
    }

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    public void checkClientTrusted(X509Certificate[] p0, String p1)
                            throws java.security.cert.CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] p0, String p1)
                            throws java.security.cert.CertificateException {
    }
}