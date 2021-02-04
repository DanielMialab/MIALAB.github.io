/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion;


import java.security.KeyStore;
import java.security.KeyStoreException;

public interface KeyStoreProvider
{

    public abstract KeyStore getKeystore(char ac[])
        throws KeyStoreException;
}
