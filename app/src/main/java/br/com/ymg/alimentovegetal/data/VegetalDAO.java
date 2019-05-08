package br.com.ymg.alimentovegetal.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.ymg.alimentovegetal.model.Beneficio;
import br.com.ymg.alimentovegetal.model.Composicao;
import br.com.ymg.alimentovegetal.model.Categoria;
import br.com.ymg.alimentovegetal.model.Vegetal;

public class VegetalDAO {

    VegetalHelper mHelper;
    private static final String TAG = "YURI";


    public VegetalDAO(Context ctx) {
        mHelper = new VegetalHelper(ctx);




    }

    public void inserirVegetal(Vegetal vegetal){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues valuesComposicao = new ContentValues();
        ContentValues valuesBeneficio = new ContentValues();
        ContentValues valuesVegetal = new ContentValues();
        ContentValues valuesComposicaoVegetal = new ContentValues();


        //Valores de vegetal
        valuesVegetal.put(VegetalHelper.COL_NOME_VEGETAL, vegetal.nome);
        valuesVegetal.put(VegetalHelper.COL_NOME_CIENTIFICO_VEGETAL, vegetal.nomeCientifico);
        valuesVegetal.put(VegetalHelper.COL_FAMILIA_VEGETAL, vegetal.familia);
        valuesVegetal.put(VegetalHelper.COL_CAPA_VEGETAL, vegetal.capa);
        valuesVegetal.put(VegetalHelper.COL_FONTE_COMPOSICAO_VEGETAL, vegetal.fonteComposicao);
        valuesVegetal.put(VegetalHelper.COL_DESC_PREPARACAO_VEGETAL, vegetal.formaPreparoComposicao);
        if(vegetal.imgDetalhe!= null){
            valuesVegetal.put(VegetalHelper.COL_IMGDETALHE_VEGETAL, vegetal.imgDetalhe);
        }
        valuesVegetal.put(VegetalHelper.COL_CATEGORIA_VEGETAL, vegetal.categoria.nome);

        long idVegetal = db.insert(VegetalHelper.TABELA_VEGETAL, null, valuesVegetal);


        //Valores de propriedade
        for (Composicao composicao: vegetal.composicaoQuimica){

            valuesComposicao.put(VegetalHelper.COL_NOME_COMPOSICAO, composicao.descricaoFormatada);
            valuesComposicao.put(VegetalHelper.COL_QTD_COMPOSICAO, composicao.qtd);
            valuesComposicao.put(VegetalHelper.COL_SUFIXO_COMPOSICAO, composicao.sufixoFormatado);
            valuesComposicao.put(VegetalHelper.COL_VEGETAL_ID_COMPOSICAO, idVegetal);
            long idComposicao = db.insert(VegetalHelper.TABELA_COMPOSICAO, null, valuesComposicao);

            valuesComposicaoVegetal.put(VegetalHelper.COL_ID_COMPOSICAO_VEGETAL, idComposicao);
            valuesComposicaoVegetal.put(VegetalHelper.COL_ID_VEGETAL_COMPOSICAO, idVegetal);

            db.insert(VegetalHelper.TABELA_COMPOSICAO_VEGETAL,null, valuesComposicaoVegetal);

            if(idComposicao == -1){
                throw new RuntimeException("Erro ao inserir composição do vegetal");
            }
        }

        // Valores de beneficio
        for(Beneficio beneficio: vegetal.beneficios){
            valuesBeneficio.put(VegetalHelper.COL_DESCRICAO_BENEFICIO, beneficio.descricao);
            valuesBeneficio.put(VegetalHelper.COL_MODO_BENEFICIO, beneficio.modoCompleto);
            valuesBeneficio.put(VegetalHelper.COL_OBS_BENEFICIO, beneficio.obsCompleta);
            valuesBeneficio.put(VegetalHelper.COL_MODO_BENEFICIO, beneficio.modoCompleto);
            valuesBeneficio.put(VegetalHelper.COL_FONTE_BENEFICIO, beneficio.fonte);
            valuesBeneficio.put(VegetalHelper.COL_VEGETAL_ID_BENEFICIO, idVegetal);
            long idBeneficio = db.insert(VegetalHelper.TABELA_BENEFICIO, null, valuesBeneficio);
            if(idBeneficio == -1){
                throw new RuntimeException("Erro ao inserir beneficio do vegetal");
            }
        }


        db.close();
    }
    public void exlcuirFruta(Vegetal vegetal){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        /*
        db.delete(FrutaHelper.TABELA_PROPRIEDADE, FrutaHelper.COL_NOME_FRUTA +"    = ?", new String[]{fruta.nome});
        db.delete(FrutaHelper.TABELA_BENEFICIO, FrutaHelper.COL_NOME_FRUTA +"    = ?", new String[]{fruta.nome});
        */

        db.delete(VegetalHelper.TABELA_VEGETAL, VegetalHelper.COL_NOME_VEGETAL +"    = ?", new String[]{vegetal.nome});
        db.close();
    }
    public int qtdBeneficios(Vegetal vegetal){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        int qtd = 0;

        Cursor cursor = db.rawQuery("SELECT _id FROM " +
                VegetalHelper.TABELA_VEGETAL + " WHERE " +
                VegetalHelper.COL_NOME_VEGETAL + " = ?", new String[]{vegetal.nome});

        int indexIdVegetal =  cursor.getColumnIndex(VegetalHelper.COL_ID_VEGETAL);

        int idVegetal = cursor.getInt(indexIdVegetal);

        Cursor cursorComposicao  = db.rawQuery(
                "SELECT * FROM " + VegetalHelper.TABELA_BENEFICIO +
                        " WHERE " + VegetalHelper.COL_VEGETAL_ID_BENEFICIO + " = ?",
                new String[]{String.valueOf(idVegetal)});

        while (cursorComposicao.moveToNext()){
            qtd+=1;
        }
        return qtd;
    }


    public List<Vegetal> listarVegetais(){

        List<Vegetal> listaVegetal = new ArrayList<>();
        List<Composicao> listaComposicao = new ArrayList<>();
        List<Beneficio> listaBeneficio = new ArrayList<>();
        List<Integer> listaIdComposicao = new ArrayList<>();

        SQLiteDatabase db = mHelper.getReadableDatabase();


        Cursor cursor = db.rawQuery(
                "SELECT DISTINCT * FROM " + VegetalHelper.TABELA_VEGETAL +
                        " ORDER BY " + VegetalHelper.COL_NOME_VEGETAL,
                null);


        int indexIdVegetal =  cursor.getColumnIndex(VegetalHelper.COL_ID_VEGETAL);
        int indexNome = cursor.getColumnIndex(VegetalHelper.COL_NOME_VEGETAL);
        int indexNomeCientifico = cursor.getColumnIndex(VegetalHelper.COL_NOME_CIENTIFICO_VEGETAL);
        int indexFamilia = cursor.getColumnIndex(VegetalHelper.COL_FAMILIA_VEGETAL);
        int indexCategoria = cursor.getColumnIndex(VegetalHelper.COL_CATEGORIA_VEGETAL);
        int indexCapa = cursor.getColumnIndex(VegetalHelper.COL_CAPA_VEGETAL);
        int indexFonteComposicao = cursor.getColumnIndex(VegetalHelper.COL_FONTE_COMPOSICAO_VEGETAL);
        int indexDescricaoPreparacao = cursor.getColumnIndex(VegetalHelper.COL_DESC_PREPARACAO_VEGETAL);
        int indexImgDetalhe = cursor.getColumnIndex(VegetalHelper.COL_IMGDETALHE_VEGETAL);


        while (cursor.moveToNext()) {
            int idVegetal = cursor.getInt(indexIdVegetal);
            String nome = cursor.getString(indexNome);
            String nomeCientifico = cursor.getString(indexNomeCientifico);
            String familia = cursor.getString(indexFamilia);
            String capa = cursor.getString(indexCapa);
            String imgDetalhe = cursor.getString(indexImgDetalhe);
            Categoria categoria = new Categoria(cursor.getString(indexCategoria));

            String cat = cursor.getString(indexCategoria);
            String fonteComposicao = cursor.getString(indexFonteComposicao);
            String descricaoPreparacao = cursor.getString(indexDescricaoPreparacao);

            Cursor cursorComposicaoVegetal  = db.rawQuery(
                    "SELECT DISTINCT * FROM " + VegetalHelper.TABELA_COMPOSICAO_VEGETAL +
                            " WHERE " + VegetalHelper.COL_ID_VEGETAL_COMPOSICAO + " = ?",
                    new String[]{String.valueOf(idVegetal)});

            int indexIdComposicao = cursorComposicaoVegetal.getColumnIndex(VegetalHelper.COL_ID_COMPOSICAO_VEGETAL);

            while(cursorComposicaoVegetal.moveToNext()){
                int idComposicao = cursorComposicaoVegetal.getInt(indexIdComposicao);
                Cursor cursorComposicao  = db.rawQuery(
                        "SELECT DISTINCT * FROM " + VegetalHelper.TABELA_COMPOSICAO +
                                " WHERE " + VegetalHelper.COL_ID_COMPOSICAO + " = ?",
                        new String[]{String.valueOf(idComposicao)});

                int indexNomeComposicao = cursorComposicao.getColumnIndex(VegetalHelper.COL_NOME_COMPOSICAO);
                int indexQtdComposicao = cursorComposicao.getColumnIndex(VegetalHelper.COL_QTD_COMPOSICAO);
                int indexSufixoComposicao = cursorComposicao.getColumnIndex(VegetalHelper.COL_SUFIXO_COMPOSICAO);
                int indexIdComposicaoVegetal = cursorComposicao.getColumnIndex(VegetalHelper.COL_VEGETAL_ID_COMPOSICAO);

                while(cursorComposicao.moveToNext()){
                        int idComposicaoVegetal = cursorComposicao.getInt(indexIdComposicaoVegetal);
                        String nomePropriedade = cursorComposicao.getString(indexNomeComposicao);
                        String qtdPropriedade = cursorComposicao.getString(indexQtdComposicao);
                        String sufixoPropriedade = cursorComposicao.getString(indexSufixoComposicao);


                        if(idVegetal == idComposicaoVegetal){
                            Composicao composicao = new Composicao(nomePropriedade, qtdPropriedade,sufixoPropriedade);
                            listaComposicao.add(composicao);

                            for (Composicao composicao1:
                                 listaComposicao) {
                            }
                        }



                }
                cursorComposicao.close();

            }


            Cursor cursorBeneficio = db.rawQuery(
                    "SELECT * FROM " + VegetalHelper.TABELA_BENEFICIO +
                            " WHERE " + VegetalHelper.COL_VEGETAL_ID_BENEFICIO + " = ?",
                    new String[]{String.valueOf(idVegetal)});

            int indexDescricaoBeneficio = cursorBeneficio.getColumnIndex(VegetalHelper.COL_DESCRICAO_BENEFICIO);
            int indexModoBeneficio = cursorBeneficio.getColumnIndex(VegetalHelper.COL_MODO_BENEFICIO);
            int indexFonteBeneficio = cursorBeneficio.getColumnIndex(VegetalHelper.COL_FONTE_BENEFICIO);
            int indexObsBeneficio = cursorBeneficio.getColumnIndex(VegetalHelper.COL_OBS_BENEFICIO);

            while(cursorBeneficio.moveToNext()){
                String descricaoBeneficio = cursorBeneficio.getString(indexDescricaoBeneficio);
                String modoBeneficio = cursorBeneficio.getString(indexModoBeneficio);
                String fonteBeneficio = cursorBeneficio.getString(indexFonteBeneficio);
                String obsBeneficio = cursorBeneficio.getString(indexObsBeneficio);
                Beneficio beneficio = new Beneficio(descricaoBeneficio,modoBeneficio, fonteBeneficio,obsBeneficio);
                listaBeneficio.add(beneficio);

            }

            Vegetal vegetal = new Vegetal(capa,imgDetalhe,nome,familia, nomeCientifico, listaComposicao,listaBeneficio, categoria,fonteComposicao,descricaoPreparacao);
            listaVegetal.add(vegetal);


            for (Vegetal vegetal1:
                 listaVegetal) {
                for (Composicao composicao:
                     vegetal1.composicaoQuimica) {
                }
            }


            cursorComposicaoVegetal.close();
            cursorBeneficio.close();

        }
        cursor.close();

        db.close();


        return listaVegetal;
    }

    public boolean isFavorito(Vegetal vegetal){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM " +
                VegetalHelper.TABELA_VEGETAL + " WHERE " +
                VegetalHelper.COL_NOME_VEGETAL + " = ?", new String[]{vegetal.nome});
        boolean existe = cursor.getCount()>0;
        cursor.close();
        db.close();
        return existe;
    }

    private Vegetal fillVegetal(Cursor cursor){

        SQLiteDatabase db = mHelper.getReadableDatabase();
        int indexIdVegetal =  cursor.getColumnIndex(VegetalHelper.COL_ID_VEGETAL);
        int indexNome = cursor.getColumnIndex(VegetalHelper.COL_NOME_VEGETAL);
        int indexNomeCientifico = cursor.getColumnIndex(VegetalHelper.COL_NOME_CIENTIFICO_VEGETAL);
        int indexFamilia = cursor.getColumnIndex(VegetalHelper.COL_FAMILIA_VEGETAL);
        int indexCategoria = cursor.getColumnIndex(VegetalHelper.COL_CATEGORIA_VEGETAL);
        int indexCapa = cursor.getColumnIndex(VegetalHelper.COL_CAPA_VEGETAL);
        int indexFonteComposicao = cursor.getColumnIndex(VegetalHelper.COL_FONTE_COMPOSICAO_VEGETAL);
        int indexDescricaoPreparacao = cursor.getColumnIndex(VegetalHelper.COL_DESC_PREPARACAO_VEGETAL);
        int indexImgDetalhe = cursor.getColumnIndex(VegetalHelper.COL_IMGDETALHE_VEGETAL);


            int idVegetal = cursor.getInt(indexIdVegetal);
            String nome = cursor.getString(indexNome);
            String nomeCientifico = cursor.getString(indexNomeCientifico);
            String familia = cursor.getString(indexFamilia);
            String capa = cursor.getString(indexCapa);
            String imgDetalhe = cursor.getString(indexImgDetalhe);
            Categoria categoria = new Categoria(cursor.getString(indexCategoria));

            String fonteComposicao = cursor.getString(indexFonteComposicao);
            String descricaoPreparacao = cursor.getString(indexDescricaoPreparacao);
            Vegetal vegetal = new Vegetal(capa, imgDetalhe, nome, familia, nomeCientifico, categoria, fonteComposicao, descricaoPreparacao);

            Cursor cursorComposicao  = db.rawQuery(
                    "SELECT DISTINCT * FROM " + VegetalHelper.TABELA_COMPOSICAO +
                            " WHERE " + VegetalHelper.COL_VEGETAL_ID_COMPOSICAO + " = ?",
                    new String[]{String.valueOf(idVegetal)});

            cursorComposicao.moveToFirst();

            while(!cursorComposicao.isAfterLast()){
                vegetal.composicaoQuimica.add(fillComposicao(cursorComposicao));
                cursorComposicao.moveToNext();
            }

             Cursor cursorBeneficio = db.rawQuery(
                "SELECT * FROM " + VegetalHelper.TABELA_BENEFICIO +
                        " WHERE " + VegetalHelper.COL_VEGETAL_ID_BENEFICIO + " = ?",
                new String[]{String.valueOf(idVegetal)});

            cursorBeneficio.moveToFirst();
            while(!cursorBeneficio.isAfterLast()){
                vegetal.beneficios.add(fillBeneficio(cursorBeneficio));
                cursorBeneficio.moveToNext();
            }

        db.close();
        return vegetal;



    }

    private Composicao fillComposicao(Cursor cursor){
        int indexNomeComposicao = cursor.getColumnIndex(VegetalHelper.COL_NOME_COMPOSICAO);
        int indexQtdComposicao = cursor.getColumnIndex(VegetalHelper.COL_QTD_COMPOSICAO);
        int indexSufixoComposicao = cursor.getColumnIndex(VegetalHelper.COL_SUFIXO_COMPOSICAO);
        int indexIdComposicaoVegetal = cursor.getColumnIndex(VegetalHelper.COL_VEGETAL_ID_COMPOSICAO);

        int idComposicaoVegetal = cursor.getInt(indexIdComposicaoVegetal);
        String nomePropriedade = cursor.getString(indexNomeComposicao);
        String qtdPropriedade = cursor.getString(indexQtdComposicao);
        String sufixoPropriedade = cursor.getString(indexSufixoComposicao);

        return new Composicao(nomePropriedade,qtdPropriedade,sufixoPropriedade);
    }

    private Beneficio fillBeneficio(Cursor cursor){
        int indexDescricaoBeneficio = cursor.getColumnIndex(VegetalHelper.COL_DESCRICAO_BENEFICIO);
        int indexModoBeneficio = cursor.getColumnIndex(VegetalHelper.COL_MODO_BENEFICIO);
        int indexFonteBeneficio = cursor.getColumnIndex(VegetalHelper.COL_FONTE_BENEFICIO);
        int indexObsBeneficio = cursor.getColumnIndex(VegetalHelper.COL_OBS_BENEFICIO);

        String fonteBeneficio = cursor.getString(indexFonteBeneficio);
        String descricaoBeneficio = cursor.getString(indexDescricaoBeneficio);
        String modoBeneficio = cursor.getString(indexModoBeneficio);
        String obsBeneficio = cursor.getString(indexObsBeneficio);
        return new Beneficio(descricaoBeneficio,modoBeneficio, fonteBeneficio, obsBeneficio);

    }

    public List<Vegetal> listaVegetal(){

        SQLiteDatabase db = mHelper.getReadableDatabase();

        List<Vegetal> lista = new ArrayList<Vegetal>();

        Cursor cursor = db.rawQuery(
                "SELECT DISTINCT * FROM " + VegetalHelper.TABELA_VEGETAL +
                        " ORDER BY " + VegetalHelper.COL_NOME_VEGETAL,
                null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Vegetal vegetal = fillVegetal(cursor);
            lista.add(vegetal);
            cursor.moveToNext();
        }

        db.close();
        return lista;

    }

}
