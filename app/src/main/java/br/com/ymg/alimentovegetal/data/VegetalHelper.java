package br.com.ymg.alimentovegetal.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VegetalHelper extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "alimentovegetal";
    public static final int VERSAO_BANCO = 1;
    public static final String TABELA_VEGETAL = "vegetal";
    public static final String COL_ID_VEGETAL = "_id";
    public static final String COL_CAPA_VEGETAL = "capa";
    public static final String COL_IMGDETALHE_VEGETAL = "imgDetalhe";
    public static final String COL_NOME_VEGETAL = "nome";
    public static final String COL_NOME_CIENTIFICO_VEGETAL = "nomeCientifico";
    public static final String COL_CATEGORIA_VEGETAL = "categoria";
    public static final String COL_FAMILIA_VEGETAL = "familia";
    public static final String TABELA_COMPOSICAO = "composicao";
    public static final String TABELA_COMPOSICAO_VEGETAL = "composicao_vegetal";
    public static final String COL_ID_COMPOSICAO = "_id";
    public static final String COL_NOME_COMPOSICAO = "nome";
    public static final String COL_QTD_COMPOSICAO = "qtd";
    public static final String COL_SUFIXO_COMPOSICAO = "sufixo";
    public static final String COL_FONTE_COMPOSICAO_VEGETAL = "fonte";
    public static final String COL_DESC_PREPARACAO_VEGETAL = "descPreparacao";
    public static final String COL_VEGETAL_ID_COMPOSICAO = "vegetal_id";
    public static final String TABELA_BENEFICIO = "beneficio";
    public static final String COL_ID_BENEFICIO = "id";
    public static final String COL_DESCRICAO_BENEFICIO = "descricao";
    public static final String COL_MODO_BENEFICIO = "modo";
    public static final String COL_OBS_BENEFICIO = "obs";
    public static final String COL_FONTE_BENEFICIO = "fonte";

    public static final String COL_VEGETAL_ID_BENEFICIO = "VEGETAL_id";

    public static final String COL_ID_COMPOSICAO_VEGETAL = "composicao_id";
    public static final String COL_ID_VEGETAL_COMPOSICAO = "vegetal_id";

    public VegetalHelper(Context ctx){
        super(ctx, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABELA_VEGETAL +"("+
                COL_ID_VEGETAL               + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_CAPA_VEGETAL             + " TEXT NOT NULL," +
                COL_IMGDETALHE_VEGETAL             + " TEXT," +
                COL_NOME_VEGETAL             + " TEXT NOT NULL, " +
                COL_NOME_CIENTIFICO_VEGETAL  + " TEXT NOT NULL, " +
                COL_CATEGORIA_VEGETAL  + " TEXT NOT NULL, " +
                COL_FONTE_COMPOSICAO_VEGETAL +" text, "+
                COL_DESC_PREPARACAO_VEGETAL +" text, "+
                COL_FAMILIA_VEGETAL          + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE "+ TABELA_COMPOSICAO + "("+
                COL_ID_COMPOSICAO +" integer primary key autoincrement,"+
                COL_NOME_COMPOSICAO + " text not null,"+
                COL_QTD_COMPOSICAO +" text not null, "+
                COL_SUFIXO_COMPOSICAO +" text not null, "+
                COL_VEGETAL_ID_COMPOSICAO +" integer not null)");

        db.execSQL("create table " + TABELA_BENEFICIO + "(" +
                COL_ID_BENEFICIO + " integer primary key autoincrement, " +
                COL_DESCRICAO_BENEFICIO + " text not null, " +
                COL_MODO_BENEFICIO + " text , " +
                COL_FONTE_BENEFICIO +" text , " +
                COL_OBS_BENEFICIO +" text , " +
                COL_VEGETAL_ID_BENEFICIO + " integer not null)");

        db.execSQL("create table " + TABELA_COMPOSICAO_VEGETAL + "(" +
                COL_ID_COMPOSICAO_VEGETAL + " integer, " +
                COL_ID_VEGETAL_COMPOSICAO + " integer ) ");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
