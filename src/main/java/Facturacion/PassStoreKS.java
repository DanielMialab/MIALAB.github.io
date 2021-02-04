/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion;

import es.mityc.javasign.pkstore.IPassStoreKS;
import java.security.cert.X509Certificate;

public class PassStoreKS
    implements IPassStoreKS
{

    public PassStoreKS(String pass)
    {
        password = new String(pass);
    }

    public char[] getPassword(X509Certificate certificate, String alias)
    {
        return password.toCharArray();
    }

    private transient String password;
}
