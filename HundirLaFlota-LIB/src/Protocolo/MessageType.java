/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocolo;

/**
 *
 * @author Pedro Miguel Navas Campoy
 */
public enum MessageType {
    LOGIN,
    LOGOUT,
    REGISTER;
    
    static public MessageType fromOrdinal( int ordinal){
        return MessageType.values()[ordinal];
    }
}
