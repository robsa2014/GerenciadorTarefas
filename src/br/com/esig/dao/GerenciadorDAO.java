package br.com.esig.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.esig.entidade.Gerenciador;
import br.com.esig.factory.ConnectionFactory;

public class GerenciadorDAO {

	private Connection conn;
	Long id;
	String titulo;
	String descricao;
	String responsavel;
	String prioridade;
	String deadline;

	public GerenciadorDAO() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	public void salvar(Gerenciador objGer) {
		try {
			String sql;
			if (String.valueOf(objGer.getId()).isEmpty()) {
				sql = "INSERT INTO gerenciador(titulo,descricao,responsavel,prioridade,deadline) "
						+ "VALUES(?,?,?,?,?)";
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.setString(1, objGer.getTitulo());
				stmt.setString(2, objGer.getDescricao());
				stmt.setString(3, objGer.getResponsavel());
				stmt.setString(4, objGer.getPrioridade());
				stmt.setString(5, objGer.getDeadline());
				stmt.execute();
				stmt.close();
			} else {
				sql = "UPDATE gerenciador SET titulo = ?, descricao = ?, responsavel = ?, "
						+ "prioridade = ?, deadline = ? WHERE gerenciador.id = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.setString(1, objGer.getTitulo());
				stmt.setString(2, objGer.getDescricao());
				stmt.setString(3, objGer.getResponsavel());
				stmt.setString(4, objGer.getPrioridade());
				stmt.setString(5, objGer.getDeadline());
				stmt.execute();
				stmt.close();
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void deletar(Gerenciador objGer) {
        try {
            String sql;
            if (!String.valueOf(objGer.getId()).isEmpty()) {
                sql = "DELETE FROM gerenciador WHERE gerenciador.id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setString(1, objGer.getId());
                stmt.execute();
                stmt.close();

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList listarTodos() {
        try {

            ArrayList dado = new ArrayList();

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM gerenciador");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                dado.add(new Object[]{
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getString("responsavel"),
                    rs.getString("prioridade"),
                    rs.getString("deadline")
                });

            }
            ps.close();
            rs.close();
            conn.close();

            return dado;
        } catch (SQLException ex) {
        	throw new RuntimeException(ex);
        }
    }
}
