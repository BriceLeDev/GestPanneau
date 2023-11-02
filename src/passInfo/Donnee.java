package passInfo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author AMEGNITO Brice
 */
public class Donnee {
    private static String NomPersonnel;
    private static String TypePersonnel;
    public static String getNomPersonnel() {
        return NomPersonnel;
    }

    public static void SetNomPersonnel(String NameCon) {
                Donnee.NomPersonnel = NameCon;

    }

    public static void SetTypePersonnel(String opt) {
         Donnee.TypePersonnel = opt;
    }

    public static String getTypePersonnel() {
        return TypePersonnel; 
    }
   
}
