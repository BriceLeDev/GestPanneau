/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package projetpanneaupub;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import  java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date; 
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import passInfo.Donnee;
//import com.lowage.text.Document;
//import net.sf.jasperreports.engine.JasperCompileManager;

/**
 *
 * @author AMEGNITO Brice
 */
public class AdminAcceuil extends javax.swing.JFrame {
    
     public String PersonTyp ;
     public String dateDuJr;
     public String VerifiPersonCon;
     public String Nomclient;
     public int MtnAbn ;
     int NbrJrs=0 ;
     int  idCliAb=0 ;
     int chck =0;
     int NBRPANS;
     int LeNbrPan=0;
     int Prix =0;
    int MtnTotalAbn=0;
     int NumFact;
    /** Creates new form AdminAcceuil */
    Color DefaulColor,ClickColor;
    public AdminAcceuil() {
      
        initComponents();
        
        Affiche();
        AfficheUTILISATEUR();
        AffichePANNEAU();
        Info();
        ladate();
        AffichzPanneauLibre();
        AffichageAbonnement();
        txtTitre.setText("Tous les Abonnements");
        txtTitre1.setText("Tous les Abonnements");

        
        DefaulColor= new Color(51,153,255);
        ClickColor= new Color(000,000,000);
        
        ClientPane.setBackground(DefaulColor);
        PaneauPane.setBackground(DefaulColor);
        AbonPane.setBackground(DefaulColor);
        UtilisateurPane.setBackground(DefaulColor);
        RecherchePane.setBackground(DefaulColor);
        
         // Visibilité des fenetre:
         //LesFenetres.removeAll();
         //LesFenetres.add(FenImpression);
       FenClients.setVisible(false);
       FenAbonnement.setVisible(false);
       FenUtilisateur.setVisible(false);
       FenPanneau.setVisible(false);
       FenetreRecherche.setVisible(false);
       BtnPrinteFact.setVisible(true);
       PanAffichAbn.setVisible(false);
       affichCol();
       
        
    }
    //Vérifiaction type personnel connecter pour l affichage des menus
    
    public void affichCol(){
      // tableAbonnement.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
       //TableColumn colone = tableAbonnement.getColumn("Identifiant");
       //colone.setMaxWidth(0);
       //colone.setMinWidth(0);
       //colone.setPreferredWidth(0);
        tableAbonnement.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableAbonnement.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableAbonnement.getColumnModel().getColumn(1).setPreferredWidth(90);
        tableAbonnement.getColumnModel().getColumn(2).setPreferredWidth(90);
        tableAbonnement.getColumnModel().getColumn(3).setPreferredWidth(90);
        tableAbonnement.getColumnModel().getColumn(4).setPreferredWidth(120);
        tableAbonnement.getColumnModel().getColumn(6).setPreferredWidth(70);
        tableAbonnement.getColumnModel().getColumn(7).setPreferredWidth(120);
       
       
        
    }
    
    //passage des informations
     public void Info(){
        lblUserCon.setText(Donnee.getNomPersonnel());
        lblTypePers.setText(Donnee.getTypePersonnel());
        PersonTyp =Donnee.getTypePersonnel();
        if(PersonTyp.equals("Personnel")){
            FenUtilisateur.setVisible(false);
            UtilisateurPane.setVisible(false);
            RecherchePane.setVisible(false);
            FenetreRecherche.setVisible(false);
        }
    }
     //Affichage clients dans la table client
     public void Affiche(){
        try {
            String donnee []={"Identifiant","Nom","Adresse","Téléphone"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            prst1=connect.prepareStatement("SELECT * FROM  client ");
            rsl=prst1.executeQuery();
            while(rsl.next()){
                Object ob[]={
                    rsl.getString("Id_Client"),
                    rsl.getString("Nom_Client"),
                    rsl.getString("Adresse_Client"),
                    rsl.getString("Tel_Client")
                };
                model.addRow(ob);
            }
           
            TableClient.setModel(model);
           
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     // Affichage Utilisateur dsns la table utilisateur
      public void AfficheUTILISATEUR(){
        try {
            String donnee []={"ID","Nom","Mot de Passe","Téléphone","Option"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            prst1=connect.prepareStatement("SELECT * FROM   admin ");
            rsl=prst1.executeQuery();
            while(rsl.next()){
                Object ob[]={
                    rsl.getString("id_Admin"),//
                    rsl.getString("Nom_Admin"),
                    rsl.getString("PasseWrdAdmin"),
                    rsl.getString("telephone_Admin"),
                    rsl.getString("OptionPersonnel")
                };
                model.addRow(ob);
            }
            TableUser.setModel(model);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
      //$$$$$$$       Affichage des panneau dans la table panneau*******************
       public void AffichePANNEAU(){
        try {
            String donnee []={"Identifiant","Itinéraire","Localisation","Taille","Prix"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            prst1=connect.prepareStatement("SELECT * FROM  panneau ");
            rsl=prst1.executeQuery();
            while(rsl.next()){
                Object ob[]={
                    rsl.getString("Id_panneau"),
                    rsl.getString("Itineraire"),
                    rsl.getString("Localisation_panneau"),
                    rsl.getString("Taille"),
                    rsl.getString("Prix")
                };
                model.addRow(ob);
            }
            TablePan.setModel(model);
            //tablePanAbon.setModel(model);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       public void AffichzPanneauLibre(){
            Date dateSys = new Date();
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            String dateSystem=spf.format(dateSys);
           try {
            String donnee []={"Identifiant","Itinéraire","Localisation","Taille","Prix"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            prst1=connect.prepareStatement("SELECT * FROM  panneau WHERE Id_panneau NOT IN (SELECT Idpan   FROM  ligneabonnement WHERE DateFin>'"+dateSystem+"' ) ");
            rsl=prst1.executeQuery();
            while(rsl.next()){
                Object ob[]={
                    rsl.getString("Id_panneau"),
                    rsl.getString("Itineraire"),
                    rsl.getString("Localisation_panneau"),
                    rsl.getString("Taille"),
                    rsl.getString("Prix")
                };
                model.addRow(ob);
            }
            //TablePan.setModel(model);
            tablePanAbon.setModel(model);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       }
       
       //affichage abonnement 
       public void AffichageAbonnement(){
           // affichCol();
           try {
            String donnee []={"Id","DateDebut","DateFin","TelClient","Nom_Client","NbrJours","NbrPanneau","MontantP"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            prst1=connect.prepareStatement("SELECT * FROM  abonnement ");
            rsl=prst1.executeQuery();
            
            while(rsl.next()){
                Object ob[]={
                    rsl.getString("IdAbon"),
                    rsl.getString("DateDebut"),
                    rsl.getString("DateFin"),
                    rsl.getString("TelClient"),
                    rsl.getString("Nom_Client"),
                    rsl.getString("NbrJours"),
                    rsl.getString("NbrPanneau"),
                    rsl.getString("MontantTotApayer"),
      
                };
                model.addRow(ob);
            }
           
            
            tableAbonnement.setModel(model);
            tablePannAff2.setModel(model);
            //tablePanAbon.setModel(model);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
       
       
      //la date 
       public void ladate(){
           Date mydate = new Date();
           SimpleDateFormat dateForm = new SimpleDateFormat("dd / MM / yyyy");
           String madate= dateForm.format(mydate);
           //txtDatDeb.setText(madate);
           //txtDatFin.setText(madate);
           dateDuJr=madate;
           
       }
       
       
       
 
       
     Connection connect ;
    PreparedStatement prst,prst1,prst2,prst3;
     ResultSet rsl,rsl1,rsl2,rsl3;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        FenRecherche = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        ClientPane = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        PaneauPane = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        AbonPane = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        UtilisateurPane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        RecherchePane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        EnteteTitre = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblUserCon = new javax.swing.JLabel();
        lblTypePers = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        BtnPrinteFact = new javax.swing.JButton();
        LesFenetres = new javax.swing.JPanel();
        FenAbonnement = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtTelCliAbon = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        btnAjouAbn = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        txtDatDeb = new com.toedter.calendar.JDateChooser();
        txtDatFin = new com.toedter.calendar.JDateChooser();
        jLabel35 = new javax.swing.JLabel();
        MntRest = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtNbrPan = new javax.swing.JLabel();
        txtMontantAPay = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        MtnPayer = new javax.swing.JTextField();
        btnAnn = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablePanAbon = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        txtpanoLibre = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        TblAbonClient = new javax.swing.JTable();
        btnRetit = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        FenClients = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtAdrCli = new javax.swing.JTextField();
        txtTelCli = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNomCl = new javax.swing.JTextField();
        AjoutBtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        ModifierBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableClient = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        txtrechCli = new javax.swing.JTextField();
        btnSupprCli = new javax.swing.JButton();
        btnSupprCli1 = new javax.swing.JButton();
        FenPanneau = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtlocalisation = new javax.swing.JTextField();
        txtTaille = new javax.swing.JTextField();
        AjoutBtn1 = new javax.swing.JButton();
        ModifierBtn2 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txtPrix = new javax.swing.JTextField();
        SupprBtn1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        btnSupprCli3 = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        txtItner = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablePan = new javax.swing.JTable();
        lblAffTlPanBd = new javax.swing.JLabel();
        recherchPano = new javax.swing.JTextField();
        cbxrecherchPanno = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        txtBoulevard = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        FenetreRecherche = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        DtInf = new com.toedter.calendar.JDateChooser();
        DtSupp = new com.toedter.calendar.JDateChooser();
        jLabel33 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        lblNbrTotal = new javax.swing.JLabel();
        lblRevenu = new javax.swing.JLabel();
        btnObteniResultat = new javax.swing.JButton();
        btnAbnExp = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnObteniResultat1 = new javax.swing.JButton();
        NumClient = new javax.swing.JTextField();
        AbonPartiel = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableAbonnement = new javax.swing.JTable();
        btnSupprAbn1 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        txtRchercheAbon = new javax.swing.JTextField();
        txtTitre = new javax.swing.JLabel();
        BtnPrinteFact1 = new javax.swing.JButton();
        btnlignAbon = new javax.swing.JButton();
        FenImpression = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtPrinter = new javax.swing.JTextArea();
        PanAffichAbn = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablePannAff2 = new javax.swing.JTable();
        txtTitre1 = new javax.swing.JLabel();
        EnteteTitre1 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        FenUtilisateur = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtUserPass = new javax.swing.JTextField();
        AjoutBtnUti = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtUserTel = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        CbxOption = new javax.swing.JComboBox<>();
        ModifierBtn1 = new javax.swing.JButton();
        SupprBtn = new javax.swing.JButton();
        btnSupprCli2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableUser = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        txtRechUtili = new javax.swing.JTextField();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 103, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout FenRechercheLayout = new javax.swing.GroupLayout(FenRecherche);
        FenRecherche.setLayout(FenRechercheLayout);
        FenRechercheLayout.setHorizontalGroup(
            FenRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 783, Short.MAX_VALUE)
        );
        FenRechercheLayout.setVerticalGroup(
            FenRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
        );

        jLabel37.setText("jLabel37");

        jLabel38.setText("jLabel38");

        jLabel39.setText("jLabel39");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        ClientPane.setBackground(new java.awt.Color(51, 153, 255));
        ClientPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClientPaneMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ClientPaneMousePressed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Client");

        javax.swing.GroupLayout ClientPaneLayout = new javax.swing.GroupLayout(ClientPane);
        ClientPane.setLayout(ClientPaneLayout);
        ClientPaneLayout.setHorizontalGroup(
            ClientPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClientPaneLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ClientPaneLayout.setVerticalGroup(
            ClientPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClientPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        PaneauPane.setBackground(new java.awt.Color(51, 153, 255));
        PaneauPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PaneauPaneMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                PaneauPaneMousePressed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Panneau ");

        javax.swing.GroupLayout PaneauPaneLayout = new javax.swing.GroupLayout(PaneauPane);
        PaneauPane.setLayout(PaneauPaneLayout);
        PaneauPaneLayout.setHorizontalGroup(
            PaneauPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneauPaneLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PaneauPaneLayout.setVerticalGroup(
            PaneauPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneauPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        AbonPane.setBackground(new java.awt.Color(51, 153, 255));
        AbonPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AbonPaneMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                AbonPaneMousePressed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Abonnement");

        javax.swing.GroupLayout AbonPaneLayout = new javax.swing.GroupLayout(AbonPane);
        AbonPane.setLayout(AbonPaneLayout);
        AbonPaneLayout.setHorizontalGroup(
            AbonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AbonPaneLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel3)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        AbonPaneLayout.setVerticalGroup(
            AbonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AbonPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        UtilisateurPane.setBackground(new java.awt.Color(51, 153, 255));
        UtilisateurPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UtilisateurPaneMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                UtilisateurPaneMousePressed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Utilisateur");

        javax.swing.GroupLayout UtilisateurPaneLayout = new javax.swing.GroupLayout(UtilisateurPane);
        UtilisateurPane.setLayout(UtilisateurPaneLayout);
        UtilisateurPaneLayout.setHorizontalGroup(
            UtilisateurPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UtilisateurPaneLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        UtilisateurPaneLayout.setVerticalGroup(
            UtilisateurPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UtilisateurPaneLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        RecherchePane.setBackground(new java.awt.Color(51, 153, 255));
        RecherchePane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RecherchePaneMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                RecherchePaneMousePressed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Recherche/Etats");

        javax.swing.GroupLayout RecherchePaneLayout = new javax.swing.GroupLayout(RecherchePane);
        RecherchePane.setLayout(RecherchePaneLayout);
        RecherchePaneLayout.setHorizontalGroup(
            RecherchePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecherchePaneLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RecherchePaneLayout.setVerticalGroup(
            RecherchePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecherchePaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ClientPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PaneauPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(AbonPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(UtilisateurPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(RecherchePane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(ClientPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(PaneauPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(AbonPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(UtilisateurPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(RecherchePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        EnteteTitre.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        EnteteTitre.setText(" Centre de Controle des Panneaux Publicitaire");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("Utilisateur Connect:");

        lblUserCon.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        lblTypePers.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        jButton1.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.shadow"));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 51));
        jButton1.setText("Se Déconnecter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel8.setText("En tant que :");

        BtnPrinteFact.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        BtnPrinteFact.setText("IMPRIMER");
        BtnPrinteFact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrinteFactActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblUserCon, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(jLabel8)
                .addGap(84, 84, 84)
                .addComponent(lblTypePers, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtnPrinteFact, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(EnteteTitre, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(312, 312, 312))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(EnteteTitre, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(BtnPrinteFact)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lblTypePers, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel8)
                        .addComponent(lblUserCon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        LesFenetres.setLayout(new java.awt.CardLayout());

        FenAbonnement.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel12.setBackground(new java.awt.Color(0, 153, 153));
        jPanel12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Effectuer un Abonnement");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel21)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel22.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel22.setText("Date Début");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel23.setText("Date Fin");

        txtTelCliAbon.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtTelCliAbon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelCliAbonActionPerformed(evt);
            }
        });
        txtTelCliAbon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelCliAbonKeyPressed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel24.setText("Téléphone Client");

        btnAjouAbn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnAjouAbn.setText("Effectuer");
        btnAjouAbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjouAbnActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel28.setText("Nombre de Panneau");

        txtDatDeb.setDateFormatString("yyyy-MM-dd");
        txtDatDeb.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        txtDatFin.setDateFormatString("yyyy-MM-dd");
        txtDatFin.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel35.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel35.setText("Montant Restent");

        MntRest.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        MntRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MntRestActionPerformed(evt);
            }
        });
        MntRest.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MntRestKeyPressed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel36.setText("Montant à Payer");

        jLabel45.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel45.setText("Montant  Payer");

        MtnPayer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MtnPayerKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                MtnPayerKeyReleased(evt);
            }
        });

        btnAnn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnAnn.setText("Annuler");
        btnAnn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel36)
                            .addComponent(jLabel35)
                            .addComponent(jLabel45)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTelCliAbon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(txtDatDeb, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDatFin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMontantAPay, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(MtnPayer, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(MntRest)
                            .addComponent(txtNbrPan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btnAjouAbn, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(btnAnn, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23)
                        .addGap(25, 25, 25))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(txtDatDeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDatFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtNbrPan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtTelCliAbon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(txtMontantAPay, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(MtnPayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel35)
                    .addComponent(MntRest, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAjouAbn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tablePanAbon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablePanAbon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePanAbonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablePanAbonMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tablePanAbon);

        jLabel27.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel27.setText("Recherche Panneaux Libres Par Zone");

        txtpanoLibre.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtpanoLibre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpanoLibreActionPerformed(evt);
            }
        });
        txtpanoLibre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpanoLibreKeyReleased(evt);
            }
        });

        TblAbonClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identifiant", "Itinéraire", "Localisation", "Taille", "Prix"
            }
        ));
        jScrollPane9.setViewportView(TblAbonClient);

        btnRetit.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnRetit.setText("Retirer");
        btnRetit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetitActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel44.setText("Les Panneaux de l'abonnement");

        javax.swing.GroupLayout FenAbonnementLayout = new javax.swing.GroupLayout(FenAbonnement);
        FenAbonnement.setLayout(FenAbonnementLayout);
        FenAbonnementLayout.setHorizontalGroup(
            FenAbonnementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FenAbonnementLayout.createSequentialGroup()
                .addGroup(FenAbonnementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FenAbonnementLayout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FenAbonnementLayout.createSequentialGroup()
                        .addComponent(btnRetit, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)))
                .addGroup(FenAbonnementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FenAbonnementLayout.createSequentialGroup()
                        .addGroup(FenAbonnementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
                            .addComponent(jScrollPane9))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FenAbonnementLayout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(189, 189, 189))))
            .addGroup(FenAbonnementLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addGap(34, 34, 34)
                .addComponent(txtpanoLibre, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121))
        );
        FenAbonnementLayout.setVerticalGroup(
            FenAbonnementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FenAbonnementLayout.createSequentialGroup()
                .addGroup(FenAbonnementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtpanoLibre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FenAbonnementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FenAbonnementLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(jLabel44)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(FenAbonnementLayout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRetit, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        LesFenetres.add(FenAbonnement, "card3");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel9.setText("Adresse");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel10.setText("Téléphone");

        txtTelCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelCliKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setText("Nom Client");

        txtNomCl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomClActionPerformed(evt);
            }
        });

        AjoutBtn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        AjoutBtn.setText("Ajouter");
        AjoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AjoutBtnActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(0, 204, 255));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("ENREGISTRMENT CLIENT");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel6)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        ModifierBtn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        ModifierBtn.setText("Modifier");
        ModifierBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierBtnActionPerformed(evt);
            }
        });

        TableClient.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TableClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nom Client", "Téléphone", "Location", ""
            }
        ));
        TableClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableClientMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableClient);

        jLabel26.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel26.setText("Recherche par nom client");

        txtrechCli.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtrechCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtrechCliKeyReleased(evt);
            }
        });

        btnSupprCli.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSupprCli.setText("Supprimer");
        btnSupprCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupprCliActionPerformed(evt);
            }
        });

        btnSupprCli1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSupprCli1.setText("Annuler");
        btnSupprCli1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupprCli1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(txtrechCli, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                    .addComponent(jLabel9)
                                                    .addGap(94, 94, 94)))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtTelCli, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtAdrCli, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtNomCl, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(AjoutBtn)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ModifierBtn)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnSupprCli)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnSupprCli1))))
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtrechCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtNomCl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtAdrCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtTelCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AjoutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ModifierBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSupprCli)
                            .addComponent(btnSupprCli1))
                        .addGap(17, 17, 17))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout FenClientsLayout = new javax.swing.GroupLayout(FenClients);
        FenClients.setLayout(FenClientsLayout);
        FenClientsLayout.setHorizontalGroup(
            FenClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        FenClientsLayout.setVerticalGroup(
            FenClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        LesFenetres.add(FenClients, "card4");

        FenPanneau.setBackground(new java.awt.Color(204, 204, 204));
        FenPanneau.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel12.setText("Localisation ");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel13.setText("Taille");

        txtTaille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTailleActionPerformed(evt);
            }
        });
        txtTaille.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTailleKeyPressed(evt);
            }
        });

        AjoutBtn1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        AjoutBtn1.setText("Ajouter");
        AjoutBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AjoutBtn1ActionPerformed(evt);
            }
        });

        ModifierBtn2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        ModifierBtn2.setText("Modifier");
        ModifierBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierBtn2ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel19.setText("Prix");

        txtPrix.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrixKeyPressed(evt);
            }
        });

        SupprBtn1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        SupprBtn1.setText("Supprimer");
        SupprBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupprBtn1ActionPerformed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(0, 153, 255));

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("ENREGISTRMENT PANNEAU");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        btnSupprCli3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSupprCli3.setText("Annuler");
        btnSupprCli3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupprCli3ActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel43.setText("Itinéraire");

        txtItner.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtItnerKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(jLabel12)
                                .addGap(30, 30, 30))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtlocalisation, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrix, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTaille, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtItner, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(AjoutBtn1)
                        .addGap(30, 30, 30)
                        .addComponent(ModifierBtn2)
                        .addGap(18, 18, 18)
                        .addComponent(SupprBtn1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSupprCli3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(45, 45, 45)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtTaille, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addComponent(jLabel19))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(txtlocalisation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(104, 104, 104)
                        .addComponent(txtPrix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtItner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AjoutBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ModifierBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SupprBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSupprCli3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        TablePan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TablePan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablePanMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TablePan);

        lblAffTlPanBd.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        recherchPano.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        recherchPano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recherchPanoActionPerformed(evt);
            }
        });
        recherchPano.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                recherchPanoKeyReleased(evt);
            }
        });

        cbxrecherchPanno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tous les Panneaux", "Panneaux Libres", "Panneaux Occupés" }));
        cbxrecherchPanno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxrecherchPannoActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSearch.setText("Recherche par itinéraire");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        txtBoulevard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBoulevardActionPerformed(evt);
            }
        });
        txtBoulevard.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBoulevardKeyReleased(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel41.setText("Recherche");

        jLabel42.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel42.setText("Total");

        javax.swing.GroupLayout FenPanneauLayout = new javax.swing.GroupLayout(FenPanneau);
        FenPanneau.setLayout(FenPanneauLayout);
        FenPanneauLayout.setHorizontalGroup(
            FenPanneauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FenPanneauLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FenPanneauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, FenPanneauLayout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(cbxrecherchPanno, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(FenPanneauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FenPanneauLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FenPanneauLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(recherchPano, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(109, 109, 109))))
            .addGroup(FenPanneauLayout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(txtBoulevard, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(lblAffTlPanBd, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );
        FenPanneauLayout.setVerticalGroup(
            FenPanneauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FenPanneauLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(FenPanneauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recherchPano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxrecherchPanno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FenPanneauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(33, 33, 33)
                .addGroup(FenPanneauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAffTlPanBd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(FenPanneauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBoulevard, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        LesFenetres.add(FenPanneau, "card5");

        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel32.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel32.setText("Date inférieure");

        DtInf.setDateFormatString("yyyy-MM-dd");
        DtInf.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        DtSupp.setDateFormatString("yyyy-MM-dd");
        DtSupp.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel33.setText("Date suppérieure ");

        jLabel30.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel30.setText("Nbr total");

        jLabel31.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel31.setText("Revenue Total");

        lblNbrTotal.setBackground(new java.awt.Color(102, 102, 102));
        lblNbrTotal.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        lblRevenu.setBackground(new java.awt.Color(102, 102, 102));
        lblRevenu.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        btnObteniResultat.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnObteniResultat.setText("les abonnements de cette période");
        btnObteniResultat.setFocusable(false);
        btnObteniResultat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObteniResultatActionPerformed(evt);
            }
        });

        btnAbnExp.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnAbnExp.setText("Les abonnements expirés");
        btnAbnExp.setFocusable(false);
        btnAbnExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbnExpActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton2.setText("Obtenir les trois meilleurs clients");
        jButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnObteniResultat1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnObteniResultat1.setText("Recherche ligne abonnrment");
        btnObteniResultat1.setFocusable(false);
        btnObteniResultat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObteniResultat1ActionPerformed(evt);
            }
        });

        NumClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumClientActionPerformed(evt);
            }
        });

        AbonPartiel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        AbonPartiel.setText("Abonnement Partiel");
        AbonPartiel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbonPartielActionPerformed(evt);
            }
        });

        jButton3.setText("imprime");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(DtSupp, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(57, 57, 57)
                                        .addComponent(jButton3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(DtInf, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                                        .addComponent(btnObteniResultat1))))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(btnAbnExp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnObteniResultat))))
                    .addComponent(jLabel32))
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NumClient, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblNbrTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addGap(9, 9, 9)
                                .addComponent(lblRevenu, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(AbonPartiel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(DtSupp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel16Layout.createSequentialGroup()
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel30)
                                    .addComponent(NumClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lblNbrTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel31)
                                .addComponent(lblRevenu, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel16Layout.createSequentialGroup()
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(DtInf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel32))
                            .addGap(18, 18, 18)
                            .addComponent(jLabel33)))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(btnObteniResultat1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addGap(40, 40, 40)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAbnExp)
                    .addComponent(btnObteniResultat)
                    .addComponent(jButton2)
                    .addComponent(AbonPartiel))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        tableAbonnement.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableAbonnement.getTableHeader().setResizingAllowed(false);
        tableAbonnement.getTableHeader().setReorderingAllowed(false);
        tableAbonnement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableAbonnementMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tableAbonnement);
        if (tableAbonnement.getColumnModel().getColumnCount() > 0) {
            tableAbonnement.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableAbonnement.getColumnModel().getColumn(1).setResizable(false);
            tableAbonnement.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableAbonnement.getColumnModel().getColumn(2).setResizable(false);
            tableAbonnement.getColumnModel().getColumn(3).setResizable(false);
            tableAbonnement.getColumnModel().getColumn(4).setResizable(false);
            tableAbonnement.getColumnModel().getColumn(5).setResizable(false);
        }

        btnSupprAbn1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSupprAbn1.setText("Supprimer");
        btnSupprAbn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupprAbn1ActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel34.setText("Recherche Par Tel Client");

        txtRchercheAbon.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtRchercheAbon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRchercheAbonActionPerformed(evt);
            }
        });
        txtRchercheAbon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRchercheAbonKeyReleased(evt);
            }
        });

        txtTitre.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        BtnPrinteFact1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        BtnPrinteFact1.setText("IMPRIMER");
        BtnPrinteFact1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrinteFact1ActionPerformed(evt);
            }
        });

        btnlignAbon.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnlignAbon.setText("Obtenir ligne abonnrment");
        btnlignAbon.setFocusable(false);
        btnlignAbon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlignAbonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                                .addComponent(txtRchercheAbon, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnlignAbon)
                                .addGap(47, 47, 47)
                                .addComponent(BtnPrinteFact1)
                                .addGap(49, 49, 49)
                                .addComponent(btnSupprAbn1)
                                .addGap(33, 33, 33)))))
                .addContainerGap())
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(258, 258, 258)
                .addComponent(txtTitre, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtRchercheAbon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnPrinteFact1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSupprAbn1)
                    .addComponent(btnlignAbon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTitre, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout FenetreRechercheLayout = new javax.swing.GroupLayout(FenetreRecherche);
        FenetreRecherche.setLayout(FenetreRechercheLayout);
        FenetreRechercheLayout.setHorizontalGroup(
            FenetreRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FenetreRechercheLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        FenetreRechercheLayout.setVerticalGroup(
            FenetreRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FenetreRechercheLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        LesFenetres.add(FenetreRecherche, "card6");

        txtPrinter.setColumns(20);
        txtPrinter.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtPrinter.setRows(5);
        jScrollPane5.setViewportView(txtPrinter);

        javax.swing.GroupLayout FenImpressionLayout = new javax.swing.GroupLayout(FenImpression);
        FenImpression.setLayout(FenImpressionLayout);
        FenImpressionLayout.setHorizontalGroup(
            FenImpressionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FenImpressionLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        FenImpressionLayout.setVerticalGroup(
            FenImpressionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
        );

        LesFenetres.add(FenImpression, "card7");

        PanAffichAbn.setBackground(new java.awt.Color(255, 255, 255));

        tablePannAff2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tablePannAff2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePannAff2.setGridColor(new java.awt.Color(204, 204, 204));
        tablePannAff2.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tablePannAff2.setShowGrid(true);
        jScrollPane6.setViewportView(tablePannAff2);

        txtTitre1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        txtTitre1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTitre1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        EnteteTitre1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        EnteteTitre1.setText(" Centre de Controle des Panneaux Publicitaires");

        jLabel40.setBackground(new java.awt.Color(153, 153, 153));
        jLabel40.setOpaque(true);

        javax.swing.GroupLayout PanAffichAbnLayout = new javax.swing.GroupLayout(PanAffichAbn);
        PanAffichAbn.setLayout(PanAffichAbnLayout);
        PanAffichAbnLayout.setHorizontalGroup(
            PanAffichAbnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanAffichAbnLayout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addGroup(PanAffichAbnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanAffichAbnLayout.createSequentialGroup()
                        .addComponent(EnteteTitre1, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(267, 267, 267))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanAffichAbnLayout.createSequentialGroup()
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 944, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanAffichAbnLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTitre1, javax.swing.GroupLayout.PREFERRED_SIZE, 782, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanAffichAbnLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 873, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        PanAffichAbnLayout.setVerticalGroup(
            PanAffichAbnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanAffichAbnLayout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addComponent(EnteteTitre1)
                .addGap(4, 4, 4)
                .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 2, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTitre1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        LesFenetres.add(PanAffichAbn, "card8");

        FenUtilisateur.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel14.setText("Nom utilisateur");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel15.setText("Mot de passe");

        txtUserPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserPassActionPerformed(evt);
            }
        });

        AjoutBtnUti.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        AjoutBtnUti.setText("Ajouter");
        AjoutBtnUti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AjoutBtnUtiActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel16.setText("Téléphone");

        txtUserTel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserTelActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(0, 204, 204));
        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("ENREGISTRMENT UTILISATEUR");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel18.setText("Option");

        CbxOption.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        CbxOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Personnel", "Administrateur" }));

        ModifierBtn1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        ModifierBtn1.setText("Modifier");
        ModifierBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierBtn1ActionPerformed(evt);
            }
        });

        SupprBtn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        SupprBtn.setText("Supprimer");
        SupprBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupprBtnActionPerformed(evt);
            }
        });

        btnSupprCli2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSupprCli2.setText("Annuler");
        btnSupprCli2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupprCli2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUserPass)
                    .addComponent(txtUserTel)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(CbxOption, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AjoutBtnUti)
                .addGap(18, 18, 18)
                .addComponent(ModifierBtn1)
                .addGap(37, 37, 37)
                .addComponent(SupprBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSupprCli2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(105, 105, 105)
                        .addComponent(jLabel16))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUserPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(33, 33, 33)
                        .addComponent(txtUserTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(CbxOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AjoutBtnUti, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ModifierBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SupprBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSupprCli2)))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        TableUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nom ", "Mot de Passe ", "Téléphone", "Option"
            }
        ));
        TableUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableUserMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableUser);

        jLabel25.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel25.setText("Recherche par nom");

        txtRechUtili.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtRechUtili.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRechUtiliKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout FenUtilisateurLayout = new javax.swing.GroupLayout(FenUtilisateur);
        FenUtilisateur.setLayout(FenUtilisateurLayout);
        FenUtilisateurLayout.setHorizontalGroup(
            FenUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FenUtilisateurLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FenUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FenUtilisateurLayout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtRechUtili, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)))
        );
        FenUtilisateurLayout.setVerticalGroup(
            FenUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FenUtilisateurLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(FenUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRechUtili, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(32, 32, 32)
                .addGroup(FenUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        LesFenetres.add(FenUtilisateur, "card2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LesFenetres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LesFenetres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ClientPaneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClientPaneMousePressed
        // TODO add your handling code here:
        ClientPane.setBackground(ClickColor);
        PaneauPane.setBackground(DefaulColor);
        AbonPane.setBackground(DefaulColor);
        UtilisateurPane.setBackground(DefaulColor);
        RecherchePane.setBackground(DefaulColor);
        
        EnteteTitre.setText("Enrégistremnt Client");
    }//GEN-LAST:event_ClientPaneMousePressed

    private void PaneauPaneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PaneauPaneMousePressed
        // TODO add your handling code here:
         ClientPane.setBackground(DefaulColor);
        PaneauPane.setBackground(ClickColor);
        AbonPane.setBackground(DefaulColor);
        UtilisateurPane.setBackground(DefaulColor);
        RecherchePane.setBackground(DefaulColor);
        
        EnteteTitre.setText("Enrégistremnt Panneau");
    }//GEN-LAST:event_PaneauPaneMousePressed

    private void AbonPaneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbonPaneMousePressed
        // TODO add your handling code here:
        ClientPane.setBackground(DefaulColor);
        PaneauPane.setBackground(DefaulColor);
        AbonPane.setBackground(ClickColor);
        UtilisateurPane.setBackground(DefaulColor);
        RecherchePane.setBackground(DefaulColor);
        
        EnteteTitre.setText("Enrégistremnt Abonnement");
    }//GEN-LAST:event_AbonPaneMousePressed

    private void UtilisateurPaneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UtilisateurPaneMousePressed
        // TODO add your handling code here:
        ClientPane.setBackground(DefaulColor);
        PaneauPane.setBackground(DefaulColor);
        AbonPane.setBackground(DefaulColor);
        UtilisateurPane.setBackground(ClickColor);
        RecherchePane.setBackground(DefaulColor);
        
        EnteteTitre.setText("Enrégistremnt Employé");
    }//GEN-LAST:event_UtilisateurPaneMousePressed

    private void RecherchePaneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RecherchePaneMousePressed
        // TODO add your handling code here:
         ClientPane.setBackground(DefaulColor);
        PaneauPane.setBackground(DefaulColor);
        AbonPane.setBackground(DefaulColor);
        UtilisateurPane.setBackground(DefaulColor);
        RecherchePane.setBackground(ClickColor);
        
        EnteteTitre.setText("Recherche et autres");
    }//GEN-LAST:event_RecherchePaneMousePressed

    private void ClientPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClientPaneMouseClicked
        // TODO add your handling code here:
       FenClients.setVisible(true);
       FenAbonnement.setVisible(false);
       FenUtilisateur.setVisible(false);
       FenPanneau.setVisible(false);
       FenetreRecherche.setVisible(false);
    }//GEN-LAST:event_ClientPaneMouseClicked

    private void PaneauPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PaneauPaneMouseClicked
        // TODO add your handling code here:
        FenClients.setVisible(false);
       FenAbonnement.setVisible(false);
       FenUtilisateur.setVisible(false);
       FenPanneau.setVisible(true);
       FenetreRecherche.setVisible(false);
    }//GEN-LAST:event_PaneauPaneMouseClicked

    private void AbonPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbonPaneMouseClicked
        // TODO add your handling code here:
        AffichzPanneauLibre();
       FenClients.setVisible(false);
       FenAbonnement.setVisible(true);
       FenUtilisateur.setVisible(false);
       FenPanneau.setVisible(false);
       FenetreRecherche.setVisible(false);
        FenImpression.setVisible(false);
        PanAffichAbn.setVisible(false);
         BtnPrinteFact.setVisible(false);

    }//GEN-LAST:event_AbonPaneMouseClicked

    private void UtilisateurPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UtilisateurPaneMouseClicked
        // TODO add your handling code here:
       FenClients.setVisible(false);
       FenAbonnement.setVisible(false);
       FenUtilisateur.setVisible(true);
       FenPanneau.setVisible(false);
       FenetreRecherche.setVisible(false);
    }//GEN-LAST:event_UtilisateurPaneMouseClicked
/*
    
 *****************Début des opérations sur le clients***************
    
*/

        //*************Code Récupération des  clients dans la table*********************
    
    private void TableClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableClientMouseClicked
        // TODO add your handling code here:
        DefaultTableModel mod =new DefaultTableModel();
        mod =(DefaultTableModel)TableClient.getModel();
        int select = TableClient.getSelectedRow();
        int id = Integer.parseInt(mod.getValueAt(select, 0).toString());
        int chck= id;
        txtNomCl.setText(mod.getValueAt(select, 1).toString());
        txtAdrCli.setText(mod.getValueAt(select, 2).toString());
        txtTelCli.setText(mod.getValueAt(select, 3).toString());
        AjoutBtn.setEnabled(false);
        //JOptionPane.showMessageDialog(null, chck);;;
    }//GEN-LAST:event_TableClientMouseClicked
//*************Code Modification client dans la table*********************
    
    private void ModifierBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierBtnActionPerformed
        // TODO add your handling code here:
        String NomCl = txtNomCl.getText();
        String AdrCli = txtAdrCli.getText();
        String TelCli = txtTelCli.getText();
        if(chck==0){
            JOptionPane.showMessageDialog(null, "Veuillez Clicker l'élément à modifier");
        } else{
            DefaultTableModel mod =new DefaultTableModel();
            mod =(DefaultTableModel)TableClient.getModel();
            int select = TableClient.getSelectedRow();
            int id = Integer.parseInt(mod.getValueAt(select, 0).toString());
            
                    try {

                        prst = connect.prepareStatement("UPDATE client SET Nom_Client=?, Adresse_Client=?, Tel_Client=? WHERE Id_Client =?");
                        prst.setString(1, NomCl);
                        prst.setString(2,AdrCli);
                        prst.setString(3,TelCli);
                        prst.setInt(4,id);
                        prst.executeUpdate();
                        JOptionPane.showMessageDialog(this, " Modifier!");
                        txtNomCl.setText("");
                        txtAdrCli.setText("");
                        txtTelCli.setText("");

                    } catch (SQLException ex) {
                        Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                  

            Affiche();
            AjoutBtn.setEnabled(true);
             chck=0;
        }
    }//GEN-LAST:event_ModifierBtnActionPerformed

    //*************Code Ajoute client dans la table*********************
    
    private void AjoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjoutBtnActionPerformed
        //String reg_paternes= "^[A-Za-z0-9]$";
        //Pattern patte = Pattern.compile(reg_paternes);
        //Matcher match =patte.matcher(txtNomCl.getText());
        String NomCl = txtNomCl.getText();
        String AdrCli = txtAdrCli.getText();
        String TelCli = txtTelCli.getText();
        if(NomCl.equals("")||AdrCli.equals("")||TelCli.equals("")){
            JOptionPane.showMessageDialog(null, "Veuillez remplir Tous les champs");
        }else{

            try {
                // TODO add your handling code here:

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
                    prst1=connect.prepareStatement("SELECT * FROM   client WHERE Tel_Client=? ");
                    prst1.setString(1, TelCli);

                    rsl=prst1.executeQuery();
                    if(rsl.next()){
                        JOptionPane.showMessageDialog(this, "Ce panneau est déja! enrégistrer");
                    }else{
                        try {

                            prst = connect.prepareStatement("INSERT INTO  client(Nom_Client,Adresse_Client,Tel_Client) VALUES(?,?,?)");
                            prst.setString(1, NomCl);
                            prst.setString(2,AdrCli);
                            prst.setString(3,TelCli);
                            prst.executeUpdate();
                            JOptionPane.showMessageDialog(this, "Panneau ajouté!");
                            txtNomCl.setText("");
                            txtAdrCli.setText("");
                            txtTelCli.setText("");

                        } catch (SQLException ex) {
                            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
                        }                     }
                    } catch (SQLException ex) {
                        Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
                }
                Affiche();
            }
    }//GEN-LAST:event_AjoutBtnActionPerformed

    private void txtNomClActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomClActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomClActionPerformed

//*************Code Vérification téléphone  client dans la table*********************
    
    private void txtTelCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelCliKeyPressed
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(Character.isLetter(c)){
            txtTelCli.setEditable(false);
        }else{
            txtTelCli.setEditable(true);
        }
    }//GEN-LAST:event_txtTelCliKeyPressed

    /*
    
 *****************Fin des opérations sur le clients***************
    
*/
    
    /*
    
 *****************Début des opérations sur les Utilisateurs ***************
    
*/
    //*************Code Vérification Mot de passe  utilisateur *********************

    private void txtUserPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserPassActionPerformed

        //*************Code Ajoue  utilisateur dans la table*********************

    private void AjoutBtnUtiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjoutBtnUtiActionPerformed
        try {
            // TODO add your handling code here:
            String Username = txtUsername.getText();
            String UserPass = txtUserPass.getText();
            String UserTel = txtUserTel.getText();
            String UserOption =  CbxOption.getSelectedItem().toString();

            if(txtUsername.getText().equals("")||UserPass.equals("")||UserTel.equals("")){

                JOptionPane.showMessageDialog(null, "Veuillez remplir Tous les champs");

            }else{
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
                    prst1=connect.prepareStatement("SELECT * FROM admin WHERE Nom_Admin=? ");
                    prst1.setString(1, UserTel);

                    rsl=prst1.executeQuery();
                    if(rsl.next()){
                        JOptionPane.showMessageDialog(this, "Utilisateur existe déja!");
                    }else{
                        try {

                            prst = connect.prepareStatement("INSERT INTO admin(Nom_Admin,PasseWrdAdmin,telephone_Admin,OptionPersonnel) VALUES(?,?,?,?)");
                            prst.setString(1, Username);
                            prst.setString(2,UserPass);
                            prst.setString(3,UserTel);
                            prst.setString(4,UserOption);
                            prst.executeUpdate();
                            JOptionPane.showMessageDialog(this, "Utilisateur ajouté!");
                            txtUsername.setText("");
                            txtUserPass.setText("");
                            txtUserTel.setText("");

                        } catch (SQLException ex) {
                            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
                        }                     }
                    } catch (SQLException ex) {
                        Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
            }
           AfficheUTILISATEUR();

    }//GEN-LAST:event_AjoutBtnUtiActionPerformed

            //*************Code Modification  utilisateur dans la table*********************

    private void ModifierBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierBtn1ActionPerformed
        // TODO add your handling code here:
        String Username = txtUsername.getText();
        String UserPass = txtUserPass.getText();
        String UserTel = txtUserTel.getText();
        String UserOption =  CbxOption.getSelectedItem().toString();
        if(chck==0){
            JOptionPane.showMessageDialog(null, "Veuillez Clicker l'élément à modifier");
        } else{
            DefaultTableModel mod =new DefaultTableModel();
            mod =(DefaultTableModel)TableUser.getModel();
            int select = TableUser.getSelectedRow();
            int id = Integer.parseInt(mod.getValueAt(select, 0).toString());

            try {

                prst = connect.prepareStatement("UPDATE admin SET Nom_Admin=?, PasseWrdAdmin=?, telephone_Admin=? ,OptionPersonnel=? WHERE id_Admin =?");
                prst.setString(1, Username);
                prst.setString(2,UserPass);
                prst.setString(3,UserTel);
                prst.setString(4,UserOption);
                prst.setInt(5,id);
                prst.executeUpdate();
                JOptionPane.showMessageDialog(this, " Modifier!");
                txtUsername.setText("");
                txtUserPass.setText("");
                txtUserTel.setText("");

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*}
        } catch (Exception e) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, e);
        } */

        AfficheUTILISATEUR();
        AjoutBtnUti.setEnabled(true);
        chck=0;
        }
    }//GEN-LAST:event_ModifierBtn1ActionPerformed
          
    //*************Code Suppression  utilisateur dans la table*********************

    private void SupprBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupprBtnActionPerformed
        // TODO add your handling code here:
        String Username = txtUsername.getText();
            if(chck==0){
            JOptionPane.showMessageDialog(null, "Veuillez Clicker l'élément à modifier");
        }else if(Username.equals("")){
            JOptionPane.showMessageDialog(null, "Veuillez Clicker l'élément à Supprimer");
        } else{
            DefaultTableModel mod =new DefaultTableModel();
            mod =(DefaultTableModel)TableUser.getModel();
            int select = TableUser.getSelectedRow();
            int id = Integer.parseInt(mod.getValueAt(select, 0).toString());

            // TODO add your handling code here:
            /*String localisation = txtlocalisation.getText();
            String taille = txtTaille.getText();
            String prix = txtPrix.getText();*/

            try {

                prst = connect.prepareStatement("DELETE FROM admin  WHERE id_Admin =?");
                prst.setInt(1,id);
                prst.executeUpdate();
                JOptionPane.showMessageDialog(this, " Supprimer!");
                txtUsername.setText("");
                txtUserPass.setText("");
                txtUserTel.setText("");

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
            }
         chck=0;
        AfficheUTILISATEUR();
        AjoutBtnUti.setEnabled(true);
        }
    }//GEN-LAST:event_SupprBtnActionPerformed

                //*************Code Récupération des  utilisateur dans la table*********************

    private void TableUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableUserMouseClicked
        // TODO add your handling code here:
        DefaultTableModel mod =new DefaultTableModel();
        mod =(DefaultTableModel)TableUser.getModel();
        int select = TableUser.getSelectedRow();
        int id = Integer.parseInt(mod.getValueAt(select, 0).toString());
        int chck =id;
        txtUsername.setText(mod.getValueAt(select, 1).toString());
        txtUserPass.setText(mod.getValueAt(select, 2).toString());
        txtUserTel.setText(mod.getValueAt(select, 3).toString());
        AjoutBtnUti.setEnabled(false);
    }//GEN-LAST:event_TableUserMouseClicked
//bouton se déconnecter
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
         new Admin().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

 /*
    
   *****************Fin des opérations sur les Utilisateurs ***************
    
*/
    
 /*
    
 *****************Début des opérations sur les Panneaux ***************
    
*/
    //code Affiche les panneausx dans la table*************
    private void TablePanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablePanMouseClicked
        // TODO add your handling code here:

        DefaultTableModel mod =new DefaultTableModel();
        mod =(DefaultTableModel)TablePan.getModel();
        int select = TablePan.getSelectedRow();
        int id = Integer.parseInt(mod.getValueAt(select, 0).toString());
        chck=id;
        txtItner.setText(mod.getValueAt(select, 1).toString());
        txtlocalisation.setText(mod.getValueAt(select, 2).toString());
        txtTaille.setText(mod.getValueAt(select, 3).toString());
        txtPrix.setText(mod.getValueAt(select, 4).toString());
        AjoutBtn1.setEnabled(false);
    }//GEN-LAST:event_TablePanMouseClicked

    private void txtTailleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTailleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTailleActionPerformed

   
//codeVérification du champ taille*************
    private void txtTailleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTailleKeyPressed
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(Character.isLetter(c)){
            txtTaille.setEditable(false);
        }else{
            txtTaille.setEditable(true);
        }
    }//GEN-LAST:event_txtTailleKeyPressed

    //code Ajoue les panneausx dans la table*************
    private void AjoutBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjoutBtn1ActionPerformed
        String reg_paternes= "^[a-zA-Z0-9]{1,25}$";
        Pattern patte = Pattern.compile(reg_paternes);
        Matcher match =patte.matcher(txtlocalisation.getText());
        String localisation = txtlocalisation.getText();
        String taille = txtTaille.getText();
        String prix = txtPrix.getText();
        String Itne = txtItner.getText();
        if(localisation.equals("")||taille.equals("")||prix.equals("")){
            JOptionPane.showMessageDialog(null, "Veuillez remplir Tous les champs");
        }else if(match.matches()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir un nom valide");

        }else{

            try {
                // TODO add your handling code here:

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
                    prst1=connect.prepareStatement("SELECT * FROM  panneau WHERE Localisation_panneau=? ");
                    prst1.setString(1, localisation);

                    rsl=prst1.executeQuery();
                    if(rsl.next()){
                        JOptionPane.showMessageDialog(this, "Ce panneau est déja! enrégistrer");
                    }else{
                        try {

                            prst = connect.prepareStatement("INSERT INTO  panneau(Itineraire,Localisation_panneau,Taille,Prix) VALUES(?,?,?,?)");
                             prst.setString(1, Itne);
                            prst.setString(2, localisation);
                            prst.setString(3,taille);
                            prst.setString(4,prix);
                            prst.executeUpdate();
                            JOptionPane.showMessageDialog(this, "Panneau ajouté!");
                            txtlocalisation.setText("");
                            txtTaille.setText("");
                            txtPrix.setText("");
                            txtItner.setText("");

                        } catch (SQLException ex) {
                            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
                        }                     }
                    } catch (SQLException ex) {
                        Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
                }
               AffichePANNEAU();
            }
    }//GEN-LAST:event_AjoutBtn1ActionPerformed

    //code modification les panneausx dans la table*************
    private void ModifierBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierBtn2ActionPerformed
        // TODO add your handling code here:
        String localisation = txtlocalisation.getText();
        String taille = txtTaille.getText();
        String prix = txtPrix.getText();
        if( chck==0){
            JOptionPane.showMessageDialog(null, "Veuillez Clicker l'élément à modifier");
        } else{
            DefaultTableModel mod =new DefaultTableModel();
            mod =(DefaultTableModel)TablePan.getModel();
            int select = TablePan.getSelectedRow();
            int id = Integer.parseInt(mod.getValueAt(select, 0).toString());

        

                    try {

                        prst = connect.prepareStatement("UPDATE panneau SET Localisation_panneau=?, Taille=?, Prix=? WHERE Id_panneau =?");
                        prst.setString(1, localisation);
                        prst.setString(2,taille);
                        prst.setString(3,prix);
                        prst.setInt(4,id);
                        prst.executeUpdate();
                        JOptionPane.showMessageDialog(this, " Modifier!");
                        txtlocalisation.setText("");
                        txtTaille.setText("");
                        txtPrix.setText("");

                    } catch (SQLException ex) {
                        Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    /*}
            } catch (Exception e) {
                Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, e);
            } */

           AffichePANNEAU();
            AjoutBtn1.setEnabled(true);
            chck=0;
        }
    }//GEN-LAST:event_ModifierBtn2ActionPerformed

    //codeVérification du champ Prix*************
    private void txtPrixKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrixKeyPressed
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(Character.isLetter(c)){
            txtPrix.setEditable(false);
        }else{
            txtPrix.setEditable(true);
        }
    }//GEN-LAST:event_txtPrixKeyPressed

       //code modification les panneausx dans la table*************
    
    private void SupprBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupprBtn1ActionPerformed
        // TODO add your handling code here:
        String localisation = txtlocalisation.getText();
        if(localisation.equals("")||chck==0){
            JOptionPane.showMessageDialog(null, "Veuillez Clicker l'élément à Supprimer");
        } else{
            DefaultTableModel mod =new DefaultTableModel();
            mod =(DefaultTableModel)TablePan.getModel();
            int select = TablePan.getSelectedRow();
            int id = Integer.parseInt(mod.getValueAt(select, 0).toString());

            // TODO add your handling code here:
            /*String localisation = txtlocalisation.getText();
            String taille = txtTaille.getText();
            String prix = txtPrix.getText();*/

            try {

                prst = connect.prepareStatement("DELETE FROM panneau  WHERE Id_panneau =?");
                prst.setInt(1,id);
                prst.executeUpdate();
                JOptionPane.showMessageDialog(this, " Supprimer!");
                txtlocalisation.setText("");
                txtTaille.setText("");
                txtPrix.setText("");

            } catch (SQLException ex) {
                Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*}
        } catch (Exception e) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, e);
        } */

        AffichePANNEAU();
        AjoutBtn1.setEnabled(true);
        }
    }//GEN-LAST:event_SupprBtn1ActionPerformed

     /*
    
 *****************Début des opérations sur les Abonnements ***************
    
*/
    private void txtTelCliAbonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelCliAbonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelCliAbonActionPerformed

     @SuppressWarnings("empty-statement")
    private void btnAjouAbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjouAbnActionPerformed
        
             // TODO add your handling code here:
             
            
             
             Date DteDeb = txtDatDeb.getDate();
             Date DteFin = txtDatFin.getDate();
             String DateDeb = ((JTextField)txtDatDeb.getDateEditor().getUiComponent()).getText();
             String DateFin = ((JTextField)txtDatFin.getDateEditor().getUiComponent()).getText();
             int MtTotalApay = Integer.parseInt(txtMontantAPay.getText());
             String TelCliAbn = txtTelCliAbon.getText();
             String IdPanLibr=MntRest.getText();
             int NbrPan =Integer.parseInt(txtNbrPan.getText());
             //NBRPANS=NbrPan;
            
            
            if(DateDeb.equals("")||DateFin.equals("")||TelCliAbn.equals("")||IdPanLibr.equals("")){
                JOptionPane.showMessageDialog(null, "Veuillez remplire tous les champs");
        }   else if(DteDeb.after(DteFin)){
            JOptionPane.showMessageDialog(null, "Veuillez la date du début ne devrait pas dépasser la date de fin abonnement");

        }   else{
                 try {
                      int Mtn = Integer.parseInt(txtMontantAPay.getText()) ;
                     Class.forName("com.mysql.cj.jdbc.Driver");
                     connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
                     prst=connect.prepareStatement("SELECT * FROM client WHERE Tel_Client =?");
                     prst.setString(1, TelCliAbn);
                     rsl=prst.executeQuery();
                     if(rsl.next()){
                            DefaultTableModel mod =new DefaultTableModel();
                            mod =(DefaultTableModel)TblAbonClient.getModel();
                            idCliAb=rsl.getInt("Id_Client");
                            Nomclient=rsl.getString("Nom_Client");
                             //insertion dans la table Abonnement
                            prst=connect.prepareStatement("INSERT INTO abonnement(DateDebut,DateFin,TelClient,Nom_Client,NbrJours,NbrPanneau,MontantTotApayer,MontantPayer,MontantRestant,nom_Utilisateur )VALUE(?,?,?,?,?,?,?,?,?,?)");
                            prst.setString(1, DateDeb);
                            prst.setString(2, DateFin);
                            prst.setString(3, TelCliAbn);
                            prst.setString(4, Nomclient);
                            prst.setInt(5, NbrJrs);
                            prst.setInt(6, NbrPan);
                            prst.setInt(7, MtTotalApay);
                            prst.setInt(8,  Integer.parseInt(MtnPayer.getText()));
                            prst.setInt(9, Integer.parseInt(MntRest.getText()));
                            prst.setString(10, lblUserCon.getText());
                            //insertion dans la table Ligne Abonnement
                            prst1=connect.prepareStatement("INSERT INTO  ligneabonnement(Idpan,Itineraire,Localisation,IdClient,DateDebut,DateFin )VALUE(?,?,?,?,?,?)");
                           for(int i=0 ;i<TblAbonClient.getRowCount();i++){
                            prst1.setString(1, mod.getValueAt((int)i, 0).toString());
                            prst1.setString(2, mod.getValueAt((int)i, 1).toString());
                            prst1.setString(3, mod.getValueAt((int)i, 2).toString());
                            prst1.setInt(4, idCliAb);
                            prst1.setString(5, DateDeb);
                            prst1.setString(6, DateFin);
                            prst1.executeUpdate();
                           }
                            prst.executeUpdate();
                            prst2=connect.prepareStatement("SELECT IdAbon FROM  abonnement WHERE TelClient=? AND IdAbon=(SELECT MAX(IdAbon) FROM abonnement) ");
                            prst2.setString(1, txtTelCliAbon.getText());
                            rsl2=prst2.executeQuery();

                             while(rsl2.next()){
                                NumFact = rsl2.getInt("IdAbon");
                            }
                             
                            JOptionPane.showMessageDialog(null, "Abonnement effectué avec succès");
                            Impression();
                               for(int i=TblAbonClient.getRowCount()-1 ;i>=0;i--){
                                mod.removeRow(i);
                               }
                            txtTelCliAbon.setText("");
                            txtMontantAPay.setText("");
                            MntRest.setText("");
                            MtnPayer.setText("");
                            txtNbrPan.setText("");
                            txtDatDeb.setCalendar(null);
                            txtDatFin.setCalendar(null);
                            FenAbonnement.setVisible(false);
                            FenImpression.setVisible(true);
                     }else{
                        JOptionPane.showMessageDialog(null, "Aucun client n a ce numéro dans la base");
                     }
                 } catch (ClassNotFoundException ex) {
                     Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (SQLException ex) {
                     Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
                 }
           
        
        }
  
        
    }//GEN-LAST:event_btnAjouAbnActionPerformed

    private void txtTelCliAbonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelCliAbonKeyPressed
        // TODO add your handling code here:
        
        char c = evt.getKeyChar();
        if(Character.isLetter(c)){
            txtTelCliAbon.setEditable(false);
        }else{
            txtTelCliAbon.setEditable(true);
        }
    }//GEN-LAST:event_txtTelCliAbonKeyPressed

    private void btnSupprCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupprCliActionPerformed
        // SUPRESSION CLIENT 
        String NomCl = txtNomCl.getText();
        String AdrCli = txtAdrCli.getText();
        String TelCli = txtTelCli.getText();
        if( chck==0){
            JOptionPane.showMessageDialog(null, "Veuillez Clicker l'élément à modifier");
        } else{
            DefaultTableModel mod =new DefaultTableModel();
            mod =(DefaultTableModel)TableClient.getModel();
            int select = TableClient.getSelectedRow();
            int id = Integer.parseInt(mod.getValueAt(select, 0).toString());

                    try {
                        prst = connect.prepareStatement("DELETE FROM client  WHERE Id_Client=?");
                        prst.setInt(1,id);
                        prst.executeUpdate();
                        JOptionPane.showMessageDialog(this, " Supprimer!");
                        txtUsername.setText("");
                        txtUserPass.setText("");
                        txtUserTel.setText("");
                    } catch (SQLException ex) {
                        Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
                    }
            Affiche();
            AjoutBtn.setEnabled(true);
        }
    }//GEN-LAST:event_btnSupprCliActionPerformed

    private void txtrechCliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrechCliKeyReleased
     rechechClient(txtrechCli.getText());
    
  
    }//GEN-LAST:event_txtrechCliKeyReleased

    private void txtRechUtiliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRechUtiliKeyReleased
      rechercheUtilisateur(txtRechUtili.getText());
    }//GEN-LAST:event_txtRechUtiliKeyReleased

    private void recherchPanoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_recherchPanoKeyReleased
       recherchPanneau(recherchPano.getText());
    }//GEN-LAST:event_recherchPanoKeyReleased

    private void recherchPanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recherchPanoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_recherchPanoActionPerformed
//RECHERCHE PANNEAUX COMBOBOX
    private void cbxrecherchPannoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxrecherchPannoActionPerformed
       String options =cbxrecherchPanno.getSelectedItem().toString();
       if(options.equalsIgnoreCase("Tous les Panneaux")){
          try {
            String donnee []={"Identifiant","Localisation","Taille","Prix"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            
            prst1=connect.prepareStatement("SELECT * FROM  panneau ");
            rsl=prst1.executeQuery();
            while(rsl.next()){
                Object ob[]={
                    rsl.getString("Id_panneau"),
                    rsl.getString("Localisation_panneau"),
                    rsl.getString("Taille"),
                    rsl.getString("Prix")
                };
                model.addRow(ob);
            }
            TablePan.setModel(model);
            //tablePanAbon.setModel(model);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
        }
       }else if(options.equalsIgnoreCase("Panneaux Libres")){
         
           //AffichePanoLibre();
            AffichePanoOcupe();
       }else {
           //AffichePanoOcupe(); 
           AffichePanoLibre();
       }
    }//GEN-LAST:event_cbxrecherchPannoActionPerformed

    private void txtpanoLibreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpanoLibreKeyReleased
        // rechercher panneau libre
        recherchePanoLibre(txtpanoLibre.getText());
    }//GEN-LAST:event_txtpanoLibreKeyReleased

    private void RecherchePaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RecherchePaneMouseClicked
 
             // TODO add your handling code here:
          
             FenetreRecherche.setVisible(true);
             FenClients.setVisible(false);
             FenAbonnement.setVisible(false);
             FenUtilisateur.setVisible(false);
             FenPanneau.setVisible(false);
             AffichageAbonnement();
             txtTitre.setText("Tous les Abonnements");

             try {
             Class.forName("com.mysql.cj.jdbc.Driver");
             connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
             prst=connect.prepareStatement("SELECT COUNT(IdAbon  )AS 'Nbr' FROM abonnement");
             rsl=prst.executeQuery();
             prst1=connect.prepareStatement("SELECT SUM(MontantPayer) AS 'revenu' FROM abonnement");
             rsl1=prst1.executeQuery();
             if(rsl.next()){
                 lblNbrTotal.setText(rsl.getString("Nbr"));
             }
              if(rsl1.next()){
                 lblRevenu.setText(rsl1.getString("revenu"));
             }
         } catch (SQLException ex) {
             Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
         }
             
          
    }//GEN-LAST:event_RecherchePaneMouseClicked

    private void tableAbonnementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAbonnementMouseClicked
        // TODO add your handling code here:
        
        DefaultTableModel mod =new DefaultTableModel();
        mod =(DefaultTableModel)tableAbonnement.getModel();
        int select = tableAbonnement.getSelectedRow();
        int id = Integer.parseInt(mod.getValueAt(select, 0).toString());
        chck=id;
        
        NumClient.setText( (mod.getValueAt(select, 3).toString()));
        
    }//GEN-LAST:event_tableAbonnementMouseClicked

    private void btnSupprAbn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupprAbn1ActionPerformed
        // SUPRESSION ABONNEMENT
            //DefaultTableModel mod =new DefaultTableModel();
            //mod =(DefaultTableModel)tableAbonnement.getModel();
            //int select = tableAbonnement.getSelectedRow();
            //int id = Integer.parseInt(mod.getValueAt(select, 0).toString());
          
            if(chck==0){
                JOptionPane.showMessageDialog(this, " Veuillez clickez sur l'élément à supprimé!");
            }else{
                int reponse=JOptionPane.showConfirmDialog(null, "Vous voulez vraiment suprimer?","Confirmer",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
              
                 if(reponse==JOptionPane.YES_OPTION){
                    try {
                        
                    prst = connect.prepareStatement("DELETE FROM  abonnement  WHERE IdAbon  =?");
                prst.setInt(1,chck);
                prst.executeUpdate();
                JOptionPane.showMessageDialog(this, " Supprimer!");
               
            } catch (SQLException ex) {
                Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
           
         AffichageAbonnement();
        chck=0;
                 }
                
    }//GEN-LAST:event_btnSupprAbn1ActionPerformed

    private void txtRchercheAbonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRchercheAbonKeyReleased
        //RECHERCHE ABONNEMENT PAR TEL CLIENT
        
        RchercheAbonParTelCli(txtRchercheAbon.getText());
    }//GEN-LAST:event_txtRchercheAbonKeyReleased

    private void btnObteniResultatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObteniResultatActionPerformed
        // RESULTAT REAQUET LA LES ABONNEMENT DURANT UNE PERIODE DONNEE
        Date dtinf= DtInf.getDate();
        Date dtSupp =DtSupp.getDate();
        String DtInfs =((JTextField)DtInf.getDateEditor().getUiComponent()).getText();
        String DtSupps=((JTextField)DtSupp.getDateEditor().getUiComponent()).getText();
        //JOptionPane.showMessageDialog(this, dtinf);
        if(DtInfs.equals("")||DtSupps.equals("")){
        JOptionPane.showMessageDialog(this, " Veuillez délimitez la période!");
        }else if(dtinf.after(dtSupp)){
        
             JOptionPane.showMessageDialog(this, " Vérifiez l'ordre des dates saisies");
        }else{
            try {
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                String dt1 = smdf.format(dtinf);
                String dt2 = smdf.format(dtSupp);
                //JOptionPane.showMessageDialog(this, dt1);
                String donnee []={"Identifiant","DateDebut","DateFin","TelClient ","Nom_Client","NbrJours","NbrPanneau","MontantPayer"};
                DefaultTableModel model = new DefaultTableModel(null,donnee);
                Class.forName("com.mysql.cj.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
                prst=connect.prepareStatement("SELECT * FROM abonnement WHERE DateDebut BETWEEN ? AND ?");
                prst.setString(1, dt1);
                prst.setString(2, dt2);
                rsl=prst.executeQuery();
                while(rsl.next()){
                Object ob[]={
                    rsl.getString("IdAbon"),
                    rsl.getString("DateDebut"),
                    rsl.getString("DateFin"),
                    rsl.getString("TelClient"),
                    rsl.getString("Nom_Client"),
                    rsl.getString("NbrJours"),
                    rsl.getString("NbrPanneau"),
                    rsl.getString("MontantPayer"),

                };
                model.addRow(ob);
                
               //compter le nombre d abonnement et le revenu total
             prst2=connect.prepareStatement("SELECT COUNT(IdAbon)AS 'Nbr' FROM abonnement WHERE DateDebut BETWEEN ? AND ?");
             prst2.setString(1, dt2);
             prst2.setString(2, dt2);
             rsl2=prst2.executeQuery();
             prst1=connect.prepareStatement("SELECT SUM(MontantPayer) AS 'revenu' FROM abonnement WHERE DateDebut BETWEEN ? AND ?");
             prst1.setString(1, dt1);
             prst1.setString(2, dt2);
             rsl1=prst1.executeQuery();
             if(rsl2.next()){
                 lblNbrTotal.setText(rsl2.getString("Nbr"));
             }
              if(rsl1.next()){
                 lblRevenu.setText(rsl1.getString("revenu"));
             }
            }
                
            ////////////////////////////////
            tableAbonnement.setModel(model);
            tablePannAff2.setModel(model);
            txtTitre1.setText("Les Abonnements entre la période du "+dt1+" au "+dt2 );
            txtTitre.setText("Les Abonnements entre les périodes saisies");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
        
    }//GEN-LAST:event_btnObteniResultatActionPerformed

    private void MntRestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MntRestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MntRestActionPerformed

    private void MntRestKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MntRestKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MntRestKeyPressed

    private void txtUserTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserTelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserTelActionPerformed

    private void btnSupprCli1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupprCli1ActionPerformed
        // TODO add your handling code here:
         txtNomCl.setText("");
         txtAdrCli.setText("");
         txtTelCli.setText("");
         AjoutBtn.setEnabled(true);
         chck=0;
    }//GEN-LAST:event_btnSupprCli1ActionPerformed

    private void btnSupprCli2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupprCli2ActionPerformed
        // TODO add your handling code here:
         txtUsername.setText("");
         txtUserPass.setText("");
         txtUserTel.setText("");
    }//GEN-LAST:event_btnSupprCli2ActionPerformed

    private void btnSupprCli3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupprCli3ActionPerformed
        // TODO add your handling code here:
         txtlocalisation.setText("");
         txtTaille.setText("");
         txtPrix.setText("");
         chck=0;
         AjoutBtn1.setEnabled(true);
    }//GEN-LAST:event_btnSupprCli3ActionPerformed

    private void BtnPrinteFactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrinteFactActionPerformed
        this.setVisible(false);
        try {
             // TODO add your handling code here:
             txtPrinter.print();
         } catch (PrinterException ex) {
             Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
         }
         txtPrinter.setText(" ");
         txtPrinter.setText(" ");
         txtPrinter.setText(" ");
         txtPrinter.setText(" ");

    }//GEN-LAST:event_BtnPrinteFactActionPerformed

    private void btnAbnExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbnExpActionPerformed
        // TODO add your handling code here:
        Date dateSys = new Date();
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        String dateSystem=spf.format(dateSys);
        
        Date dtinf= DtInf.getDate();
        Date dtSupp =DtSupp.getDate();
        String DtInfs =((JTextField)DtInf.getDateEditor().getUiComponent()).getText();
        String DtSupps=((JTextField)DtSupp.getDateEditor().getUiComponent()).getText();
        //JOptionPane.showMessageDialog(this, dtinf);
        if(DtInfs.equals("")||DtSupps.equals("")){
        JOptionPane.showMessageDialog(this, " Veuillez délimitez la période!");
        }else if(dtinf.after(dtSupp)){
        
             JOptionPane.showMessageDialog(this, " Vérifiez l'ordre des dates saisies");
        }else{
            try {
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                String dt1 = smdf.format(dtinf);
                String dt2 = smdf.format(dtSupp);
                //JOptionPane.showMessageDialog(this, dt1);
                String donnee []={"Identifiant","DateDebut","DateFin","TelClient ","Nom_Client","NbrJours","NbrPanneau","MontantPayer"};
                DefaultTableModel model = new DefaultTableModel(null,donnee);
                Class.forName("com.mysql.cj.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
                prst=connect.prepareStatement("SELECT * FROM abonnement WHERE DateFin<?");
                prst.setString(1, dateSystem);
                
                rsl=prst.executeQuery();
                while(rsl.next()){
                Object ob[]={
                    rsl.getString("IdAbon"),
                    rsl.getString("DateDebut"),
                    rsl.getString("DateFin"),
                    rsl.getString("TelClient"),
                    rsl.getString("Nom_Client"),
                    rsl.getString("NbrJours"),
                    rsl.getString("NbrPanneau"),
                    rsl.getString("MontantPayer"),
                   
                        
                };
                model.addRow(ob);
                ////////COMPTER ET REVENU TOTAL
                
             prst2=connect.prepareStatement("SELECT COUNT(IdAbon)AS 'Nbr' FROM abonnement WHERE DateFin<?");
             prst2.setString(1, dateSystem);
             rsl2=prst2.executeQuery();
             prst1=connect.prepareStatement("SELECT SUM(MontantPayer) AS 'revenu' FROM abonnement WHERE IdAbon IN (SELECT IdAbon  FROM abonnement WHERE DateFin<?)");
             prst1.setString(1, dateSystem);
             rsl1=prst1.executeQuery();
             if(rsl2.next()){
                 lblNbrTotal.setText(rsl2.getString("Nbr"));
             }
              if(rsl1.next()){
                 lblRevenu.setText(rsl1.getString("revenu"));
             }
                ////////////////////
            }
            tableAbonnement.setModel(model);
            tablePannAff2.setModel(model);

            txtTitre.setText("Les Abonnements expirés entre la période du " +dt1+" au " +dt2);
            txtTitre1.setText("Les Abonnements expirés");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }//GEN-LAST:event_btnAbnExpActionPerformed

    private void BtnPrinteFact1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrinteFact1ActionPerformed
        
        //PrintEtats(PanAffichAbn);
            
               int reponse=JOptionPane.showConfirmDialog(null, "Vous voulez vraiment Imprimer cette lister?","Confirmer",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
              
                 if(reponse==JOptionPane.YES_OPTION){
                 MessageFormat header = new MessageFormat(txtTitre.getText());
                 MessageFormat footer = new MessageFormat("   CENTRE DE CONTROLE DES PANNEAUX PUBLICITAIRES  ");
            try {
                tableAbonnement.print(JTable.PrintMode.NORMAL, header, footer);
            } catch (PrinterException ex) {
                Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    }//GEN-LAST:event_BtnPrinteFact1ActionPerformed

    private void txtRchercheAbonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRchercheAbonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRchercheAbonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
       
       
        Date dtinf= DtInf.getDate();
        Date dtSupp =DtSupp.getDate();
        String DtInfs =((JTextField)DtInf.getDateEditor().getUiComponent()).getText();
        String DtSupps=((JTextField)DtSupp.getDateEditor().getUiComponent()).getText();
        //JOptionPane.showMessageDialog(this, dtinf);
        if(DtInfs.equals("")||DtSupps.equals("")){
        JOptionPane.showMessageDialog(this, " Veuillez délimitez la période!");
        }else if(dtinf.after(dtSupp)){
        
             JOptionPane.showMessageDialog(this, " Vérifiez l'ordre des dates saisies");
        }else{
            try {
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                String dt1 = smdf.format(dtinf);
                String dt2 = smdf.format(dtSupp);
                //JOptionPane.showMessageDialog(this, dt1);
                String donnee []={"Nom_Client","TelClient","Total"};
                DefaultTableModel model = new DefaultTableModel(null,donnee);
                Class.forName("com.mysql.cj.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
                prst=connect.prepareStatement("SELECT Nom_Client,TelClient,SUM(MontantPayer) AS 'Total' FROM `abonnement` WHERE DateDebut BETWEEN ? AND ? GROUP BY TelClient  ORDER BY SUM(MontantPayer) DESC  LIMIT 3");
                prst.setString(1, dt1);
                prst.setString(2, dt2);
                rsl=prst.executeQuery();
                while(rsl.next()){
                Object ob[]={
                    rsl.getString("Nom_Client"),
                    rsl.getString("TelClient"),
                    rsl.getString("Total")
             
                };
                model.addRow(ob);
                
              
            }
                
            ////////////////////////////////
            tableAbonnement.setModel(model);
            tablePannAff2.setModel(model);
            txtTitre.setText("Les trois meilleurs clients entre la période du "+dt1+" au "+dt2 );
            //txtTitre.setText("Les Abonnements entre les périodes saisies");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        tableAbonnement.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableAbonnement.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableAbonnement.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableAbonnement.getColumnModel().getColumn(2).setPreferredWidth(100);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tablePanAbonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePanAbonMousePressed

    }//GEN-LAST:event_tablePanAbonMousePressed

    private void tablePanAbonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePanAbonMouseClicked
        //  TODO RECUPERATION DE L ID PANNEAU POUR UN ABONNEMENT
        Date DteDeb = txtDatDeb.getDate();
        Date DteFin = txtDatFin.getDate();
        String NbrPan =(txtNbrPan.getText());

        String DateDeb = ((JTextField)txtDatDeb.getDateEditor().getUiComponent()).getText();
        String DateFin = ((JTextField)txtDatFin.getDateEditor().getUiComponent()).getText();

        if(DateDeb.equals("")||DateFin.equals("")){
            JOptionPane.showMessageDialog(null, "Veuillez définir les dates d'abord ainsi aue le nombre de panneau a occupé");
        }else if(DteDeb.after(DteFin)){
            JOptionPane.showMessageDialog(null, "Veuillez la date du début ne devrait pas dépasser la date de fin abonnement");

        }else{
            //int NbrPn = Integer.parseInt(NbrPan);
            LocalDate DtDeb = LocalDate.parse(DateDeb);
            LocalDate Dtfin = LocalDate.parse(DateFin);
            NbrJrs=(int) ChronoUnit.DAYS.between(DtDeb, Dtfin);
            DefaultTableModel mod =new DefaultTableModel();
            mod =(DefaultTableModel)tablePanAbon.getModel();
            DefaultTableModel Tblmod2 = (DefaultTableModel)TblAbonClient.getModel();
            int select = tablePanAbon.getSelectedRow();
            int id = Integer.parseInt(mod.getValueAt(select, 0).toString());
             for(int i=0 ;i<TblAbonClient.getRowCount();i++){
                          if(String.valueOf(id).equals(Tblmod2.getValueAt((int)i, 0).toString())){
                                JOptionPane.showMessageDialog(null, "Ce panneau existe déjà veuillez le retirer");
                               
                          }
                       }
            Prix = Integer.parseInt(mod.getValueAt(select, 4).toString());
            MntRest.setText((mod.getValueAt(select, 0).toString()));
            LeNbrPan++;
            txtNbrPan.setText( String.valueOf(LeNbrPan));
            MtnAbn += Prix;
            MtnTotalAbn=MtnAbn*NbrJrs;
            txtMontantAPay.setText(String.valueOf(MtnTotalAbn) );
            MtnPayer.setText(txtMontantAPay.getText());
            MntRest.setText( String.valueOf(MtnTotalAbn- Integer.parseInt(MtnPayer.getText())));
            
            
            String datas[]={
                (mod.getValueAt(select, 0).toString()),
                (mod.getValueAt(select, 1).toString()),
                (mod.getValueAt(select, 2).toString()),
                (mod.getValueAt(select, 3).toString()),
                (mod.getValueAt(select, 4).toString())
                    
            };
           
            Tblmod2.addRow(datas);
        }
     

    }//GEN-LAST:event_tablePanAbonMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtBoulevardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBoulevardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBoulevardActionPerformed

    private void txtBoulevardKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBoulevardKeyReleased
            // TODO add your handling code here:
            RecherPanBoulevard(txtBoulevard.getText());
    }//GEN-LAST:event_txtBoulevardKeyReleased

    private void txtItnerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItnerKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItnerKeyPressed

    private void btnRetitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetitActionPerformed
        // TODO add your handling code here:
        
         DefaultTableModel Tblmod2 = (DefaultTableModel)TblAbonClient.getModel();
         if( TblAbonClient.getSelectedRow()==-1){
              JOptionPane.showMessageDialog(null, "Veuillez sélestionner lélément à retirer ou le table est vide");
         }else{
              int select = TblAbonClient.getSelectedRow();
          Prix = Integer.parseInt(Tblmod2.getValueAt(select, 4).toString());
        
          LeNbrPan--;
          txtNbrPan.setText( String.valueOf(LeNbrPan));
          MtnAbn-=Prix;
          MtnTotalAbn -= Prix*NbrJrs;
          txtMontantAPay.setText(String.valueOf(MtnTotalAbn) );
          MtnPayer.setText(String.valueOf(MtnTotalAbn));
          Tblmod2.removeRow(TblAbonClient.getSelectedRow());
         }
         
       
       
    }//GEN-LAST:event_btnRetitActionPerformed

    private void MtnPayerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MtnPayerKeyReleased
        // TODO add your handling code here:     
       MntRest.setText( String.valueOf(MtnTotalAbn- Integer.parseInt(MtnPayer.getText())));
    }//GEN-LAST:event_MtnPayerKeyReleased

    private void MtnPayerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MtnPayerKeyPressed
        // TODO add your handling code here:
        //MntRest.setText( String.valueOf(MtnAbn- Integer.parseInt(MtnPayer.getText())));
    }//GEN-LAST:event_MtnPayerKeyPressed

    private void btnAnnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnnActionPerformed
        // TODO add your handling code here:
        txtDatDeb.setCalendar(null);
        txtDatFin.setCalendar(null);
        txtNbrPan.setText("");
        txtTelCliAbon.setText("");
        txtMontantAPay.setText("");
        MtnPayer.setText("");
        MntRest.setText("");
        
    }//GEN-LAST:event_btnAnnActionPerformed

    private void txtpanoLibreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpanoLibreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpanoLibreActionPerformed

    private void btnObteniResultat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObteniResultat1ActionPerformed
        // TODO add your handling code here:
        Date dtinf= DtInf.getDate();
        Date dtSupp =DtSupp.getDate();
        String DtInfs =((JTextField)DtInf.getDateEditor().getUiComponent()).getText();
        String DtSupps=((JTextField)DtSupp.getDateEditor().getUiComponent()).getText();
        //JOptionPane.showMessageDialog(this, dtinf);
        if(DtInfs.equals("")||DtSupps.equals("")|| NumClient.equals("")){
        JOptionPane.showMessageDialog(this, " Veuillez délimitez la période!");
        }else if(dtinf.after(dtSupp)){
        
             JOptionPane.showMessageDialog(this, " Vérifiez l'ordre des dates saisies");
        }else{
            try {
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                String dt1 = smdf.format(dtinf);
                String dt2 = smdf.format(dtSupp);
                //JOptionPane.showMessageDialog(this, dt1);
                String donnee []={"Identifiant","Id Panneau","Itineraire","Localisation","IdClient","Date Debut","Date Fin"};
                DefaultTableModel model = new DefaultTableModel(null,donnee);
                Class.forName("com.mysql.cj.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
                prst=connect.prepareStatement("SELECT * FROM ligneabonnement WHERE DateFin BETWEEN ? AND ? AND IdClient=(SELECT Id_Client FROM client WHERE Tel_Client=?)");
                prst.setString(1, dt1);
                prst.setString(2, dt2);
                prst.setString(3, NumClient.getText());
                rsl=prst.executeQuery();
                while(rsl.next()){
                Object ob[]={
                    rsl.getString("NumLignAbn"),
                    rsl.getString("Idpan"),
                    rsl.getString("Itineraire"),
                    rsl.getString("Localisation"),
                    rsl.getString("IdClient"),
                    rsl.getString("DateDebut"),
                    rsl.getString("DateFin"),
                   
                };
                model.addRow(ob);
                
               //compter le nombre d abonnement et le revenu total
             prst2=connect.prepareStatement("SELECT COUNT(NumLignAbn )AS 'Nbr' FROM ligneabonnement WHERE DateFin BETWEEN ? AND ? AND IdClient=(SELECT Id_Client FROM client WHERE Tel_Client=?)");
             prst2.setString(1, dt2);
             prst2.setString(2, dt2);
             prst2.setString(3, NumClient.getText());
             rsl2=prst2.executeQuery();
             
          
             if(rsl2.next()){
                 lblNbrTotal.setText(String.valueOf( model.getRowCount()));
             }
            
            }
                String NameCli ="";
                //le nom du client
                prst3=connect.prepareStatement("SELECT * FROM client WHERE Tel_Client=?");
                prst3.setString(1, NumClient.getText());
                rsl3=prst3.executeQuery();
                if(rsl3.next()){
                 NameCli=  rsl3.getString("Nom_Client");
             }
                
            ////////////////////////////////
            tableAbonnement.setModel(model);
            tablePannAff2.setModel(model);
            txtTitre1.setText("Les lignes Abonnements  du "+NameCli );
            txtTitre.setText("Les Abonnements entre les périodes saisies");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
    }//GEN-LAST:event_btnObteniResultat1ActionPerformed

    private void btnlignAbonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlignAbonActionPerformed
        // TODO add your handling code here:
       
        Date dateSys = new Date();
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        String dateSystem=spf.format(dateSys);
        
        Date dtinf= DtInf.getDate();
        Date dtSupp =DtSupp.getDate();
        String DtInfs =((JTextField)DtInf.getDateEditor().getUiComponent()).getText();
        String DtSupps=((JTextField)DtSupp.getDateEditor().getUiComponent()).getText();
        //JOptionPane.showMessageDialog(this, dtinf);
        if(DtInfs.equals("")||DtSupps.equals("")){
        JOptionPane.showMessageDialog(this, " Veuillez délimitez la période!");
        }else if(dtinf.after(dtSupp)){
        
             JOptionPane.showMessageDialog(this, " Vérifiez l'ordre des dates saisies");
        }else{
            try {
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                String dt1 = smdf.format(dtinf);
                String dt2 = smdf.format(dtSupp);
                //JOptionPane.showMessageDialog(this, dt1);
                String donnee []={"Identifiant","Id Panneau","Itineraire","Localisation","IdClient","Date Debut","Date Fin"};
                DefaultTableModel model = new DefaultTableModel(null,donnee);
                Class.forName("com.mysql.cj.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
                prst=connect.prepareStatement("SELECT * FROM ligneabonnement  WHERE DateDebut BETWEEN ? AND ?");
                prst.setString(1, dt1);
                prst.setString(2, dt2);
                
                rsl=prst.executeQuery();
                while(rsl.next()){
                Object ob[]={
                    rsl.getString("NumLignAbn"),
                    rsl.getString("Idpan"),
                    rsl.getString("Itineraire"),
                    rsl.getString("Localisation"),
                    rsl.getString("IdClient"),
                    rsl.getString("DateDebut"),
                    rsl.getString("DateFin"),
              
                        
                };
                model.addRow(ob);
                ////////COMPTER ET REVENU TOTAL
                
          
             
                ////////////////////
            }
            tableAbonnement.setModel(model);
            tablePannAff2.setModel(model);

            txtTitre.setText("Les lignes Abonnements du " +dt1+ " au "+dt2);
            txtTitre1.setText("Les Abonnements expirés");
            
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AdminAcceuil.class.getName()).log(Level.SEVERE, null, ex);
            }
        tableAbonnement.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableAbonnement.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableAbonnement.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableAbonnement.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableAbonnement.getColumnModel().getColumn(3).setPreferredWidth(90);
        tableAbonnement.getColumnModel().getColumn(4).setPreferredWidth(90);
        tableAbonnement.getColumnModel().getColumn(6).setPreferredWidth(120);
        }
    }//GEN-LAST:event_btnlignAbonActionPerformed

    private void AbonPartielActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbonPartielActionPerformed
        // TODO add your handling code here:
           try {
            String donnee []={"Identifiant","DateDebut","DateFin","TelClient","Nom_Client","NbrJours","NbrPanneau","MontantPayer"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            prst1=connect.prepareStatement("SELECT * FROM  abonnement ");
            rsl=prst1.executeQuery();
            prst2=connect.prepareStatement("SELECT * FROM  abonnement WHERE MontantRestant<>'"+0+"'");
            rsl2=prst2.executeQuery();
            //NumFact = rsl2.getInt("IdAbon");
            while(rsl.next()){
                Object ob[]={
                    rsl.getString("IdAbon"),
                    rsl.getString("DateDebut"),
                    rsl.getString("DateFin"),
                    rsl.getString("TelClient"),
                    rsl.getString("Nom_Client"),
                    rsl.getString("NbrJours"),
                    rsl.getString("NbrPanneau"),
                    rsl.getString("MontantPayer"),
     
                };
                model.addRow(ob);
            }
            tableAbonnement.setModel(model);
            tablePannAff2.setModel(model);
            txtTitre.setText("Les abonnements partiels");
            //tablePanAbon.setModel(model);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AbonPartielActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
        /*DefaultTableModel JsperTbl =  (DefaultTableModel)tableAbonnement.getModel();
        HashMap <String,Object> para=new HashMap<>();
        para.put("title","");
        JasperPrint jspPrint = null;
        JasperCompileManager.compileReportToFile("");
        JsperTbl = JasperFillManager.fillReport("",para, new JRTableModelDataSource(JsperTbl) );
        JasperViewer.vievReport(jasperPrint,false);*/
        //JasperDesign jDesign = JRXmlLoader.load("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        /*String path="";
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = jfc.showSaveDialog(this);
        if(x==JFileChooser.APPROVE_OPTION){
            path=jfc.getSelectedFile().getPath();
            
        } */
       // Document doc =new Document();
        
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void NumClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NumClientActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NumClientActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminAcceuil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminAcceuil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminAcceuil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminAcceuil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminAcceuil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AbonPane;
    private javax.swing.JButton AbonPartiel;
    private javax.swing.JButton AjoutBtn;
    private javax.swing.JButton AjoutBtn1;
    private javax.swing.JButton AjoutBtnUti;
    private javax.swing.JButton BtnPrinteFact;
    private javax.swing.JButton BtnPrinteFact1;
    private javax.swing.JComboBox<String> CbxOption;
    private javax.swing.JPanel ClientPane;
    private com.toedter.calendar.JDateChooser DtInf;
    private com.toedter.calendar.JDateChooser DtSupp;
    private javax.swing.JLabel EnteteTitre;
    private javax.swing.JLabel EnteteTitre1;
    private javax.swing.JPanel FenAbonnement;
    private javax.swing.JPanel FenClients;
    private javax.swing.JPanel FenImpression;
    private javax.swing.JPanel FenPanneau;
    private javax.swing.JPanel FenRecherche;
    private javax.swing.JPanel FenUtilisateur;
    private javax.swing.JPanel FenetreRecherche;
    private javax.swing.JPanel LesFenetres;
    private javax.swing.JTextField MntRest;
    private javax.swing.JButton ModifierBtn;
    private javax.swing.JButton ModifierBtn1;
    private javax.swing.JButton ModifierBtn2;
    private javax.swing.JTextField MtnPayer;
    private javax.swing.JTextField NumClient;
    private javax.swing.JPanel PanAffichAbn;
    private javax.swing.JPanel PaneauPane;
    private javax.swing.JPanel RecherchePane;
    private javax.swing.JButton SupprBtn;
    private javax.swing.JButton SupprBtn1;
    private javax.swing.JTable TableClient;
    private javax.swing.JTable TablePan;
    private javax.swing.JTable TableUser;
    private javax.swing.JTable TblAbonClient;
    private javax.swing.JPanel UtilisateurPane;
    private javax.swing.JButton btnAbnExp;
    private javax.swing.JButton btnAjouAbn;
    private javax.swing.JButton btnAnn;
    private javax.swing.JButton btnObteniResultat;
    private javax.swing.JButton btnObteniResultat1;
    private javax.swing.JButton btnRetit;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSupprAbn1;
    private javax.swing.JButton btnSupprCli;
    private javax.swing.JButton btnSupprCli1;
    private javax.swing.JButton btnSupprCli2;
    private javax.swing.JButton btnSupprCli3;
    private javax.swing.JButton btnlignAbon;
    private javax.swing.JComboBox<String> cbxrecherchPanno;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblAffTlPanBd;
    private javax.swing.JLabel lblNbrTotal;
    private javax.swing.JLabel lblRevenu;
    private javax.swing.JLabel lblTypePers;
    private javax.swing.JLabel lblUserCon;
    private javax.swing.JTextField recherchPano;
    private javax.swing.JTable tableAbonnement;
    private javax.swing.JTable tablePanAbon;
    private javax.swing.JTable tablePannAff2;
    private javax.swing.JTextField txtAdrCli;
    private javax.swing.JTextField txtBoulevard;
    private com.toedter.calendar.JDateChooser txtDatDeb;
    private com.toedter.calendar.JDateChooser txtDatFin;
    private javax.swing.JTextField txtItner;
    private javax.swing.JLabel txtMontantAPay;
    private javax.swing.JLabel txtNbrPan;
    private javax.swing.JTextField txtNomCl;
    private javax.swing.JTextArea txtPrinter;
    private javax.swing.JTextField txtPrix;
    private javax.swing.JTextField txtRchercheAbon;
    private javax.swing.JTextField txtRechUtili;
    private javax.swing.JTextField txtTaille;
    private javax.swing.JTextField txtTelCli;
    private javax.swing.JTextField txtTelCliAbon;
    private javax.swing.JLabel txtTitre;
    private javax.swing.JLabel txtTitre1;
    private javax.swing.JTextField txtUserPass;
    private javax.swing.JTextField txtUserTel;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtlocalisation;
    private javax.swing.JTextField txtpanoLibre;
    private javax.swing.JTextField txtrechCli;
    // End of variables declaration//GEN-END:variables

    private Color Color(int i, int i0, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void rechechClient(String text) {
        try {
            String donnee []={"Identifiant","Nom","Adresse","Téléphone"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            prst1=connect.prepareStatement("SELECT * FROM  client WHERE Nom_Client like'%"+text+"%'");
            rsl=prst1.executeQuery();
            while(rsl.next()){
                Object ob[]={
                    rsl.getString("Id_Client"),
                    rsl.getString("Nom_Client"),
                    rsl.getString("Adresse_Client"),
                    rsl.getString("Tel_Client")
                };
                model.addRow(ob);
            }
          
            TableClient.setModel(model);
           
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void rechercheUtilisateur(String text) {
         try {
            String donnee []={"ID","Nom","Mot de Passe","Téléphone","Option"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            prst1=connect.prepareStatement("SELECT * FROM   admin WHERE Nom_Admin like'%"+text+"%'");
            rsl=prst1.executeQuery();
            while(rsl.next()){
                Object ob[]={
                    rsl.getString("id_Admin"),//
                    rsl.getString("Nom_Admin"),
                    rsl.getString("PasseWrdAdmin"),
                    rsl.getString("telephone_Admin"),
                    rsl.getString("OptionPersonnel")
                };
                model.addRow(ob);
            }
            TableUser.setModel(model);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void recherchPanneau(String text) {
        try {
            String donnee []={"Identifiant","Itineraire","Localisation","Taille","Prix"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            
            prst1=connect.prepareStatement("SELECT * FROM  panneau WHERE Localisation_panneau like'%"+text+"%'");
            rsl=prst1.executeQuery();
            while(rsl.next()){
                Object ob[]={
                     rsl.getString("Id_panneau"),
                    rsl.getString("Itineraire"),
                    rsl.getString("Localisation_panneau"),
                    rsl.getString("Taille"),
                    rsl.getString("Prix")
                };
                model.addRow(ob);
            }
            TablePan.setModel(model);
            //tablePanAbon.setModel(model);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void recherchePanoLibre(String text) {
        Date dateSys = new Date();
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        String dateSystem=spf.format(dateSys);
        
        try {
            String donnee []={"Identifiant","Itineraire","Localisation","Taille","Prix"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            
            prst1=connect.prepareStatement("SELECT * FROM  panneau WHERE Id_panneau NOT IN (SELECT Idpan FROM abonnement WHERE DateFin>"+"'"+dateSystem+"')AND Localisation_panneau LIKE '%"+text+"%'");
            rsl=prst1.executeQuery();
            while(rsl.next()){
                Object ob[]={
                    rsl.getString("Id_panneau"),
                    rsl.getString("Itineraire"),
                    rsl.getString("Localisation_panneau"),
                    rsl.getString("Taille"),
                    rsl.getString("Prix")
                };
                model.addRow(ob);
            }
            //TablePan.setModel(model);
            tablePanAbon.setModel(model);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void AffichePanoLibre() {
        Date dateSys = new Date();
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        String dateSystem=spf.format(dateSys);
        
        try {
            String donnee []={"Identifiant","Itineraire","Localisation","Taille","Prix"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            
            prst1=connect.prepareStatement("SELECT * FROM  panneau WHERE Id_panneau IN (SELECT Id_panneau FROM  ligneabonnement WHERE DateFin>"+"'"+dateSystem+"')");
            rsl=prst1.executeQuery();
            while(rsl.next()){
                Object ob[]={
                    rsl.getString("Id_panneau"),
                    rsl.getString("Itineraire"),
                    rsl.getString("Localisation_panneau"),
                    rsl.getString("Taille"),
                    rsl.getString("Prix")
                };
                model.addRow(ob);
            }
            //TablePan.setModel(model);
            TablePan.setModel(model);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void AffichePanoOcupe() {
        Date dateSys = new Date();
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        String dateSystem=spf.format(dateSys);
        
        try {
            String donnee []={"Identifiant","Itineraire","Localisation","Taille","Prix"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            
            prst1=connect.prepareStatement("SELECT * FROM  panneau WHERE Id_panneau NOT IN (SELECT Idpan FROM  ligneabonnement WHERE DateFin>"+"'"+dateSystem+"')");
            rsl=prst1.executeQuery();
            while(rsl.next()){
                Object ob[]={
                    rsl.getString("Id_panneau"),
                    rsl.getString("Itineraire"),
                    rsl.getString("Localisation_panneau"),
                    rsl.getString("Taille"),
                    rsl.getString("Prix")
                };
                model.addRow(ob);
            }
            //TablePan.setModel(model);
            TablePan.setModel(model);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void RchercheAbonParTelCli(String text) {
        
       
       try {
            String donnee []={"Identifiant","DateDebut","DateFin","Nom client","TelClient"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            
            prst1=connect.prepareStatement("SELECT * FROM  abonnement WHERE TelClient like'%"+text+"%'");
            rsl=prst1.executeQuery();
            while(rsl.next()){
                Object ob[]={
                    rsl.getString("IdAbon"),
                    rsl.getString("DateDebut"),
                    rsl.getString("DateFin"),
                    rsl.getString("Nom_Client"),
                    rsl.getString("TelClient")
                };
                model.addRow(ob);
            }
            tableAbonnement.setModel(model);
            //tablePanAbon.setModel(model);
        } catch (ClassNotFoundException | SQLException ex) {
            
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        tableAbonnement.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableAbonnement.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableAbonnement.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableAbonnement.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableAbonnement.getColumnModel().getColumn(3).setPreferredWidth(90);
        tableAbonnement.getColumnModel().getColumn(4).setPreferredWidth(90);
    }

    private void Impression() {
     
        if(txtPrinter.equals("")){
            JOptionPane.showMessageDialog(null, "un reçu est en cours d'impression");
        }else{
            txtPrinter.setText(txtPrinter.getText()+"                                                             \n\n ");
            txtPrinter.setText(txtPrinter.getText() +"                          CENTRE DE CONTROLE DES PANNEAUX PUBLICITAIRES   \n\n");
            txtPrinter.setText(txtPrinter.getText()+"*********************************************************************************************\n");
            txtPrinter.setText(txtPrinter.getText() +"*                                    Reçu d'abonnement panneau publicitaire                                   *\n");
            txtPrinter.setText(txtPrinter.getText() +"*********************************************************************************************\n");
            
            Date dateSys = new Date();
            SimpleDateFormat spf = new SimpleDateFormat("d MMM y");
            String dateSystem=spf.format(dateSys);
            Date DteDeb = txtDatDeb.getDate();
            Date DteFin = txtDatFin.getDate();
            String dtFac1 = spf.format(DteDeb);
            String dtFac2 = spf.format(DteFin);
            
            txtPrinter.setText(txtPrinter.getText()+"Date d'opération  :"+dateSystem+"                 N° Facture : 0000"+NumFact+"\n\n");
            txtPrinter.setText(txtPrinter.getText()+"         Nom client  :"+Nomclient+" \n");
            txtPrinter.setText(txtPrinter.getText()+"         Téléphone client  :"+txtTelCliAbon.getText()+" \n");
            txtPrinter.setText(txtPrinter.getText()+"         Date Début Abonnement  :"+dtFac1+" \n");
            txtPrinter.setText(txtPrinter.getText()+"         Date Fin   Abonnement  : "+dtFac2+" \n \n \n");
            txtPrinter.setText(txtPrinter.getText()+"         Montant Total Facture  :"+MtnPayer+" \n\n\n");
            txtPrinter.setText(txtPrinter.getText()+"                                                                                     Signature \n");
        }
               BtnPrinteFact.setVisible(true);

    }

    private void PrintEtats(JPanel PanAffichAbn) {
        
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("impression");
        printerJob.setPrintable(new Printable(){
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if(pageIndex >0){
                    return Printable.NO_SUCH_PAGE;
                    
                }
                Graphics2D graphics2d=(Graphics2D)graphics;
                graphics2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                graphics2d.scale(0.47, 0.47);
                PanAffichAbn.paint(graphics2d);
                
                return Printable.PAGE_EXISTS;
            }
        
        
    });
            boolean resutl = printerJob.printDialog();
            if(resutl){
            try{
                printerJob.print();
            }catch(PrinterException ptEx ){
                JOptionPane.showMessageDialog(null, ptEx);
            }
        }
    }

    private void RecherPanBoulevard(String text) {
           try {
            String donnee []={"Identifiant","Itineraire","Localisation","Taille","Prix"};
            DefaultTableModel model=new DefaultTableModel(null,donnee);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/panneau_pub","root","");
            
            prst1=connect.prepareStatement("SELECT * FROM  panneau WHERE Itineraire like'%"+text+"%'");
            rsl=prst1.executeQuery();
            while(rsl.next()){
                Object ob[]={
                    rsl.getString("Id_panneau"),
                    rsl.getString("Itineraire"),
                    rsl.getString("Localisation_panneau"),
                    rsl.getString("Taille"),
                    rsl.getString("Prix")
                };
                model.addRow(ob);
            }
            TablePan.setModel(model);
            //tablePanAbon.setModel(model);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(panneaux.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
