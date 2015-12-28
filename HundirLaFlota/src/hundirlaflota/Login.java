/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hundirlaflota;

import java.awt.Cursor;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author harnick
 */
public class Login extends javax.swing.JDialog {

    private final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[&+*@#$%]).{8,24})";
    private HundirLaFlota hlf; 
    /**
     * Creates new form Login
     */
    public Login(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jLabelCredenciales.setVisible(false);
        jLabelRegistrarse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        KeyboardFocusManager kb = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        kb.addKeyEventPostProcessor(new KeyEventPostProcessor(){
            @Override
            public boolean postProcessKeyEvent(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                    jButtonCancelar.doClick();
                }
                return true;
            }
        });
        jTextFieldUsuario.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(jButtonLogin.isEnabled()){
                        jButtonLogin.doClick();
                    }
                }
            }
        });
        jPasswordField.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(jButtonLogin.isEnabled()){
                        jButtonLogin.doClick();
                    }
                }
            }
        });
        jTextFieldUsuario.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                activarBotonLimpiar();
                backgroundNombre();
                activarBotonLogin();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                activarBotonLimpiar();
                backgroundNombre();
                activarBotonLogin();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                activarBotonLimpiar();
                backgroundNombre();
                activarBotonLogin();
            }
        });
        jPasswordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                activarBotonLimpiar();
                backgroundPassword();
                activarBotonLogin();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                activarBotonLimpiar();
                backgroundPassword();
                activarBotonLogin();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                activarBotonLimpiar();
                backgroundPassword();
                activarBotonLogin();
            }
        });
    }
    public void backgroundNombre(){
        if (!jTextFieldUsuario.getText().matches("(.*\\s.*)")&&jTextFieldUsuario.getText().length()>7&&jTextFieldUsuario.getText().length()<25){
            jTextFieldUsuario.setBackground(new java.awt.Color(255, 255, 255));
        }
    }
    public void backgroundPassword(){
        if (!jPasswordField.getText().matches("(.*\\s.*)")&&jPasswordField.getText().matches(PASSWORD_PATTERN)){
            jPasswordField.setBackground(new java.awt.Color(255, 255, 255));
        }
    }

    public void activarBotonLimpiar() {
        if (jTextFieldUsuario.getText().equals("")&&jPasswordField.getText().equals("")){
            jButtonLimpiar.setEnabled(false);
        }else{
            jButtonLimpiar.setEnabled(true);
        }
    }
    
    public void activarBotonLogin() {
        if (!jTextFieldUsuario.getText().matches("(.*\\s.*)")&&!jPasswordField.getText().matches("(.*\\s.*)")&&jTextFieldUsuario.getText().length()>7&&jTextFieldUsuario.getText().length()<25&&jPasswordField.getText().matches(PASSWORD_PATTERN)){
            jButtonLogin.setEnabled(true);
        }else{
            jButtonLogin.setEnabled(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonLimpiar = new javax.swing.JButton();
        jButtonLogin = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jLabelContrasena = new javax.swing.JLabel();
        jPasswordField = new javax.swing.JPasswordField();
        jLabelUsuario = new javax.swing.JLabel();
        jTextFieldUsuario = new javax.swing.JTextField();
        jLabelRegistrarse = new javax.swing.JLabel();
        jLabelCredenciales = new javax.swing.JLabel();
        jLabelIconSetting = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Hundir La Flota Login");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jButtonLimpiar.setText("Limpiar");
        jButtonLimpiar.setToolTipText("");
        jButtonLimpiar.setEnabled(false);
        jButtonLimpiar.setNextFocusableComponent(jButtonCancelar);
        jButtonLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimpiarActionPerformed(evt);
            }
        });

        jButtonLogin.setText("Login");
        jButtonLogin.setEnabled(false);
        jButtonLogin.setNextFocusableComponent(jButtonLimpiar);
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jLabelContrasena.setText("Contraseña:");

        jPasswordField.setNextFocusableComponent(jButtonLogin);
        jPasswordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPasswordFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordFieldFocusLost(evt);
            }
        });

        jLabelUsuario.setText("Usuario:");

        jTextFieldUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldUsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldUsuarioFocusLost(evt);
            }
        });

        jLabelRegistrarse.setText("<html><a href='' >Registrarse</a></html>");
        jLabelRegistrarse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelRegistrarseMouseClicked(evt);
            }
        });

        jLabelCredenciales.setForeground(java.awt.Color.red);
        jLabelCredenciales.setText("Credenciales Incorrectas");

        jLabelIconSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/SettingIcon.png"))); // NOI18N
        jLabelIconSetting.setToolTipText("<html>Usuario: tiene que tener 8-24 caracteres sin espacio<br>Password: tiene que tener 8-24 caracteres sin espacio<br>una mayuscula, un numero, y un caracter especial</html>");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelUsuario)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelRegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonLimpiar)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonLogin)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonCancelar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelCredenciales)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelContrasena)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelIconSetting)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUsuario)
                    .addComponent(jTextFieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelRegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelCredenciales)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelContrasena)
                            .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabelIconSetting, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLimpiar)
                    .addComponent(jButtonLogin)
                    .addComponent(jButtonCancelar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpiarActionPerformed
        // TODO add your handling code here:
        jTextFieldUsuario.setText("");
        jTextFieldUsuario.setBackground(new java.awt.Color(255, 255, 255));
        jPasswordField.setText("");
        jPasswordField.setBackground(new java.awt.Color(255, 255, 255));
        jLabelCredenciales.setVisible(false);
    }//GEN-LAST:event_jButtonLimpiarActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        // TODO add your handling code here:
        StringBuffer hexString = new StringBuffer();
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(jPasswordField.getText().getBytes());
            byte messageDigest[] = digest.digest();

            for (int i=0; i<messageDigest.length; i++){
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            hlf = new HundirLaFlota();
            if(!hlf.execLogin(jTextFieldUsuario.getText(), hexString.toString())){
                jLabelCredenciales.setVisible(true);
            }else{
                jButtonLimpiar.doClick();
                jLabelCredenciales.setVisible(false);
            }

        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jPasswordFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordFieldFocusGained
        // TODO add your handling code here:
        jPasswordField.selectAll();
    }//GEN-LAST:event_jPasswordFieldFocusGained

    private void jPasswordFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordFieldFocusLost
        // TODO add your handling code here:
        if (!jPasswordField.getText().matches("(.*\\s.*)")&&jPasswordField.getText().matches(PASSWORD_PATTERN)){
            jPasswordField.setBackground(new java.awt.Color(255, 255, 255));
        }else{
            jPasswordField.setBackground(java.awt.Color.red);
        }
    }//GEN-LAST:event_jPasswordFieldFocusLost

    private void jTextFieldUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldUsuarioFocusGained
        // TODO add your handling code here:
        jTextFieldUsuario.selectAll();
    }//GEN-LAST:event_jTextFieldUsuarioFocusGained

    private void jTextFieldUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldUsuarioFocusLost
        // TODO add your handling code here:
        if (!jTextFieldUsuario.getText().matches("(.*\\s.*)")&&jTextFieldUsuario.getText().length()>7&&jTextFieldUsuario.getText().length()<25){
            jTextFieldUsuario.setBackground(new java.awt.Color(255, 255, 255));
        }else{
            jTextFieldUsuario.setBackground(java.awt.Color.red);
        }
    }//GEN-LAST:event_jTextFieldUsuarioFocusLost

    private void jLabelRegistrarseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRegistrarseMouseClicked
        // TODO add your handling code here:
        ventanaRegister = new Register(new javax.swing.JFrame(), true);
        ventanaRegister.setVisible(true);
    }//GEN-LAST:event_jLabelRegistrarseMouseClicked

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Login dialog = new Login(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private Register ventanaRegister;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonLimpiar;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JLabel jLabelContrasena;
    private javax.swing.JLabel jLabelCredenciales;
    private javax.swing.JLabel jLabelIconSetting;
    private javax.swing.JLabel jLabelRegistrarse;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JTextField jTextFieldUsuario;
    // End of variables declaration//GEN-END:variables
}
