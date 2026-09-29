package ufes.especificacao_mvp.view;

public class TelaPrincipal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaPrincipal.class.getName());

   
    public TelaPrincipal() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BarraMenu = new javax.swing.JMenuBar();
        menuDados = new javax.swing.JMenu();
        itemIncluirProdutos = new javax.swing.JMenuItem();
        itemBuscarProdutos = new javax.swing.JMenuItem();
        itemCategorias = new javax.swing.JMenuItem();
        itemCalcularMargem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Supermercado");

        BarraMenu.setToolTipText("");

        menuDados.setText("Dados");

        itemIncluirProdutos.setText("Incluir produtos");
        menuDados.add(itemIncluirProdutos);

        itemBuscarProdutos.setText("Buscar produtos");
        menuDados.add(itemBuscarProdutos);

        itemCategorias.setText("Categorias");
        menuDados.add(itemCategorias);

        itemCalcularMargem.setText("Calcular margem de lucro");
        menuDados.add(itemCalcularMargem);

        BarraMenu.add(menuDados);

        setJMenuBar(BarraMenu);
        BarraMenu.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 369, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 347, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar BarraMenu;
    private javax.swing.JMenuItem itemBuscarProdutos;
    private javax.swing.JMenuItem itemCalcularMargem;
    private javax.swing.JMenuItem itemCategorias;
    private javax.swing.JMenuItem itemIncluirProdutos;
    private javax.swing.JMenu menuDados;
    // End of variables declaration//GEN-END:variables
}
