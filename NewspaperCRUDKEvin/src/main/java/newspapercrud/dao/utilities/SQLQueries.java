package newspapercrud.dao.utilities;

public class SQLQueries {
    public static final String GET_ALL_ARTICLES = "SELECT a.id, a.name_article, a.id_newspaper, t.id AS type_id, t.description AS type_description FROM article a INNER JOIN type t ON a.id_type = t.id;";
    public static final String GET_ALL_TYPES = "select * from type";
    public static final String GET_ALL_READERS = "select * from reader where id > 0";
    public static final String GET_ALL_READERS_BY_ARTICLEID = "select * from reader where id in (select id_reader from readarticle where id_article = ?)";
    public static final String GET_READER_BY_ID = "select * from reader where id = ?";
    public static final String INSERT_READER = "insert into reader (name_reader,birth_reader) values (?,?)";
    public static final String GET_ALL_NEWSPAPERS = "select * from newspaper";
    public static final String GET_ALL_NEWSPAPERS_BY_READERID= "select * from newspaper where id in (select id_newspaper from subscribe where id_reader = ?)";
    public static final String GET_TYPE_BY_ID = "select * from type where id = ?";
    public static final String GET_NEWSPAPER_BY_ID = "select * from newspaper where id = ?";
    public static final String GET_READARTICLE = "select * from readarticle where id_article = ? and id_reader = ?";
    public static final String GET_READARTICLES_BY_ARTICLEID= "select 0, id_article, 0, avg(rating) from readarticle group by id_article";
    public static final String GET_CREDENTIAL = "select * from login where userlog = ?";
    public static final String INSERT_ARTICLE = "insert into article (name_article,id_type,id_newspaper) values (?,?,?)";
    public static final String UPDATE_ARTICLE = "update article set name_article = ?, id_type = ?, id_newspaper = ? where id = ?";
    public static final String DELETE_ARTICLE = "delete from article where id = ?";
    public static final String DELETE_ARTICLE_READARTICLES = "delete from readarticle where id_article = ?";
    public static final String INSERT_READARTICLE = "insert into readarticle (id_article,id_reader,rating) values (?,?,?)";
    public static final String DELETE_READER = "delete from reader where id = ?";
    public static final String DELETE_READER_READARTICLES = "delete from readarticle where id_reader = ?";
    public static final String DELETE_READER_SUBSCRIPTIONS = "delete from subscribe where id_reader = ?";
    public static final String DELETE_READER_LOGIN = "delete from login where id_reader = ?";
    public static final String DELETE_READARTICLE = "delete from readarticle where id_reader = ? and id_article = ?";
    public static final String UPDATE_READARTICLE = "update readarticle set rating = ? where id_article = ? and id_reader = ?";
    public static final String INSERT_CREDENTIAL = "insert into login (userlog,password,id_reader) values (?,?,?)";


    private SQLQueries() {
    }
}
