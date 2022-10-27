package org.jredfoot.utgrid.cert.pki;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class Credential
{
  X509Certificate certificate;
  PrivateKey privateKey;

  public Credential(X509Certificate cert, PrivateKey key)
  {
    this.certificate = cert;
    this.privateKey = key;
  }

  public X509Certificate getCertificate()
  {
    return this.certificate;
  }

  public PrivateKey getPrivateKey()
  {
    return this.privateKey;
  }

  public void setPrivateKey(PrivateKey privateKey)
  {
    this.privateKey = privateKey;
  }
}