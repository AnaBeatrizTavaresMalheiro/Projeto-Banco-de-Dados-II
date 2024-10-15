package mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import org.bson.Document;
import tabelas.*;

import java.util.Arrays;
import java.util.List;

public class MongoDBService {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private List<Professor> professores;
    private List<Departamento> departamentos;
    private List<Curso> cursos;
    private List<Aluno> alunos;
    private List<Disciplina> disciplinas;
    private List<MatrizCurricular> matrizCurricular;
    private List<HistoricoAluno> historicoAlunos;
    private List<GrupoAluno> grupoAlunos;
    private List<HistoricoTCC> historicoTCC;
    private List<HistoricoProfessor> historicoProfessores;
    private String dbName;
    private String uri;


    // Construtor atualizado e corrigido
    public MongoDBService(String uri, String dbName,
                          List<Professor> professores,
                          List<Departamento> departamentos,
                          List<Curso> cursos,
                          List<Aluno> alunos,
                          List<Disciplina> disciplinas,
                          List<MatrizCurricular> matrizCurricular,
                          List<HistoricoAluno> historicoAlunos,
                          List<GrupoAluno> grupoAlunos,
                          List<HistoricoTCC> historicoTCC,
                          List<HistoricoProfessor> historicoProfessores) {

        mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase(dbName);
        this.uri = uri;
        this.dbName = dbName;
        this.professores = professores;
        this.departamentos = departamentos;
        this.cursos = cursos;
        this.alunos = alunos;
        this.disciplinas = disciplinas;
        this.matrizCurricular = matrizCurricular;
        this.historicoAlunos = historicoAlunos;
        this.grupoAlunos = grupoAlunos;
        this.historicoTCC = historicoTCC;
        this.historicoProfessores = historicoProfessores;
    }

    public void insertDataIntoDB() {
        System.out.println("MongoDB - Trying to inset data");
        mongoClient.getDatabase(this.dbName).drop();
        insertProfessores(professores);
        insertDepartamentos(departamentos);
        insertCursos(cursos);
        insertAlunos(alunos);
        insertDisciplinas(disciplinas);
        insertMatrizCurricular(matrizCurricular);
        insertHistoricoAlunos(historicoAlunos);
        insertGrupoAlunos(grupoAlunos);
        insertHistoricoTCC(historicoTCC);
        insertHistoricoProfessores(historicoProfessores);
    }

    public void insertProfessores(List<Professor> professores) {
        MongoCollection<Document> collection = database.getCollection("professores");
        for (Professor professor : professores) {
            Document doc = new Document("idProfessor", professor.getIdProfessor())
                    .append("nome", professor.getNome())
                    .append("sobrenome", professor.getSobrenome())
                    .append("formacao", professor.getFormacao())
                    .append("cpf", professor.getCpf());
            collection.insertOne(doc);
        }
    }

    public void insertDepartamentos(List<Departamento> departamentos) {
        MongoCollection<Document> collection = database.getCollection("departamentos");
        for (Departamento departamento : departamentos) {
            Document doc = new Document("idDepartamento", departamento.getIdDepartamento())
                    .append("titulo", departamento.getTitulo())
                    .append("verba", departamento.getVerba())
                    .append("idProfessor", departamento.getIdProfessor());
            collection.insertOne(doc);
        }
    }

    public void insertCursos(List<Curso> cursos) {
        MongoCollection<Document> collection = database.getCollection("cursos");
        for (Curso curso : cursos) {
            Document doc = new Document("idCurso", curso.getIdCurso())
                    .append("idProfessor", curso.getIdProfessor())
                    .append("idDepartamento", curso.getIdDepartamento())
                    .append("titulo", curso.getTitulo());
            collection.insertOne(doc);
        }
    }

    public void insertAlunos(List<Aluno> alunos) {
        MongoCollection<Document> collection = database.getCollection("alunos");
        for (Aluno aluno : alunos) {
            Document doc = new Document("idAluno", aluno.getIdAluno())
                    .append("idCurso", aluno.getIdCurso())
                    .append("nome", aluno.getNome())
                    .append("sobrenome", aluno.getSobrenome())
                    .append("dataAdesao", aluno.getDataAdesao())
                    .append("cpf", aluno.getCpf());
            collection.insertOne(doc);
        }
    }

    public void insertDisciplinas(List<Disciplina> disciplinas) {
        MongoCollection<Document> collection = database.getCollection("disciplinas");
        for (Disciplina disciplina : disciplinas) {
            Document doc = new Document("idDisciplina", disciplina.getIdDisciplina())
                    .append("titulo", disciplina.getTitulo());
            collection.insertOne(doc);
        }
    }

    public void insertMatrizCurricular(List<MatrizCurricular> matrizCurricular) {
        MongoCollection<Document> collection = database.getCollection("matriz_curricular");
        for (MatrizCurricular mc : matrizCurricular) {
            Document doc = new Document("idMatrizCurricular", mc.getIdMatrizCurricular())
                    .append("idCurso", mc.getIdCurso())
                    .append("idDisciplina", mc.getIdDisciplina())
                    .append("notaDeCorte", mc.getNotaDeCorte());
            collection.insertOne(doc);
        }
    }

    public void insertHistoricoAlunos(List<HistoricoAluno> historicoAlunos) {
        MongoCollection<Document> collection = database.getCollection("historico_alunos");
        for (HistoricoAluno ha : historicoAlunos) {
            Document doc = new Document("idHistoricoAluno", ha.getIdHistoricoAluno())
                    .append("idAluno", ha.getIdAluno())
                    .append("idDisciplina", ha.getIdDisciplina())
                    .append("media", ha.getMedia())
                    .append("semestre", ha.getSemestre())
                    .append("ano", ha.getAno());
            collection.insertOne(doc);
        }
    }

    public void insertGrupoAlunos(List<GrupoAluno> grupoAlunos) {
        MongoCollection<Document> collection = database.getCollection("grupo_alunos");
        for (GrupoAluno ga : grupoAlunos) {
            Document doc = new Document("idGrupoAluno", ga.getIdGrupoAluno())
                    .append("idAluno", ga.getIdAluno())
                    .append("idGrupo", ga.getIdGrupo());
            collection.insertOne(doc);
        }
    }

    public void insertHistoricoTCC(List<HistoricoTCC> historicoTCCs) {
        MongoCollection<Document> collection = database.getCollection("historico_tcc");
        for (HistoricoTCC ht : historicoTCCs) {
            Document doc = new Document("idHistoricoTCC", ht.getIdHistoricoTCC())
                    .append("idGrupoAluno", ht.getIdGrupoAluno())
                    .append("idProfessor", ht.getIdProfessor())
                    .append("semestre", ht.getSemestre())
                    .append("ano", ht.getAno())
                    .append("nota", ht.getNota());
            collection.insertOne(doc);
        }
    }

    public void insertHistoricoProfessores(List<HistoricoProfessor> historicoProfessores) {
        MongoCollection<Document> collection = database.getCollection("historico_professores");
        for (HistoricoProfessor hp : historicoProfessores) {
            Document doc = new Document("idHistoricoProfessor", hp.getIdHistoricoProfessor())
                    .append("idProfessor", hp.getIdProfessor())
                    .append("idDisciplina", hp.getIdDisciplina())
                    .append("semestre", hp.getSemestre())
                    .append("ano", hp.getAno());
            collection.insertOne(doc);
        }
    }

    public void close() {
        System.out.println("MongoDB - Close");
        mongoClient.close();
    }

    public void buscarHistoricoEscolarAluno(String nomeAluno) {

        System.out.println("Histórico escolar de qualquer aluno -> " + nomeAluno);

        MongoCollection<Document> alunosCollection = database.getCollection("alunos");

        alunosCollection.aggregate(Arrays.asList(
                Aggregates.match(Filters.eq("nome", nomeAluno)),
                Aggregates.lookup("historico_alunos", "idAluno", "idAluno", "historico"),
                Aggregates.unwind("$historico", new UnwindOptions().preserveNullAndEmptyArrays(true)),
                Aggregates.lookup("disciplinas", "historico.idDisciplina", "idDisciplina", "disciplina"),
                Aggregates.unwind("$disciplina", new UnwindOptions().preserveNullAndEmptyArrays(true)),
                Aggregates.project(Projections.fields(
                        Projections.include("nome"),
                        Projections.computed("idDisciplina", "$historico.idDisciplina"),
                        Projections.computed("disciplinaTitulo", "$disciplina.titulo"),
                        Projections.computed("semestre", "$historico.semestre"),
                        Projections.computed("ano", "$historico.ano"),
                        Projections.computed("media", "$historico.media")
                ))
        )).forEach(doc -> System.out.println(doc.toJson()));

    }

    public void buscarHistoricoDisciplinasProfessor(String nomeProfessor) {

        System.out.println("Historico Disciplinas de um professor -> " + nomeProfessor);

        MongoCollection<Document> professoresCollection = database.getCollection("professores");

        professoresCollection.aggregate(Arrays.asList(
                Aggregates.match(Filters.eq("nome", nomeProfessor)),
                Aggregates.lookup("historico_professores", "idProfessor", "idProfessor", "historico"),
                Aggregates.unwind("$historico", new UnwindOptions().preserveNullAndEmptyArrays(true)),
                Aggregates.lookup("disciplinas", "historico.idDisciplina", "idDisciplina", "disciplina"),
                Aggregates.unwind("$disciplina", new UnwindOptions().preserveNullAndEmptyArrays(true)),
                Aggregates.project(Projections.fields(
                        Projections.include("nome"),
                        Projections.computed("disciplinaTitulo", "$disciplina.titulo"),
                        Projections.computed("semestre", "$historico.semestre"),
                        Projections.computed("ano", "$historico.ano")
                ))
        )).forEach(doc -> System.out.println(doc.toJson()));
    }

    public void listarAlunosFormadosNoAno(int ano) {
        System.out.println("Listar alunos formados no ano " + ano);

        MongoCollection<Document> alunosCollection = database.getCollection("alunos");

        alunosCollection.aggregate(Arrays.asList(
                // Relacionar histórico dos alunos
                Aggregates.lookup("historico_alunos", "idAluno", "idAluno", "historico"),
                Aggregates.unwind("$historico", new UnwindOptions().preserveNullAndEmptyArrays(true)),
                Aggregates.match(Filters.and(
                        Filters.gte("historico.media", 4.9), // Alunos aprovados
                        Filters.eq("historico.ano", ano), // Ano específico
                        Filters.eq("historico.semestre", "Segundo")
                )),
                // Relacionar cursos
                Aggregates.lookup("cursos", "idCurso", "idCurso", "curso"),
                Aggregates.unwind("$curso", new UnwindOptions().preserveNullAndEmptyArrays(true)),
                // Relacionar matriz curricular do curso
                Aggregates.lookup("matriz_curricular", "curso.idCurso", "idCurso", "matrizCurricular"),
                // Agrupar disciplinas aprovadas e necessárias
                Aggregates.group("$idAluno",
                        Accumulators.addToSet("disciplinas_aprovadas", "$matrizCurricular.idDisciplina"),
                        Accumulators.first("nome", "$nome"),
                        Accumulators.first("cursoTitulo", "$curso.titulo")
                ),
                // Exibir disciplinas aprovadas e necessárias para debug
                Aggregates.project(Projections.fields(
                        Projections.include( "disciplinas_aprovadas", "nome", "cursoTitulo")
                ))
        )).forEach(doc -> System.out.println(doc.toJson())); // Para debug
    }


    public void listarChefesDepartamentos() {
        System.out.println("Lista de chefes de departamento");

        MongoCollection<Document> departamentosCollection = database.getCollection("departamentos");

        departamentosCollection.aggregate(Arrays.asList(
                Aggregates.lookup("professores", "idProfessor", "idProfessor", "professor"),
                Aggregates.unwind("$professor"),
                Aggregates.project(Projections.fields(
                        Projections.computed("chefeDepartamento", "$professor.nome"),
                        Projections.computed("tituloDepartamento", "$titulo")
                ))
        )).forEach(doc -> System.out.println(doc.toJson()));
    }

    public void listarAlunosEOrientadoresTCC() {
        System.out.println("Alunos que formaram um grupo de TCC e seu professor orientador");

        MongoCollection<Document> grupoAlunosCollection = database.getCollection("grupo_alunos");

        grupoAlunosCollection.aggregate(Arrays.asList(
                Aggregates.lookup("alunos", "idAluno", "idAluno", "aluno"),
                Aggregates.unwind("$aluno"),
                Aggregates.lookup("historico_tcc", "idGrupo", "idGrupoAluno", "tcc"),
                Aggregates.unwind("$tcc"),
                Aggregates.lookup("professores", "tcc.idProfessor", "idProfessor", "professor"),
                Aggregates.unwind("$professor"),
                Aggregates.project(Projections.fields(
                        Projections.computed("grupoTCC", "$idGrupo"),
                        Projections.computed("nomeAluno", "$aluno.nome"),
                        Projections.computed("nomeOrientador", "$professor.nome")
                ))
        )).forEach(doc -> System.out.println(doc.toJson()));
    }

}