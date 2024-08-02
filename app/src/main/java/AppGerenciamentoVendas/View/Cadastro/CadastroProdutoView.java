package AppGerenciamentoVendas.View.Cadastro;

import AppGerenciamentoVendas.Model.Produto;
import AppGerenciamentoVendas.Service.ProdutoService;
import java.math.BigDecimal;
import javax.swing.JOptionPane;

/**
 *
 * @author Luiz
 */
public class CadastroProdutoView extends javax.swing.JDialog {
    
    private ProdutoService produtoService = new ProdutoService();
    private Produto produto = new Produto();

    public CadastroProdutoView(java.awt.Frame parent, boolean modal, Long id) {
        super(parent, modal);
        initComponents();
        
        if(id == null){
            txtCodigoProduto.setText(String.valueOf(produtoService.carregarId()));
        } else {
            produto = produtoService.carregaProduto(id);
            
            txtCodigoProduto.setText(String.valueOf(produto.getId()));
            txtDescricaoProduto.setText(produto.getDescricao());
            txtValorUnitario.setText(String.valueOf(produto.getPreco()));
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

        lblCodigoProduto = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblValorUnitario = new javax.swing.JLabel();
        txtCodigoProduto = new javax.swing.JTextField();
        txtDescricaoProduto = new javax.swing.JTextField();
        txtValorUnitario = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblCodigoProduto.setText("Codigo");

        jLabel2.setText("Descri��o");

        lblValorUnitario.setText("Valor Unitario");

        txtCodigoProduto.setEditable(false);

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnFechar.setText("Fechar");
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addComponent(btnSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFechar)
                .addGap(11, 11, 11))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCodigoProduto)
                    .addComponent(jLabel2)
                    .addComponent(lblValorUnitario)
                    .addComponent(txtDescricaoProduto)
                    .addComponent(txtCodigoProduto)
                    .addComponent(txtValorUnitario, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblCodigoProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescricaoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(lblValorUnitario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnFechar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        try {
            Long id = Long.valueOf(txtCodigoProduto.getText());
            String descricao = txtDescricaoProduto.getText();
            BigDecimal preco = new BigDecimal(txtValorUnitario.getText());
            
            if(id < produtoService.carregarId()){
                Produto editarProduto = produtoService.editarProduto(id, descricao, preco);
                JOptionPane.showMessageDialog(this, "Produto editado com Sucesso!");
            }else{
                Produto cadastrarProduto = produtoService.cadastrarProduto(descricao, preco);
                JOptionPane.showMessageDialog(this, "Produto cadastrado com Sucesso!");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        this.dispose();
    }//GEN-LAST:event_btnSalvarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblCodigoProduto;
    private javax.swing.JLabel lblValorUnitario;
    private javax.swing.JTextField txtCodigoProduto;
    private javax.swing.JTextField txtDescricaoProduto;
    private javax.swing.JTextField txtValorUnitario;
    // End of variables declaration//GEN-END:variables
}