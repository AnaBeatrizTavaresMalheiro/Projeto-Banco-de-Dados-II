package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import mongodb.MongoDBService;
import tabelas.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static final String URL = "jdbc:postgresql://autorack.proxy.rlwy.net:57661/railway";
    private static final String USER = "postgres";
    private static final String PASSWORD = "BvzKsFbCslzRtGiWrrelANloUarxsVCZ";

    // Substitua pelo nome do seu banco de dados
    private static String dbName = "Faculdade";

    // Sua string de conexão
    private static String uri = "mongodb+srv://bruno:b3a43mongo@projetomongo.hdjpt.mongodb.net/?retryWrites=true&w=majority&appName=ProjetoMongo";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            List<Professor> professores = getProfessores(connection);
            List<Departamento> departamentos = getDepartamentos(connection);
            List<Curso> cursos = getCursos(connection);
            List<Aluno> alunos = getAlunos(connection);
            List<Disciplina> disciplinas = getDisciplinas(connection);
            List<MatrizCurricular> matrizCurricular = getMatrizCurricular(connection);
            List<HistoricoAluno> historicoAlunos = getHistoricoAlunos(connection);
            List<GrupoAluno> grupoAlunos = getGrupoAlunos(connection);
            List<HistoricoTCC> historicoTCC = getHistoricoTCC(connection);
            List<HistoricoProfessor> historicoProfessores = getHistoricoProfessores(connection);

            MongoDBService mongoDBService = new MongoDBService(uri, dbName, professores, departamentos, cursos, alunos, disciplinas, matrizCurricular, historicoAlunos, grupoAlunos, historicoTCC, historicoProfessores);
            mongoDBService.insertDataIntoDB();
            //mongoDBService.buscarHistoricoEscolarAluno("Luiz");
            //mongoDBService.buscarHistoricoDisciplinasProfessor("Pedro");
            //mongoDBService.listarAlunosFormadosNoAno(2003);
            mongoDBService.listarChefesDepartamentos();
            mongoDBService.listarAlunosEOrientadoresTCC();
            mongoDBService.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<Professor> getProfessores(Connection connection) throws SQLException {
        List<Professor> professores = new ArrayList<>();
        String query = "SELECT * FROM faculdade.professor";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Professor professor = new Professor(
                        rs.getInt("Id_Professor"),
                        rs.getString("Nome"),
                        rs.getString("Sobrenome"),
                        rs.getString("Formacao"),
                        rs.getString("CPF")
                );
                professores.add(professor);
            }
        }
        return professores;
    }

    private static List<Departamento> getDepartamentos(Connection connection) throws SQLException {
        List<Departamento> departamentos = new ArrayList<>();
        String query = "SELECT * FROM faculdade.departamento";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Departamento departamento = new Departamento(
                        rs.getInt("Id_Departamento"),
                        rs.getString("Titulo"),
                        rs.getDouble("Verba"),
                        rs.getInt("Id_Professor")
                );
                departamentos.add(departamento);
            }
        }
        return departamentos;
    }

    private static List<Curso> getCursos(Connection connection) throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        String query = "SELECT * FROM faculdade.curso";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getInt("Id_Curso"),
                        rs.getInt("Id_Professor"),
                        rs.getInt("Id_Departamento"),
                        rs.getString("Titulo")
                );
                cursos.add(curso);
            }
        }
        return cursos;
    }

    private static List<Aluno> getAlunos(Connection connection) throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String query = "SELECT * FROM faculdade.aluno";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getInt("Id_Aluno"),
                        rs.getInt("Id_Curso"),
                        rs.getString("Nome"),
                        rs.getString("Sobrenome"),
                        rs.getDate("Data_Adesao"),
                        rs.getString("CPF")
                );
                alunos.add(aluno);
            }
        }
        return alunos;
    }

    private static List<Disciplina> getDisciplinas(Connection connection) throws SQLException {
        List<Disciplina> disciplinas = new ArrayList<>();
        String query = "SELECT * FROM faculdade.disciplina";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Disciplina disciplina = new Disciplina(
                        rs.getInt("Id_Disciplina"),
                        rs.getString("Titulo")
                );
                disciplinas.add(disciplina);
            }
        }
        return disciplinas;
    }

    private static List<MatrizCurricular> getMatrizCurricular(Connection connection) throws SQLException {
        List<MatrizCurricular> matrizCurricular = new ArrayList<>();
        String query = "SELECT * FROM faculdade.matriz_curricular";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                MatrizCurricular matriz = new MatrizCurricular(
                        rs.getInt("Id_Matriz_Curricular"),
                        rs.getInt("Id_curso"),
                        rs.getInt("Id_disciplina"),
                        rs.getDouble("nota_de_corte")
                );
                matrizCurricular.add(matriz);
            }
        }
        return matrizCurricular;
    }

    private static List<HistoricoAluno> getHistoricoAlunos(Connection connection) throws SQLException {
        List<HistoricoAluno> historicoAlunos = new ArrayList<>();
        String query = "SELECT * FROM faculdade.historico_aluno";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                HistoricoAluno historico = new HistoricoAluno(
                        rs.getInt("Id_Historico_Escolar"),
                        rs.getInt("Id_Aluno"),
                        rs.getInt("Id_Disciplina"),
                        rs.getDouble("Media"),
                        rs.getString("Semestre"),
                        rs.getInt("Ano")
                );
                historicoAlunos.add(historico);
            }
        }
        return historicoAlunos;
    }

    private static List<GrupoAluno> getGrupoAlunos(Connection connection) throws SQLException {
        List<GrupoAluno> grupoAlunos = new ArrayList<>();
        String query = "SELECT * FROM faculdade.grupo_aluno";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                GrupoAluno grupo = new GrupoAluno(
                        rs.getInt("Id_Grupo_Aluno"),
                        rs.getInt("Id_Aluno"),
                        rs.getInt("Id_grupo")
                );
                grupoAlunos.add(grupo);
            }
        }
        return grupoAlunos;
    }

    private static List<HistoricoTCC> getHistoricoTCC(Connection connection) throws SQLException {
        List<HistoricoTCC> historicoTCC = new ArrayList<>();
        String query = "SELECT * FROM faculdade.historico_tcc";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                HistoricoTCC historico = new HistoricoTCC(
                        rs.getInt("Id_Historico_TCC"),
                        rs.getInt("Id_Grupo_Aluno"),
                        rs.getInt("Id_Professor"),
                        rs.getString("Semestre"),
                        rs.getInt("Ano"),
                        rs.getDouble("Nota")
                );
                historicoTCC.add(historico);
            }
        }
        return historicoTCC;
    }

    private static List<HistoricoProfessor> getHistoricoProfessores(Connection connection) throws SQLException {
        List<HistoricoProfessor> historicoProfessores = new ArrayList<>();
        String query = "SELECT * FROM faculdade.historico_professor";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                HistoricoProfessor historico = new HistoricoProfessor(
                        rs.getInt("Id_Historico_Professor"),
                        rs.getInt("Id_Professor"),
                        rs.getInt("Id_Disciplina"),
                        rs.getString("Semestre"),
                        rs.getInt("Ano")
                );
                historicoProfessores.add(historico);
            }
        }
        return historicoProfessores;
    }
}