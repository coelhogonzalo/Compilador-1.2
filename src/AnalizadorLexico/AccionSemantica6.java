package AnalizadorLexico;//asd

//FLOAT==260
public class AccionSemantica6 implements AccionSemantica {
    //ES PARA FLOAT
    //CHEQUEA RANGO CONSTANTE Y AGREGA A TABLA DE SIMBOLOS, SI SE VA DE RANGO REEMPLAZA CON EL MAYOR DEL RANGO
    //PIDE DEVOLVER EL PAR <ID,PTR>

	public Token ejecutar(StringBuilder buffer, char c) {
    	Token unToken=null;
        if (buffer.toString().contains("F"))
            buffer.replace(buffer.indexOf("F"), buffer.indexOf("F") + 1, "E");
        Double flotante = Double.parseDouble(buffer.toString());
        if (((flotante <= 3.40282347E38) && (flotante >= 1.17549435E-38)) || (flotante == 0)){
        	String lexema=buffer.toString();
        	unToken=new Token(lexema, AnalizadorLexico.TOKEN_FLOAT,"single");
        	unToken.uso="constante";
            AnalizadorLexico.tablaSimbolos.put(lexema, unToken);
        }
        else {
            Error e = new Error("WARNING", buffer.toString() + ": valor fuera de rango", AnalizadorLexico.cantLN);
            buffer.delete(0, buffer.length());
            buffer.append("3.40282346E38");
            String lexema=buffer.toString();
            unToken=new Token(lexema, AnalizadorLexico.TOKEN_FLOAT,"single");
            AnalizadorLexico.errores.add(e);
            unToken.uso="constante";
            AnalizadorLexico.tablaSimbolos.put(lexema, unToken);
        }
        return unToken;
    }


}