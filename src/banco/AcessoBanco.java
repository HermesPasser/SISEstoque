package banco;
import java.sql.*;
import javax.swing.JOptionPane;

public class AcessoBanco extends Conexao{
    	
   public ResultSet rs = null;
    
    public void add(String cod, String nome, String endeOuQntd, String telOuCnpj, String tabela) {
        Connection conexao = null;
        int qntd = -1;
        String cd = "";
  
        try {

            /*Se a tabela for produto entao adiciona o cod para add nela, se nao adiciona de add fornecedor*/
            if (tabela.equals("produto")) {
                cd = "INSERT INTO produto (cod_produto, nome_produto, quantidade, cnpj_fornecedor) VALUES(?, ?, ?, ?);";
            } else {
                cd = "INSERT INTO fornecedor (cnpj_fornecedor, nome_fornecedor, endereco, telefone) VALUES(?, ?, ?, ?);";
            }
            conexao = DriverManager.getConnection(this.getUrl(), this.getLogin(), this.getSenhaBanco());
            PreparedStatement state = null;
            state = conexao.prepareStatement(cd);
            state.setString(1, cod);
            state.setString(2, nome);

            /*Se a tabela for produto, ent?o endeOuQntd ser? um int qntd, a quantidade, se n?o uma string com o valor de endereco*/
            if (tabela.equals("produto")) {
                try {
                    qntd = Integer.parseInt(endeOuQntd);
                    state.setInt(3, qntd);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Somente serão permitidos números no campo quantidade.");
                    return;
                }
            } else {
                state.setString(3, endeOuQntd);
            }
            state.setString(4, telOuCnpj);
            state.executeUpdate();
            state.close();
            conexao.close();
            JOptionPane.showMessageDialog(null, "Sucesso ao adicionar.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Produto ja existe ou fornecedor inexistente.");
        }
    }
    
    public void delete(String tabela, String cod) {
        Connection conexao = null;
            
        try {
            conexao = DriverManager.getConnection(this.getUrl(), this.getLogin(), this.getSenhaBanco());
            PreparedStatement state = null;
            String cd = "";
            if (tabela.equals("produto")) {
                cd = "DELETE FROM produto WHERE cod_produto=?;";
            } else {
                cd = "DELETE FROM fornecedor WHERE cnpj_fornecedor=?;";
            }
            int txt = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?");
            if (txt == JOptionPane.YES_OPTION) {
                state = conexao.prepareStatement(cd);
                state.setString(1, cod);
                state.executeUpdate();
            } else {
                return;
            }

            state.close();
            conexao.close();
            JOptionPane.showMessageDialog(null, "Sucesso ao apagar.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar ação..\nErro: " + e);
        }
    }
        
    /*Atualiza elemento da tabela 'tabela' recebendo uma String com os campos a serem atualizados*/
    public void update(String codigo, String nome, String qntdOuEnde, String cnpjOuTel, String tabela){
		Connection conexao = null;
		int intQntd = -1;
		
		/*Se a tabela for produto, ent�o endeOuQntd ser� um int qntd, a quantidade, se n�o uma string com o valor de endereco*/
		
		try{
                    try {
                        Class.forName(getDriver());
                    } catch (ClassNotFoundException ex) {
                        System.out.printf("Erro ao realizar ação..\nErro: "+ex);
                    }
			conexao = DriverManager.getConnection(this.getUrl(), this.getLogin(),this.getSenhaBanco());
			PreparedStatement state = null;
			String cd = "";

			/*Se a tabela for produto, a atualiza, se n�o atualiza a tabela fornecedor*/
			if (tabela.equals("produto")){
				if (!(nome == null)){
					cd = "UPDATE produto SET nome_produto=? WHERE cod_produto=?;";
                                        state = conexao.prepareStatement(cd);
					state.setString(1, nome);
					state.setString(2, codigo);
					state.executeUpdate();
					state.close();
				}
                                
                                if (!(qntdOuEnde == null)){
                                    if (tabela.equals("produto")){
                                        try {
                                            intQntd = Integer.parseInt(qntdOuEnde);
                                            cd = "UPDATE produto SET quantidade=? WHERE cod_produto=?;";
                                            state = conexao.prepareStatement(cd);
                                            state.setInt(1, intQntd);
                                            state.setString(2, codigo);
                                            state.executeUpdate();
                                            state.close();
                                        }catch (NumberFormatException e) {
                                            JOptionPane.showMessageDialog(null,"Somente serão permitidos números no campo quantidade.");
                                        return;
                                        }
                                    }
                                }
                                
				if (!(cnpjOuTel == null)){
					cd = "UPDATE produto SET cnpj_fornecedor=? WHERE cod_produto=?;";
                                        state = conexao.prepareStatement(cd);
					state.setString(1, cnpjOuTel);
					state.setString(2, codigo);
					state.executeUpdate();
					state.close();
				}
                        }else{
				if (!(nome == null)){
					cd = "UPDATE fornecedor SET nome_fornecedor=? WHERE cnpj_fornecedor=?;";
                                        state = conexao.prepareStatement(cd);
					state.setString(1, nome);
					state.setString(2, codigo);
					state.executeUpdate();
					state.close();
				}
				if (!(qntdOuEnde == null)){
					cd = "UPDATE fornecedor SET endereco=? WHERE cnpj_fornecedor=?;";
                                        state = conexao.prepareStatement(cd);
					state.setString(1, qntdOuEnde);
					state.setString(2, codigo);
					state.executeUpdate();
					state.close();
				}
				if (!(cnpjOuTel == null)){
					cd = "UPDATE fornecedor SET telefone=? WHERE cnpj_fornecedor=?;";
                                        state = conexao.prepareStatement(cd);
					state.setString(1, cnpjOuTel);
					state.setString(2, codigo);
					state.executeUpdate();
					state.close();
				}
			}
			conexao.close();
			JOptionPane.showMessageDialog(null,"Sucesso ao atualizar.");
		}catch(SQLException e){JOptionPane.showMessageDialog(null,"Erro ao realizar ação..\nErro: "+e);}
    }
    
    /*
    Responsavel por enviar os dados da tabela 'tabela'para serem exibidos 
    (Somente para modo de seguranca), 
    para o modo normal funcao no metodo recarregar.
    */
    
    public String mostrarDado(String tabela) {
        Connection conexao = null;
        String cd = "";
        try {
            conexao = DriverManager.getConnection(getUrl(), getLogin(), getSenhaBanco());
            Statement state = conexao.createStatement();
            if (tabela.equals("produto")) {
                cd = "select * from produto;";
                rs = state.executeQuery(cd);
                cd = "Codigo | Nome | Quantidade | CNPJ Fornecedor\n";
            } else {
                cd = "select * from fornecedor;";
                rs = state.executeQuery(cd);
                cd = "CNPJ | Nome | Endereço | Telefone\n";
            }

            while (rs.next()) {
                if (tabela.equals("produto")) {
                    cd += rs.getString("cod_produto") + " | "
                        + rs.getString("nome_produto") + " | "
                        + rs.getString("quantidade") + " | "
                        + rs.getString("cnpj_fornecedor") + " | " + "\n";
            }else{
		cd += rs.getString("cnpj_fornecedor")+" | "
                    +rs.getString("nome_fornecedor")+" | "
                    +rs.getString("endereco")+" | "
                    +rs.getString("telefone")+" | "+"\n";
            }
        }
    }
    catch (SQLException ex) {System.out.println("SQLException: " + ex.getMessage() + "\nSQLState: " + ex.getSQLState() + "\nVendorError: " + ex.getErrorCode());}
    catch (Exception se) {System.out.println("Erro ao conectar no banco de dados, erro " + se);}
        try {
            conexao.close();
            rs.close();
    }
    catch (SQLException c) {c.printStackTrace(); JOptionPane.showMessageDialog(null, "Erro ao tentar desconectar.\nErro: " + c);}
    return cd ;
}
}

    