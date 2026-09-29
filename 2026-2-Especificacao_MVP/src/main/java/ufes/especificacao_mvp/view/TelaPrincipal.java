package ufes.especificacao_mvp.view;

import javax.swing.JMenuItem;

public class TelaPrincipal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaPrincipal.class.getName());

   
    public TelaPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public JMenuItem getMiIncluirProduto() {
        return miIncluirProduto;
    }

    public JMenuItem getMiBuscarProdutos() {
        return miBuscarProdutos;
    }

    public JMenuItem getMiCategorias() {
        return miCategorias;
    }

    public JMenuItem getMiCalculoMargem() {
        return miCalculoMargem;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        JMenuItem = new javax.swing.JMenu();
        miIncluirProduto = new javax.swing.JMenuItem();
        miBuscarProdutos = new javax.swing.JMenuItem();
        miCategorias = new javax.swing.JMenuItem();
        miCalculoMargem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Supermercado");

        jMenuBar1.setToolTipText("");

        JMenuItem.setText("Dados");

        miIncluirProduto.setText("Incluir produtos");
        JMenuItem.add(miIncluirProduto);

        miBuscarProdutos.setText("Buscar produtos");
        JMenuItem.add(miBuscarProdutos);

        miCategorias.setText("Categorias");
        JMenuItem.add(miCategorias);

        miCalculoMargem.setText("Calcular margem de lucro");
        JMenuItem.add(miCalculoMargem);

        jMenuBar1.add(JMenuItem);

        setJMenuBar(jMenuBar1);
        jMenuBar1.getAccessibleContext().setAccessibleName("");

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu JMenuItem;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem miBuscarProdutos;
    private javax.swing.JMenuItem miCalculoMargem;
    private javax.swing.JMenuItem miCategorias;
    private javax.swing.JMenuItem miIncluirProduto;
    // End of variables declaration//GEN-END:variables
}
