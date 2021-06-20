package br.com.noname.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {

    private enum TipoComando{
        ZERAR, NUM, DIV, SUB, SOMA, MULT, IGUAL, VIRGULA, MAISMENOS,PERCENTUAL
    }

    private static final Memoria mem = new Memoria();

    private TipoComando ultimaOperacao=null;
    private boolean substituir = false;
    private String textoAtual="";
    private String textoBuffer="";

    private final List<MemoriaObservador> obs = new ArrayList<>();

    public Memoria() {
    }

    public static Memoria getMem() {
        return mem;
    }

    public void adicionarObservador(MemoriaObservador o){
        obs.add(o);
    }

    public String getTextoAtual() {
        return textoAtual.isEmpty()?"0":textoAtual;
    }

    public void processarComando(String valor){
        TipoComando tipoComando = detectarTipoComando(valor);
        if(tipoComando==null){
            return;
        }else if(tipoComando == TipoComando.ZERAR) {
            textoAtual = "";
            textoBuffer = "";
            substituir = false;
            ultimaOperacao = null;
        }else if(tipoComando==TipoComando.MAISMENOS && textoAtual.contains("-")){
            textoAtual=textoAtual.substring(1);
        }
        else if(tipoComando==TipoComando.MAISMENOS && !textoAtual.contains("-")){
                textoAtual="-"+textoAtual;
            }else if(tipoComando==TipoComando.PERCENTUAL && textoAtual.contains("%")){
            textoAtual=textoAtual.substring(1);
        }else if(tipoComando==TipoComando.PERCENTUAL && !textoAtual.contains("%")){
            textoAtual=textoAtual+"%";
        }
        else if(tipoComando== TipoComando.NUM || tipoComando == TipoComando.VIRGULA){
            textoAtual= substituir?valor: textoAtual+valor;
            substituir=false;
        }else {
            substituir = true;
            textoAtual = obterResultadoOperacao();
            textoBuffer = textoAtual;
            ultimaOperacao = tipoComando;
        }
        obs.forEach(o->o.valorAlterado(getTextoAtual()));

    }

    private String obterResultadoOperacao() {
        if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL){
            return textoAtual;
        }
        double numeroBuffer = Double.parseDouble(textoBuffer.replace(",","."));
        double numeroAtual = Double.parseDouble(textoAtual.replace(",","."));
        double resultado = 0.;
        if(ultimaOperacao == TipoComando.SOMA){
            resultado = numeroBuffer + numeroAtual;
        }else if(ultimaOperacao== TipoComando.SUB){
            resultado = numeroBuffer - numeroAtual;
        }else if(ultimaOperacao==TipoComando.DIV){
            resultado = numeroBuffer/numeroAtual;
        }else if(ultimaOperacao==TipoComando.MULT) {
            resultado = numeroBuffer * numeroAtual;
        }
        String resultadoString =  Double.toString(resultado).replace(",",".");
        boolean inteiro = resultadoString.endsWith(",0");
        return inteiro ? resultadoString.replace(",0",""): resultadoString;
    }

    private TipoComando detectarTipoComando(String valor) {
        if(textoAtual.isEmpty()&&valor=="0"){
            return null;
        }
        try{
            Integer.parseInt(valor);
            return TipoComando.NUM;
        }catch(NumberFormatException e){
            if("AC".equals(valor)){
                return TipoComando.ZERAR;
            }else if("/".equals(valor)){
                return TipoComando.DIV;
            }else if("*".equals(valor)){
                return TipoComando.MULT;
            }else if("+".equals(valor)){
                return TipoComando.SOMA;
            }else if("-".equals(valor)){
                return TipoComando.SUB;
            }else if("=".equals(valor)){
                return TipoComando.IGUAL;
            }else if(",".equals(valor) && !textoAtual.contains(",") && !textoAtual.isEmpty()){
                return TipoComando.VIRGULA;
            }else if("±".equals(valor)){
                return TipoComando.MAISMENOS;
            }else if("%".equals(valor)){
                return TipoComando.PERCENTUAL;
            }
        }
        return null;
    }

}

